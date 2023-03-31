package com.example.monitoringsepedaapps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {

    private TextView nama,no_telp,email,mpoin,mlevel;//,reward,rank;
    private ImageView logout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        nama = v.findViewById(R.id.tV_name_profile);
        no_telp = v.findViewById(R.id.tV_no_telp_profile);
        email = v.findViewById(R.id.tV_email_profile);
        logout = v.findViewById(R.id.img_logout);
        mpoin = v.findViewById(R.id.tv_pointpr);
        mlevel = v.findViewById(R.id.tV_levelpr);
//        reward = v.findViewById(R.id.tV_reward);
//        rank = v.findViewById(R.id.tv_rank_ldb);
        checkUser();
//        if(rank.equals("1"))
//        {
//            reward.setText("motor");
//        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        return v;
    }

    private void checkUser(){
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    nama.setText(value.getString("nama"));
                    no_telp.setText(value.getString("no_telfon"));
                    email.setText(value.getString("email"));
                    mpoin.setText(value.getString("poin"));
                    mlevel.setText(value.getString("reward"));

                }
            });
        }
    }
}