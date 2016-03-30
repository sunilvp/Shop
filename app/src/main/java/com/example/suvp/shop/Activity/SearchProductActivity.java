package com.example.suvp.shop.Activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suvp.shop.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import DataBase.ManagedObjects.Product;
import DataBase.Util.OrmLiteDbHelper;
import com.example.suvp.shop.Adapters.CustomProductListAdapter;
import Resource.BodyType;
import Resource.HP;
import Resource.Stage;

/**
 * Created by suvp on 3/17/2016.
 */
public class SearchProductActivity extends ListActivity
{
    private final String LOG_TAG = getClass().getSimpleName();

    public final static String SERIALIZED_PRODUCT = "productPassed";
    public final static String SERIALIZED_PRODUCTS_SELECTED = "productSelectedToBeShown";
    public final static String SERIALIZED_PRODUCTS_FINALY_SELECTED = "productSelectedDone";

    private OrmLiteDbHelper dbHelper_;
    private final Context context = this;

    private Spinner hpSpinner;
    private Spinner bodyTypeSpinner;
    private Spinner stageSpinner;

    final List<Product> productListDb = new LinkedList<>();
    final List<Product> selectedProductList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_product);
        populateSpinners();

        final CustomProductListAdapter adapter = new CustomProductListAdapter(this,
                productListDb);
        setListAdapter(adapter);

        //Action to be performed on clicking search action
        setActionListenerForSearch(adapter);

        //When View Selected Items Button is clicked
        setActionListenerForViewSelected();

        //When Done Button is clicked
        setActionListenerForDone();

        //ItemOnClickAction
        setListItemActions(adapter);
    }

    private void setActionListenerForSearch(final CustomProductListAdapter aInAdapter)
    {   Button lSearchButton  = (Button)findViewById(R.id.buttonSearchProduct);
        lSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reInitializeDbTable(aInAdapter);
                HP lSelectedHp = (HP) hpSpinner.getSelectedItem();
                BodyType lSelectedBodyType = (BodyType) bodyTypeSpinner.getSelectedItem();
                Stage lSelectedStage = (Stage) stageSpinner.getSelectedItem();

                Log.i(LOG_TAG, "The Selected HP : " + lSelectedHp + " BodyType:" + lSelectedBodyType + " Stage:" + lSelectedStage);

                final RuntimeExceptionDao<Product, Integer> productDao = getHelper().getProductDAO();
                QueryBuilder<Product, Integer> lQueryBuilder = productDao.queryBuilder();
                final Where<Product, Integer> lWhere = lQueryBuilder.where();
                try {
                    lWhere.and(lWhere.eq(Product.COL_BODYTYPE, lSelectedBodyType),
                            lWhere.eq(Product.COL_STAGE, lSelectedStage),
                            lWhere.eq(Product.COL_HP, lSelectedHp));
                    PreparedQuery<Product> preparedQuery = null;

                    preparedQuery = lQueryBuilder.prepare();
                    List<Product> lProductsReceived = productDao.query(preparedQuery);
                    if (lProductsReceived != null && lProductsReceived.size() > 0) {
                        Log.i(LOG_TAG, "productsReceived found " + lProductsReceived.size());
                        for (Product ldbProduct : lProductsReceived) {
                            if (!productListDb.contains(ldbProduct)) {
                                productListDb.add(ldbProduct);
                            }
                        }
                        aInAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "No Item Present", Toast.LENGTH_SHORT).show();
                        Log.i(LOG_TAG, "NO PRODUCTS FOUND WITH FOR THE QUERY");
                    }
                } catch (SQLException e) {
                    Log.e(LOG_TAG, "Failed to generate query for the product");
                    e.printStackTrace();
                }
            }
        });
    }

    private void setActionListenerForViewSelected()
    {
        Button lSelectedProductsButton = (Button)findViewById(R.id.buttonSelectedProducts);
        lSelectedProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedProductList != null && selectedProductList.size() > 0)
                {
                    Intent singleProductListViewActivity = new Intent(context, ProductListViewActivity.class);
                    singleProductListViewActivity.putExtra(SERIALIZED_PRODUCTS_SELECTED, selectedProductList.toArray());
                    startActivity(singleProductListViewActivity);
                }
                else
                {
                    Toast.makeText(context, "No ItemSelected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setActionListenerForDone()
    {
        Button lDoneButton = (Button)findViewById(R.id.buttonDone);
        lDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doneResult = new Intent();
                if(selectedProductList != null && selectedProductList.size() > 0 )
                {
                    doneResult.putExtra(SERIALIZED_PRODUCTS_FINALY_SELECTED, selectedProductList.toArray());
                    setResult(RESULT_OK, doneResult);
                    finish();
                }
                else
                {
                    setResult(RESULT_CANCELED, doneResult );
                    finish();
                }
            }
        });
    }

    //Clear the current selection
    private void reInitializeDbTable(final CustomProductListAdapter aInAdapter)
    {
        aInAdapter.clear();
        aInAdapter.notifyDataSetChanged();
    }

    //Populate spinners with the possible values.
    private void populateSpinners()
    {
         hpSpinner = (Spinner)findViewById(R.id.spinnerHP);
         stageSpinner = (Spinner)findViewById(R.id.spinnerBodyType);
         bodyTypeSpinner = (Spinner)findViewById(R.id.spinnerStage);

        hpSpinner.setAdapter(getArrayAdapterT(Arrays.asList(HP.values())));
        stageSpinner.setAdapter(getArrayAdapterT(Arrays.asList(Stage.values())));
        bodyTypeSpinner.setAdapter(getArrayAdapterT(Arrays.asList(BodyType.values())));
    }

    //All List Based Actions
    private void setListItemActions(final CustomProductListAdapter aInAdapter)
    {
        ListView lv = (ListView) findViewById(android.R.id.list);
        //Setting on single item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product lSelectedProduct = productListDb.get(position);
                Intent singleProductViewAcitivity = new Intent(context, ProductActivity.class);
                singleProductViewAcitivity.putExtra(SERIALIZED_PRODUCT, lSelectedProduct);
                startActivity(singleProductViewAcitivity);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, "Long click of the item");

                boolean lIsDeleteAvailible = false;

                final CharSequence[] items = {"Add"};
                final CharSequence[] itemsDelete = {"Add", "Delete"};

                final Product lSelectedProduct = productListDb.get(position);
                if (selectedProductList.contains(lSelectedProduct)) {
                    lIsDeleteAvailible = true;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Action:");
                builder.setItems(lIsDeleteAvailible ? itemsDelete : items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            selectedProductList.add(lSelectedProduct);
                            Log.i(LOG_TAG, "Item Added to Selected List:" +lSelectedProduct.getDisplayedName());
                        } else {
                            selectedProductList.remove(lSelectedProduct);
                            Log.i(LOG_TAG, "Item Deleted from Selected List:" + lSelectedProduct.getDisplayedName());
                        }
                        aInAdapter.notifyDataSetChanged();
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
                //Says if the event is consumed or should be propogated .
                return true;
            }
        });
    }

    //Get arrayAdapters For each spinners
    private <T> ArrayAdapter<T>  getArrayAdapterT(List<T> aInSpinnerList)
    {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                this, android.R.layout.simple_spinner_item, aInSpinnerList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return  adapter;
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
