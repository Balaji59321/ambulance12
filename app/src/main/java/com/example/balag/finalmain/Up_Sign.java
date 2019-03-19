package com.example.balag.finalmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import com.example.balag.finalmain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Up_Sign extends AppCompatActivity {
    private Button signup;
    private EditText Name, email, PhoneNumber, password, confirmpassword;
    private FirebaseAuth fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setupUiViews();

        fire=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                if(validate()){
                    //upload
                    String esmail=email.getText().toString().trim();
                    String upas=password.getText().toString().trim();
                    fire.createUserWithEmailAndPassword(esmail,upas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendemail();
                                //Toast.makeText(Up_Sign.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Up_Sign.this,Main2Activity.class));
                            }else{
                                Toast.makeText(Up_Sign.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }

    private void setupUiViews() {
        Name=(EditText) findViewById(R.id.Name);
        email=(EditText) findViewById(R.id.email);
        PhoneNumber=(EditText) findViewById(R.id.PhoneNumber);
        password=(EditText) findViewById(R.id.password);
        confirmpassword=(EditText) findViewById(R.id.confirmpassword);
        signup =(Button) findViewById(R.id.signup);

    }
    private Boolean validate(){
        Boolean result=false;
        String name=Name.getText().toString();
        String eemil=email.getText().toString();
        String ph=PhoneNumber.getText().toString();
        String pass=password.getText().toString();
        String cpass=confirmpassword.getText().toString();


        if(name.isEmpty() || eemil.isEmpty() || ph.isEmpty() || pass.isEmpty() || cpass.isEmpty()){
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT);

        }else{
            result=true;
        }
        return result;
    }

    /*public void openUpsign() {
        Intent intent;
        intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_main2);
    }*/
    private void sendemail(){
        FirebaseUser firebaseUser=fire.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Up_Sign.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                        fire.signOut();
                        finish();
                        startActivity(new Intent(Up_Sign.this,Main2Activity.class));
                    }else {
                        Toast.makeText(Up_Sign.this,"Verification mail hasn't been send",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
