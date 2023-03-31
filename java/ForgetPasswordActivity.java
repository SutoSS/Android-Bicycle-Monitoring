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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button submit;
    private TextView kembali;
    private TextInputLayout email;
    ProgressDialog mLoading;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        submit = findViewById(R.id.btn_submit);
        email = findViewById(R.id.eT_email_forget);
        firebaseAuth = FirebaseAuth.getInstance();
        mLoading = new ProgressDialog(this);
        mLoading.setMessage("Please Wait");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoading.show();
                String email_user = email.getEditText().getText().toString().trim();
                forgetPassword(email_user);
            }
        });
    }

    private void forgetPassword(String email_user) {
        mLoading.setMessage("Please Wait..");
        mLoading.show();
        firebaseAuth.sendPasswordResetEmail(email_user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mLoading.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPasswordActivity.this, "Email Sending..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ForgetPasswordActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoading.dismiss();
                Toast.makeText(ForgetPasswordActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}