package com.example.sharansingh.solarifyenergy;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Login extends AppCompatActivity {

    private Button loginButton,resetButton;
    private TextInputLayout mEmailID,mPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    RelativeLayout forgotRelativeLayout;
    private EditText forgotEmailId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailID=(TextInputLayout)findViewById(R.id.login_email);
        mPassword=(TextInputLayout)findViewById(R.id.login_password);
        loginButton=(Button)findViewById(R.id.login_loginButton);
        progressDialog=new ProgressDialog(this);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        forgotRelativeLayout=(RelativeLayout)findViewById(R.id.forgot);
        forgotEmailId=(EditText)findViewById(R.id.forgotEmailID);
        resetButton=(Button)findViewById(R.id.forgotSubmitButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mEmailID.getEditText().getText()) && !TextUtils.isEmpty(mPassword.getEditText().getText())){
                    progressDialog.setTitle("SIGNING IN");
                    progressDialog.setMessage("Data is being Checked");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String email=mEmailID.getEditText().getText().toString();
                    String pass=mPassword.getEditText().getText().toString();
                    login(email,pass);}
            }
        });
    }
    private void login(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                progressDialog.dismiss();
                Toast.makeText(Activity_Login.this,"SignIn Successfull",Toast.LENGTH_SHORT).show();
               // Intent intent=new Intent(Activity_Login.this,Survey1.class);
                Intent intent=new Intent(Activity_Login.this,PreSurvey1.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Activity_Login.this,"SignIn UnSuccessfull",Toast.LENGTH_SHORT).show();}
            }
        });

    }

    public void resetPassword(View view)
    {
        forgotRelativeLayout.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(forgotRelativeLayout,"alpha",1.0f).setDuration(500).start();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Reset Password");
                progressDialog.show();
                if(!TextUtils.isEmpty(forgotEmailId.getText().toString()))
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(forgotEmailId.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Activity_Login.this,"Reset Email Sent",Toast.LENGTH_SHORT).show();
                                        forgotRelativeLayout.setVisibility(View.GONE);
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Activity_Login.this,"Reset Email Not Sent",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    forgotRelativeLayout.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }
        });
    }
}
