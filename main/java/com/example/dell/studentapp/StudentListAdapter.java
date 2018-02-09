package com.example.dell.studentapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dell on 1/16/2018.
 */

public class StudentListAdapter extends BaseAdapter {
   private static  final int REQUEST_CALL=1;
    private Context context;
    private  int layout;
    private ArrayList<Student> studentList;

    public StudentListAdapter(Context context, int layout, ArrayList<Student> studentList) {
        this.context = context;
        this.layout = layout;
        this.studentList = studentList;
    }
    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView imageView,imageCall,imageLoc,imageVideo;
        TextView txtName,txtPhone,txtAddress,txtPincode,txtVideoid;
        //,txtLatitude;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        if (row ==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(layout,null);

            holder.txtName = (TextView)row.findViewById(R.id.txtName);
            holder.txtPhone = (TextView) row.findViewById(R.id.txtPhone);
            holder.txtAddress= (TextView)row.findViewById(R.id.txtAddress);
            holder.imageView= (ImageView)row.findViewById(R.id.imgStu);
            holder.imageVideo = (ImageView)row.findViewById(R.id.imgVideo);
            holder.imageCall = (ImageView)row.findViewById(R.id.imgCall);
            holder.imageLoc =(ImageView)row.findViewById(R.id.imgLoc);
            holder.txtPincode =(TextView) row.findViewById(R.id.txtPin);
            holder.txtVideoid = (TextView)row.findViewById(R.id.txtVideo);
           // holder.txtLongitude = (TextView)row.findViewById(R.id.txtLng);
            row.setTag(holder);
        }
        else
            holder = (ViewHolder)row.getTag();

        final Student student = studentList.get(position);
        holder.txtName.setText(student.getName());
        holder.txtPhone.setText(student.getPhone());
        holder.txtAddress.setText(student.getAddress());
        byte[] stuImage= student.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(stuImage,0,stuImage.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.txtPincode.setText(student.getPincode());
        holder.txtVideoid.setText(student.getVideoid());
        //holder.txtLatitude.setText(student.getLat());
        //holder.txtLongitude.setText(student.getLng());

        final ViewHolder finalHolder = holder;
        final ViewHolder finalHolder1 = holder;
        final ViewHolder finalHolder2 = holder;
        final ViewHolder finalHolder3 = holder;

        holder.imageCall.setTag(new Integer(position));
        holder.imageCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String number= (String) finalHolder.txtPhone.getText();
                if (number.length()>0) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    else
                        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
               }
                String name= (String) finalHolder1.txtName.getText();

                Toast.makeText(context, "Calling to "+name, Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageLoc.setTag(new Integer(position));
        holder.imageLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = (String) finalHolder2.txtPincode.getText();
                Intent i= new Intent(context,PostActivity.class);
                i.putExtra("pincode",pin);
                context.startActivity(i);
            }
        });
        holder.imageVideo.setTag(new Integer(position));
        holder.imageVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoid= (String) finalHolder3.txtVideoid.getText();
                Intent i2 = new Intent(context,VideoActivity.class);
                i2.putExtra("youtubeVideo",videoid);
                context.startActivity(i2);

            }
        });

        return row;
    }
}