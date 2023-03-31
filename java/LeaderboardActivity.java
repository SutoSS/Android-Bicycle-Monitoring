package com.example.monitoringsepedaapps;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<LeaderboardList> userArraylist;
    LeaderboardAdapter leaderboardAdapter;
//    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private LinearLayout greetImg;
    private ImageView img;
    int reward_sebelum;
    private TextView greetText, tV_nama,mPoin,mLevel;
    private static final String TAG="MUyeah";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        tV_nama = findViewById(R.id.tv_nama);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        greetImg = findViewById(R.id.greeting_img);
        greetText = findViewById(R.id.greeting_text);
        mPoin = findViewById(R.id.tv_poin);
        mLevel = findViewById(R.id.tv_level);
        img = findViewById(R.id.coin);

//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading.......");
//        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView_ldb);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userArraylist = new ArrayList<LeaderboardList>();
        leaderboardAdapter = new LeaderboardAdapter(LeaderboardActivity.this,userArraylist);

        recyclerView.setAdapter(leaderboardAdapter);

        checkUser();
        greeting();
        Eventgetchange();

//        for(int i = 0; i<userArraylist.size();i++){
//            Log.d(TAG,"On Create: level"+userArraylist.get(i));
//        }

    }

    private void Eventgetchange() {

        fstore.collection("user").orderBy("level", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
                            Log.e("Error",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                userArraylist.add(dc.getDocument().toObject(LeaderboardList.class));
                            }
                            leaderboardAdapter.notifyDataSetChanged();
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();

                        }
                    }
                });
    }
    private void checkUser() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    tV_nama.setText(value.getString("nama"));
                    String poin = value.getString("poin");
                    String reward = value.getString("reward");
                    mPoin.setText("Point: " +poin);
                    mLevel.setText("Reward: " +reward);
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
}