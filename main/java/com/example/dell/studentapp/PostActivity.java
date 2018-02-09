package com.example.dell.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PostActivity extends AppCompatActivity {
    //  String URL="http://postalpincode.in/api/pincode/201206";
    TextView message,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        String pin = getIntent().getExtras().getString("pincode");

        StringBuilder strbuilder= new StringBuilder("http://postalpincode.in/api/pincode/");
        strbuilder.append(pin);
        final String URL =strbuilder.toString();

        message = (TextView)findViewById(R.id.txtMsg);
        status = (TextView)findViewById(R.id.txtStatus);
        Button btnShow = (Button)findViewById(R.id.btnShow);

        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Code",response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson= gsonBuilder.create();
                Post post = gson.fromJson(response,Post.class);
                message.setText(post.getMessage());
                status.setText(post.getStatus());
                // postList.setAdapter(new PostalAdapter(MainActivity.this,users));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PostActivity.this,MapsActivity.class);
                in.putExtra("url",URL);
                startActivity(in);
            }
        });
    }
}
