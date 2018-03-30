package gabbarreport.ndm.com.gabbarreporting.reporter;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import gabbarreport.ndm.com.gabbarreporting.R;
import gabbarreport.ndm.com.gabbarreporting.ViewReportActivity;
import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;
import gabbarreport.ndm.com.gabbarreporting.dataobject.ReportsObject;
import gabbarreport.ndm.com.gabbarreporting.general.CalanderFormat;
import gabbarreport.ndm.com.gabbarreporting.general.ConnectionDetector;
import gabbarreport.ndm.com.gabbarreporting.general.WebUrl;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.AsyncResult;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.HttpAsyncResult;
import gabbarreport.ndm.com.gabbarreporting.imagemanupulation.ImageUriPath;
import gabbarreport.ndm.com.gabbarreporting.parseservices.AsyncTaskUpload;

public class ReporterLoginActivity extends GabbarActionBarActivity implements AsyncResult
{

    EditText userNameEditText,passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_login);
        setActionBarTitle("Reporter Login");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        initComponents();

    }


    public void initComponents()
    {
        userNameEditText=(EditText)findViewById(R.id.editTextuserid);
        passwordEditText=(EditText)findViewById(R.id.editTextpassword);
    }



    public void onLogin(View view)
    {

        String username= userNameEditText.getText().toString();
        String password= passwordEditText.getText().toString();

        JSONObject jsonObj=new JSONObject();
        JSONArray jar=new JSONArray();
        try
        {
           String appId= sharedPreference.getString("APPLICATION_ID","");

            jar.put(jsonObj.put("userid", username).put("pass", password).put("appId", appId));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(ConnectionDetector.isConnectingToInternet(this))
        {
            new HttpAsyncResult(this, WebUrl.REPORTER_LOGIN_URL, jar.toString(), "LOGIN", this).execute();
        }
        else
        {
            Toast.makeText(this,"Please Connect To Internet",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("LOGIN"))
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
                    viewReportintent.putExtra("REPORTER_LOGIN","YES");
                    startActivity(viewReportintent);




                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



            //Toast.makeText(this,data,Toast.LENGTH_LONG).show();
        }

    }

}
