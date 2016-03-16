package com.example.suvp.shop.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.suvp.shop.R;

import java.io.Serializable;

import DataBase.ManagedObjects.Product;

/**
 * Created by suvp on 2/24/2016.
 */
public class ProductActivity extends ActionBarActivity {
    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_single);
        Serializable lSerProduct = getIntent().getSerializableExtra(ProductListViewActivity.SERIALIZED_PRODUCT);
        if(lSerProduct != null)
        {
            initializeWithProduct((Product)lSerProduct);
        }
        else
        {
            Log.i(LOG_TAG, "No item Selected");
        }

    }

    private void initializeWithProduct(Product aInProduct)
    {
        final TextView displayedName = (TextView)findViewById(R.id.product_displayedName);
        final TextView bodyType = (TextView)findViewById(R.id.product_bodyType);
        final TextView hp = (TextView)findViewById(R.id.product_hp);
        final TextView phase = (TextView)findViewById(R.id.product_phase);
        final TextView stage = (TextView)findViewById(R.id.product_stage);
        final TextView type = (TextView)findViewById(R.id.product_type);

        displayedName.setText(aInProduct.getDisplayedName());
        bodyType.setText(aInProduct.getBodyType().getBodyType());
        hp.setText(aInProduct.getHp().getString());
        phase.setText(aInProduct.getPhase().name());
        stage.setText(String.valueOf(aInProduct.getStage().getStage()));
        type.setText(aInProduct.getType().name());
    }
}

