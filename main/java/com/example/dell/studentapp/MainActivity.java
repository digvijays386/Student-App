package com.example.dell.studentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;
    ArrayList<Student> imgList;
    ViewPager viewPager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this,"StudentDB.sqlite",null,1);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        imgList= new ArrayList<>();
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,imgList);
        viewPager.setAdapter(adapter);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS STUDENT(Id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,phone VARCHAR,address VARCHAR,image BLOG,pincode VARCHAR,videoid VARCHAR)");

        Cursor cursor = sqLiteHelper.getData("SELECT image FROM STUDENT");
        if (cursor!=null) {
            imgList.clear();
            int iImage = cursor.getColumnIndex("image");
            cursor.moveToLast();
            do {
                byte[] image = cursor.getBlob(iImage);
                imgList.add(new Student(image));
            }
            while (cursor.moveToPrevious());
            adapter.notifyDataSetChanged();
        }
        else
            startActivity(new Intent(MainActivity.this,HomeActivity.class));


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position==viewPager.getAdapter().getCount()-1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
                            MainActivity.this.startActivity(mainIntent);
                            MainActivity.this.finish();
                        }
                    }, 1000);

                }
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
        });
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 3000);*/

    }
}
