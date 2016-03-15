package com.example.suvp.shop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by suvp on 3/15/2016.
 */
public class InvoiceMenuActivity extends ActionBarActivity
{
    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_menu);
        Log.i(LOG_TAG, "Invoice Menu Create");
    }


}
