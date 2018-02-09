package com.example.dell.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.dell.studentapp.MainActivity.sqLiteHelper;

public class LoginActivity extends AppCompatActivity {
   // public static SQLiteHelper sqLiteHelper;
    EditText editEmail,editPassword;
    TextView textForSignup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText)findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        login = findViewById(R.id.btn_Login);
        textForSignup = findViewById(R.id.text_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString();
                String pass = editPassword.getText().toString();

                String password = sqLiteHelper.searchPassForEmail(email);
                if (pass.equals(password)){
                    Intent i = new Intent(LoginActivity.this,FormActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this,"Email and Password d'not matched",Toast.LENGTH_SHORT).show();
                }

            }
        });

        textForSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

    }
}
