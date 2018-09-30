package uis.Communication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

import uis.Manage.AppManager;
import uis.festival.R;

public class SttFragment extends Fragment {

    /* Adapter – 기존의 ListView에서 사용하는 Adapter와 같은 개념으로 데이터와 아이템에 대한 View생성
    ViewHolder – 재활용 View에 대한 모든 서브 뷰를 보유
    LayoutManager – 아이템의 항목을 배치
    ItemDecoration – 아이템 항목에서 서브뷰에 대한 처리
    ItemAnimation – 아이템 항목이 추가, 제거되거나 정렬될때 애니메이션 처리 */

    // 아이템을 보여줄 recyclerview
    private RecyclerView mRecyclerView;
    // 아이템에 대한 view와 데이터 생성
    private ResultAdapter mAdapter;
    // 아이템의 항목을 linear로 배치
    private RecyclerView.LayoutManager mLayoutManager;

    // dateset
    private static ArrayList<String> sttData = new ArrayList<String>();
    private static ArrayList<String> translateData = new ArrayList<String>();

    // view reference
    private ImageView exchangeBtn;
    private Spinner startSpinner, endSpinner;
    // 번역할 언어와 번역될 언어의 spinner 내의 위치
    private int startPosition = AppManager.getInstance().getLanguage();
    private int endPosition;
    private FloatingActionButton fab;
    private boolean isListening = false;

    // speech api reference
    public SpeechRecognizer mRecognizer;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    // 인터넷 환경을 탐색하기 위한 맴버변수
    public static final String WIFI_STATE = "WIFI";
    public static final String MOBILE_STATE = "MOBILE";
    public static final String NONE_STATE = "NONE";
    private boolean isOKinMobile = false;   // 모바일 환경에서 stt 사용 여부

    // AlertDialog Text
    private String notice = AppManager.getInstance().getAppNotice().getNotice();
    private String noService = AppManager.getInstance().getAppNotice().getNoService();
    private String lte = AppManager.getInstance().getAppNotice().getLte();
    private String none = AppManager.getInstance().getAppNotice().getNone();
    private String delete = AppManager.getInstance().getAppNotice().getDelete();
    private String deleteNotice = AppManager.getInstance().getAppNotice().getDeleteNotice();
    private String yes = AppManager.getInstance().getAppNotice().getYes();
    private String no = AppManager.getInstance().getAppNotice().getNo();
    private String fine = AppManager.getInstance().getAppNotice().getFine();

    private FabListener fabListener;
    // 생성자
    public SttFragment(){
        AppManager.getInstance().setSttData(sttData);
        AppManager.getInstance().setTranslateData(translateData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =
                inflater.inflate(R.layout.fragment_stt, container, false );


        // 번역할 언어 선택 spinner
        fabListener = new FabListener();
        startSpinner = (Spinner) view.findViewById(R.id.startSpinner);
        startSpinner.setSelection(startPosition);// 초기값 설정
        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 번역될 언어 선택 spinner
        if(startPosition == 0){ // 한국어 설정
            endPosition = 1;
        }else{  // 외국어 설정
            endPosition = 0;
        }
        endSpinner = (Spinner) view.findViewById(R.id.endSpinner);
        endSpinner.setSelection(endPosition); // 초기값 설정
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 버튼 클릭 시 startPosition, endPosition 교환
        exchangeBtn = (ImageView) view.findViewById(R.id.exchange);
        exchangeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                anim(0);

                int temp = startPosition;
                startPosition=endPosition;
                endPosition=temp;

                startSpinner.setSelection(startPosition);
                endSpinner.setSelection(endPosition);

                // 채팅창 UI 변경
                //turn *= -1;

            }
        });



        // stt 녹음을 시작하는 floatingActionButton
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(fabListener);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new ResultAdapter(sttData, translateData);
        mAdapter.setHasStableIds(true); // 깜빡임 방지
        mRecyclerView.setAdapter(mAdapter);
        AppManager.getInstance().setmAdapter(mAdapter);

        // add itemTouchListener on recyclerView
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemDoubleClick(View view, int position) {
                        // 더블 클릭하면 텍스트 내용 수정
                    }

                    @Override
                    public void onLongItemClick(View view, final int position) {
                        // 길게 누르면 해당 내용 삭제

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        // builder에게 옵션 주기
                        builder.setTitle(delete);
                        builder.setMessage(deleteNotice);

                        // 삭제
                        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAdapter.remove(position);
                            }
                        });

                        // 취소
                        builder.setNegativeButton(no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.show();
                    }
                })
        );

        // floating action button scroll 시 사라지게 만듦
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy < 0){
                    fab.hide();
                } else{
                    fab.show();
                }
            }

        });

        // AppManager에 RecyclerView 전달
        AppManager.getInstance().setmRecyclerView(mRecyclerView);

        return view;
    }
    private class FabListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (getWhatKindOfNework(getContext()) == WIFI_STATE || isOKinMobile) {
                startService();

            } else if (getWhatKindOfNework(getContext()) == MOBILE_STATE) {
                // 알림창 속성 설정
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(notice)
                        .setMessage(lte)
                        .setPositiveButton(fine, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isOKinMobile = true;
                            }
                        })
                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isOKinMobile = false;
                            }
                        }).show();
            } else if(getWhatKindOfNework(getContext()) == NONE_STATE){
                // 알림창 속성 설정
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(notice)
                        .setMessage(none)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();

            }
        }


    }
    private void startListening(Intent intent){


        mRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        mRecognizer.setRecognitionListener(listener);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            mRecognizer.startListening(intent);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.RECORD_AUDIO)) {
            //showPermissionMessageDialog();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }

    }

    // 음성 인식 리스너
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            changeFabImg(0);// 녹음 진행 중인 이미지로 변경
            mAdapter.addString();
            //Toast.makeText(getContext(),"OnReadyForSpeech", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {

            //Toast.makeText(getContext(),"OnBeginningOfSpeech", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d("onBufferReceived", "true");
        }

        @Override
        public void onEndOfSpeech() {
            changeFabImg(1); // 마이크 이미지로 변경
            isListening = false;    // 녹음종료
            //Toast.makeText(getContext(),"OnEndOfSpeech", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int error) {
            changeFabImg(1); // 마이크 이미지로 변경
            // 아무것도 인식되지 않으면 삭제
            int position = sttData.size() -1;
            mAdapter.errorRemove(position);
            isListening = false;    // 녹음 종료
            fab.setOnClickListener(fabListener);
            //Toast.makeText(getContext(),"OnError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);

            addItem(rs[0]);

            // Automatically scroll to bottom of RecyclerView
            int position = sttData.size() + translateData.size() -1;
            mRecyclerView.scrollToPosition(position);

            fab.setOnClickListener(fabListener);
            //Toast.makeText(getContext(),"OnResults", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mPartialResult = partialResults.getStringArrayList(key);
            String[] rs = new String[mPartialResult.size()];
            mPartialResult.toArray(rs);

            addPartialItem(rs[0]);

            // Automatically scroll to bottom of RecyclerView
            int position = sttData.size() + translateData.size() -1;
            mRecyclerView.scrollToPosition(position);

            //Toast.makeText(getContext(),"OnPartialResults ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };
    // Stt service 실행
    private void startService(){
        if (!isListening) {
            // intent 정보 넣어줌
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

            switch (startPosition) {
                case 0: // 한국어
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getString(R.string.Korean));
                    break;
                case 1: // 영어
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getString(R.string.English));
                    break;
                case 2: // 일본어
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getString(R.string.Japanese));
                    break;
                case 3: // 중국어
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getString(R.string.Chinese));
                    break;
            }

            if (endPosition != 0 && (startPosition == 1 || startPosition == 2 || startPosition == 3)) {
                // 번역 대상 언어가 외국어 일때 번역 언어가 한국어가 아닌 경우 알림창을 띄움
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // 알림창 속성 설정
                builder.setTitle(notice)
                        .setMessage(noService)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();

            } else {
                startListening(intent);
                isListening = true; // 녹음시작
                fab.setOnClickListener(null);
            }
        } else {
            mRecognizer.stopListening();
            isListening = false;    // 녹음 종료
        }

    }

    // 현재 인터넷 연결상태 확인
    private static String getWhatKindOfNework(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                return WIFI_STATE;
            }else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                return MOBILE_STATE;
            }
        }
        return NONE_STATE;
    }

    // fab 버튼의 애니메이션
    private void anim(int type){
        Animation anim;
        switch (type){
            case 0: // 180도 회전
                anim = AnimationUtils.loadAnimation(
                        getContext(),
                        R.anim.half_rotate_anim);
                exchangeBtn.startAnimation(anim);
                break;
            case 1: // 360도 무한 회전
                anim = AnimationUtils.loadAnimation(
                        getContext(),
                        R.anim.rotate_anim);
                fab.startAnimation(anim);
                break;
            case 2: // 애니메이션 멈춤
                fab.clearAnimation();
                break;
        }
    }

    // fab 버튼 이미지 변경 및 애니메이션 적용
    private void changeFabImg(int type){
        switch(type){
            case 0: // 녹음 진행중 이미지
                fab.setImageResource(R.drawable.ic_loading);
                anim(1);
                break;
            case 1: // 마이크 이미지
                fab.setImageResource(R.drawable.ic_mic);
                anim(2);
                break;
        }
    }

    private String getLanguage(int position){
        switch (position){
            case 0:
                return getString(R.string.Korean);
            case 1:
                return getString(R.string.English);
            case 2:
                return getString(R.string.Japanese);
            case 3:
                return getString(R.string.Chinese_translate);
        }
        return "except";
    }

    private void addItem(String string){

        // 최종 결과를 저장하고 번역
        int position = sttData.size()-1;
        String source = getLanguage(startPosition);
        String target = getLanguage(endPosition);

        // AsyncTask 이용 요청하고 결과 반환받음
        TranslateTask asyncTask = new TranslateTask(source, target);
        asyncTask.execute(string);  // NaverTranslateTask에서 결과 적용

        mAdapter.addResult(position,string);

    }

    private void addPartialItem(String string){

        int position = sttData.size()-1;

        mAdapter.addResult(position, string);
    }


}
