package gabbarreport.ndm.com.gabbarreporting;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import gabbarreport.ndm.com.gabbarreporting.database.GabbarDatabase;
import gabbarreport.ndm.com.gabbarreporting.general.ConnectionDetector;
import gabbarreport.ndm.com.gabbarreporting.general.CreateDirectory;
import gabbarreport.ndm.com.gabbarreporting.general.SharedPrefence;
import gabbarreport.ndm.com.gabbarreporting.general.WebUrl;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.AsyncResult;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.HttpAsyncResult;
import gabbarreport.ndm.com.gabbarreporting.parseservices.LocationParse;

public class SplashActivity extends Activity implements AsyncResult
{
     Intent myintent =null;
    SharedPreferences shareprefraence;
    SharedPreferences.Editor edit;
    GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();
        new GabbarDatabase(this);

        myintent= new Intent(this, SelectCityActivity.class);



       try {
           String flogin = shareprefraence.getString("FIRST_LOGIN", "0");
           if (flogin.equals("0"))
           {

               CreateDirectory.makeDirectory();

               new HttpAsyncResult(this, WebUrl.LOCATIONS_URL, null, "LOCATION_DATA", this).execute();
           }
           else
           {

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run()
                   {

                       String citycode = shareprefraence.getString("CITY_CODE", "0");
                       if(citycode.equals("0"))
                       {

                           //startActivity(myintent);
                       }
                       else
                       {
                           myintent=new Intent(SplashActivity.this,HomeActivity.class);
                       }
                       startActivity(myintent);
                       finish();
                   }
               }, 4000);

           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

    }



    @Override
    public void getResponse(String key, String data)
    {

      if(key.equalsIgnoreCase("LOCATION_DATA"))
      {
          getRegId();
          LocationParse.parseCityAndState(data, this);
          startActivity(myintent);
          finish();
      }

    }

    private void initialize()
    {

        shareprefraence = SharedPrefence.getSharedPrefence(this);
        edit=shareprefraence.edit();

    }





    void getRegId()
    {
        new AsyncTask<String, Void, String>(){

            ProgressDialog pd;
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
//    			pd=ProgressDialog.show(getApplicationContext(), "Wait", "Please");
            }
            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                String regig=null;
                try {

                    if(gcm==null)
                    {

                        gcm= GoogleCloudMessaging.getInstance(getApplicationContext());

                    }
                    regig= gcm.register("374711969100");

                } catch (Exception e) {
                    // TODO: handle exception
                    return e.getMessage();
                }
                return regig;
            }

            @Override
            protected void onPostExecute(String result)
            {
                // TODO Auto-generated method stub
                super.onPostExecute(result);

                edit.putString("APPLICATION_ID", result);
                edit.commit();


            }


        }.execute();


    }


}
