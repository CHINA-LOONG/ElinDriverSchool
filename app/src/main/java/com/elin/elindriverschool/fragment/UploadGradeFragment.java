package com.elin.elindriverschool.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elin.elindriverschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadGradeFragment extends Fragment {


    public UploadGradeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_grade, container, false);
    }

}
