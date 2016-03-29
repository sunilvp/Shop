package com.example.suvp.shop.Activity;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Item;

import android.app.ListActivity;
import android.os.Bundle;

import android.widget.TextView;

import com.example.suvp.shop.Adapters.CustomProductItemListAdapter;
import com.example.suvp.shop.General.Utility;
import com.example.suvp.shop.R;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by suvp on 3/29/2016.
 */
public class InvoiceViewActivity  extends ListActivity
{
    private Invoice invoice_;
    private TextView invoiceNumberTextView_;
    private TextView dateTextView_;
    private CustomProductItemListAdapter itemListAdapter_;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_view);
        initialize();
    }

    private void initialize()
    {
        Serializable lSerProduct = getIntent().getSerializableExtra(SearchInvoiceActivity.SERIALIZED_INVOICE);
        if(lSerProduct != null && lSerProduct instanceof Invoice)
        {
            invoice_ = (Invoice)lSerProduct;
        }
        invoiceNumberTextView_ = (TextView)findViewById(R.id.editTextSingleInvoiceNumber);
        dateTextView_ = (TextView)findViewById(R.id.editTextSingleDate);

        invoiceNumberTextView_.setEnabled(false);
        dateTextView_.setEnabled(false);

        itemListAdapter_ = new CustomProductItemListAdapter(this, new LinkedList<Item>(), false);
        setListAdapter(itemListAdapter_);

        setData();
    }

    private void setData()
    {
        if(invoice_ != null)
        {
            invoiceNumberTextView_.setText(invoice_.getInvoiceNumber());
            dateTextView_.setText(Utility.getDateStringFromDate(invoice_.getDate()));

            reInitList();

            itemListAdapter_.addAll(invoice_.getItemList());
            itemListAdapter_.notifyDataSetChanged();
        }
    }

    private void reInitList()
    {
        itemListAdapter_.clear();
        itemListAdapter_.notifyDataSetChanged();
    }
}
