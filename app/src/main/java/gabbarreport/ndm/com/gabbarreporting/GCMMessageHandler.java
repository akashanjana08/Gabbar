package gabbarreport.ndm.com.gabbarreporting;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import gabbarreport.ndm.com.gabbarreporting.general.GeneralNotification;

public class GCMMessageHandler extends IntentService{

	String msg="",ms="",title="",notaction;
    private Handler handle;



	public GCMMessageHandler() {
		super("GCMMessageHandler");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handle=new Handler();
		
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle extras=intent.getExtras();
		
		GoogleCloudMessaging gcm= GoogleCloudMessaging.getInstance(getApplicationContext());
		  String msgType    =   gcm.getMessageType(intent);

		  
		   msg   = extras.getString("message");
		try {
			title = extras.getString("Title");
			notaction = extras.getString("Action");
			ms = extras.getString("msg");
		}
		catch (Exception e){}

		  showToast();
		  
		  GCMReciever.completeWakefulIntent(intent);

	}


	
	public void showToast()
	{

		handle.post(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				Random rand = new Random();
				int  n = rand.nextInt(50) + 1;
				GeneralNotification.setNotificationMessage(getApplicationContext(),title,msg,n,ViewReportActivity.class );
			}
		});
		
	}





	
	

}
