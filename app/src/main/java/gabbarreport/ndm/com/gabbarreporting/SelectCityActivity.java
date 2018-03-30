package gabbarreport.ndm.com.gabbarreporting;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;
import gabbarreport.ndm.com.gabbarreporting.database.CityMasterTable;
import gabbarreport.ndm.com.gabbarreporting.database.StateMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.AreaMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CityMaster;
import gabbarreport.ndm.com.gabbarreporting.dataobject.StateMaster;
import gabbarreport.ndm.com.gabbarreporting.general.AlertDialogRadio;
import gabbarreport.ndm.com.gabbarreporting.general.ConnectionDetector;
import gabbarreport.ndm.com.gabbarreporting.general.WebUrl;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.AsyncResult;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.HttpAsyncResult;
import gabbarreport.ndm.com.gabbarreporting.parseservices.AreaParse;
import gabbarreport.ndm.com.gabbarreporting.parseservices.LocationParse;

public class SelectCityActivity extends GabbarActionBarActivity implements AlertDialogRadio.AlertPositiveListener,View.OnClickListener,AsyncResult
{


    private int position = 0;
    EditText cityEditText,stateEditText;
    private String []states,city;
    private List<StateMaster> liststates;
    private List<CityMaster>  listcity;
    private CityMasterTable cityMasterTable;
    String stateCode,cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        setActionBarTitle("Select Location");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        if(!checkCameraPermission(this))
        {
            permissions(SelectCityActivity.this);
        }

        initComponents();

        StateMasterTable stateMaster   =new StateMasterTable(this);
        cityMasterTable=new CityMasterTable(this);

        liststates=stateMaster.getAllBankMaster();
        if(liststates!=null)
        {

            states = new String[liststates.size()];
            for (int i = 0; i < liststates.size(); i++)
            {
                states[i] = liststates.get(i).getStateName();
            }
        }




    }

    private void initComponents()
    {
        stateEditText=(EditText)findViewById(R.id.editTextstate);
        cityEditText =(EditText)findViewById(R.id.editTextCity);

        listenerRegistration();
        initEditTextValue();
    }

    private void listenerRegistration()
    {

        stateEditText.setOnClickListener(this);
        cityEditText.setOnClickListener(this);
    }


    public void save(View view)
    {


      if(checkEmptyValidation()) {

          sharedPreferenceEdit.putString("COUNTRY", "INDIA");
          sharedPreferenceEdit.putString("STATE_CODE", stateCode);
          sharedPreferenceEdit.putString("CITY_CODE", cityCode);
          sharedPreferenceEdit.commit();

          if (ConnectionDetector.isConnectingToInternet(this)) {
              new HttpAsyncResult(this, WebUrl.GET_AREA_URL + "?cityID=" + cityCode, null, "AREA_DATA", this).execute();
          } else {
              Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
          }
      }



    }



    @Override
    public void onClick(View v)
    {


        if(v.getId()==R.id.editTextCity)
        {

           if(stateCode!=null)
           {

               listcity=cityMasterTable.getAllBankMaster(stateCode);
               if(listcity!=null)
               {

                   city = new String[listcity.size()];
                   for (int i = 0; i < listcity.size(); i++)
                   {
                       city[i] = listcity.get(i).getCityName();
                   }
               }

               showResonDialod(city, "Please Select City", "City");


           }
           else
           {
               Toast.makeText(this,"Select state First",Toast.LENGTH_LONG).show();
           }
        }
        if(v.getId()==R.id.editTextstate)
        {
           showResonDialod(states, "Please Select State", "State");
        }
    }


    public void showResonDialod(String[] array,String title,String type)
    {
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();

        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();
        alert.setArryalertDialogRadio(array,title,type);

        /** Creating a bundle object to store the selected item's index */
        Bundle b  = new Bundle();

        /** Storing the selected item's index in the bundle object */
        b.putInt("position", position);

        /** Setting the bundle object to the dialog fragment object */
        alert.setArguments(b);

        /** Creating the dialog fragment object, which will in turn open the alert dialog window */
        alert.show(manager, "alert_dialog_radio");


    }


    @Override
    public void onPositiveClick(int position,String type)
    {
        this.position = position;

        /** Setting the selected android version in the textview */
        if(type.equalsIgnoreCase("City"))
        {
              cityEditText.setText("" + AlertDialogRadio.reason[this.position]);

              cityCode=listcity.get(position).getCityCode();
              cityEditText.setError(null);
        }
        else if (type.equalsIgnoreCase("State"))
        {
              stateEditText.setText("" + AlertDialogRadio.reason[this.position]);
              cityEditText.setText("");
              cityEditText.setHint("--Select City--");
              if(liststates!=null)
              {
                  stateCode= liststates.get(position).getStateCode();
                  stateEditText.setError(null);
              }
        }


    }



    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("AREA_DATA"))
        {

            AreaParse.parseArea(data, this);
            Intent homeIntent=new Intent(this,HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }

    }

    public static void permissions(Context mContext)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            if (!android.provider.Settings.System.canWrite(mContext))
            {
                ((Activity)mContext).requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE
                        , android.Manifest.permission.CAMERA}, 4);

            }

        }
    }



    public static  boolean checkCameraPermission(Context mContext)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.CAMERA);


        if(permissionCheck==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }






    private boolean checkEmptyValidation()
    {

        if((cityEditText.getText().toString().trim().length() == 0)){
            cityEditText.setError("Please Select City");
        }
        if(stateEditText.getText().toString().trim().length() == 0) {

            stateEditText.setError("Please Select State");
        }

        if ((cityEditText.getText().toString().trim().length() != 0) && (stateEditText.getText().toString().trim().length() != 0))
        {

            return true;
        }

        else
        {

            return false;
        }

    }



    private void initEditTextValue()
    {
        String data=cityName;

        if(!cityName.equalsIgnoreCase(""))
        {
            cityEditText.setText(cityName);
            stateEditText.setText(stateName);
            this.stateCode=statecodeu;
        }
    }


}
