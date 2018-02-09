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

public class SignUpActivity extends AppCompatActivity {
   // public  static SQLiteHelper sqLiteHelper;
    EditText editEmail,editPass;
    Button register;
    TextView textforLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editEmail = (EditText)findViewById(R.id.edit_email);
        editPass = (EditText)findViewById(R.id.edit_password);
        register = (Button)findViewById(R.id.register);
        textforLogin = (TextView)findViewById(R.id.text_login);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT(Id INTEGER PRIMARY KEY AUTOINCREMENT,email VARCHAR,password VARCHAR)");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{   sqLiteHelper.insertForSignUp(
                        editEmail.getText().toString().trim(),
                        editPass.getText().toString().trim());
                    Toast.makeText(getApplicationContext(),"User registered succuessfully",Toast.LENGTH_SHORT).show();
                    editEmail.setText("");
                    editPass.setText("");

                    startActivity(new Intent(SignUpActivity.this,FormActivity.class));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        textforLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
}
