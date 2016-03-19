package com.example.suvp.shop.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suvp.shop.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Product;
import DataBase.Util.OrmLiteDbHelper;
import General.CustomProductListAdapter;

/**
 * Created by suvp on 2/20/2016.
 */
public class ProductListViewActivity extends ListActivity
{
    private final String LOG_TAG = getClass().getSimpleName();
    public final static String SERIALIZED_PRODUCT = "productPassed";

    private OrmLiteDbHelper dbHelper_;
    private List<Product> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final RuntimeExceptionDao<Product, Integer> lProductDao = getHelper().getProductDAO();
        setContentView(R.layout.product__list_view);
        Serializable lSerProduct = getIntent().getSerializableExtra(SearchProductActivity.SERIALIZED_PRODUCTS_SELECTED);
        if(lSerProduct == null)
        {
            Log.i(LOG_TAG, "Activity called from menu view");
            productsList = getDbData(lProductDao);
        }
        else
        {
            Log.i(LOG_TAG, "Activity called from View Selected Items");
            Object[] lReceivedProductArray = (Object[])lSerProduct;
            List<Product> lReceivedList = new ArrayList<>();
            if(lReceivedProductArray != null && lReceivedProductArray.length > 0)
            {
                for(Object lObject : lReceivedProductArray)
                {
                    lReceivedList.add((Product)lObject);
                }
                productsList = lReceivedList;
            }
            else
            {
                productsList = new LinkedList<>();
                Toast.makeText(this, "No ItemSelected", Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "No Items selected");
            }
        }

        final CustomProductListAdapter adapter = new CustomProductListAdapter(this,
                productsList);
        setListAdapter(adapter);
    }

    private List<Product> getDbData(RuntimeExceptionDao<Product, Integer> aInProductDao)
    {
        // get our dao
        final RuntimeExceptionDao<Invoice, Integer> invoiceDao = getHelper().getInvoiceDataDao();

        // query for all of the data objects in the database
        return aInProductDao.queryForAll();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Product lSelectedProduct = productsList.get(position);
        Intent singleProductViewAcitivity = new Intent(this, ProductActivity.class);
        singleProductViewAcitivity.putExtra(SERIALIZED_PRODUCT, lSelectedProduct);
        startActivity(singleProductViewAcitivity);
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
