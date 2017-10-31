package com.example.cspeir.collegeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AlertDialogLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by shunt on 10/12/2017.
 */

public class DatePickerFragment extends DialogFragment {
    Date mdate;
    DatePicker mDatePicker;
    Calendar mCalendar;
    private static final String DATE_ARGUMENT= "date of birth";
    public static final String EXTRA_DATE_OF_BIRTH = "org.pltw.examples.collegeapp.dateofbirth";
    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_of_birth, null);
        mdate = (Date) getArguments().getSerializable(DATE_ARGUMENT);
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mdate);
        mDatePicker= (DatePicker)v.findViewById(R.id.dialog_date_of_birth);
        mDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH), null);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Date of Birth")
                .setPositiveButton("Save",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = new GregorianCalendar(mDatePicker.getYear(),
                                mDatePicker.getMonth(), mDatePicker.getDayOfMonth()).getTime();
                        sendResult(Activity.RESULT_OK, date);

                    }
                })
                .create();
    }
    public void sendResult(int resultCode, Date date){
        if (getTargetFragment()!=null){
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE_OF_BIRTH, date);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(DATE_ARGUMENT, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
