package com.example.suvp.shop.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.shop.Adapters.CustomInvoiceListAdapter;
import com.example.suvp.shop.Adapters.CustomProductItemListAdapter;
import com.example.suvp.shop.Fragments.DatePickerFragment;
import com.example.suvp.shop.General.Utility;
import com.example.suvp.shop.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Item;
import DataBase.Util.OrmLiteDbHelper;

/**
 * Created by suvp on 3/28/2016.
 */
public class SearchInvoiceActivity extends FragmentActivity
{
    CheckBox invoiceNumberCheckBox;
    CheckBox dateCheckBox;
    TextView serialNumberText;
    Button searchButton;
    Button startDateSelectButton;
    Button endDateSelectButton;

    private final Context context = this;
    private OrmLiteDbHelper dbHelper_;
    CustomInvoiceListAdapter customListAdapter;

    Calendar selectedStartCalendar;
    Calendar selectedEndCalendar;

    public final static String SERIALIZED_INVOICE = "invoicePassed";
    private final String LOG_TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_invoice);
        initialize();
    }

    private void initialize()
    {
        invoiceNumberCheckBox = (CheckBox)findViewById(R.id.checkBoxInvoiceNumber);
        dateCheckBox = (CheckBox)findViewById(R.id.checkBoxInvoiceDate);
        serialNumberText = (TextView)findViewById(R.id.editTextSearchInvoiceNumber);
        searchButton = (Button)findViewById(R.id.buttonSearchInvoice);
        startDateSelectButton = (Button)findViewById(R.id.buttonSelectStartDate);
        endDateSelectButton = (Button)findViewById(R.id.buttonSelectEndDate);

        setListActionListener();

        enableDisableInvoiceNumberSearch(false);
        enableDisableDateButtons(false);

        invoiceNumberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableDisableInvoiceNumberSearch(isChecked);
            }
        });

        dateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableDisableDateButtons(isChecked);
            }
        });

        setSearchButtonActionListener();
        setDateButtonActionListener();
    }

    private void enableDisableInvoiceNumberSearch(Boolean aInOption)
    {
        invoiceNumberCheckBox.setChecked(aInOption);
        serialNumberText.setEnabled(aInOption);
    }

    private void enableDisableDateButtons(Boolean aInOption)
    {
        startDateSelectButton.setEnabled(aInOption);
        endDateSelectButton.setEnabled(aInOption);
    }

    private void setSearchButtonActionListener()
    {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lErr = getErrorString();
                if (!Utility.isNullOrEmptyString(lErr)) {
                    Toast.makeText(context, lErr, Toast.LENGTH_SHORT).show();
                } else {
                    populateResultToList();
                }
            }
        });
    }

    private String getErrorString()
    {
        StringBuffer lErrString = new StringBuffer();
        if(invoiceNumberCheckBox.isChecked() && Utility.isNullOrEmptyString(serialNumberText.getText().toString()))
        {
            lErrString.append("Please Enter Serial Number \n");
        }
        return lErrString.toString();
    }

    private void populateResultToList()
    {
        customListAdapter.reInitList();
        RuntimeExceptionDao<Invoice, Integer> lInvoiceDao = getHelper().getInvoiceDataDao();
        if(invoiceNumberCheckBox.isChecked())
        {
            String lSerialNumber =  serialNumberText.getText().toString();
            QueryBuilder<Invoice, Integer> lQueryBuilder = lInvoiceDao.queryBuilder();
            final Where<Invoice, Integer> lWhere = lQueryBuilder.where();
            try {
                lWhere.eq(Invoice.COL_INVOICE_NUMBER, lSerialNumber);
                PreparedQuery<Invoice> preparedQuery = null;

                preparedQuery = lQueryBuilder.prepare();
                List<Invoice> lInvoiceReceived = lInvoiceDao.query(preparedQuery);

                if(lInvoiceReceived == null || lInvoiceReceived.size() <= 0)
                {
                    Toast.makeText(context, "No Invoice Present", Toast.LENGTH_SHORT).show();
                    Log.i(LOG_TAG, "NO INVOICE FOUND WITH FOR THE QUERY");
                }
                else
                {
                    customListAdapter.addAll(lInvoiceReceived);
                }
            } catch (SQLException e) {
                Log.e(LOG_TAG, "Failed to generate query for the product");
                e.printStackTrace();
            }
        }
        else
        {
            List<Invoice> lInvoiceList = lInvoiceDao.queryForAll();
            customListAdapter.addAll(lInvoiceList);
        }
    }

    private void setListActionListener()
    {
        ListView listView = (ListView)findViewById(R.id.listInvoice);
        customListAdapter = new CustomInvoiceListAdapter(this, new LinkedList<Invoice>());
        listView.setAdapter(customListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Invoice> lInvoice = customListAdapter.getItemList();
                Invoice lSelectedInvoice = lInvoice.get(position);
                Intent singleInvoiceViewActivity = new Intent(context, InvoiceViewActivity.class);
                singleInvoiceViewActivity.putExtra(SERIALIZED_INVOICE, lSelectedInvoice);
                context.startActivity(singleInvoiceViewActivity);
            }
        });
    }


    private void setDateButtonActionListener()
    {
        selectedStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
        selectedEndCalendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));

        startDateSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(onStartdate);
            }
        });

        endDateSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(onEndDate);
            }
        });

        TextView lStartDateSelectedTextView = (TextView)findViewById(R.id.textFieldStartDate);
        TextView lEndDateSelectedTextView = (TextView)findViewById(R.id.textFieldEndDate);

        StringBuffer lStartSelectedDate = new StringBuffer();
        lStartSelectedDate.append(selectedStartCalendar.get(Calendar.DAY_OF_MONTH)).append(":").
                append(selectedStartCalendar.get(Calendar.MONTH)+1).append(":").
                append(selectedStartCalendar.get(Calendar.YEAR));
        lStartDateSelectedTextView.setText(lStartSelectedDate);

        StringBuffer lEndSelectedDate = new StringBuffer();
        lEndSelectedDate.append(selectedEndCalendar.get(Calendar.DAY_OF_MONTH)).append(":").
                append(selectedEndCalendar.get(Calendar.MONTH)+1).append(":").
                append(selectedEndCalendar.get(Calendar.YEAR));
        lEndDateSelectedTextView.setText(lEndSelectedDate);
    }

    //The listener for the datepicker fragment or the dialog
    DatePickerDialog.OnDateSetListener onStartdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth)
        {
            TextView lDateSelectedTextView = (TextView)findViewById(R.id.textFieldStartDate);
            StringBuffer lSelectedDate = new StringBuffer();
            lSelectedDate.append(dayOfMonth).append(":").append(monthOfYear+1).append(":").append(year);
            lDateSelectedTextView.setText(lSelectedDate);

            selectedStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
            selectedStartCalendar.set(year, monthOfYear, dayOfMonth);
        }
    };

    //The listener for the datepicker fragment or the dialog
    DatePickerDialog.OnDateSetListener onEndDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth)
        {
            TextView lDateSelectedTextView = (TextView)findViewById(R.id.textFieldEndDate);
            StringBuffer lSelectedDate = new StringBuffer();
            lSelectedDate.append(dayOfMonth).append(":").append(monthOfYear+1).append(":").append(year);
            lDateSelectedTextView.setText(lSelectedDate);

            selectedEndCalendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
            selectedEndCalendar.set(year, monthOfYear, dayOfMonth);
        }
    };

    //whenever a datepicker dialog is created we have to pass the callback date picked object also so that the date is received back.
    private void showDatePicker(DatePickerDialog.OnDateSetListener aInListener) {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        calender.getTime();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(aInListener);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper_ != null) {
            OpenHelperManager.releaseHelper();
            dbHelper_ = null;
        }
    }

    private OrmLiteDbHelper getHelper() {
        if (dbHelper_ == null) {
            dbHelper_ =
                    OpenHelperManager.getHelper(this, OrmLiteDbHelper.class);
        }
        return dbHelper_;
    }
}
