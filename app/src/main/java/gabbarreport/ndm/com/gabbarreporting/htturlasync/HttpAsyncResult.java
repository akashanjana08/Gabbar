package gabbarreport.ndm.com.gabbarreporting.htturlasync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import gabbarreport.ndm.com.gabbarreporting.httpurlconnection.HttpUrlServerConnection;


/**
 * Created by Ndm-PC on 12/14/2015.
 */
public class HttpAsyncResult extends AsyncTask<String,Void,String>
{
    Context mContext;
    String URLString=null,JsonString=null,stringKey=null;
    ProgressDialog pdialog;
    AsyncResult asyncResult;


    public HttpAsyncResult(Context mContext, String URLString, String JsonString,String stringKey,AsyncResult asyncResult)
    {
        this.mContext    = mContext;
        this.URLString   = URLString;
        this.JsonString  = JsonString;
        this.asyncResult = asyncResult;
        this.stringKey   = stringKey;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pdialog= new ProgressDialog(mContext);
        pdialog.setMessage("Please Wait...");
        pdialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        HttpUrlServerConnection httpUrlServerConnection=new HttpUrlServerConnection();
        String serverResult= httpUrlServerConnection.setURLandData(URLString,JsonString);

        return serverResult;
    }


    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        pdialog.dismiss();
        if(asyncResult!=null)
        {
            asyncResult.getResponse(stringKey,result);
        }


    }

}
