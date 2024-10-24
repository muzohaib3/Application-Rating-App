package tcf.kcompute.com.tcf_schoolprofile.Fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import tcf.kcompute.com.tcf_schoolprofile.Activities.TabActivity;
import tcf.kcompute.com.tcf_schoolprofile.Helpers.DAO;
import tcf.kcompute.com.tcf_schoolprofile.Models.SchoolModel;
import tcf.kcompute.com.tcf_schoolprofile.R;

public class ThreeFragment extends Fragment {

    TextView tv_AreaManagerName, tv_AreaManagerContact, tv_AreaEducationManagerName, tv_AreaEducationManagerContact;

    private SchoolModel schoolModel;
//    private List<SchoolModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        init(view);
        setValuesToTextViews(view);
        return view;
    }

    private void init(View view) {
        tv_AreaManagerName = view.findViewById(R.id.tv_AreaManagerName);
        tv_AreaManagerContact = view.findViewById(R.id.tv_AreaManagerContact);
        tv_AreaEducationManagerName = view.findViewById(R.id.tv_AreaEducationManagerName);
        tv_AreaEducationManagerContact = view.findViewById(R.id.tv_AreaEducationManagerContact);
    }

    private void setValuesToTextViews(View view) {
//        DAO dao = new DAO(view.getContext());
//        dao.open();

        String school = ((TabActivity) getActivity()).school;

        if (school != null && !school.isEmpty() && !school.equals("null")) {
            schoolModel = DAO.getInstance(view.getContext()).GetSchoolDataByColumnAndName("School", school);
//            list = dao.GetSchoolDataByColumnAndName("School", school);
        }

        if(schoolModel != null) {
            if (schoolModel.getAreaManager() != null && !schoolModel.getAreaManager().equalsIgnoreCase("null"))
                tv_AreaManagerName.setText(schoolModel.getAreaManager());
            if (schoolModel.getAreaEducationManagerName() != null && !schoolModel.getAreaEducationManagerName().equalsIgnoreCase("null"))
                tv_AreaEducationManagerName.setText(schoolModel.getAreaEducationManagerName());

            tv_AreaManagerContact.setText(checkAMContact(schoolModel.getAreaManagerContact()));
            tv_AreaEducationManagerContact.setText(checkAMContact(schoolModel.getAreaEducationManagerContact()));
        }
//        dao.close();
    }


    private String checkAMContact(String contacts) {
        try {

            if (contacts.isEmpty() || contacts.toLowerCase().equals("null")) {
                contacts = "No Contact";
            } else {
//            contacts.replaceAll(";|,\\s+", "");
                contacts = contacts.replaceAll(";|,\\s", " ");
                String[] AreaManagerContact = contacts.split("\\s+");
                contacts = "";
                HashSet<String> hashSet = new HashSet<String>();
                ArrayList<String> values = new ArrayList<String>();
                values.addAll(Arrays.asList(AreaManagerContact));

                if (values.size() > 1) {
                    hashSet.addAll(values);
                    values.clear();
                    values.addAll(hashSet);

                    for (int i = 0; i < values.size(); i++) {
                        contacts += values.get(i) + "\n";
                    }
                } else {
                    return contacts;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        if (contacts != null && !contacts.equals("") && !contacts.isEmpty()) {
//            String[] AreaManagerContact = contacts.split("; ");
//            if (AreaManagerContact.length == 2) {
//                if (AreaManagerContact[0].equals(AreaManagerContact[1])) {
//                    AMContacts = AreaManagerContact[0];
//                }
//            }
//            else if(AreaManagerContact.length>2){
//                for (int i = 0; i < AreaManagerContact.length; i++) {
//
//                }
//            }
//            else {
//                AMContacts = contacts;
//            }
//        }
//        else {
//            AMContacts = "Null";
//        }
        return contacts;
    }
}
