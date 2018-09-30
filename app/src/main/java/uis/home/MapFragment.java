package uis.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uis.DataBase.FestivalInformationVO;
import uis.DataBase.ManageFestivalData;
import uis.Manage.AppManager;
import uis.festival.Festival;
import uis.festival.R;

public class MapFragment extends Fragment implements View.OnTouchListener {
    private TextView map_explanation;
    private TextView east_north, west_north, west_south, east_south, center;
    //    private LocationClickListener listener;
    private final int RESULT_CODE = 100;
    private String Gu[] = null;
    private String[] localName = AppManager.getInstance().getAppData().getLocalName();
    private String selectPlace = AppManager.getInstance().getAppData().getSelectPlace();
    private String category;


    // 빈 생성자
    public MapFragment() {
        //listener = new LocationClickListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_map, container, false);

        settingView(view);

        return view;
    }

    private void settingView(View view) {
        map_explanation = view.findViewById(R.id.map_explanation);

        west_north = view.findViewById(R.id.map_west_north);
        east_south = view.findViewById(R.id.map_east_south);
        east_north = view.findViewById(R.id.map_east_north);
        west_south = view.findViewById(R.id.map_west_south);
        center = view.findViewById(R.id.map_center);


        west_north.setOnTouchListener(this);
        east_north.setOnTouchListener(this);
        east_south.setOnTouchListener(this);
        west_south.setOnTouchListener(this);
        center.setOnTouchListener(this);


        // 진 추가
        map_explanation.setText(selectPlace);
        west_north.setText(localName[0]);
        east_north.setText(localName[1]);
        east_south.setText(localName[2]);
        west_south.setText(localName[3]);
        center.setText(localName[4]);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ArrayList<FestivalInformationVO> data = new ArrayList<FestivalInformationVO>();
        final int main_color = getResources().getColor(R.color.main_color);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                switch (v.getId()) {
                    case R.id.map_west_north:
                        west_north.setTextColor(Color.WHITE);
                        west_north.setBackgroundResource(R.drawable.map_name_sel);
                        Gu = new String[]{"은평구","서대문구","마포구"};
                        category = localName[0];
                        break;
                    case R.id.map_east_south:
                        east_south.setTextColor(Color.WHITE);
                        east_south.setBackgroundResource(R.drawable.map_name_sel);
                        Gu = new String[]{"서초구","강남구","송파구","강동구"};
                        category = localName[1];
                        break;
                    case R.id.map_west_south:
                        west_south.setTextColor(Color.WHITE);
                        west_south.setBackgroundResource(R.drawable.map_name_sel);
                        Gu = new String[]{"강서구","양천구","구로구","영등포구","금천구","관악구","동작구"};
                        category = localName[3];
                        break;
                    case R.id.map_east_north:
                        east_north.setTextColor(Color.WHITE);
                        east_north.setBackgroundResource(R.drawable.map_name_sel);
                        Gu = new String[]{"도봉구", "노원구", "성북구", "강북구","동대문구","중랑구","성동구","광진구"};
                        category = localName[2];
                        break;
                    case R.id.map_center:
                        center.setTextColor(Color.WHITE);
                        center.setBackgroundResource(R.drawable.map_name_sel);
                        Gu = new String[]{"종로구", "중구", "용산구"};
                        category = localName[4];
                        break;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                switch (v.getId()) {
                    case R.id.map_west_north:
                        west_north.setBackgroundResource(R.drawable.map_name_button);
                        west_north.setTextColor(main_color);
                        break;
                    case R.id.map_east_south:
                        east_south.setBackgroundResource(R.drawable.map_name_button);
                        east_south.setTextColor(main_color);
                        break;
                    case R.id.map_west_south:
                        west_south.setBackgroundResource(R.drawable.map_name_button);
                        west_south.setTextColor(main_color);
                        break;
                    case R.id.map_east_north:
                        east_north.setBackgroundResource(R.drawable.map_name_button);
                        east_north.setTextColor(main_color);
                        break;
                    case R.id.map_center:
                        center.setBackgroundResource(R.drawable.map_name_button);
                        center.setTextColor(main_color);
                        break;

                }
                Intent intent = new Intent(getActivity(), Festival.class);
                for (int i = 0; i < ManageFestivalData.getInstance().festivalInformationVOArrayList.size(); i++) {
                    String GCode = ManageFestivalData.getInstance().festivalInformationVOArrayList.get(i).getGCode();
                    for (int j = 0; j < Gu.length; j++) {
                        if (GCode.equals(Gu[j])) {
                            data.add(ManageFestivalData.getInstance().festivalInformationVOArrayList.get(i));
                            break;
                        }
                    }
                }
                intent.putExtra("category", category);
                intent.putExtra("data", data);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, RESULT_CODE);
                break;
            }
        }

        return true;
    }


}