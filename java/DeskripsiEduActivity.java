package com.example.monitoringsepedaapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DeskripsiEduActivity extends AppCompatActivity {
    private TextView tV_judul, tV_deskripsi;
    private ImageView image, kembali;
    private String getjudul, getDeskripsi,getImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_edu);
        tV_judul = findViewById(R.id.tV_judul);
        tV_deskripsi = findViewById(R.id.tV_deskripsi);
        image = findViewById(R.id.imageView);
        kembali = findViewById(R.id.iV_kembali);
        Intent iin = getIntent();
        final Bundle b = iin.getExtras();

        if (b != null) {
            getjudul = (String) b.get("judul");
            getDeskripsi = (String) b.get("deskripsi");
            getImage = (String) b.get("gambar");

            tV_judul.setText(getjudul);
            tV_deskripsi.setText(getDeskripsi);
            Picasso.get().load(getImage).into(image);
//            Glide.with(this)
//                    .load(getImage)
//                    .into(image);

        }

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeskripsiEduActivity.this, EducationFragment.class));
                finish();
            }
        });

    }
}