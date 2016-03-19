package DataBase.Util;

/**
 * Created by suvp on 2/20/2016.
 */
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import DataBase.ManagedObjects.Bill;
import DataBase.ManagedObjects.Customer;
import DataBase.ManagedObjects.Invoice;
import DataBase.ManagedObjects.Item;
import DataBase.ManagedObjects.Product;
import Resource.ProductEnum;

public class OrmLiteDbHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "shopDatabase.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Product, Integer> productDao = null;
    private RuntimeExceptionDao<Product, Integer> productRuntimeDao = null;
    private RuntimeExceptionDao<Invoice, Integer> invoiceRuntimeDao = null;
    private RuntimeExceptionDao<Item, Integer> itemRuntimedao = null;

    public OrmLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(OrmLiteDbHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, Product.class);
            TableUtils.createTable(connectionSource, Invoice.class);
            TableUtils.createTable(connectionSource, Customer.class);
            TableUtils.createTable(connectionSource, Bill.class);
            TableUtils.createTable(connectionSource, Item.class);

            Log.i(OrmLiteDbHelper.class.getName(), "created Table Database Schema ");
        } catch (SQLException e) {
            Log.e(OrmLiteDbHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<Product, Integer> dao = getProductDAO();
        long millis = System.currentTimeMillis();
        // create some entries in the onCreate

        for(ProductEnum lEnum : ProductEnum.values())
        {
            Product simple = new Product(lEnum.displayedName, lEnum.type, lEnum.bodyType, lEnum.phase, lEnum.hp, lEnum.stage);
            dao.create(simple);
        }

        Log.i(OrmLiteDbHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(OrmLiteDbHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Product.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(OrmLiteDbHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Product, Integer> getProductDAO() {
        if (productRuntimeDao == null) {
            productRuntimeDao = getRuntimeExceptionDao(Product.class);
        }
        return productRuntimeDao;
    }

    public RuntimeExceptionDao<Invoice, Integer> getInvoiceDataDao()
    {
        if (invoiceRuntimeDao == null)
        {
            invoiceRuntimeDao = getRuntimeExceptionDao(Invoice.class);
        }
        return invoiceRuntimeDao;
    }
    public RuntimeExceptionDao<Item, Integer> getItemDao()
    {
        if (itemRuntimedao == null)
        {
            itemRuntimedao = getRuntimeExceptionDao(Item.class);
        }
        return itemRuntimedao;
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        productDao = null;
        productRuntimeDao = null;
    }
}
