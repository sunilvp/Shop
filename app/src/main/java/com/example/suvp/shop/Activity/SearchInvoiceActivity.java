package com.example.suvp.shop.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.shop.General.Utility;
import com.example.suvp.shop.R;

/**
 * Created by suvp on 3/28/2016.
 */
public class SearchInvoiceActivity extends FragmentActivity
{
    CheckBox invoiceNumberCheckBox;
    TextView serialNumberText;
    Button searchbutton;

    private final Context context = this;

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
        searchbutton = (Button)findViewById(R.id.buttonSearchInvoice);

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
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lErr = getErrorString();
                if(!Utility.isNullOrEmptyString(lErr))
                {
                    Toast.makeText(context, lErr, Toast.LENGTH_SHORT).show();
                }
                else
                {
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

    }
}
