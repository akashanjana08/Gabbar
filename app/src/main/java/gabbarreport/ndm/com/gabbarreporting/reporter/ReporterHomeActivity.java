package gabbarreport.ndm.com.gabbarreporting.reporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import gabbarreport.ndm.com.gabbarreporting.IncidentReportActivity;
import gabbarreport.ndm.com.gabbarreporting.R;
import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;

public class ReporterHomeActivity extends GabbarActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_home);
        setActionBarTitle("Reporter Home");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void reporterLogin(View view)
    {

        Intent homeIntent=new Intent(this,ReporterLoginActivity.class);
        startActivity(homeIntent);

    }

    public void reporterNews(View view)
    {

        Intent homeIntent=new Intent(this,IncidentReportActivity.class);
        startActivity(homeIntent);

    }
}
