package gabbarreport.ndm.com.gabbarreporting;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.List;
import gabbarreport.ndm.com.gabbarreporting.actionbar.GabbarActionBarActivity;
import gabbarreport.ndm.com.gabbarreporting.database.CategoryMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.ReportsObject;
import gabbarreport.ndm.com.gabbarreporting.imageloader.ImageLoader;

public class ViewReportActivity extends GabbarActionBarActivity
{


    private RecyclerView recyclerView;
    private ReportAdapter mAdapter;
    List<ReportsObject> reporterList;
    public static ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        setActionBarTitle("Report List");
        actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CategoryMasterTable catMaster=new CategoryMasterTable(this);

        imageLoader = new ImageLoader(this);
        reporterList =(List<ReportsObject>)getIntent().getSerializableExtra("REPORT_LIST");

        final String rep_login=getIntent().getStringExtra("REPORTER_LOGIN");

        if(rep_login.equalsIgnoreCase("YES"))
        {
            mAdapter = new ReportAdapter(reporterList, catMaster, imageLoader,true );
        }
        else
        {
            mAdapter = new ReportAdapter(reporterList, catMaster, imageLoader, false);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);








        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ReportsObject reportsObject = reporterList.get(position);

                Intent reportDetailIntent = new Intent(getApplicationContext(), ReportDetails.class);
                reportDetailIntent.putExtra("REPORT_DETAIL", reportsObject);
                reportDetailIntent.putExtra("REPORTER_LOGIN", rep_login);
                startActivity(reportDetailIntent);


            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));




        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent=new Intent(ViewReportActivity.this,IncidentReportActivity.class);
                startActivity(homeIntent);


            }
        });

    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

     static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
     {

        private GestureDetector gestureDetector;
        private ViewReportActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ViewReportActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}