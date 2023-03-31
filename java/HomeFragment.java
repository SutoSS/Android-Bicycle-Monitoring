package com.example.monitoringsepedaapps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;


public class HomeFragment extends Fragment {
    private LinearLayout greetImg;
    private ImageView img;
    private TextView greetText, tV_nama, mPoin, mLevel;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore fstore;
    private LinearLayout ll_play, ll_leaderboard;
    private ImageView iV_shop;
    int intPoin, reward_sebelum;
    float flDistance, flWaktu;


    public HomeFragment() {
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        greetImg = v.findViewById(R.id.greeting_img);
        greetText = v.findViewById(R.id.greeting_text);
        tV_nama = v.findViewById(R.id.tv_nama);
        ll_play = v.findViewById(R.id.LL_play);
        mPoin = v.findViewById(R.id.tv_point);
        mLevel = v.findViewById(R.id.tv_level);
        ll_leaderboard = v.findViewById(R.id.LL_leaderboard);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        img = v.findViewById(R.id.coin);
        greeting();
        checkUser();

        ll_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PlayActivity.class));
            }
        });

        ll_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LeaderboardActivity.class));
            }
        });
        return v;
    }

    @SuppressLint("SetTextI18n")
    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
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

    private void checkUser() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ESP32_APP");
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tV_nama.setText(value.getString("nama"));
                    String nama = value.getString("nama");
                    String poin = value.getString("poin");
                    String reward = value.getString("reward");
                    mPoin.setText("Point: " + poin);
                    mLevel.setText("Reward: " + reward);
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

                    int a = 0;

                    HashMap hashMap = new HashMap();
                    hashMap.put("Nama", nama);
                    hashMap.put("AVERAGE", a);
                    hashMap.put("CADENCE", a);
                    hashMap.put("CALORIES", a);
                    hashMap.put("DISTANCE", a);
                    hashMap.put("HOUR", a);
                    hashMap.put("KECEPATAN", a);
                    hashMap.put("MINUTE", a);
                    hashMap.put("RPM", a);
                    reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                        }
                    });
                }
            });
        }
    }
}

//    private void checkUser(){
//        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ESP32_APP");
//        final FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null){
//            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
//
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String distance = snapshot.child("DISTANCE").getValue().toString();
//                    String waktu = snapshot.child("MINUTE").getValue().toString();
//                    flDistance = Float.parseFloat(distance);
//                    flWaktu = Float.parseFloat(waktu);
//
//                    //Poin
//                    if(flDistance >= 1 && flDistance < 2 ){
//                        fstore.collection("user").document(user.getUid()).update("poin","1");
//                    }
//                    else if(flDistance >= 2 && flDistance < 3){
//                        fstore.collection("user").document(user.getUid()).update("poin","3");
//                    }
//                    else if(flDistance >= 4 && flDistance < 6){
//                        fstore.collection("user").document(user.getUid()).update("poin","6");
//                    }
//                    else if(flDistance >= 6 && flDistance <= 10){
//                        fstore.collection("user").document(user.getUid()).update("poin","6");
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    String nama = value.getString("nama");
//                    String poin = value.getString("poin");
//                    String level = value.getString("level");
////                    tV_nama.setText(value.getString("email"));
//                    tV_nama.setText(nama);
//                    mPoin.setText("Point: "+poin);
//                    mLevel.setText("Level: "+level);
//
//                    //poin 12> level>= 11 -- level 10
//                    //poin 11> level>= 10 -- level 9
//                    //poin 10> level>= 9 -- level 8
//                    //poin 9> level>= 8 -- level 7
//                    //poin 8> level>= 7 -- level 6
//                    //poin 7> level>= 6 -- level 5
//                    //poin 6> level>= 5 -- level 4
//                    //poin 5> level>= 4 -- level 3
//                    //poin 4> level>= 3 -- level 2
//                    //poin 3> level >= 2 -- level 1
//
//
//                    HashMap hashMap = new HashMap();
//                    hashMap.put("Nama", nama);
//                    reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                        @Override
//                        public void onSuccess(Object o) {
//
//                        }
//
//
//                    });
//
//                }
//            });
//
//
//        }else{
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }
//    }
//}