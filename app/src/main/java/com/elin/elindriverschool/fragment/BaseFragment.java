package com.elin.elindriverschool.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elin.elindriverschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    /**
     * 跳转到另一个Activity，携带数据
     *
     * @param context
     *            上下文
     * @param cls
     *            目标类
     *
     */
    public static void goToActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
//        ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    /**
     * 启动一个activity
     *
     * @param context
     *            上下文
     * @param cls
     *            目标类
     */
    public static void goToActivity(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
//        ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
