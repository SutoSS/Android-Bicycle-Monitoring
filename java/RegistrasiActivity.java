package com.example.monitoringsepedaapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistrasiActivity extends AppCompatActivity {
    private Button regist;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;
    private ProgressDialog mLoading;
    private TextView login;
    private TextInputLayout email, nama, noTelp,password;
    private Button registrasi;
    String emailUser,namaUser,noTelpUser,passwordUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        regist = findViewById(R.id.btn_regist);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrasiActivity.this, LoginActivity.class));
            }
        });
        initial();
    }
    private void initial() {
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mLoading = new ProgressDialog(this);
        mLoading.setMessage("Please Wait..");
//        login = findViewById(R.id.tV_login);
        email = findViewById(R.id.eT_email_register);
        nama = findViewById(R.id.eT_name_register);
        noTelp = findViewById(R.id.eT_no_telpon);
        password = findViewById(R.id.eT_password_regist);
        registrasi = findViewById(R.id.btn_regist);

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialRegist();
            }
        });
    }

    private void initialRegist(){

        emailUser = email.getEditText().getText().toString();
        namaUser = nama.getEditText().getText().toString();
        passwordUser = password.getEditText().getText().toString();
        noTelpUser = noTelp.getEditText().getText().toString();

        if (emailUser.isEmpty()){
            email.setError("Masukan Email Terlebih Dahulu");
            email.setFocusable(true);
        }else if (namaUser.isEmpty()) {
            nama.setError("Masukan Nama Terlebih Dahulu");
            nama.setFocusable(true);
        }else if(noTelpUser.isEmpty()){
            noTelp.setError("Masukkan No Telfon terlebih dahulu");
        }else if (passwordUser.isEmpty()) {
            password.setError("Masukan Password Anda");
            password.setFocusable(true);
        } else if (passwordUser.length() < 8) {
            password.setError("Masukan Password minimal 8");
            password.setFocusable(true);

        }else{
            registrasiUser(emailUser, passwordUser);
        }

    }

    private void registrasiUser(String emailUser, String passwordUser) {

        firebaseAuth.createUserWithEmailAndPassword(emailUser,passwordUser)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (task.isSuccessful()){
                            String uid = user.getUid();
                            DocumentReference documentReference = fStore.collection("user").document(uid);


                            HashMap hashMap = new HashMap();
                            String emailmap = user.getEmail();

                            hashMap.put("email", emailmap);
                            hashMap.put("uid", uid);
                            hashMap.put("nama", namaUser);
                            hashMap.put("no_telfon", noTelpUser);
                            hashMap.put("poin", "0");
                            hashMap.put("level", "0");


                            documentReference.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mLoading.dismiss();
                                    Toast.makeText(RegistrasiActivity.this, "Berhasil Daftar Dengan Email\n" + user.getEmail(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrasiActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}