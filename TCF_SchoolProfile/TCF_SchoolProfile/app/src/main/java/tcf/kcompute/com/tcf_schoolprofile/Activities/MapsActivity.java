package tcf.kcompute.com.tcf_schoolprofile.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import tcf.kcompute.com.tcf_schoolprofile.Adapters.FavoriteSchoolsAdapter;
import tcf.kcompute.com.tcf_schoolprofile.Adapters.SchoolsAdapter;
import tcf.kcompute.com.tcf_schoolprofile.Dialogs.DirectionsDialog;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.DAO;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.MyLocation;
import tcf.kcompute.com.tcf_schoolprofile.Models.SchoolModel;
import tcf.kcompute.com.tcf_schoolprofile.R;
import tcf.kcompute.com.tcf_schoolprofile.Utils.GpsUtils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    private List<String> listOfNearbySchools = new ArrayList<>();
    List<SchoolModel> schools;
  public   List<SchoolModel> filteredSchools;
    public GoogleMap mMap;
    public RecyclerView list_directions;
//    public RecyclerView list_favorite;
    LinearLayout ll_direction;
//    LinearLayout ll_favorite;
    Button bt_direction;
//    Button bt_favorite;
    Location currLocation = null;
    HashMap<Marker, Integer> hashMap;
    MyLocation myLocation;
//    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        dao = new DAO(this);

        checkGps();
        init();
    }

    private void init() {
        schools = DAO.getInstance(MapsActivity.this).GetAllSchoolData();

        list_directions = findViewById(R.id.list_directions);
//        list_favorite=findViewById(R.id.list_favorite);

        list_directions.setLayoutManager(new LinearLayoutManager(this));
//        list_favorite.setLayoutManager(new LinearLayoutManager(this));

        ll_direction = findViewById(R.id.ll_direction);
//        ll_favorite=findViewById(R.id.ll_favorite);
        bt_direction = findViewById(R.id.bt_direction);
//        bt_favorite=findViewById(R.id.bt_favorite);

        bt_direction.setOnClickListener(this);
//        bt_favorite.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pakistan = new LatLng(30.3753,69.3451);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pakistan, 5.50f));
//        dao.open();
//        List<SchoolModel> list = dao.GetAllSchoolData();
//
//        for (SchoolModel model : list) {
//            LatLng schoolsLatLng = new LatLng(model.getLatitude(), model.getLongitude());
//            MarkerOptions mo = new MarkerOptions();
//            mo.position(schoolsLatLng).
//                    title(model.getSchool()).
//                    icon(BitmapDescriptorFactory.fromResource(R.drawable.school_marker));
//            mMap.addMarker(mo);
//        }

//        dao.close();

        setUpMap();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    SchoolModel school = schools.get(hashMap.get(marker));
                    DirectionsDialog dd = new DirectionsDialog(MapsActivity.this, school,
                            currLocation);
                    dd.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_direction:
                if (schools.size() > 0)
                    showSearchResults();
                break;
//            case R.id.bt_favorite:
//                    showFavoriteSchoolsList();
        }
    }

//    private void showFavoriteSchoolsList() {
//        List<SchoolModel> favoriteSchoolsList = dao.getFavoriteSchools();
//        FavoriteSchoolsAdapter adapter=new FavoriteSchoolsAdapter(this,favoriteSchoolsList);
//
//        if (favoriteSchoolsList != null && favoriteSchoolsList.size() > 0) {
//
//
//            list_favorite.setAdapter(adapter);
//
//            if (list_favorite.getVisibility() == View.VISIBLE) {
//                list_favorite.setVisibility(View.GONE);
//            } else {
//                ll_favorite.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
//                list_favorite.setVisibility(View.VISIBLE);
//            }
//        }
//        else {
//            if (list_favorite.getVisibility() == View.VISIBLE) {
//                list_favorite.setVisibility(View.GONE);
//            }
//            Toast.makeText(getApplicationContext(), "No Favorite school!", Toast.LENGTH_LONG).show();
//            adapter.notifyDataSetChanged();
//        }
//    }

    public void setUpMap() {

        hashMap = new HashMap<Marker, Integer>();
        if (schools.size() > 0) {
            for (int i = 0; i < schools.size(); i++) {

                if (String.valueOf(schools.get(i).getLatitude()) != null &&
                        String.valueOf(schools.get(i).getLongitude()) != null) {
//                    MarkerOptions mo = new MarkerOptions();
//
//                    double lat = schools.get(i).getLatitude();
//                    double longi = schools.get(i).getLongitude();
//
//                    mo.position(new LatLng(lat, longi));
//                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.school_marker));

//                    Marker marker = mMap.addMarker(mo);

                    MarkerOptions mo = new MarkerOptions();
                    double lat = schools.get(i).getLatitude();
                    double longi = schools.get(i).getLongitude();

                    mo.position(new LatLng(lat, longi));
                    mo.title(schools.get(i).getSchool()).
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.school_building));
                    mMap.addMarker(mo);

                    Marker marker = mMap.addMarker(mo);
                    hashMap.put(marker, i);
                }
            }
        }




        this.myLocation = new MyLocation(this);
        myLocation.getLocation(this, locationResult);

//        Log.d("Lat",fusedLocationService.getLocation()+"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GpsUtils.REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_CANCELED: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Caution!").setMessage("For proper functioning of near me and directions functions please enable GPS by pressing 'Enable'")
                                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        new GpsUtils(MapsActivity.this).enableLoc();

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                        break;
                    }
                    case Activity.RESULT_OK:
                        if (myLocation != null)
                            myLocation.getLocation(MapsActivity.this, locationResult);
                        break;
                    default: {
                        break;
                    }
                }
                break;
        }
    }

    MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            currLocation = location;
            try {
                if (mMap != null && location != null) {
                    final LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    try {
                        MapsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.setMyLocationEnabled(true);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));
                            }
                        });

                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }

                    generateNearMe(location);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void checkGps() {
        GpsUtils utils = new GpsUtils(this);
        if (!utils.hasGPSDevice(this)) {
            Toast.makeText(this, "Gps not Supported", Toast.LENGTH_SHORT).show();
        }
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && utils.hasGPSDevice(this)) {
            utils.enableLoc();
        }
    }

    private void generateNearMe(Location loc) {
        try {
            List<SchoolModel> tempList = new ArrayList<>();
            for (SchoolModel model : schools) {
                //TODO change distance according to requirement now is showing schools around 10km
                if (calculateDistance(loc, model.getLatitude(), model.getLongitude()) < 10) {
                    tempList.add(model);
                }
            }
            filteredSchools = tempList;
            setAdapter("Locations Near Me", filteredSchools);
            tempList = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(String title, List<SchoolModel> schoolsList) {
        bt_direction.setText(title);
        for (SchoolModel model : schoolsList) {
            double roundedDistance = Math.round(calculateDistance(currLocation, model.getLatitude(), model.getLongitude()) * 100);
            roundedDistance = roundedDistance / 100;
            model.setNearMeDistance(roundedDistance);
//            model.setNearMeDistance(String.valueOf(calculateDistance(currLocation, model.getLatitude(), model.getLongitude())));

        }
        sortUsingDistance(schoolsList);
        list_directions.setAdapter(new SchoolsAdapter(this, schoolsList, currLocation));
    }

    private void sortUsingDistance(List<SchoolModel> schoolsList) {
        Collections.sort(schoolsList, new Comparator<SchoolModel>() {
            @Override
            public int compare(SchoolModel lhs, SchoolModel rhs) {
                return Double.compare(lhs.getNearMeDistance(),rhs.getNearMeDistance());
            }
        });
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

    private void showSearchResults() {
        if (filteredSchools != null && filteredSchools.size() > 0) {
            if (list_directions.getVisibility() == View.VISIBLE) {
                list_directions.setVisibility(View.GONE);
            } else {
                ll_direction.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
                list_directions.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No School's found near by!", Toast.LENGTH_LONG).show();
        }
    }
}
