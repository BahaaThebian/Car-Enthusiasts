package com.lau.carenthusiasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText regname,regUsername,regEmail,regPhoneNo,regPassword;
    Button regBtn,regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regname=(EditText) findViewById(R.id.regName);
        regUsername=(EditText) findViewById(R.id.regUsername);
        regEmail=(EditText) findViewById(R.id.regEmail);
        regPhoneNo=(EditText) findViewById(R.id.regPhoneNo);
        regPassword=(EditText) findViewById(R.id.regPassword);
        regBtn=(Button) findViewById(R.id.RegButton);
        regToLoginBtn=(Button) findViewById(R.id.regLogin);
    }
    public void Register(View view){
        rootNode=FirebaseDatabase.getInstance();
        reference= rootNode.getReference("users");
        String name=regname.getText().toString();
        String username=regUsername.getText().toString();
        String email=regEmail.getText().toString();
        String phoneNo=regPhoneNo.getText().toString();
        String password=regPassword.getText().toString();
        UserHelperClass helperClass=new UserHelperClass(name,username,email,phoneNo,password);

        reference.child(username).setValue(helperClass);
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }
    public void goToLogin(View view){
        Intent intent=new Intent(this, Login.class);
        startActivity(intent);

    }
}