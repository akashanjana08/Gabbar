package gabbarreport.ndm.com.gabbarreporting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.dataobject.AreaMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CityMaster;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class AreaMasterTable extends  GabbarDatabase
{


    Context ctx;

    public AreaMasterTable(Context context)
    {
        super(context);
        ctx = context;

    }


    public String insertdata(AreaMasterObject attObj)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AREA_ID, attObj.getAreaCode());
        values.put(KEY_AREA_NAME, attObj.getAreaName());
        values.put(KEY_CITY_ID, attObj.getCityCode());

        db.insert(TABLE_AREA_MASTER, null, values);

        db.close();

        return null;
    }




    public List<AreaMasterObject> getAllAreaMaster()
    {
        List<AreaMasterObject> areaMaster= new ArrayList<AreaMasterObject>();
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_AREA_MASTER;
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                AreaMasterObject obj = new AreaMasterObject();
                obj.setAreaCode(cursor.getString(cursor.getColumnIndex(KEY_AREA_ID)));
                obj.setAreaName(cursor.getString(cursor.getColumnIndex(KEY_AREA_NAME)));
                obj.setCityCode(cursor.getString(cursor.getColumnIndex(KEY_CITY_ID)));
                areaMaster.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  areaMaster;

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

    public void deleteData()
    {

        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_AREA_MASTER, null, null);
        db.close();


    }
}
