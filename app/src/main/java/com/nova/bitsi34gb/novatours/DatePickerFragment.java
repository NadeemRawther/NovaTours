package com.nova.bitsi34gb.novatours;


import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
       View v;

    public DatePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
