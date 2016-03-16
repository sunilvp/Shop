package com.example.suvp.shop.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.suvp.shop.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.Date;
import java.util.List;

import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Product;
import DataBase.Util.OrmLiteDbHelper;
import General.CustomAdapter;

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
        setContentView(R.layout.product__list_view);
        getDbData();
    }

    private void getDbData()
    {
        // get our dao
        final RuntimeExceptionDao<Product, Integer> productDao = getHelper().getProductDAO();
        final RuntimeExceptionDao<Invoice, Integer> invoiceDao = getHelper().getInvoiceDataDao();

        // query for all of the data objects in the database
        productsList = productDao.queryForAll();

        Date lDate = new Date();
        Invoice lInvoice = new Invoice(1234, new Date());
        invoiceDao.create(lInvoice);
        Log.i(LOG_TAG, "Creating Invoice Object with data ");

        // query for all of the data objects in the database
        List<Invoice> listInvoice = invoiceDao.queryForAll();
        Invoice lInvoiceReceived =  listInvoice.get(0);
        Date lDateReceived = lInvoiceReceived.getDate();
        Log.i(LOG_TAG, "Received Invoice from DB" + lDateReceived.getTime());

        Log.i(LOG_TAG, "Received Invoice from DB" + " \t" + lInvoiceReceived.getInvoiceNumber());

        final CustomAdapter adapter = new CustomAdapter(this,
                productsList);
        setListAdapter(adapter);

        Button lRefreshButton = (Button)findViewById(R.id.refresh_button);
        lRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsList = productDao.queryForAll();

                adapter.notifyDataSetChanged();
            }
        });

        Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Product lSelectedProduct = productsList.get(position);
        Intent secondAcitivityIntent = new Intent(this, ProductActivity.class);
        secondAcitivityIntent.putExtra(SERIALIZED_PRODUCT, lSelectedProduct);
        startActivity(secondAcitivityIntent);
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
