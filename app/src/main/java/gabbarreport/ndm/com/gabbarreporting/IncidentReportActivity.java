package gabbarreport.ndm.com.gabbarreporting;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;
import gabbarreport.ndm.com.gabbarreporting.database.AreaMasterTable;
import gabbarreport.ndm.com.gabbarreporting.database.CategoryMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.AreaMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CategoryMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.StateMaster;
import gabbarreport.ndm.com.gabbarreporting.general.AlertDialogRadio;
import gabbarreport.ndm.com.gabbarreporting.general.CalanderFormat;
import gabbarreport.ndm.com.gabbarreporting.general.ConnectionDetector;
import gabbarreport.ndm.com.gabbarreporting.general.SharedPrefence;
import gabbarreport.ndm.com.gabbarreporting.general.WebUrl;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.AsyncResult;
import gabbarreport.ndm.com.gabbarreporting.htturlasync.HttpAsyncResult;
import gabbarreport.ndm.com.gabbarreporting.imagemanupulation.CopyImage;
import gabbarreport.ndm.com.gabbarreporting.imagemanupulation.ImageUriPath;
import gabbarreport.ndm.com.gabbarreporting.imagemanupulation.ResizeImage;
import gabbarreport.ndm.com.gabbarreporting.parseservices.AreaParse;
import gabbarreport.ndm.com.gabbarreporting.parseservices.AsyncTaskUpload;
import gabbarreport.ndm.com.gabbarreporting.videorecording.VideoCapture;

public class IncidentReportActivity extends GabbarActionBarActivity implements AsyncResult,View.OnClickListener,AlertDialogRadio.AlertPositiveListener
{
    private int position = 0;
    private AreaMasterTable areaMasterTable;
    private CategoryMasterTable categoryMasterTable;
    private String []areaarray,catagoryarray;
    private List<AreaMasterObject> listareas;
    private List<CategoryMasterObject> listcatagory;
    private EditText areaEditText,categoryEditText,messageEditText,nameEditText,phoneEditText;
    private ProgressBar progressBar;
    private String areaCode,catCode;
    CircleImageView captureImageView,galleryImageView,vediorecImageView,audiorecimageview;
    private int CAMERA_REQUEST = 5,IMAGE_PICKER_SELECT=2,VIDEO_REQUEST=3;
    static Bitmap bitmapImg;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    List<String> list;
    TextView txtPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_report);
        setActionBarTitle("Gabbar Incident");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        initComponents();
        initialize();
        getImagePath();

    }


    public void saveInfo(View view)
    {
      if(checkEmptyValidation()) {
          String message = messageEditText.getText().toString();
          String name = nameEditText.getText().toString();
          String phone = phoneEditText.getText().toString();
          String deviceId = Settings.Secure.getString(this.getContentResolver(),
                  Settings.Secure.ANDROID_ID);
          JSONObject jsonObj = new JSONObject();
          JSONArray jar = new JSONArray();
          try
          {
              jar.put(jsonObj.put("areaId", areaCode).put("catId", catCode).put("message", message).put("name", name)
                      .put("phonenum", phone).put("deviceId", deviceId).put("dateTime", CalanderFormat.getDateTimeCalender()));

          }
          catch (Exception e)
          {
              e.printStackTrace();
          }

          if (ConnectionDetector.isConnectingToInternet(this))
          {
              File videofile = new File(Environment.getExternalStorageDirectory() + "/gabbar/gabbarvideo/");
              Set<String> imageuriList= ImageUriPath.getMp4file(videofile);
              list.addAll(imageuriList);

              if (list.size()>0)
              {
                  if(ConnectionDetector.isConnectingToInternet(this))
                  {

                      new AsyncTaskUpload(new ArrayList<String>(list), this,"BG101").execute();
                  }
              }
              else
              {
                  finish();
              }
              //new HttpAsyncResult(this, WebUrl.SEND_INFO_URL, jar.toString(), "AREA_DATA", this).execute();
          } else {
              Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
          }
      }

    }




    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.editTextarea)
        {
            showResonDialod(areaarray, "Please Select Area", "Area");
        }
        else if(v.getId()==R.id.editTextcategory)
        {
            showResonDialod(catagoryarray,"Please Select Category","Category");
        }
        else if(v.getId()==R.id.captureimageview)
        {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "gabbar/images/"+timeStamp+".jpg");
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        else if(v.getId()==R.id.galleryimageview)
        {

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/* video/*");
            startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
        }
        else if(v.getId()==R.id.videorecimageview)
        {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "gabbar/gabbarvideo/"+timeStamp+".mp4");
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            cameraIntent.putExtra("android.intent.extra.durationLimit", 60);
            cameraIntent.putExtra("EXTRA_VIDEO_QUALITY", 0);
            startActivityForResult(cameraIntent, VIDEO_REQUEST);
        }
        else if(v.getId()==R.id.audiorecimageview)
        {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "gabbar/gabbarvideo/"+timeStamp+".mp4");
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            cameraIntent.putExtra("android.intent.extra.durationLimit", 60);
            cameraIntent.putExtra("EXTRA_VIDEO_QUALITY", 0);
            startActivityForResult(cameraIntent, VIDEO_REQUEST);
        }
    }




    public void showResonDialod(String[] array,String title,String type)
    {
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();

        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();
        alert.setArryalertDialogRadio(array, title, type);

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
        if(type.equalsIgnoreCase("Area"))
        {
            areaEditText.setText("" + AlertDialogRadio.reason[this.position]);

            areaCode=listareas.get(position).getAreaCode();

            areaEditText.setError(null);
        }
        if(type.equalsIgnoreCase("Category"))
        {
            categoryEditText.setText("" + AlertDialogRadio.reason[this.position]);

            catCode=listcatagory.get(position).getCatId();
            categoryEditText.setError(null);
        }

    }



    private void initComponents()
    {
        areaEditText=(EditText)findViewById(R.id.editTextarea);
        categoryEditText=(EditText)findViewById(R.id.editTextcategory);
        messageEditText=(EditText)findViewById(R.id.editTextmessage);
        nameEditText=(EditText)findViewById(R.id.editTextname);
        phoneEditText=(EditText)findViewById(R.id.editTextphone);

        captureImageView=(CircleImageView)findViewById(R.id.captureimageview);
        galleryImageView=(CircleImageView)findViewById(R.id.galleryimageview);
        vediorecImageView=(CircleImageView)findViewById(R.id.videorecimageview);
        audiorecimageview=(CircleImageView)findViewById(R.id.audiorecimageview);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtPercentage= (TextView) findViewById(R.id.txtPercentage);

        listenerRegistration();
    }

    private void listenerRegistration()
    {

        areaEditText.setOnClickListener(this);
        categoryEditText.setOnClickListener(this);
        captureImageView.setOnClickListener(this);
        galleryImageView.setOnClickListener(this);
        vediorecImageView.setOnClickListener(this);
        audiorecimageview.setOnClickListener(this);

    }

    private void initialize()
    {

        areaMasterTable = new AreaMasterTable(this);
        categoryMasterTable=new CategoryMasterTable(this);

        //List area
        listareas=areaMasterTable.getAllAreaMaster();
        if(listareas!=null)
        {

            areaarray = new String[listareas.size()];
            for (int i = 0; i < listareas.size(); i++)
            {
                areaarray[i] = listareas.get(i).getAreaName();
            }
        }

        //List Catagory
        listcatagory=categoryMasterTable.getAllCategoryMaster();
        if(listcatagory!=null)
        {

            catagoryarray = new String[listcatagory.size()];
            for (int i = 0; i < listcatagory.size(); i++)
            {
                catagoryarray[i] = listcatagory.get(i).getCatName();
            }
        }


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        if(requestCode == IMAGE_PICKER_SELECT  && resultCode == RESULT_OK)
        {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Uri selectedImageUri = data.getData();
            Toast.makeText(this,selectedImageUri.toString(),Toast.LENGTH_LONG).show();
            String selectedMediaUri= ImageUriPath.getRealPathFromURI(this, selectedImageUri);
            File filedest = new File(Environment.getExternalStorageDirectory()+File.separator + "gabbar/images/"+timeStamp+".jpg");
            if (selectedImageUri.toString().contains("/images/"))
            {
              try {
                  CopyImage.copyFile(new File(selectedMediaUri),filedest);
                  Toast.makeText(this,"images handle",Toast.LENGTH_LONG).show();
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }

                //handle image
            }
            else  if (selectedImageUri.toString().contains("/video/"))
            {
                 filedest = new File(Environment.getExternalStorageDirectory()+File.separator + "gabbar/gabbarvideo/"+timeStamp+".mp4");
                Toast.makeText(this,"Video handle",Toast.LENGTH_LONG).show();
                try {
                    CopyImage.copyFile(new File(selectedMediaUri),filedest);
                    Toast.makeText(this,"Video handle",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == VIDEO_REQUEST  && resultCode == RESULT_OK)
        {
//            Uri selectedImageUri = data.getData();
//            String selectedMediaUri= ImageUriPath.getRealPathFromURI(this,selectedImageUri);
            Toast.makeText(this,"Capture Video",Toast.LENGTH_LONG).show();

        }

        getImagePath();
    }

    public String getDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    private Set<String> getImagePath()
    {

        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "gabbar/images/");

        Set<String> imageuriList= ImageUriPath.getfile(file);
        showBatryPwrProduct(imageuriList);
        int a=10;
        return  imageuriList;
    }


    void showBatryPwrProduct(Set<String> imageList)
    {
        list = new ArrayList<String>(imageList);

        File videofile = new File(Environment.getExternalStorageDirectory() + "/gabbar/gabbarvideo/");
        Set<String> imageuriList= ImageUriPath.getMp4file(videofile);
        list.addAll(imageuriList);



        HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScroll);
        if (scrollView.getChildCount() > 0) {
            scrollView.removeAllViews();

        }

        scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScroll);

        LinearLayout topLinearLayout = new LinearLayout(this);
//        int width = 150;
//        int height = 250;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topLinearLayout.setLayoutParams(parms);
        int paddingPixel = 15;
        float density = getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);

        topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < list.size(); i++)
        {

            int size = 10; //minimize  as much as you want

            String path=list.get(i);


            if (path.toString().contains("/images/")) {

                final ImageView imageView = new ImageView(this);
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                //imageView.setImageResource(R.drawable.gabbarlogo);
                int width = 150;
                int height = 150;
                LinearLayout.LayoutParams parmsi = new LinearLayout.LayoutParams(width, height);
                imageView.setLayoutParams(parmsi);

                if (path != null)
                {
                    Bitmap bitmapOriginal = BitmapFactory.decodeFile(path);
                    Bitmap bitmapsimplesize = Bitmap.createScaledBitmap(bitmapOriginal, bitmapOriginal.getWidth() / size, bitmapOriginal.getHeight() / size, true);
                    bitmapOriginal.recycle();
                    imageView.setImageBitmap(bitmapsimplesize);

                }
                topLinearLayout.addView(imageView);
            }
            else
            {

                VideoView videoView=new VideoView(this);

                int width = 150;
                int height = 150;
                LinearLayout.LayoutParams parmsi = new LinearLayout.LayoutParams(width, height);
                videoView.setLayoutParams(parmsi);

                videoView.setVideoPath(path);
                topLinearLayout.addView(videoView);

            }

           // imageView.setImageURI(Uri.fromFile(new File(list.get(i))));

            View v=new View(this);
            v.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(parm);

            topLinearLayout.addView(v);

        }

        scrollView.addView(topLinearLayout);

    }


    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("AREA_DATA"))
        {

            File videofile = new File(Environment.getExternalStorageDirectory() + "/gabbar/gabbarvideo/");
            Set<String> imageuriList= ImageUriPath.getMp4file(videofile);
            list.addAll(imageuriList);

            if (list.size()>0)
            {
                if(ConnectionDetector.isConnectingToInternet(this))
                {

                    new AsyncTaskUpload(new ArrayList<String>(list), this,data).execute();
                }
            }
            else
            {
                finish();
            }
        }

    }




    private boolean checkEmptyValidation()
    {

        if(areaEditText.getText().toString().trim().length() == 0){
            areaEditText.setError("Cannot be empty");
        }
        if(categoryEditText.getText().toString().equals("")) {

            categoryEditText.setError("Cannot be empty");
        }

        if(messageEditText.getText().toString().equals("")) {

            messageEditText.setError("Cannot be empty");
        }


         if ((areaEditText.getText().toString().trim().length() != 0) && (!categoryEditText.getText().toString().equals(""))
                && (!messageEditText.getText().toString().equals("")))
                {

            return true;
        }

        else
        {

            return false;
        }


    }


}
