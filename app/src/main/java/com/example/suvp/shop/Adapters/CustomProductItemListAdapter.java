package com.example.suvp.shop.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.shop.Activity.ProductActivity;
import com.example.suvp.shop.R;

import java.util.List;

import DataBase.ManagedObjects.Item;

/**
 * Created by suvp on 3/17/2016.
 */
public class CustomProductItemListAdapter extends ArrayAdapter<Item>
{
    private final Context context;
    private final List<Item> itemList_;

    public final static String SERIALIZED_PRODUCT = "productPassed";

    public CustomProductItemListAdapter(Context aInContext, List<Item> aInValues)
    {
        super(aInContext,-1 , aInValues);
        context = aInContext;
        itemList_ = aInValues;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView firstTextView = (TextView)(rowView.findViewById(R.id.listItem_displayedName));
        TextView secondTextView = (TextView)(rowView.findViewById(R.id.listItem_type));
        TextView serialNumberTextView = (TextView)(rowView.findViewById(R.id.editText));
        if(itemList_ != null && itemList_.size() >0 )
        {
            Item lItem = itemList_.get(position);

            firstTextView.setText(lItem.getProduct().getDisplayedName());
            secondTextView.setText(lItem.getProduct().getType().getType());
        }


        serialNumberTextView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                Toast.makeText(context, "Edit Listener", Toast.LENGTH_SHORT).show();
                String input;
                Item lItem = itemList_.get(position);
                if((event.getAction() == KeyEvent.ACTION_DOWN))
                {
                    input= v.getText().toString();
                    lItem.setSerialNumber(input);
                    return true; // consume.
                }
                return false; // pass on to other listeners.
            }
        });

        firstTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item lSelectedItem = itemList_.get(position);
                Intent singleProductViewActivity = new Intent(context, ProductActivity.class);
                singleProductViewActivity.putExtra(SERIALIZED_PRODUCT, lSelectedItem.getProduct());
                context.startActivity(singleProductViewActivity);
            }
        });

        firstTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show();
                final CharSequence[] items = {"Delete"};

                final Item lSelectedItem = itemList_.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Action:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        itemList_.remove(lSelectedItem);
                        notifyDataSetChanged();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                //Says if the event is consumed or should be propogated .
                return true;
            }
        });


        return rowView;
    }

    public List<Item> getItemList()
    {
        return itemList_;
    }

}