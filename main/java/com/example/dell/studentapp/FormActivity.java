package com.example.dell.studentapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import static com.example.dell.studentapp.MainActivity.sqLiteHelper;

public class FormActivity extends AppCompatActivity {
    EditText editN,editP,editA,editPin,editVideo;
    Button addStu,btnchoose;
    ImageView imgView;
    final  int REQUEST_CODE_GALLERY=999;
    static final int REQUEST_LOCATION =1;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        init();
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS STUDENT(Id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,phone VARCHAR,address VARCHAR,image BLOG,pincode VARCHAR,videoid VARCHAR)");

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        FormActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
        addStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{   sqLiteHelper.insertData(
                            editN.getText().toString().trim(),
                            editP.getText().toString().trim(),
                            editA.getText().toString().trim(),
                            imageViewToByte(imgView),
                            editPin.getText().toString().trim(),
                            editVideo.getText().toString().trim());
                    Toast.makeText(getApplicationContext(),"Data added succuessfully",Toast.LENGTH_SHORT).show();
                    editN.setText("");
                    editP.setText("");
                    editA.setText("");
                    editPin.setText("");
                    editVideo.setText("");
                    imgView.setImageResource(R.drawable.ic_launcher_background);

                    startActivity(new Intent(FormActivity.this,ViewListActivity.class));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray= stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GALLERY)
        { if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else
                Toast.makeText(getApplicationContext(),"You don't have permission to access the file from gallery",Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (requestCode == REQUEST_LOCATION)
            getLocation();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try { InputStream is = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        editN =(EditText)findViewById(R.id.editName);
        editP =(EditText)findViewById(R.id.editPhone);
        editA =(EditText)findViewById(R.id.editAddress);
        addStu = (Button)findViewById(R.id.addStudent);
        btnchoose = (Button)findViewById(R.id.chooseDP);
        imgView =(ImageView)findViewById(R.id.imageView);
        editPin = (EditText)findViewById(R.id.editPin);
        editVideo = (EditText)findViewById(R.id.editVideoID);
    }
}
