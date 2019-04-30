package uis.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uis.DataBase.FestivalInformationVO;
import uis.DataBase.ManageFestivalData;
import uis.Manage.AppManager;
import uis.festival.Festival;
import uis.festival.R;

/*
 * gridView에 Theme에 해당하는 정보를 뿌려주는 Fragment Class
 * Theme의 분류는 미리 정해놓고 그 분류에 따라 서울시 API에서 정해준 목록에따라 데이터를 축소시킨다.
 */
public class ThemeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private final int RESULT_CODE = 100;
    String[] themeName = AppManager.getInstance().getAppData().getThemeName();
    Integer[] themeId = {R.drawable.theme_img_box1, R.drawable.theme_img_box2, R.drawable.theme_img_box3, R.drawable.theme_img_box4,
            R.drawable.theme_img_box5, R.drawable.theme_img_box6};

    private String theme[] = null;
    private String category;
    private Bitmap[] bm;

    private static int count ;
    //이미지 배열 선언
    ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();


    // 빈 생성자
    public ThemeFragment() {


    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_theme, container, false);

        gridView = (GridView) view.findViewById(R.id.theme_gridview);
        gridView.setAdapter(new gridAdapter());
        gridView.setOnItemClickListener(this);

        for (int i = 0; i < bm.length; i++) {
            picArr.add(bm[i]);
        }

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), Festival.class);
        ArrayList<FestivalInformationVO> data = new ArrayList<FestivalInformationVO>();


        switch (position) {
            case 0:
                theme = new String[]{"콘서트", "클래식", "연극", "독주/독창회", "뮤지컬/오페라", "무용", "국악"};
                category = themeName[0];
            break;
            case 1:
                theme = new String[]{"문화교양/강좌"};
                category = themeName[1];
                break;
            case 2:
                theme = new String[]{"영화"};
                category = themeName[2];
                break;
            case 3:
                theme = new String[]{"전시/미술"};
                category = themeName[3];
                break;
            case 4:
                theme = new String[]{"축제-문화·예술", "축제-자연·경관", "축제-전통·역사", "축제-시민화합"};
                category = themeName[4];
                break;
            case 5:
                theme = new String[]{"기타", "축제-기타"};
                category = themeName[5];
                break;

        }


        //데이터 정리
        for (int i = 0; i < ManageFestivalData.getInstance().festivalInformationVOArrayList.size(); i++) {
            String CodeName = ManageFestivalData.getInstance().festivalInformationVOArrayList.get(i).getCodeName();
            for (int j = 0; j < theme.length; j++) {
                if (CodeName.equals(theme[j])) {
                    data.add(ManageFestivalData.getInstance().festivalInformationVOArrayList.get(i));
                    break;
                }
            }
        }
        intent.putExtra("category",category);
        intent.putExtra("data", data);
        startActivityForResult(intent, RESULT_CODE);

    }


    public class gridAdapter extends BaseAdapter {
        LayoutInflater inflater;
        ImageView imageView;
        private TextView theme_view_text;



        public gridAdapter() {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            bm = new Bitmap[themeName.length];
            for (int i = 0; i < themeName.length; i++) {
                bm[i] = BitmapFactory.decodeResource(getResources(), themeId[i]);
            }
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return picArr.size();    //그리드뷰에 출력할 목록 수
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return picArr.get(position);    //아이템을 호출할 때 사용하는 메소드
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;    //아이템의 아이디를 구할 때 사용하는 메소드
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_themeview, parent, false);
                imageView = (ImageView) convertView.findViewById(R.id.theme_view);
                theme_view_text = (TextView)convertView.findViewById(R.id.theme_view_text);
//                imageView.setImageBitmap(bm[position]);
                imageView.setImageResource(themeId[position]);
                theme_view_text.append(themeName[position]);



            }


            return convertView;
        }
    }

}