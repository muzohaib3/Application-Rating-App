package tcf.kcompute.com.tcf_schoolprofile.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;

import java.util.List;

import tcf.kcompute.com.tcf_schoolprofile.Activities.TabActivity;
import tcf.kcompute.com.tcf_schoolprofile.AsyncTasks.AsyncTaskUpdateData2;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.DAO;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.MyLocation;
import tcf.kcompute.com.tcf_schoolprofile.Models.SchoolModel;
import tcf.kcompute.com.tcf_schoolprofile.R;
import tcf.kcompute.com.tcf_schoolprofile.Utils.SchoolUtils;

public class FavoriteSchoolsAdapter extends RecyclerView.Adapter<FavoriteSchoolsAdapter.SchoolsViewholder> {

    private static Context context;
    private List<SchoolModel> favSchools;
    private static Activity activity;
    private static String longitude, latitude, favschoolLat, favschoolLng;
    private static LocationManager locationManager;
    boolean isLocationProvided = false;
    boolean isDirectionsClicked = false;

    private static LocationRequest mLocationRequest;

    private static long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private static long FASTEST_INTERVAL = 2000; /* 2 sec */
    private static ProgressDialog progressDialog;
    private static Location currLocation = null;
    private boolean isChecked = false;


    public FavoriteSchoolsAdapter(Context context, Activity activity, List<SchoolModel> favSchools,Location currLocation) {
        this.context = context;
        this.activity = activity;
        this.favSchools = favSchools;
        this.currLocation=currLocation;
    }
//    public FavoriteSchoolsAdapter(Context context, List<SchoolModel> favSchools) {
//        this.context = context;
//        this.favSchools = favSchools;
//    }


    @Override
    public SchoolsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fav_schools, parent, false);

        SchoolsViewholder schoolVH = new SchoolsViewholder(itemView);

        return schoolVH;
    }

    @Override
    public void onBindViewHolder(final SchoolsViewholder holder, final int position) {
        final int id = favSchools.get(position).getId();

        if (favSchools.get(position).getSchool() != null)
            holder.tv_schoolname.setText(favSchools.get(position).getSchool());
        if (favSchools.get(position).getSchoolLocalName() != null)
            holder.tv_school_localname.setText(favSchools.get(position).getSchoolLocalName());
        if (favSchools.get(position).getArea() != null)
            holder.tv_area.setText(favSchools.get(position).getArea());
        if (Double.valueOf(favSchools.get(position).getLatitude()) != null)
            favschoolLat = String.valueOf(favSchools.get(position).getLatitude());
        if (Double.valueOf(favSchools.get(position).getLongitude()) != null)
            favschoolLng = String.valueOf(favSchools.get(position).getLongitude());
    }

    @Override
    public int getItemCount() {
        return favSchools.size();
    }

    public  class SchoolsViewholder extends RecyclerView.ViewHolder {

        TextView tv_schoolname, tv_school_localname, tv_area;
        Button btn_getDirections;
        MyLocation myLocation = new MyLocation(context);

        public SchoolsViewholder(View itemView) {
            super(itemView);
            tv_schoolname = itemView.findViewById(R.id.tv_schoolname);
            tv_school_localname = itemView.findViewById(R.id.tv_school_localname);
            tv_area = itemView.findViewById(R.id.tv_area);
            btn_getDirections = itemView.findViewById(R.id.btn_getDirections);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                  Updating data from server
//                    DAO dao= new DAO(context);
//                    dao.open();

                    String schoolname = tv_schoolname.getText().toString().trim();
                    String SchoolID = DAO.getInstance(context).GetSchoolID(schoolname);
                    if (SchoolUtils.getInstance().isConnectedToInternet(context)) {
                        updateSchoolData(SchoolID, schoolname);
                    }
                    else {
                        Intent intent= new Intent(context, TabActivity.class);
                        intent.putExtra("schoolName", schoolname);
                        intent.putExtra("schoolid", SchoolID);
                        context.startActivity(intent);
                    }
//                    dao.close();
//                LatLng latLng = new LatLng(favSchools.get(position).getLatitude(), favSchools.get(position).getLongitude());
//                ((MapsActivity) context).mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
//                ((MapsActivity) context).list_favorite.setVisibility(View.GONE);

                }
            });
            btn_getDirections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myLocation.getLocation(context,locationResult);

                }
            });

        }
        public MyLocation.LocationResult locationResult=new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {

                String lat = Double.toString(location.getLatitude());
                String lng = Double.toString(location.getLongitude());

                if (lat != null && lng != null && favschoolLat != null && favschoolLng != null) {
                    String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + lat + "," + lng + "&daddr=" +
                            favschoolLat + "," + favschoolLng;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    context.startActivity(Intent.createChooser(intent, "Select an application"));
                }
            }
        };

        private void updateSchoolData(String schoolID, String schoolName) {
            AsyncTaskUpdateData2 asyncTaskUpdateData2 = new AsyncTaskUpdateData2(context,schoolID,schoolName);
            asyncTaskUpdateData2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            Intent intent=new Intent(context,TabActivity.class);
            intent.putExtra("schoolName", schoolName);
            context.startActivity(intent);
        }

    }
}