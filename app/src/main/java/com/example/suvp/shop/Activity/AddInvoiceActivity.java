package com.example.suvp.shop.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.suvp.shop.Fragments.DatePickerFragment;
import com.example.suvp.shop.R;

import java.util.Calendar;

import General.CustomProductListAdapter;

/**
 * Created by suvp on 3/16/2016.
 */
public class AddInvoiceActivity extends FragmentActivity {
    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_invoice);

        Button lButton = (Button)findViewById(R.id.buttonSelectDate);
        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        Log.i(LOG_TAG, "Invoice Menu Create");
    }

    //whenever a datepicker dialog is created we have to pass the callback date picked object also so that the date is received back.
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    //The listener for the datepicker fragment or the dialog
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth)
        {
            TextView lDateSelectedTextView = (TextView)findViewById(R.id.textFieldDate);
            StringBuffer lSelectedDate = new StringBuffer();
            lSelectedDate.append(dayOfMonth).append(":").append(monthOfYear).append(":").append(year);
            lDateSelectedTextView.setText(lSelectedDate);
        }
    };

}
