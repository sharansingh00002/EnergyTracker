package com.example.sharansingh.solarifyenergy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Signup extends AppCompatActivity{

private TextInputLayout mEmailID,mPassword;
private Button mSignupButton;
private FirebaseAuth mAuth;
private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mEmailID=(TextInputLayout)findViewById(R.id.signup_email);
        mPassword=(TextInputLayout)findViewById(R.id.signup_password);
        mSignupButton=(Button)findViewById(R.id.signup_signupButton);
        FirebaseApp.initializeApp(this);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mEmailID.getEditText().getText()) && !TextUtils.isEmpty(mPassword.getEditText().getText()))
                {
                    progressDialog.setTitle("SIGNING UP");
                    progressDialog.setMessage("Data is being Entered");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String email=mEmailID.getEditText().getText().toString();
                    String pass=mPassword.getEditText().getText().toString();
                    signUp(email,pass);
                }
            }
        });

    }
    private void signUp(String email,String pass)
    {
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                   Toast.makeText(Activity_Signup.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
