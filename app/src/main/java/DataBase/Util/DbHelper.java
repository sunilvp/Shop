package DataBase.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Resource.ProductEnum;

/**
 * Created by suvp on 1/25/2016.
 */
public class DbHelper extends SQLiteOpenHelper
{

    // All Static variables

    private static final String LOG = "DB Helper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shopDatabase.db";

    // Product table name
    public static final String TABLE_PRODUCTS = "products";


    //Table Coloumn Name
    public static final String COL_NAME = "name";
    public static final String COL_HP = "hp";
    public static final String COL_TYPE = "type";
    public static final String COL_BODY_TYPE = "bodyType";
    public static final String COL_PHASE = "phase";
    public static final String COL_STAGE = "stage";
    public static final String COL_PK = "ID";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(LOG, "OnCreate-> Creating the schema");

        String CREATE_CONTACTS_TABLE_SQL = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COL_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT," + COL_HP + " FLOAT,"
                + COL_TYPE + " TEXT, " +  COL_BODY_TYPE + " TEXT, " + COL_PHASE + " INTEGER, "
                + COL_STAGE + " INTEGER " +
        ")";
        try {
            db.execSQL(CREATE_CONTACTS_TABLE_SQL);
        }
        catch (Exception e)
        {
            Log.e(LOG, "Sql Exception"+e);
        }

        Log.v(LOG, "OnCreate-> Creating the schema");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //TODO DO NOTHING FOR NOW.
    }

    public void initializeProducts()
    {
        Log.v(LOG, "InitializeProducts-> Pushing the DB with Products");
        SQLiteDatabase database = getWritableDatabase();
        if(database != null && !database.isReadOnly())
        {
            database.beginTransaction();
            try
            {
                for(ProductEnum product : ProductEnum.values())
                {
                    ContentValues lContentValues = new ContentValues();
                    lContentValues.put(DbHelper.COL_NAME, product.displayedName);
                    lContentValues.put(DbHelper.COL_HP, product.hp.getHpFloat());
                    lContentValues.put(DbHelper.COL_TYPE, product.type.getType());
                    lContentValues.put(DbHelper.COL_BODY_TYPE, product.bodyType.getBodyType());
                    lContentValues.put(DbHelper.COL_PHASE, product.phase.getPhase());
                    lContentValues.put(DbHelper.COL_STAGE, product.stage.getStage());

                    String[] allColumns = {DbHelper.COL_NAME, DbHelper.COL_HP, DbHelper.COL_TYPE, DbHelper.COL_BODY_TYPE, DbHelper.COL_PHASE, DbHelper.COL_STAGE};

                    database.insert(DbHelper.TABLE_PRODUCTS, "", lContentValues);
                }
                database.setTransactionSuccessful();
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                database.endTransaction();
                close();
            }
            Log.v(LOG, "InitializeProducts<- Pushing the DB with Products");
        }
    }
}
