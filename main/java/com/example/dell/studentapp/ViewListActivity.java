package com.example.dell.studentapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

import static com.example.dell.studentapp.MainActivity.sqLiteHelper;

public class ViewListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Student> list;
    StudentListAdapter adapter;

    private static final int REQUEST_CALL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        listView = (ListView)findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new StudentListAdapter(this, R.layout.student_cardview, list);
        listView.setAdapter(adapter);

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM STUDENT");
        list.clear();
        cursor.moveToLast();
        do {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String address = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String pincode = cursor.getString(5);
            String videoid = cursor.getString(6);
           // String lng = cursor.getString(6);

            list.add(new Student(id,name,phone,address,image,pincode,videoid));
        }
        while (cursor.moveToPrevious());
        adapter.notifyDataSetChanged();

    }
}
