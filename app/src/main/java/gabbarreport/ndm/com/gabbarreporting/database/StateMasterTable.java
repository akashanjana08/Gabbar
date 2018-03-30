package gabbarreport.ndm.com.gabbarreporting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.dataobject.StateMaster;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class StateMasterTable extends  GabbarDatabase
{


    Context ctx;

    public StateMasterTable(Context context) {
        super(context);
        ctx = context;

    }


    public String insertdata(StateMaster attObj) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STATE_ID, attObj.getStateCode());
        values.put(KEY_STATE_NAME, attObj.getStateName());
        values.put(KEY_COUNTRY_CODE, attObj.getCountryCode());


        db.insert(TABLE_STATE_MASTER, null, values);


        db.close();

        return null;
    }




    public List<StateMaster> getAllBankMaster()
    {
        List<StateMaster> stateMaster= new ArrayList<StateMaster>();
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_STATE_MASTER +" where "+KEY_COUNTRY_CODE +" ='CON1105'";
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                StateMaster obj = new StateMaster();
                obj.setStateCode(cursor.getString(cursor.getColumnIndex(KEY_STATE_ID)));
                obj.setCountryCode(cursor.getString(cursor.getColumnIndex(KEY_COUNTRY_CODE)));
                obj.setStateName(cursor.getString(cursor.getColumnIndex(KEY_STATE_NAME)));
                stateMaster.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  stateMaster;

    }

    public String getStateNameOverStateId(String stateId)
    {
        String state="";
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_STATE_MASTER +" where "+KEY_STATE_ID +" = '"+stateId+"'";
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {

                state=cursor.getString(cursor.getColumnIndex(KEY_STATE_NAME));

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  state;

    }
}
