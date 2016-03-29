package com.example.suvp.shop.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.shop.Adapters.CustomInvoiceListAdapter;
import com.example.suvp.shop.Adapters.CustomProductItemListAdapter;
import com.example.suvp.shop.General.Utility;
import com.example.suvp.shop.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.LinkedList;
import java.util.List;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Item;
import DataBase.Util.OrmLiteDbHelper;

/**
 * Created by suvp on 3/28/2016.
 */
public class SearchInvoiceActivity extends FragmentActivity
{
    CheckBox invoiceNumberCheckBox;
    TextView serialNumberText;
    Button searchButton;

    private final Context context = this;
    private OrmLiteDbHelper dbHelper_;
    CustomInvoiceListAdapter customListAdapter;

    public final static String SERIALIZED_INVOICE = "invoicePassed";

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
        serialNumberText = (TextView)findViewById(R.id.editTextSearchInvoiceNumber);
        searchButton = (Button)findViewById(R.id.buttonSearchInvoice);

        setListActionListener();

        enableDisableInvoiceNumberSearch(false);

        invoiceNumberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableDisableInvoiceNumberSearch(isChecked);
            }
        });

        setSearchButtonActionListener();
    }

    private void enableDisableInvoiceNumberSearch(Boolean aInOption)
    {
        invoiceNumberCheckBox.setChecked(aInOption);
        serialNumberText.setEnabled(aInOption);
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
        RuntimeExceptionDao<Invoice, Integer> lInvoiceDao = getHelper().getInvoiceDataDao();
        List<Invoice> lInvoiceList  = lInvoiceDao.queryForAll();
        customListAdapter.addAll(lInvoiceList);
        customListAdapter.notifyDataSetChanged();
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
