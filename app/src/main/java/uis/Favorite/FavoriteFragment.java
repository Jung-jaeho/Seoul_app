package uis.Favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionException;

import uis.DataBase.BitmapDownloaderTask;
import uis.DataBase.FestivalInformationVO;
import uis.DataBase.Model;
import uis.Manage.AppManager;
import uis.festival.Information;
import uis.festival.R;

public class FavoriteFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<FestivalInformationVO> data;
    private mAdapter adapter;
    private TextView favorite;
    private String favoriteText = AppManager.getInstance().getAppData().getFavorite();
    private String addFavorite = AppManager.getInstance().getAppNotice().getAddFavorite();
    private String deleteFavorite = AppManager.getInstance().getAppNotice().getDeleteFavorite();
    private Button back_btn;
    private View view;

    @Override
    public void onResume() {
        super.onResume();
        data = Model.getInstance(getActivity().getApplicationContext()).select();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (data.size() != 0)
            task.cancel(false);
        task=null;
    }

    private BitmapDownloaderTask task;

    public FavoriteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        data = Model.getInstance(getActivity().getApplicationContext()).select();


        view = inflater.inflate(R.layout.festival, container, false);
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        adapter = new mAdapter();
        listView.setAdapter(adapter);

        favorite = view.findViewById(R.id.category);
        favorite.setText(favoriteText);
        back_btn = view.findViewById(R.id.festival_backbtn);
        back_btn.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(!data.isEmpty()) {
            Intent intent = new Intent(getContext(), Information.class);
            intent.putExtra("data", data.get(i));
            task.cancel(false);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


    private class mAdapter extends BaseAdapter {
        private ImageView image;
        private TextView title;
        private TextView Context;
        private Button favoriteBtn;
        private Button non_favoriteBtn;
        private TextView gcode_And_IsFree;
        FavoritesAdapater favoritesAdapater;
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
            if(convertView==null) {
                 inflater = (LayoutInflater) getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

            }
            if (!data.isEmpty()) { //데이터베이스에 데이터가 있을 때

                convertView = inflater.inflate(R.layout.festival_view, parent, false);
                image = (ImageView) convertView.findViewById(R.id.img);
                title = (TextView) convertView.findViewById(R.id.title);
                Context = (TextView) convertView.findViewById(R.id.context);

                //즐겨찾기버튼 구성
                favoriteBtn = (Button) convertView.findViewById(R.id.favorite_btn);
                favoriteBtn.setFocusable(false);
                non_favoriteBtn = (Button) convertView.findViewById(R.id.non_favorite_btn);
                non_favoriteBtn.setFocusable(false);
                //즐겨찾기 버튼 보이기 설정 (즐겨찾기 된 부분이니 빨간하트로)
                non_favoriteBtn.setVisibility(View.INVISIBLE);
                favoriteBtn.setVisibility(View.VISIBLE);

                //즐겨찾기 버튼에 Listener장착
                favoritesAdapater = new FavoritesAdapater(position);
                favoriteBtn.setOnClickListener(favoritesAdapater);
                non_favoriteBtn.setOnClickListener(favoritesAdapater);

                // 아이템 내 각 위젯에 데이터 반영
                title.setText(data.get(position).getTitle());
                Context.setText(data.get(position).getStart_Date() + " ~ " + data.get(position).getEnd_Date());
                //유/무료
                gcode_And_IsFree = convertView.findViewById(R.id.gcode_and_isfree);
                String gcode = data.get(position).getGCode();

                if(!gcode.equals("")||!gcode.equals(" ")||!gcode.equals("\n")) {
                    gcode_And_IsFree.setText(gcode + " | ");
                }
                if (data.get(position).getIs_Free().equals("1")) {
                    gcode_And_IsFree.append("무료");
                } else {
                    gcode_And_IsFree.setText("유료");
                }
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
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                listView.setEnabled(true);

            } else {
                convertView = inflater.inflate(R.layout.no_result, parent, false);
                listView.setOnItemClickListener(null);
                listView.setEnabled(false);
            }


            return convertView;
        }

        @Override
        public int getCount() {
            if (!data.isEmpty()) {
                return data.size();
            } else {
                return 1;
            }
        }

        private class FavoritesAdapater implements View.OnClickListener {
            private int pos;

            public FavoritesAdapater(int pos) {
                this.pos = pos;
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.non_favorite_btn:
                        try {
                            Model.getInstance(getContext()).insert(data.get(pos));
                            Toast.makeText(getContext(), addFavorite, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(),"Parsing Data Error.",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.favorite_btn:
                        Toast.makeText(getContext(), deleteFavorite, Toast.LENGTH_SHORT).show();
                        Model.getInstance(getContext()).delete(data.get(pos).getCultCode());
                        break;
                }

                data = Model.getInstance(getActivity().getApplicationContext()).select();
                adapter.notifyDataSetChanged();
            }
        }
    }
}