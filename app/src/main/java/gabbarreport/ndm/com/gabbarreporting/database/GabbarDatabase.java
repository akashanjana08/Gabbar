package gabbarreport.ndm.com.gabbarreporting.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class GabbarDatabase extends SQLiteOpenHelper
{
    String CREATE_TABLE_STATE_MASTER,CREATE_TABLE_CITY_MASTER,CREATE_TABLE_AREA_MASTER,CREATE_TABLE_CATEGORY_MASTER;


    //Database Details
    private static final String DATABASE_NAME="Gabbar_Database";
    private static final int DATABASE_VERSION=1;

    //Table Details

    protected static final String TABLE_STATE_MASTER             = "StateMaster";
    protected static final String TABLE_CITY_MASTER              = "CityMaster";
    protected static final String TABLE_AREA_MASTER              = "AreaMaster";
    protected static final String TABLE_CATEGORY_MASTER          = "Category";



    // Table state
    protected static final String KEY_STATE_ID                   = "StateID";
    protected static final String KEY_STATE_NAME                 = "StateName";
    protected static final String KEY_COUNTRY_CODE               = "CountryCode";


    // Table City
    protected static final String KEY_CITY_ID                   = "CityID";
    protected static final String KEY_CITY_NAME                 = "CityName";



    // Table AREA
    protected static final String KEY_AREA_ID                   = "AreaID";
    protected static final String KEY_AREA_NAME                 = "AreaName";


    // Table CATEGORY
    protected static final String KEY_CATEGORY_ID               = "CategoryID";
    protected static final String KEY_CATEGORY_NAME             = "CategoryName";










    public GabbarDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub


        InitialTable();


        db.execSQL(CREATE_TABLE_STATE_MASTER);
        db.execSQL(CREATE_TABLE_CITY_MASTER);
        db.execSQL(CREATE_TABLE_AREA_MASTER);
        db.execSQL(CREATE_TABLE_CATEGORY_MASTER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    private void InitialTable()
    {

        CREATE_TABLE_STATE_MASTER  = "CREATE TABLE " + TABLE_STATE_MASTER + "("
                + KEY_STATE_ID + " TEXT,"
                + KEY_STATE_NAME + " TEXT," + KEY_COUNTRY_CODE +" TEXT"+")";


        CREATE_TABLE_CITY_MASTER  = "CREATE TABLE " + TABLE_CITY_MASTER + "("
                + KEY_CITY_ID + " TEXT,"
                + KEY_CITY_NAME + " TEXT," + KEY_STATE_ID +" TEXT"+")";




        CREATE_TABLE_AREA_MASTER  = "CREATE TABLE " + TABLE_AREA_MASTER + "("
                + KEY_AREA_ID + " TEXT,"
                + KEY_AREA_NAME + " TEXT," + KEY_CITY_ID +" TEXT"+")";



        CREATE_TABLE_CATEGORY_MASTER= "CREATE TABLE " + TABLE_CATEGORY_MASTER + "("
                + KEY_CATEGORY_ID + " TEXT,"
                + KEY_CATEGORY_NAME + " TEXT"+")";


    }

}

