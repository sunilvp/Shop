package com.example.suvp.shop.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.suvp.shop.R;

/**
 * Created by suvp on 3/15/2016.
 */
public class InvoiceMenuActivity extends AppCompatActivity
{
    private final String LOG_TAG = getClass().getSimpleName();
    private final Context context_ = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_menu);
        Log.i(LOG_TAG, "Invoice Menu Create");

        Button lAddInvoiceButton  = (Button)findViewById(R.id.addInvoice_button);
        Button lViewInvoiceButton  = (Button)findViewById(R.id.viewInvoice_button);
        Button lDeleteInvoiceButtion  = (Button)findViewById(R.id.deleteInvoice_button);
        Button lEditInvoiceButtion  = (Button)findViewById(R.id.editInvoice_button);

        lAddInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lInvoiceMenuActivity = new Intent(context_, AddInvoiceActivity.class);
                startActivity(lInvoiceMenuActivity);
            }
        });
    }


}
