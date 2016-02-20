package com.example.suvp.shop;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import DataBase.ManagedObjects.Product;
import DataBase.Util.OrmLiteDbHelper;
import General.CustomAdapter;

/**
 * Created by suvp on 2/20/2016.
 */
public class ProductViewActivity extends ListActivity
{
    private final String LOG_TAG = getClass().getSimpleName();
    private OrmLiteDbHelper dbHelper_;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view);
        getDbData();
    }

    private void getDbData() {
        // get our dao
        RuntimeExceptionDao<Product, Integer> productDao = getHelper().getProductDAO();
        // query for all of the data objects in the database
        List<Product> list = productDao.queryForAll();
        Product[] lProductArray = new Product[list.size()];
        int i=0;
        for(Product lProduct : list)
        {
            lProductArray[i++]= lProduct;
        }

        CustomAdapter adapter = new CustomAdapter(this,
                lProductArray);
        setListAdapter(adapter);
        Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
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
