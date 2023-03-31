package com.example.monitoringsepedaapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class PlayActivity extends AppCompatActivity {
    private ImageView iV_speed, img;
    private LinearLayout greetImg;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DocumentReference reference;
    private FirebaseFirestore fstore;
    private CollectionReference notebookRef;
    float Jarak, Jarak_Final, Jarak_sebelum;
    int Poin, reward_sebelum = 0, level_final, poin_final, reward;
    private TextView greetText, tV_nama,speed,rpm,calories,distance,cadence,average,mPoin,mLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        iV_speed = findViewById(R.id.img_speed);
        greetImg = findViewById(R.id.greeting_img);
        greetText = findViewById(R.id.greeting_text);
        img = findViewById(R.id.coin);
        tV_nama = findViewById(R.id.tv_nama);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        notebookRef = fstore.collection("user");
        speed = findViewById(R.id.tV_speed);
        rpm = findViewById(R.id.tV_rpm);
        calories = findViewById(R.id.tV_kalori);
        distance = findViewById(R.id.tV_distance);
        cadence = findViewById(R.id.tV_cadence);
        average = findViewById(R.id.tV_average);
        mPoin = findViewById(R.id.tv_pointpl);
        mLevel = findViewById(R.id.tv_levelpl);

        checkUser();
        greeting();
        testingData();

    }

    private void testingData() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ESP32_APP");
        if(user != null) {
            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String nilai1 = snapshot.child("AVERAGE").getValue().toString();
                    String nilai2 = snapshot.child("CADENCE").getValue().toString();
                    String nilai3 = snapshot.child("CALORIES").getValue().toString();
                    String nilai4 = snapshot.child("DISTANCE").getValue().toString();
                    String nilai5 = snapshot.child("KECEPATAN").getValue().toString();
                    String nilai6 = snapshot.child("RPM").getValue().toString();
                    average.setText(nilai1);
                    cadence.setText(nilai2);
                    calories.setText(nilai3);
                    distance.setText(nilai4);
                    speed.setText(nilai5);
                    rpm.setText(nilai6);

                    Jarak = Float.parseFloat(nilai4);
                    Jarak_Final = Jarak+Jarak_sebelum;
                    String j =String.valueOf(Jarak_Final);
                    fstore.collection("user").document(user.getUid()).update("distance", j);

                    //poin
                    if (Jarak > 2) {
                        poin_final = Poin + 1;
                        String p = String.valueOf(poin_final);
                        fstore.collection("user").document(user.getUid()).update("poin", p);}
                    else if (Jarak >= 10){
                        poin_final = Poin + 2;
                        String p = String.valueOf(poin_final);
                        fstore.collection("user").document(user.getUid()).update("poin", p);}
                    else if (Jarak >= 20){
                        poin_final = Poin + 3;
                        String p = String.valueOf(poin_final);
                        fstore.collection("user").document(user.getUid()).update("poin", p);}
                    else if (Jarak >= 30){
                        poin_final = Poin + 4;
                        String p = String.valueOf(poin_final);
                        fstore.collection("user").document(user.getUid()).update("poin", p);}
                    else if (Poin >= 100){
                        poin_final = 100;
                        String p = String.valueOf(poin_final);
                        fstore.collection("user").document(user.getUid()).update("poin", p);}

                    //level
                    if(Jarak != 0) {
                        if (Jarak_Final >=4) {
                            reward = 1; //dapat Reward 1
                            String l = String.valueOf(level_final);
                            fstore.collection("user").document(user.getUid()).update("reward", l);
                        }
                        else if(Jarak_Final >= 20){
                            reward = 2;// dapat Reward 2
                            String l = String.valueOf(level_final);
                            fstore.collection("user").document(user.getUid()).update("reward", l);
                        }
                        else if(Jarak_Final >= 50){
                            reward = 3; //dapat Reward 3
                            String l = String.valueOf(level_final);
                            fstore.collection("user").document(user.getUid()).update("reward", l);
                        }
                        if (reward_sebelum >= 3) {
                            reward = 3;
                            String l = String.valueOf(level_final);
                            fstore.collection("user").document(user.getUid()).update("reward", l);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

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
                    String jarak = value.getString("distance");
                    mPoin.setText("Point: " +poin);
                    mLevel.setText("Reward: " +reward);
                    Poin = Integer.parseInt(poin);
                    Jarak_sebelum = Float.parseFloat(jarak);
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
}