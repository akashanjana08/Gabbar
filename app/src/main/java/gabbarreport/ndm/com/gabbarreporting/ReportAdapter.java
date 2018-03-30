package gabbarreport.ndm.com.gabbarreporting;

/**
 * Created by Ndm-PC on 8/22/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gabbarreport.ndm.com.gabbarreporting.database.CategoryMasterTable;
import gabbarreport.ndm.com.gabbarreporting.dataobject.CategoryMasterObject;
import gabbarreport.ndm.com.gabbarreporting.dataobject.ReportsObject;
import gabbarreport.ndm.com.gabbarreporting.imageloader.ImageLoader;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder>  {

    private List<ReportsObject> reportList;
    private CategoryMasterTable catMaster;
    public static ImageLoader imageLoader;
    boolean isLoginView;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView description, catId , name ,phone;
        public ImageView imgNews,imgapprovestatus;


        public MyViewHolder(View view)
        {
            super(view);
            description = (TextView) view.findViewById(R.id.description);
            catId = (TextView) view.findViewById(R.id.catagoty);
            name = (TextView) view.findViewById(R.id.nametextview);
            phone = (TextView) view.findViewById(R.id.phonetextview);

            imgNews= (ImageView) view.findViewById(R.id.imgNews);
            imgapprovestatus= (ImageView) view.findViewById(R.id.imageViewapprove);
        }
    }


    public ReportAdapter(List<ReportsObject> reportList,CategoryMasterTable catMaster,ImageLoader imageLoader,boolean isLoginView) {
        this.reportList = reportList;
        this.catMaster  = catMaster;
        this.imageLoader=imageLoader;
        this.isLoginView=isLoginView;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reports_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        ReportsObject report = reportList.get(position);
        holder.description.setText(report.getDescription());
        //holder.description.setText(report.getInfoId());
        holder.catId.setText(catMaster.getCatnameOvercatId(report.getCatId()));

        holder.name.setText(report.getName());
        holder.phone.setText(report.getPhone());



        if(!isLoginView)
        {
            holder.name.setVisibility(View.GONE);
            holder.phone.setVisibility(View.GONE);
        }


        if(holder.imgNews!=null)
        {
            imageLoader.DisplayImage(report.getImage(), holder.imgNews);

        }
        if(holder.imgNews!=null)
        {
            imageLoader.DisplayImage(report.getImage(), holder.imgNews);

        }
        if(holder.imgapprovestatus!=null)
        {
            if(report.getApprovalStatus().equalsIgnoreCase("A")) {

                holder.imgapprovestatus.setImageResource(R.drawable.approvedimage);
            }
            else if(report.getApprovalStatus().equalsIgnoreCase("R"))
            {
                holder.imgapprovestatus.setImageResource(R.drawable.rejectedimage);
            }
            else if(report.getApprovalStatus().equalsIgnoreCase("P"))
            {
                holder.imgapprovestatus.setImageResource(R.drawable.pendingimage);
            }

        }

        final Context mcontext=holder.imgapprovestatus.getContext();
        holder.imgapprovestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(mcontext,"Approved",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


}
