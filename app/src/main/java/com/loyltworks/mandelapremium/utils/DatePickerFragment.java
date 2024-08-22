package com.loyltworks.mandelapremium.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    Context context;
    CalenderCallBack calenderCallBack;
    Integer action = 0;

    public interface CalenderCallBack {
        void OnCalenderClickResult(String selectedDate, String serverFormat, Integer actionType) throws ParseException;
    }


    public DatePickerFragment(Context context, CalenderCallBack calenderCallBack, Integer action) {
        this.context=context;
        this.calenderCallBack = calenderCallBack;
        this.action = action;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


//       if(
//       calenderCallBack.getClass().getSimpleName().equals("MyRewardsFragment") || calenderCallBack.getClass().getSimpleName().equals("MyRedemptionFragment")) {
//           DatePickerDialog datepickerdialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, year, month, day);
//           datepickerdialog.getDatePicker().setMaxDate(new Date().getTime());
//           return datepickerdialog;
//       } else {
        DatePickerDialog datepickerdialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
//           datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datepickerdialog.getDatePicker().setMaxDate(new Date().getTime());
        return datepickerdialog;
//       }

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        try {
            calenderCallBack.OnCalenderClickResult(String.format("%02d", Integer.parseInt(String.valueOf(day))) + "/" + String.format("%02d", Integer.parseInt(String.valueOf(month + 1))) + "/" + String.valueOf(year),

                    String.valueOf(year) + "/" + String.format("%02d", Integer.parseInt(String.valueOf(month + 1))) + "/" + String.format("%02d", Integer.parseInt(String.valueOf(day))), action);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}