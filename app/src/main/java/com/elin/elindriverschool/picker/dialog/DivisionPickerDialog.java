package com.elin.elindriverschool.picker.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.picker.DivisionModel;
import com.elin.elindriverschool.picker.Divisions;

import java.util.List;

import top.defaults.view.Division;
import top.defaults.view.DivisionPickerView;
import top.defaults.view.PickerViewDialog;

public class DivisionPickerDialog extends BaseDialogFragment {

    private DivisionPickerView divisionPicker;

    public static DivisionPickerDialog newInstance(int type, ActionListener actionListener) {
        return BaseDialogFragment.newInstance(DivisionPickerDialog.class, type, actionListener);
    }

    @Override
    protected Dialog createDialog(Bundle savedInstanceState) {
        PickerViewDialog dialog = new PickerViewDialog(getActivity());
        dialog.setContentView(R.layout.dialog_division_picker);
        divisionPicker = (DivisionPickerView)dialog.findViewById(R.id.divisionPicker);

        setupPickers();
        attachActions(dialog.findViewById(R.id.done), dialog.findViewById(R.id.cancel));
        return dialog;
    }

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_division_picker, container, false);
        divisionPicker = (DivisionPickerView)view.findViewById(R.id.divisionPicker);

        setupPickers();
        attachActions(view.findViewById(R.id.done), view.findViewById(R.id.cancel));
        return view;
    }

    public Division getSelectedDivision() {
        return divisionPicker.getSelectedDivision();
    }

    private void setupPickers() {
        final List<DivisionModel> divisions = Divisions.get(getActivity());
        divisionPicker.setDivisions(divisions);
    }
}
