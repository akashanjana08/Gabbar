package gabbarreport.ndm.com.gabbarreporting.parseservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import gabbarreport.ndm.com.gabbarreporting.imagemanupulation.DeleteImage;

/**
 * Created by Ndm-PC on 8/8/2016.
 */
public class AsyncTaskUpload extends AsyncTask<String,Void,String>
{

    ArrayList<String> uploadList;
    Context mContext;
    String infoID;
    ProgressDialog pdialog;

    public AsyncTaskUpload(ArrayList<String> uploadList, Context mContext,String infoID)
    {
        this.uploadList=uploadList;

        this.mContext=mContext;
        this.infoID=infoID;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdialog= new ProgressDialog(mContext);
        pdialog.setMessage("Please Wait while upload Media...");
        pdialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {


        try {
         do {

          String sourceFileUri =  uploadList.get(0);


          String fileName = sourceFileUri+"ccccc"+infoID;
          int serverResponseCode = 0;
          HttpURLConnection conn = null;
          DataOutputStream dos = null;
          String lineEnd = "\r\n";
          String twoHyphens = "--";
          String boundary = "*****";
          int bytesRead, bytesAvailable, bufferSize;
          byte[] buffer;
          int maxBufferSize = 5 * 1024 * 1024;


          File sourceFile = new File(sourceFileUri);
          //String upLoadServerUri = "http://www.bloodtrackerplus.in/saveimage.php";

           String upLoadServerUri ="http://49.50.70.41/CerrebroServiceV11/CerrebroService/Pages/UploadLocationImage.aspx";


          if (!sourceFile.isFile())
          {

              String sou = "File not exeis";

          }
          else
          {

                  // open a URL connection to the Servlet
                  FileInputStream fileInputStream = new FileInputStream(sourceFile);
                  URL url = new URL(upLoadServerUri);

                  // Open a HTTP  connection to  the URL
                  conn = (HttpURLConnection) url.openConnection();
                  conn.setDoInput(true); // Allow Inputs
                  conn.setDoOutput(true); // Allow Outputs
                  conn.setUseCaches(true); // Don't use a Cached Copy
                  conn.setRequestMethod("POST");
                  conn.setRequestProperty("Connection", "Keep-Alive");
                  conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                  conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                  conn.setRequestProperty("uploaded_file", fileName);


                  dos = new DataOutputStream(conn.getOutputStream());

                  dos.writeBytes(twoHyphens + boundary + lineEnd);
                  dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename= '"
                          + fileName + "'" + lineEnd);

                  dos.writeBytes(lineEnd);

                  // create a buffer of  maximum size
                  bytesAvailable = fileInputStream.available();

                  bufferSize = Math.min(bytesAvailable, maxBufferSize);
                  buffer = new byte[bufferSize];

                  // read file and write it into form...
                  bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                  while (bytesRead > 0)
                  {
                      dos.write(buffer, 0, bufferSize);
                      bytesAvailable = fileInputStream.available();
                      bufferSize = Math.min(bytesAvailable, maxBufferSize);
                      bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                  }
                  // send multipart form data necesssary after file data...
                  dos.writeBytes(lineEnd);
                  dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                  // Responses from the server (code and message)
                  serverResponseCode = conn.getResponseCode();
                  String serverResponseMessage = conn.getResponseMessage();

                  Log.i("uploadFile", "HTTP Response is : "
                          + serverResponseMessage + ": " + serverResponseCode);

                  if (serverResponseCode == 200)
                  {
                      fileInputStream.close();
                      dos.flush();
                      dos.close();
                      DeleteImage.delete(new File(uploadList.get(0)));
                      uploadList.remove(0);



                  }

                  //close the streams //

          }

        }
        while (uploadList.size() > 0);

        } catch (MalformedURLException ex) {

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            e.printStackTrace();

        }

        return "";
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pdialog.dismiss();
    }
}
