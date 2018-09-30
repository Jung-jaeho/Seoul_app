package uis.festival;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionException;

import uis.Communication.TranslateDialog;
import uis.DataBase.BitmapDownloaderTask;
import uis.DataBase.FestivalInformationVO;
import uis.DataBase.Model;
import uis.Manage.AppManager;

public class Festival extends Activity {


    private ArrayList<FestivalInformationVO> data;

    private ListView listView;
    private ImageView image;
    private TextView title;
    private TextView Context;
    private TextView category;
    private Button favoriteBtn;
    private Button non_favoriteBtn;
    private Button back_btn;
    BitmapDownloaderTask task;
    private mAdapter adapter;

    ArrayList<FestivalInformationVO> favorites;

    private String categoryName;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        task=null;
    }


    public Festival() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        favorites = Model.getInstance(getApplicationContext()).select();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festival);

        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        data = (ArrayList<FestivalInformationVO>) intent.getExtras().get("data");
        categoryName = (String) intent.getExtras().get("category");
        back_btn = (Button)findViewById(R.id.festival_backbtn);
        category = (TextView) findViewById(R.id.category);
        category.setText("# "+categoryName);

        adapter = new mAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
        // 진 추가
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 번역 창 띄움
                TranslateDialog translateDialog = new TranslateDialog(Festival.this, data.get(position).getTitle());
                translateDialog.show();
                Window window = translateDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.TOP);
                return true;
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class mAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        View view;
        FavoritesAdapater favoritesAdapater;
        private TextView gcode_And_IsFree;

        LayoutInflater inflater;
        public mAdapter() {

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //postion = ListView의 위치      /   첫번째면 position = 0
            favorites = Model.getInstance(getApplicationContext()).select();
            if(convertView==null) {
                inflater = (LayoutInflater) getApplicationContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            }
            if(!data.isEmpty()) {
                convertView = inflater.inflate(R.layout.festival_view, parent, false);

                gcode_And_IsFree = (TextView)convertView.findViewById(R.id.gcode_and_isfree);
                image = (ImageView) convertView.findViewById(R.id.img);
                title = (TextView) convertView.findViewById(R.id.title);
                Context = (TextView) convertView.findViewById(R.id.context);
                favoriteBtn = (Button) convertView.findViewById(R.id.favorite_btn);
                favoriteBtn.setFocusable(false);
                non_favoriteBtn = (Button) convertView.findViewById(R.id.non_favorite_btn);
                non_favoriteBtn.setFocusable(false);
                // 아이템 내 각 위젯에 데이터 반영

                for (int i = 0; i < favorites.size(); i++) {
                    if (favorites.get(i).getCultCode().equals(data.get(position).getCultCode())) {
                        non_favoriteBtn.setVisibility(View.INVISIBLE);
                        favoriteBtn.setVisibility(View.VISIBLE);
                    }
                }
                //지역과 유/무료 여부 넣기
                String gcode = data.get(position).getGCode();

                if(!gcode.equals("")||!gcode.equals(" ")||!gcode.equals("\n")) {
                    gcode_And_IsFree.setText(gcode + " | ");
                }
                if(data.get(position).getIs_Free().equals("1")){
                    gcode_And_IsFree.append("무료");
                }
                else{
                    gcode_And_IsFree.setText("유료");
                }

                favoritesAdapater = new FavoritesAdapater(position);
                favoriteBtn.setOnClickListener(favoritesAdapater);
                non_favoriteBtn.setOnClickListener(favoritesAdapater);

                title.setText(data.get(position).getTitle());
                Context.setText(data.get(position).getStart_Date() + " ~ " + data.get(position).getEnd_Date());

                task = new BitmapDownloaderTask(image);

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data.get(position).getMain_Img());

                    } else {
                        task.execute(data.get(position).getMain_Img());
                    }
                } catch (RejectedExecutionException e) {
                    try {
                        Thread.sleep(1); //예외 발생 시 잠시 대기함으로 ThreadQueue에 있는 작업들이 수행될 시간을 벌어준다.
//                    task.wait();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                listView.setEnabled(true);
            }
            else{
                convertView = inflater.inflate(R.layout.no_result, parent, false);
                listView.setOnItemClickListener(null);
                listView.setEnabled(false);
            }

            return convertView;
        }

        @Override
        public int getCount() {
            if(!data.isEmpty()){
                return data.size();
            }
            else
                return 1;

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(!data.isEmpty()) {
                Intent intent = new Intent(Festival.this, Information.class);
                intent.putExtra("data", data.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }
        private class FavoritesAdapater implements View.OnClickListener{
            private int pos;
            public FavoritesAdapater(int pos){
                this.pos= pos;
            }
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.non_favorite_btn:
                        String addFavorite = AppManager.getInstance().getAppNotice().getAddFavorite();
                        try {
                            Model.getInstance(getApplicationContext()).insert(data.get(pos));
                            Toast.makeText(getApplicationContext(), addFavorite, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Parsing Data Error.",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.favorite_btn:
                        String deleteFavorite =  AppManager.getInstance().getAppNotice().getDeleteFavorite();
                        Toast.makeText(getApplicationContext(), deleteFavorite, Toast.LENGTH_SHORT).show();
                        Model.getInstance(getApplicationContext()).delete(data.get(pos).getCultCode());
                        break;
                }
//            listView.invalidateViews();
                favorites = Model.getInstance(getApplicationContext()).select();
                adapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}


