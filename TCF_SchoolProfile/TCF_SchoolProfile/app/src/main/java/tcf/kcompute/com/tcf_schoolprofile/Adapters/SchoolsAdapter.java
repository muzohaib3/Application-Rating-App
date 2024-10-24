package tcf.kcompute.com.tcf_schoolprofile.Adapters;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tcf.kcompute.com.tcf_schoolprofile.Activities.MapsActivity;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.DAO;
import tcf.kcompute.com.tcf_schoolprofile.Models.SchoolModel;
import tcf.kcompute.com.tcf_schoolprofile.R;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.SchoolsViewholder> {

    private Context context;
    private List<SchoolModel> schools;
    private Location currLocation;
//    private DAO dao;
    private int i;

    public SchoolsAdapter(Context context, List<SchoolModel> schools, Location currLocation) {
        this.context = context;
        this.schools = schools;
        this.currLocation = currLocation;
        i =schools.size();
    }


    @Override
    public SchoolsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cell_schools, parent, false);

        SchoolsViewholder schoolVH = new SchoolsViewholder(itemView);
//        dao=new DAO(context);

        return schoolVH;
    }

    @Override
    public void onBindViewHolder(final SchoolsViewholder holder, final int position) {
        final int id = schools.get(position).getId();

        if (schools.get(position).getSchool() != null)
            holder.tv_schoolname.setText(schools.get(position).getSchool());
        if (schools.get(position).getSchoolLocalName() != null)
            holder.tv_school_localname.setText(schools.get(position).getSchoolLocalName());
        if (schools.get(position).getArea() != null)
            holder.tv_area.setText(schools.get(position).getArea());
        if (schools.get(position).getPrincipal() != null)
            holder.tv_principal.setText(schools.get(position).getPrincipal());
        if (schools.get(position).getPrincipalContact() != null)
            holder.tv_principalcontact.setText(schools.get(position).getPrincipalContact());
//        if (schools.get(position).getFavourite_schools()==1){
//            holder.fav_img.setImageResource(R.drawable.favourite_icon2);
//        }
//        if (schools.get(position).getNearMeDistance() != null)
            holder.tv_distance.setText(schools.get(position).getNearMeDistance()+"\n Km");

//        double roundedDistance = Math.round(calculateDistance(currLocation, schools.get(position).getLatitude(),schools.get(position).getLongitude()) * 100);
//        roundedDistance = roundedDistance / 100;
//        holder.tv_distance.setText(roundedDistance + " \n km");


        //        ((MapsActivity) context).filteredSchools.get(position).setNearMeDistance(String.valueOf(roundedDistance));


//        schools.get(position).setNearMeDistance(String.valueOf(roundedDistance));

//        i--;
//        if (i == 1) {
//            sortUsingDistance();
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latLng = new LatLng(schools.get(position).getLatitude(), schools.get(position).getLongitude());
                ((MapsActivity) context).mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f));
                ((MapsActivity) context).list_directions.setVisibility(View.GONE);
            }
        });

//        holder.fav_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.fav_img.getDrawable().getConstantState() == holder.fav_img.getResources().getDrawable( R.drawable.favourite_icon1).getConstantState())
//                {
//                    holder.fav_img.setImageResource(R.drawable.favourite_icon2);
//                    Toast.makeText(context, "Added to favorite List", Toast.LENGTH_SHORT).show();
//                    int i= dao.setFavoriteSchools(id,1);
//                }
//                else {
//                    holder.fav_img.setImageResource(R.drawable.favourite_icon1);
//                    Toast.makeText(context, "Removed from favorite List", Toast.LENGTH_SHORT).show();
//                    int j= dao.setFavoriteSchools(id,0);
//                }
//            }
//        });
    }

//    private void sortUsingDistance() {
//        Collections.sort(schools, new Comparator<SchoolModel>() {
//            @Override
//            public int compare(SchoolModel o1, SchoolModel o2) {
//                return o1.getNearMeDistance().compareTo(o2.getNearMeDistance());
//            }
//        });
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    public static class SchoolsViewholder extends RecyclerView.ViewHolder {

        TextView tv_schoolname, tv_school_localname, tv_area, tv_principal,tv_principalcontact, tv_distance;
//        ImageView fav_img;

        public SchoolsViewholder(View itemView) {
            super(itemView);
            tv_schoolname = itemView.findViewById(R.id.tv_schoolname);
            tv_school_localname = itemView.findViewById(R.id.tv_school_localname);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_principal = itemView.findViewById(R.id.tv_principal);
            tv_principalcontact=itemView.findViewById(R.id.tv_principalcontact);
            tv_distance = itemView.findViewById(R.id.tv_distance);

//            fav_img=itemView.findViewById(R.id.fav_img);
        }
    }

    private float calculateDistance(Location loc, double latitude, double longitude) {
        Location loc1 = new Location("");
        loc1.setLatitude(loc.getLatitude());
        loc1.setLongitude(loc.getLongitude());

        Location loc2 = new Location("");
        loc2.setLatitude(latitude);
        loc2.setLongitude(longitude);
        return loc1.distanceTo(loc2) / 1000;
    }
}