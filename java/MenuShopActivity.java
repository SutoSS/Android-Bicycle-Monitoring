package com.example.monitoringsepedaapps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;

public class MenuShopActivity extends AppCompatActivity {
    private LinearLayout greetImg;
    private TextView greetText, tV_nama, tV_point;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore fstore;
    private Button btn_claim_100, btn_claim_200, btn_claim_250, btn_claim_300;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_shop);
        greetImg = findViewById(R.id.greeting_img);
        greetText = findViewById(R.id.greeting_text);
        tV_nama = findViewById(R.id.tv_nama);
        tV_point = findViewById(R.id.tv_point);
        btn_claim_100 = findViewById(R.id.btn_claim_100);
        btn_claim_200 = findViewById(R.id.btn_claim_200);
        btn_claim_250 = findViewById(R.id.btn_claim_250);
        btn_claim_300 = findViewById(R.id.btn_claim_300);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        greeting();
        checkUser();
        shop();
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
                    tV_point.setText("Point Anda : " + value.getString("point"));
                }
            });
        }
    }

//    private void showAlertDialog(int layout) {
//        dialogBuilder = new AlertDialog.Builder(MenuShopActivity.this);
//        View layoutView = getLayoutInflater().inflate(layout, null);
//        Button kirim = layoutView.findViewById(R.id.btn_simpan_penyakit);
//        dialogBuilder.setView(layoutView);
//        alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();
//        kirim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                initialForm();
//            }
//        });
//    }

    private void shop() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        DocumentReference documentReference = fstore.collection("user").document(user.getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                int point = Integer.parseInt(value.getString("point"));

                if (point < 100) {
                    btn_claim_100.setClickable(false);
                    btn_claim_200.setClickable(false);
                    btn_claim_250.setClickable(false);
                    btn_claim_300.setClickable(false);
                    btn_claim_100.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_200.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_250.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_300.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                } else if (point <= 199) {
                    btn_claim_100.setClickable(true);
                    btn_claim_200.setClickable(false);
                    btn_claim_250.setClickable(false);
                    btn_claim_300.setClickable(false);
                    btn_claim_100.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_200.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_250.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_300.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_100.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 100;
                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (point <= 200) {
                    btn_claim_100.setClickable(true);
                    btn_claim_200.setClickable(true);
                    btn_claim_250.setClickable(false);
                    btn_claim_300.setClickable(false);
                    btn_claim_100.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_200.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_250.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_300.setBackgroundResource(R.drawable.bg_btn_shop_grey);
                    btn_claim_100.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 100;
                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                    btn_claim_200.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 200;

                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                } else if (point <= 250) {
                    btn_claim_100.setClickable(true);
                    btn_claim_200.setClickable(true);
                    btn_claim_250.setClickable(true);
                    btn_claim_300.setClickable(false);
                    btn_claim_100.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_200.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_250.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_300.setBackgroundResource(R.drawable.bg_btn_shop_grey);

                    btn_claim_100.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 100;
                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                    btn_claim_200.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 200;

                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                    btn_claim_250.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 250;

                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                } else if (point <= 300) {
                    btn_claim_100.setClickable(true);
                    btn_claim_200.setClickable(true);
                    btn_claim_250.setClickable(true);
                    btn_claim_300.setClickable(true);
                    btn_claim_100.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_200.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_250.setBackgroundResource(R.drawable.bg_btn_shop_blue);
                    btn_claim_300.setBackgroundResource(R.drawable.bg_btn_shop_blue);

                    btn_claim_100.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 100;
                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                    btn_claim_200.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 200;

                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();
                        }
                    });

                    btn_claim_250.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 250;

                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                    btn_claim_300.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hasil = point - 300;

                            String hasilAkhir = String.valueOf(hasil);

                            DocumentReference documentReference = fstore.collection("user").document(user.getUid());
                            documentReference.update("point", hasilAkhir);
                            Toast.makeText(MenuShopActivity.this, "Berhasil Mendapatkan Barangnya, Sisa Point Anda " + hasilAkhir, Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
    }
}