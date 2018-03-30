package gabbarreport.ndm.com.gabbarreporting.parseservices;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import gabbarreport.ndm.com.gabbarreporting.database.AreaMasterTable;
import gabbarreport.ndm.com.gabbarreporting.database.CityMasterTable;
import gabbarreport.ndm.com.gabbarreporting.database.StateMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.AreaMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CityMaster;
import gabbarreport.ndm.com.gabbarreporting.dataobject.StateMaster;
import gabbarreport.ndm.com.gabbarreporting.general.SharedPrefence;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class AreaParse
{

  public static void parseArea(String JSONString,Context mContext)
   {
       AreaMasterTable areaMasterTable=new AreaMasterTable(mContext);



       try
       {
          JSONObject jsonObject=new JSONObject(JSONString);

           String status= jsonObject.getString("Status");
           if(status.equalsIgnoreCase("Success"))
           {


               areaMasterTable.deleteData();

               JSONArray jsonArray=jsonObject.getJSONArray("Area");
               JSONObject stateJSONObject=null;
               for (int i=0;i<jsonArray.length();i++)
               {
                   stateJSONObject=jsonArray.getJSONObject(i);

                   String AreaId  =stateJSONObject.getString("AreaId");
                   String AreaName=stateJSONObject.getString("AreaName");
                   String CityId  =stateJSONObject.getString("CityId");

                   AreaMasterObject am=new AreaMasterObject(AreaId, AreaName, CityId);

                   areaMasterTable.insertdata(am);
               }

           }


       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

   }

}
