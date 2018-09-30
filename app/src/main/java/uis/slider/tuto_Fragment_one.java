package uis.slider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import uis.festival.R;


public class tuto_Fragment_one extends Fragment {
    int image;
    ImageView imageView;
    public static tuto_Fragment_one newInstance(int image) {
        Bundle args = new Bundle();
        tuto_Fragment_one fragment = new tuto_Fragment_one();
        fragment.setArguments(args);
        fragment.image = image;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tuto_fragment_one,container,false);
        imageView = (ImageView)v.findViewById(R.id.frag1_img);
        imageView.setImageResource(image);
        return v;
    }
}