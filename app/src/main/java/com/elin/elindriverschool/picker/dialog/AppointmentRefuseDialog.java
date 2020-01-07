package com.elin.elindriverschool.picker.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elin.elindriverschool.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import top.defaults.view.DivisionPickerView;
import top.defaults.view.PickerViewDialog;

public class AppointmentRefuseDialog extends DialogFragment {

    public interface ActionListener{
        void onClickType(ActionType type);
    }

    public enum ActionType{
        BUSY,
        FULL,
        REST,
        CANCEL
    }

    protected ActionListener actionListener;

    TextView tv_busy;
    TextView tv_full;
    TextView tv_rest;
    TextView tv_cancel;

    public static AppointmentRefuseDialog newInstance(ActionListener actionListener) {
        AppointmentRefuseDialog dialogFragment = new AppointmentRefuseDialog();
        dialogFragment.actionListener = actionListener;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        PickerViewDialog dialog = new PickerViewDialog(getActivity());
        dialog.setContentView(R.layout.dialog_appointment_refuse);

//        ButterKnife.bind(dialog);

        tv_busy = (TextView)dialog.findViewById(R.id.tv_today_busy);
        tv_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null) {
                    actionListener.onClickType(ActionType.BUSY);
                }
                dismiss();
            }
        });
        tv_full = (TextView)dialog.findViewById(R.id.tv_today_full);
        tv_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null) {
                    actionListener.onClickType(ActionType.FULL);
                }
                dismiss();
            }
        });
        tv_rest = (TextView)dialog.findViewById(R.id.tv_today_rest);
        tv_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null) {
                    actionListener.onClickType(ActionType.REST);
                }
                dismiss();
            }
        });
        tv_cancel = (TextView)dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionListener != null) {
                    actionListener.onClickType(ActionType.CANCEL);
                }
                dismiss();
            }
        });
        return dialog;
    }

}
