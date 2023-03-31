package com.example.monitoringsepedaapps;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EducationFragment extends Fragment {
    private RecyclerView rV_tips,rV_health;
    private ImageView img;
    EducationAdapter educationAdapter;
    ArrayList<EducationList> listEdu = new ArrayList<>();
    ArrayList<EducationList> listEdu2 = new ArrayList<>();
    private LinearLayout greetImg;
    private TextView greetText,tV_nama, tV_poin, tV_level;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore fstore;
    ProgressDialog pd;
    int reward_sebelum;
    public EducationFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        // Add the following lines to create RecyclerView
        rV_tips = view.findViewById(R.id.rV_tips);
        rV_health = view.findViewById(R.id.rV_health);
        greetImg = view.findViewById(R.id.greeting_img);
        greetText = view.findViewById(R.id.greeting_text);
        img = view.findViewById(R.id.coin);
        tV_nama = view.findViewById(R.id.tv_nama);
        tV_poin = view.findViewById(R.id.tv_poin);
        tV_level = view.findViewById(R.id.tv_level);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(getActivity());
        checkUser();
        greeting();
        showData();
        showDataHealth();


        LinearLayoutManager lm2 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rV_health.setLayoutManager(lm2);
        educationAdapter = new EducationAdapter(view.getContext(), listEdu);
        rV_health.setAdapter(educationAdapter);

        return view;
    }

    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12){
            greetText.setText("Selamat Pagi");
            greetImg.setBackgroundResource(R.drawable.img_default_half_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText.setText("Selamat Siang");
            greetImg.setBackgroundResource(R.drawable.img_default_half_afternoon);
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText.setText("Selamat Sore");
            greetImg.setBackgroundResource(R.drawable.img_default_half_without_sun);
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText.setText("Selamat Malam");
            greetText.setTextColor(Color.WHITE);
            greetImg.setBackgroundResource(R.drawable.img_default_half_night);
        }
    }

    private void checkUser(){
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tV_nama.setText(value.getString("nama"));
                    String poin = value.getString("poin");
                    String reward = value.getString("reward");
                    tV_poin.setText("Point: " +poin);
                    tV_level.setText("Reward: " +reward);

                    reward_sebelum = Integer.parseInt(reward);

                    if(reward_sebelum == 1){
                        img.setImageResource(R.drawable.img_coin_br);
                    }
                    else if(reward_sebelum == 2){
                        img.setImageResource(R.drawable.img_coin_sil);
                    }
                    else if(reward_sebelum == 3){
                        img.setImageResource(R.drawable.img_coin_em);
                    }
                }
            });
        }
    }

    public void showData() {
        pd.setTitle("Loading Data...");
        pd.show();
        fstore.collection("edu_tips")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        pd.dismiss();
                        for (DocumentSnapshot doc : snapshotList) {
                            EducationList edulist = new EducationList(
                                    doc.getString("judul"),
                                    doc.getString("deskripsi"),
                                    doc.getString("image"));
                            listEdu.add(edulist);
                        }
                        //adapter
                        educationAdapter = new EducationAdapter(getActivity(), listEdu);
                        rV_tips.setAdapter(educationAdapter);
                        rV_tips.setHasFixedSize(true);
                        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rV_tips.setLayoutManager(lm);
                        educationAdapter = new EducationAdapter(getActivity().getBaseContext(), listEdu);
                        rV_tips.setNestedScrollingEnabled(true);
                        rV_tips.setAdapter(educationAdapter);
                    }
                });
    }

    public void showDataHealth() {
        pd.setTitle("Loading Data...");
        pd.show();
        fstore.collection("edu_tahap")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        pd.dismiss();
                        for (DocumentSnapshot doc : snapshotList) {
                            EducationList edulist = new EducationList(
                                    doc.getString("judul"),
                                    doc.getString("deskripsi"),
                                    doc.getString("image"));
                            listEdu2.add(edulist);
                        }
                        //adapter
                        educationAdapter = new EducationAdapter(getActivity(), listEdu2);
                        rV_health.setAdapter(educationAdapter);
                        rV_health.setHasFixedSize(true);
                        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rV_health.setLayoutManager(lm);
                        educationAdapter = new EducationAdapter(getActivity().getBaseContext(), listEdu2);
                        rV_health.setNestedScrollingEnabled(true);
                        rV_health.setAdapter(educationAdapter);
                    }
                });
    }
}