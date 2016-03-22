package com.example.suvp.shop.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.shop.Fragments.DatePickerFragment;
import com.example.suvp.shop.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Item;
import DataBase.ManagedObjects.Product;
import DataBase.Util.OrmLiteDbHelper;
import General.CustomProductItemListAdapter;

/**
 * Created by suvp on 3/16/2016.
 */
public class AddInvoiceActivity extends FragmentActivity
{
    private final String LOG_TAG = getClass().getSimpleName();
    private final Context context_ = this;
    public final static String SERIALIZED_PRODUCT = "productPassed";

    private OrmLiteDbHelper dbHelper_;

    Date selectedDate;
    CustomProductItemListAdapter customListAdapter;

    static final int REQUEST_CODE_FOR_SELECTE_PRODUCT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calender = Calendar.getInstance();
        selectedDate = calender.getTime();
        setContentView(R.layout.new_invoice);

        setDateButtonActionListener();
        setAddButtonActionListener();
        setListActionListener();
        setSaveActionListener();

        Log.i(LOG_TAG, "Invoice Menu Create");
    }

    private void setDateButtonActionListener()
    {
        Button lSelectButton = (Button)findViewById(R.id.buttonSelectDate);
        lSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        TextView lDateSelectedTextView = (TextView)findViewById(R.id.textFieldDate);

        StringBuffer lSelectedDate = new StringBuffer();
        lSelectedDate.append(selectedDate.getDate()).append(":").append(selectedDate.getMonth()).append(":").append(selectedDate.getYear());
        lDateSelectedTextView.setText(lSelectedDate);
    }

    //whenever a datepicker dialog is created we have to pass the callback date picked object also so that the date is received back.
    private void showDatePicker() {
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
            selectedDate = new Date(year, monthOfYear, dayOfMonth);
        }
    };

    private void setAddButtonActionListener()
    {
        Button lAddButton = (Button)findViewById(R.id.buttonAddItem);
        lAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lSearchProductActivity = new Intent(context_, SearchProductActivity.class);
                startActivityForResult(lSearchProductActivity, REQUEST_CODE_FOR_SELECTE_PRODUCT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_SELECTE_PRODUCT) {
            if (resultCode == RESULT_OK) {
                Object[] lReceivedProducts = (Object[])data.getExtras().get(SearchProductActivity.SERIALIZED_PRODUCTS_FINALY_SELECTED);
                List<Item> lSelectedItems = new ArrayList<>();

                for(Object lProduct : lReceivedProducts)
                {
                    Item lItem = new Item((Product)lProduct);
                    lSelectedItems.add(lItem);
                }

                customListAdapter.addAll(lSelectedItems);
                customListAdapter.notifyDataSetChanged();
                Log.i(LOG_TAG, "Selected:" + lSelectedItems.size());
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "No Item Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setListActionListener()
    {
        ListView listView = (ListView)findViewById(R.id.itemList);
        customListAdapter = new CustomProductItemListAdapter(this, new LinkedList<Item>());
        listView.setAdapter(customListAdapter);
    }

    private void setSaveActionListener()
    {
        Button lSaveButton = (Button)findViewById(R.id.buttonAddSave);
        lSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Item> lItemList = customListAdapter.getItemList();
                TextView lTextView = (TextView)findViewById(R.id.editTextInvoiceNumber);
                String lInvoiceNumber = lTextView.getText().toString();
                Boolean lIsValid = validate(lItemList, lInvoiceNumber);
                if(lIsValid)
                {
                    Invoice lNewInvoice = new Invoice();
                    lNewInvoice.setInvoiceNumber(lInvoiceNumber);
                    lNewInvoice.setDate(selectedDate);

                    RuntimeExceptionDao<Invoice, Integer> lInvoiceDao=  getHelper().getInvoiceDataDao();
                    RuntimeExceptionDao<Item, Integer> lItemDao =  getHelper().getItemDao();
                    lInvoiceDao.create(lNewInvoice);

                    for(Item lItem: lItemList)
                    {
                        lItem.setInvoice(lNewInvoice);
                        lItemDao.create(lItem);
                    }

                    Toast.makeText(context_, "Invoice Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private boolean validate(List<Item> aInProductList, String aInInvoiceNumber)
    {
        StringBuffer lErrorMesage = new StringBuffer();
        if(aInInvoiceNumber == null || aInInvoiceNumber.isEmpty())
        {
            lErrorMesage.append("Please Select Invoice Number.\n") ;
        }

        if(aInProductList == null || aInProductList.size() <=0)
        {
            lErrorMesage.append("Please Select Product.\n") ;
        }

        if(lErrorMesage.length() > 0)
        {
            Toast.makeText(this, lErrorMesage, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
