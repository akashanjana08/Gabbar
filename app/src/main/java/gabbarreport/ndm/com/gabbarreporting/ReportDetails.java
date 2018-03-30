package gabbarreport.ndm.com.gabbarreporting;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;
import gabbarreport.ndm.com.gabbarreporting.database.CategoryMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.ReportsObject;
import gabbarreport.ndm.com.gabbarreporting.general.ApplicaionDialog;
import gabbarreport.ndm.com.gabbarreporting.general.CalanderFormat;
import gabbarreport.ndm.com.gabbarreporting.general.ConnectionDetector;
import gabbarreport.ndm.com.gabbarreporting.general.WebUrl;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.AsyncResult;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.HttpAsyncResult;
import gabbarreport.ndm.com.gabbarreporting.imageloader.ImageLoader;
import gabbarreport.ndm.com.gabbarreporting.imagemanupulation.ImageUriPath;
import gabbarreport.ndm.com.gabbarreporting.parseservices.AsyncTaskUpload;

public class ReportDetails extends GabbarActionBarActivity implements AsyncResult
{

    private TextView incidentnoTextView,dateTimeTextView,descriptionTextView;
    private ReportsObject reportsObject;
    private CategoryMasterTable  categoryMasterTable;
    public static ImageLoader imageLoader;
    private ImageView imageViews;
    ViewFlipper viewFlipper;
    String rep_login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_details);
        setActionBarTitle("Report Details");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        initComponents();
        reportsObject =(ReportsObject)getIntent().getSerializableExtra("REPORT_DETAIL");
        imageLoader = new ImageLoader(this);
        initvalue();

    }


    private void initComponents()
    {
        incidentnoTextView=(TextView)findViewById(R.id.incidentnumtextview);
        dateTimeTextView=(TextView)findViewById(R.id.datetimetextview);
        //catagoryTextView=(TextView)findViewById(R.id.catagorytextview);
        descriptionTextView=(TextView)findViewById(R.id.descriptiontextview);
        //imageViews = (ImageView)findViewById(R.id.about_us_image);
        viewFlipper = (ViewFlipper)findViewById(R.id.about_us_image);

    }

    void initvalue()
    {


        rep_login=getIntent().getStringExtra("REPORTER_LOGIN");


        // init table
        categoryMasterTable = new CategoryMasterTable(this);


        incidentnoTextView.setText(reportsObject.getInfoId());
        dateTimeTextView.setText(CalanderFormat.dateFormatConverter(reportsObject.getDateTime()));
        //catagoryTextView.setText(categoryMasterTable.getCatnameOvercatId(reportsObject.getCatId()));
        setActionBarTitle(categoryMasterTable.getCatnameOvercatId(reportsObject.getCatId()));
        descriptionTextView.setText(reportsObject.getDescription());



        if(imageViews!=null)
        {
            imageLoader.DisplayImage(reportsObject.getImage(), imageViews);

        }

        if(ConnectionDetector.isConnectingToInternet(this))
        {
            new HttpAsyncResult(this, WebUrl.IMAGES_URL+"?infoId="+reportsObject.getInfoId(), null, "IMAGES", this).execute();
        }
        else
        {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void setFlipperImage(String url)
    {

        ImageView image = new ImageView(getApplicationContext());
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.FIT_XY);

//        Picasso.with(this)
//                .load(url)
//                .into(image);
//        viewFlipper.addView(image);




        if(viewFlipper!=null)
        {
            imageLoader.DisplayImage(url, image);
            viewFlipper.addView(image);
        }
    }


    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("IMAGES"))
        {
             try
             {
                 JSONObject jsonobj=new JSONObject(data);
                 String status=jsonobj.getString("status");
                 if(status.equalsIgnoreCase("success"))
                 {
                     JSONArray jsonArray=jsonobj.getJSONArray("images");


                     for (int i=0;i<jsonArray.length();i++)
                     {
                         jsonobj=jsonArray.getJSONObject(i);

                         String imageUrl=jsonobj.getString("image");
                         setFlipperImage(imageUrl);
                     }
                 }

             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(rep_login.equalsIgnoreCase("YES"))
          getMenuInflater().inflate(R.menu.menu_approval, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.approve:

                ApplicaionDialog applicaionDialog= ApplicaionDialog.setMessage("", "Do you want to approved this Incident?", "Yes", "No", this);

                applicaionDialog.buildDialog(new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(ConnectionDetector.isConnectingToInternet(ReportDetails.this))
                        {
                            new HttpAsyncResult(ReportDetails.this, WebUrl.APPROVAL_STATUS_URL+"?approvalStatus=A&infoId="+reportsObject.getInfoId(), null, "approved",ReportDetails. this).execute();
                        }
                        else
                        {
                            Toast.makeText(ReportDetails.this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                return true;

            case R.id.reject:

                ApplicaionDialog applicaionDialog2= ApplicaionDialog.setMessage("","Do you want to Reject this Incident?","Yes","No", this);
                applicaionDialog2.buildDialog(new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(ConnectionDetector.isConnectingToInternet(ReportDetails.this))
                        {
                            new HttpAsyncResult(ReportDetails.this, WebUrl.APPROVAL_STATUS_URL+"?approvalStatus=R&infoId="+reportsObject.getInfoId(), null, "Reject",ReportDetails. this).execute();
                        }
                        else
                        {
                            Toast.makeText(ReportDetails.this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                });


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
