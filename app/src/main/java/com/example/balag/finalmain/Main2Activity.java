package com.example.balag.finalmain;

import android.app.Activity;
import android.content.Intent;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.*;
import android.widget.ImageButton;

import com.example.balag.finalmain.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;


public class Main2Activity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button signup;
    private ImageButton signin;
    private FirebaseAuth fire;
    private ProgressDialog pd;

    String emal ;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main2);

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);


        signin = (ImageButton) findViewById(R.id.signin);

        fire=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        /*if(email.getText().toString() == null || password.getText().toString() == null){
            Toast.makeText(this, "Enter your Username to Continue", Toast.LENGTH_SHORT).show();
        }*/

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emal = email.getText().toString();
                    pwd = password.getText().toString();
                     if(emal.isEmpty() || pwd.isEmpty()){

                Toast.makeText(Main2Activity.this, "Enter your Username to Continue", Toast.LENGTH_SHORT).show();

                  }
                    else {
                         validate(email.getText().toString(), password.getText().toString());
                     }

                }
            });
        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openUpsign();
            }
        });
    }

    private void validate(String  email,String password){
        if(email!=null && password !=null) {

            pd.setMessage("Please wait");
            pd.show();

            fire.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        //Toast.makeText(Main2Activity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                        checkemail();
                        //startActivity(new Intent(Main2Activity.this, MainActivity.class));
                    } else {
                        Toast.makeText(Main2Activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        //startActivity(new Intent(Main2Activity.this,Main2Activity.class));
                    }
                }
            });
        }
        else{
            if(email.toString() == null || password.toString() == null){
                Toast.makeText(this, "Enter your Username to Continue", Toast.LENGTH_SHORT).show();
            }

        }
        }
        private void checkEmail(){
        FirebaseUser firebaseUser=fire.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag){
            startActivity(new Intent(Main2Activity.this,Up_Sign.class));
        }else{
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT);

            //logout
        }
        }

    /*public void openMainActivity(){
        Intent intent;
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_main2);
    }*/
    public void openUpsign() {
        Intent intent;
        intent = new Intent(this, Up_Sign.class);
        startActivity(intent);
        //setContentView(R.layout.activity_main2);
    }
    private void checkemail(){
        FirebaseUser firebaseUser=fire.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(Main2Activity.this,MainActivity.class));
        }else {
            Toast.makeText(this,"Verify your EmailId",Toast.LENGTH_SHORT).show();
            fire.signOut();
        }

    }
}
