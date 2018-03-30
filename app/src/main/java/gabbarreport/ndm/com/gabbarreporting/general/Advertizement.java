package gabbarreport.ndm.com.gabbarreporting.general;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import gabbarreport.ndm.com.gabbarreporting.R;

/**
 * Created by Ndm-PC on 8/31/2016.
 */
public class Advertizement
{
    public static void advertizement(LinearLayout ll,Context mContext,String unitId)
    {
        AdView adview=null;
        //LinearLayout ll=(LinearLayout)findViewById(R.id.forAddwindow);
        //LinearLayout ll=(LinearLayout)activity.findViewById(viewId);
        adview=new AdView(mContext);
        adview.setAdSize(AdSize.SMART_BANNER);
        adview.setAdUnitId(unitId);
        ll.addView(adview,0);
        AdRequest adreq=new AdRequest.Builder().build();
        adview.loadAd(adreq);
    }
}
