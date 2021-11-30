package com.lau.carenthusiasts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.etUsername);
        password=(EditText) findViewById(R.id.etPassword);

    }
    private Boolean validateUsername(){
        String val= username.getText().toString();
        if(val.isEmpty()){
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }

    }
    private Boolean validatePassword(){
        String val=password.getText().toString();
        if(val.isEmpty()){
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
    public void goToReg(View view){
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }
    public void login(View view){
        if(!validatePassword() | !validateUsername()){
            return;
        }else{
            isUser();
        }
    }
    public void isUser(){
        String etUsername=username.getText().toString().trim();
        String etPassword=password.getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("username").equalTo(etUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String dbpassword=snapshot.child(etUsername).child("password").getValue(String.class);
                    if(dbpassword.equals(etPassword)){
                        Intent intent=new Intent(getApplicationContext(),Screen.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No such user",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}