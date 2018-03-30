package gabbarreport.ndm.com.gabbarreporting.parseservices;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import gabbarreport.ndm.com.gabbarreporting.database.CategoryMasterTable;
import gabbarreport.ndm.com.gabbarreporting.database.CityMasterTable;
import gabbarreport.ndm.com.gabbarreporting.database.StateMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CategoryMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CityMaster;
import gabbarreport.ndm.com.gabbarreporting.dataobject.StateMaster;
import gabbarreport.ndm.com.gabbarreporting.general.SharedPrefence;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class LocationParse
{

  public static void parseCityAndState(String JSONString,Context mContext)
   {
       StateMasterTable stateMasterTable=new StateMasterTable(mContext);
       CityMasterTable  cityMasterTable =new CityMasterTable(mContext);
       CategoryMasterTable categoryMasterTable=new CategoryMasterTable(mContext);

       SharedPreferences shareprefraence = SharedPrefence.getSharedPrefence(mContext);
       SharedPreferences.Editor edit=shareprefraence.edit();


       try
       {
          JSONObject jsonObject=new JSONObject(JSONString);

           String status= jsonObject.getString("Status");
           if(status.equalsIgnoreCase("Sucess"))
           {

               JSONArray jsonArray=jsonObject.getJSONArray("States");
               JSONObject stateJSONObject=null;
               for (int i=0;i<jsonArray.length();i++)
               {
                   stateJSONObject=jsonArray.getJSONObject(i);

                   String stateCode=stateJSONObject.getString("StateCode");
                   String stateName=stateJSONObject.getString("StateName");
                   String countryCode=stateJSONObject.getString("CountryCode");

                   StateMaster sm=new StateMaster(stateCode, stateName, countryCode);

                   stateMasterTable.insertdata(sm);
               }

               jsonArray=jsonObject.getJSONArray("Cities");
               for (int i=0;i<jsonArray.length();i++)
               {
                   stateJSONObject=jsonArray.getJSONObject(i);

                   String citycode=stateJSONObject.getString("Citycode");
                   String cityname=stateJSONObject.getString("CityName");
                   String stateId=stateJSONObject.getString("StateCode");

                   CityMaster cm=new CityMaster(citycode, cityname, stateId);

                   cityMasterTable.insertdata(cm);
               }



               jsonArray=jsonObject.getJSONArray("Category");
               for (int i=0;i<jsonArray.length();i++)
               {
                   stateJSONObject=jsonArray.getJSONObject(i);

                   String CategoryId=stateJSONObject.getString("CategoryCode");
                   String CategoryName=stateJSONObject.getString("CategoryName");


                   CategoryMasterObject cm=new CategoryMasterObject(CategoryId, CategoryName);

                   categoryMasterTable.insertdata(cm);
               }



               edit.putString("FIRST_LOGIN", "1");
               edit.commit();


           }




       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

   }

}
