package me.rodrigo.sputiflais.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rodrigo.sputiflais.MainActivity;
import me.rodrigo.sputiflais.R;


public class HomeFragment extends Fragment{

    public static final String ARG_SECTION_TITLE = "section_number";

    public static HomeFragment newInstance(String sectionTitle) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_profile, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getUserFacebook();
    }
}
