package com.example.allseven64.cataloguemovieuiux.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allseven64.cataloguemovieuiux.R;
import com.example.allseven64.cataloguemovieuiux.SharedPreference;

public class AboutFragment  extends Fragment {
    static final String TAG = AboutFragment.class.getSimpleName();
    Context context;
    SharedPreference sharedPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        context = view.getContext();

        sharedPreference = new SharedPreference(getActivity());
        if (sharedPreference.loadDarkModeState()==true) {
            getActivity().setTheme(R.style.DarkTheme);
        }
        else getActivity().setTheme(R.style.AppTheme);


        return view;
    }
}
