package gabbarreport.ndm.com.gabbarreporting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.dataobject.AreaMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CategoryMasterObject;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class CategoryMasterTable extends  GabbarDatabase
{


    Context ctx;

    public CategoryMasterTable(Context context)
    {
        super(context);
        ctx = context;

    }


    public String insertdata(CategoryMasterObject attObj)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CATEGORY_ID, attObj.getCatId());
        values.put(KEY_CATEGORY_NAME, attObj.getCatName());


        db.insert(TABLE_CATEGORY_MASTER, null, values);

        db.close();

        return null;
    }




    public List<CategoryMasterObject> getAllCategoryMaster()
    {
        List<CategoryMasterObject> catMaster= new ArrayList<CategoryMasterObject>();
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_CATEGORY_MASTER;
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                CategoryMasterObject obj = new CategoryMasterObject();
                obj.setCatId(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_ID)));
                obj.setCatName(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME)));

                catMaster.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  catMaster;

    }



    public String getCatnameOvercatId(String catId)
    {
        String catName="";
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_CATEGORY_MASTER +" where "+KEY_CATEGORY_ID +" = '"+catId+"'";
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {

                catName=cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME));

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  catName;

    }
}
