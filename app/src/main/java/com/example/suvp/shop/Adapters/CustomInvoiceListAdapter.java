package com.example.suvp.shop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suvp.shop.General.Utility;
import com.example.suvp.shop.R;

import java.util.Collection;
import java.util.List;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Item;

/**
 * Created by suvp on 3/28/2016.
 */
public class CustomInvoiceListAdapter extends ArrayAdapter<Invoice>
{

    private final Context context;
    private final List<Invoice> invoiceList_;

    public CustomInvoiceListAdapter(Context aInContext, List<Invoice> aInValues) {
        super(aInContext,-1 , aInValues);
        context = aInContext;
        invoiceList_ = aInValues;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_invoice, parent, false);

        TextView lInvoiceNumber = (TextView)(rowView.findViewById(R.id.textView_list_invoiceNumber));
        TextView lInvoiceDate = (TextView)(rowView.findViewById(R.id.textView_list_date));
        TextView lInvoiceNoOfItems = (TextView)(rowView.findViewById(R.id.textView_list_noOfItems));

        if(invoiceList_ != null && invoiceList_.size() > 0)
        {
            Invoice lInvoice = invoiceList_.get(position);
            lInvoiceNumber.setText(lInvoice.getInvoiceNumber());
            lInvoiceDate.setText(Utility.getDateStringFromDate(lInvoice.getDate()));
            lInvoiceNoOfItems.setText(""+lInvoice.getItemList().size()+" Items");
        }

        return rowView;
    }

    public void reInitList()
    {
        clear();
        notifyDataSetChanged();
    }

    public List<Invoice> getItemList()
    {
        return invoiceList_;
    }

    @Override
    public void addAll(Collection<? extends Invoice> aInInvoiceList)
    {
        super.addAll(aInInvoiceList);
        notifyDataSetChanged();
    }
}
