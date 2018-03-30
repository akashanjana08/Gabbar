package gabbarreport.ndm.com.gabbarreporting;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;
import gabbarreport.ndm.com.gabbarreporting.database.CityMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CityMaster;
import gabbarreport.ndm.com.gabbarreporting.dataobject.ReportsObject;
import gabbarreport.ndm.com.gabbarreporting.general.Advertizement;
import gabbarreport.ndm.com.gabbarreporting.general.ConnectionDetector;
import gabbarreport.ndm.com.gabbarreporting.general.WebUrl;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.AsyncResult;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.HttpAsyncResult;
import gabbarreport.ndm.com.gabbarreporting.reporter.ReporterHomeActivity;
import gabbarreport.ndm.com.gabbarreporting.reporter.ReporterLoginActivity;

public class HomeActivity extends GabbarActionBarActivity implements AsyncResult
{

    TextView cityTitleTextview;
    LinearLayout loclinearlayout, ll;
    private final String UNIT_ID="ca-app-pub-1411313281688322/4507945294";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        setActionBarTitle("Gabbar Reporting");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        initComponents();
        init();

        Advertizement.advertizement(ll,this,UNIT_ID);
    }

    public void citizen(View view)
    {

        Intent homeIntent=new Intent(this,IncidentReportActivity.class);
        startActivity(homeIntent);

    }

    public void reporter(View view)
    {

        Intent homeIntent=new Intent(this,ReporterLoginActivity.class);
        startActivity(homeIntent);

    }


    public void viewReport(View view)
    {
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JSONObject jsonObj=new JSONObject();
        JSONArray jar=new JSONArray();
        try
        {
            jar.put(jsonObj.put("deviceId", deviceId));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(ConnectionDetector.isConnectingToInternet(this))
        {
            new HttpAsyncResult(this, WebUrl.VIEW_REPORT_URL, jar.toString(), "VIEWREPORT", this).execute();
        }
        else
        {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }

    }

    private void initComponents()
    {
        cityTitleTextview=(TextView)findViewById(R.id.citytitletextview);
        loclinearlayout  =(LinearLayout)findViewById(R.id.loclinearlayout);
        ll=(LinearLayout)findViewById(R.id.forAddwindow);
        listenerRegistration();
    }


    private void init()
    {
        cityTitleTextview.setText(cityName);
    }



    private void listenerRegistration()
    {
        loclinearlayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(HomeActivity.this,SelectCityActivity.class));
            }
        });

    }

    @Override
    public void getResponse(String key, String data)
    {
        if(key.equalsIgnoreCase("VIEWREPORT"))
        {
            try
            {
                JSONObject jsonObject=new JSONObject(data);
                String status=jsonObject.getString("status");
                if(status.equalsIgnoreCase("success"))
                {
                    JSONArray jsonArray =jsonObject.getJSONArray("reportList");

                    Gson gson = new Gson();
                    ReportsObject[] favoriteItems = gson.fromJson(jsonArray.toString(),
                            ReportsObject[].class);

                    List<ReportsObject> reportList = Arrays.asList(favoriteItems);
                    ArrayList<ReportsObject> reportarrayList = new ArrayList<ReportsObject>(reportList);
                    Intent viewReportintent=new Intent(this, ViewReportActivity.class);
                    viewReportintent.putExtra("REPORT_LIST",reportarrayList);
                    viewReportintent.putExtra("REPORTER_LOGIN","NO");
                    startActivity(viewReportintent);




                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }




//    private void advertizement()
//    {
//        LinearLayout ll=(LinearLayout)findViewById(R.id.forAddwindow);
//        adview=new AdView(this);
//        adview.setAdSize(AdSize.SMART_BANNER);
//        adview.setAdUnitId(UNIT_ID);
//        ll.addView(adview,0);
//        AdRequest adreq=new AdRequest.Builder().build();
//        adview.loadAd(adreq);
//    }

}
