package com.applicationcommunity;

import android.app.Dialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.Locale;

public class Calendar_Dialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView textView_cal = getActivity().findViewById(R.id.textView_cal);
                textView_cal.setText(String.format(Locale.JAPAN,"%02d/%02d/%02d",year,month+1,dayOfMonth));

            }
        },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)

                );



    }
}
