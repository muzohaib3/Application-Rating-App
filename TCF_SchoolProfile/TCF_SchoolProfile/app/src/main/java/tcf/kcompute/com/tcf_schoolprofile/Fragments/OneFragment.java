package tcf.kcompute.com.tcf_schoolprofile.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tcf.kcompute.com.tcf_schoolprofile.Activities.TabActivity;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.DAO;
import tcf.kcompute.com.tcf_schoolprofile.Models.SchoolModel;
import tcf.kcompute.com.tcf_schoolprofile.R;

public class OneFragment extends Fragment implements View.OnClickListener {

    TextView tv_province, tv_area, tv_region, tv_district, tv_town,
            tv_schoolname, tv_ecompund, tv_cluster, tv_units, tv_establishYear,tv_schoold;
//    private List<SchoolModel> list = new ArrayList<>();

    private SchoolModel schoolModel;
    ImageView fav_school_img, show_map_img;
    private int id = 0;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);

        init(view);
        setValuesToTextViews(view);

        return view;
    }

    private void init(View view) {
        tv_province = view.findViewById(R.id.tv_province);
        tv_area = view.findViewById(R.id.tv_area);
        tv_region = view.findViewById(R.id.tv_region);
        tv_district = view.findViewById(R.id.tv_district);
        tv_town = view.findViewById(R.id.tv_town);
        tv_schoold = view.findViewById(R.id.tv_schoold);
        tv_schoolname = view.findViewById(R.id.tv_schoolname);
        tv_ecompund = view.findViewById(R.id.tv_ecompund);
        tv_cluster = view.findViewById(R.id.tv_cluster);
        tv_units = view.findViewById(R.id.tv_units);
        tv_establishYear = view.findViewById(R.id.tv_establishYear);
        fav_school_img = view.findViewById(R.id.fav_school_img);
        show_map_img = view.findViewById(R.id.show_map_img);

        fav_school_img.setOnClickListener(this);
        show_map_img.setOnClickListener(this);
//        tv_schoolname.setSelected(true);
    }

    private void setValuesToTextViews(final View view) {

//        final DAO dao = new DAO(view.getContext());
//        dao.open();


        String school = ((TabActivity) getActivity()).school;

        if (school != null || !school.isEmpty() || !school.equals("null")) {
            schoolModel = DAO.getInstance(view.getContext()).GetSchoolDataByColumnAndName("School", school);
//            list = dao.GetSchoolDataByColumnAndName("School", school);
        }

        if(schoolModel != null) {
            tv_province.setText(schoolModel.getProvince());
            tv_area.setText(schoolModel.getArea());
            tv_region.setText(schoolModel.getRegion());
            tv_district.setText(schoolModel.getDistrict());
            tv_town.setText(schoolModel.getTown());
            tv_schoold.setText(schoolModel.getSchoolId()+"");
            tv_schoolname.setText(schoolModel.getSchool());
            tv_ecompund.setText(schoolModel.getECompound());
            tv_cluster.setText(schoolModel.getCluster());
            tv_units.setText(schoolModel.getUnits());
            tv_establishYear.setText(schoolModel.getEstablishmentYear());

            id = schoolModel.getId();
            if (schoolModel.getFavourite_schools() == 1) {
                fav_school_img.setImageResource(R.drawable.favourite_icon2);
            }
        }

//        dao.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fav_school_img:
//                DAO dao = new DAO(v.getContext());
//                dao.open();
                if (fav_school_img.getDrawable().getConstantState() == fav_school_img.getResources().getDrawable(R.drawable.favourite_icon1).getConstantState()) {
                    fav_school_img.setImageResource(R.drawable.favourite_icon2);
                    Toast.makeText(getContext(), "Added to favorite List", Toast.LENGTH_SHORT).show();
                    int i = DAO.getInstance(view.getContext()).setFavoriteSchools(id, 1);
                } else {
                    fav_school_img.setImageResource(R.drawable.favourite_icon1);
                    Toast.makeText(getContext(), "Removed from favorite List", Toast.LENGTH_SHORT).show();
                    int j = DAO.getInstance(view.getContext()).setFavoriteSchools(id, 0);
                }

//                dao.close();
                break;
            case R.id.show_map_img:
                Toast.makeText(view.getContext(), "Navigating to selected school", Toast.LENGTH_SHORT).show();

                if(schoolModel != null) {
                    double lat = schoolModel.getLatitude();
                    double lng = schoolModel.getLongitude();
                    String uri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + schoolModel.getSchool() + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    view.getContext().startActivity(Intent.createChooser(intent, "Select an application"));
                }

                break;
        }
    }
//        dao.close();
}
