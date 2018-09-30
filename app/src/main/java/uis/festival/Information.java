package uis.festival;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uis.Communication.TranslateDialog;
import uis.DataBase.BitmapDownloaderTask;
import uis.DataBase.FestivalInformationVO;
import uis.DataBase.Model;
import uis.Manage.AppManager;


public class Information extends AppCompatActivity implements OnMapReadyCallback {
    private TextView homepageBtn;
    private Button favoriteBtn;
    private Button clearBtn;
    private Button backBtn;
    private Button colorlessBtn;
    private ImageView Festival_Img;
    private Intent intent;
    private FestivalInformationVO data;
    private Geocoder geocoder;
    private ArrayList<FestivalInformationVO> favorites;

    private TextView festInfo, festInfo2;
    private TextView detail, location, etcInfo;
    private String[] info = AppManager.getInstance().getAppData().getInfo();
    private String top = AppManager.getInstance().getAppData().getTop();
    private String homepage = AppManager.getInstance().getAppData().getHomepageBtn();
    private String[] pay = AppManager.getInstance().getAppData().getPay();
    private String clickMap = AppManager.getInstance().getAppData().getClickMap();
    private String target = AppManager.getInstance().getAppData().getUseTarget();
    private String inquery = AppManager.getInstance().getAppData().getInquery();
    private String time = AppManager.getInstance().getAppData().getTime();
    GoogleMap mMap;

    String homepgLink;
    BitmapDownloaderTask task;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(false);
        task = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation);

        homepageBtn = (TextView) findViewById(R.id.homepageBtn);
        favoriteBtn = (Button) findViewById(R.id.favoriteBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        colorlessBtn = (Button) findViewById(R.id.colorless_Btn);
        Festival_Img = (ImageView) findViewById(R.id.festivalImg);

        festInfo = (TextView) findViewById(R.id.Top);
        festInfo2 = (TextView) findViewById(R.id.info_FestivalLocation2);
        detail = (TextView) findViewById(R.id.detail);
        location = (TextView) findViewById(R.id.location);
        etcInfo = (TextView) findViewById(R.id.info_Etc);

        festInfo.setText(top);
        festInfo2.setText(clickMap);
        detail.setText(info[0]);
        location.setText(info[1]);
        etcInfo.setText(info[2]);
        homepageBtn.setText(homepage);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        setData();

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model.getInstance(getApplicationContext()).delete(data.getCultCode());
                String deleteFavorite = AppManager.getInstance().getAppNotice().getDeleteFavorite();
                Toast.makeText(getApplicationContext(), deleteFavorite, Toast.LENGTH_SHORT).show();

                favoriteBtn.setVisibility(View.INVISIBLE);
                clearBtn.setVisibility(View.VISIBLE);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Model.getInstance(getApplicationContext()).insert(data);
                    String addFavorite = AppManager.getInstance().getAppNotice().getAddFavorite();
                    Toast.makeText(getApplicationContext(), addFavorite, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Parsing Data Error.", Toast.LENGTH_SHORT).show();
                }


                clearBtn.setVisibility(View.INVISIBLE);
                favoriteBtn.setVisibility(View.VISIBLE);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        homepageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepgLink = data.getOrg_Link().trim();
                Intent intent;

                if(homepgLink.contains(" ") || homepgLink.equals("-")) {
                   Toast.makeText(getApplicationContext(), AppManager.getInstance().getAppData().getHomepageErrorMsg(), Toast.LENGTH_SHORT).show();
                }
                else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homepgLink));

                    try {
                        startActivity(intent);
                    }
                    catch(ActivityNotFoundException e){
                        Toast.makeText(getApplicationContext(),  AppManager.getInstance().getAppData().getHomepageErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });


        //Toast.makeText(getApplicationContext(), AppManager.getInstance().getAppData().getHomepageErrorMsg(), Toast.LENGTH_SHORT).show();

        colorlessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        intent = getIntent();
        data = (FestivalInformationVO) intent.getExtras().get("data");

        favorites = Model.getInstance(getApplicationContext()).select();

        final String title = data.getTitle();
        final String startDate = data.getStart_Date();
        String endDate = data.getEnd_Date();
        final String place = data.getPlace();
        final String Gu = data.getGCode();
        final String isFree = data.getIs_Free().trim();
        final String useTarget = data.getUse_Trgt();
        final String useTime = data.getTime();
        String etc = data.getInquiry();

        TextView Festival_title = (TextView) findViewById(R.id.info_FestivalName);
        Festival_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 번역 창 띄움
                TranslateDialog translateDialog = new TranslateDialog(Information.this, title);
                translateDialog.show();
                Window window = translateDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.TOP);
                return false;
            }
        });
        TextView Festival_date = (TextView) findViewById(R.id.info_FestivalTerm);
        TextView Festival_place = (TextView) findViewById(R.id.info_FestivalLocation);
        Festival_place.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 번역 창 띄움
                TranslateDialog translateDialog = new TranslateDialog(Information.this, Gu + " " + place);
                translateDialog.show();
                Window window = translateDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.TOP);
                return false;
            }
        });
        TextView Festival_detail = (TextView) findViewById(R.id.info_Detail);
        Festival_detail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 번역 창 띄움
                String string;
                if (isFree.equals("1")) {
                    string = pay[0] + target + useTarget + "\n" + time + useTime;
                } else {
                    string = pay[1] + target + useTarget + "\n" + time + useTime;
                }

                TranslateDialog translateDialog = new TranslateDialog(Information.this, string);
                translateDialog.show();
                Window window = translateDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.TOP);
                return false;
            }
        });
        TextView Festival_etc = (TextView) findViewById(R.id.etc_Info);

        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getCultCode().equals(data.getCultCode())) {
                clearBtn.setVisibility(View.INVISIBLE);
                favoriteBtn.setVisibility(View.VISIBLE);
            }
        }

        if (isFree.equals("1")) {
            Festival_detail.setText(pay[0]);
        } else {
            Festival_detail.setText(pay[1]);
        }
        Festival_detail.append(target + useTarget + "\n" + time + useTime);
        Festival_etc.setText(inquery + etc);
        Festival_title.setText(title);
        Festival_date.setText(startDate + " ~ " + endDate);
        Festival_place.setText(Gu + " " + place);

//        Glide.with(getApplicationContext()).load(data.getMain_Img()).into(Festival_Img);
        task = new BitmapDownloaderTask(Festival_Img);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data.getMain_Img());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.geocoder = new Geocoder(this);
        // 서울에 대한 위치 설정


        List<Address> addressList = null;
        char[] tmp;

        String place = data.getGCode();
        place += " " + data.getPlace();

        tmp = place.toCharArray();

        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] == '(') {
                for (int j = i; j < tmp.length; j++) {
                    if (tmp[j] == ')') {
                        tmp[j] = ' ';
                        break;
                    }
                    tmp[j] = ' ';
                }
            }
        }
        place = String.valueOf(tmp);
        place.trim();


        try {
            addressList = geocoder.getFromLocationName(place, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String[] splitStr = addressList.get(0).toString().split(",");
            String addr = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);

            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);


            LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            MarkerOptions mOptions2 = new MarkerOptions();
            mOptions2.title("" + place + "");
            mOptions2.snippet(addr);
            mOptions2.position(point);
            ///


            mMap.addMarker(mOptions2);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
        } catch (IndexOutOfBoundsException e) {
            colorlessBtn.setBackgroundResource(R.drawable.no_map_info);
            colorlessBtn.setOnClickListener(null);
        }
    }
}