package gabbarreport.ndm.com.gabbarreporting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.dataobject.CityMaster;
import gabbarreport.ndm.com.gabbarreporting.dataobject.StateMaster;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class CityMasterTable extends  GabbarDatabase
{


    Context ctx;

    public CityMasterTable(Context context) {
        super(context);
        ctx = context;

    }


    public String insertdata(CityMaster attObj)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CITY_ID, attObj.getCityCode());
        values.put(KEY_CITY_NAME, attObj.getCityName());
        values.put(KEY_STATE_ID, attObj.getStateCode());

        db.insert(TABLE_CITY_MASTER, null, values);

        db.close();

        return null;
    }




    public List<CityMaster> getAllBankMaster(String stateId)
    {
        List<CityMaster> cityMaster= new ArrayList<CityMaster>();
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_CITY_MASTER +" where "+KEY_STATE_ID +" = '"+stateId+"' order by "+KEY_CITY_NAME;
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                CityMaster obj = new CityMaster();
                obj.setStateCode(cursor.getString(cursor.getColumnIndex(KEY_STATE_ID)));
                obj.setCityCode(cursor.getString(cursor.getColumnIndex(KEY_CITY_ID)));
                obj.setCityName(cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME)));
                cityMaster.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  cityMaster;

    }



    public String getcityNameOverCityId(String cityId)
    {
        String city="";
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_CITY_MASTER +" where "+KEY_CITY_ID +" = '"+cityId+"'";
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {

                city=cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME));

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  city;

    }
}
