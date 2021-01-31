package com.example.hindlogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class QcustomItem {
    public String queue_id;
    public String car_member_number;
    public String car_service_name;
    public String color_name;
    public String status_name;
    public QcustomItem(String queue_id, String car_member_number,String car_service_name, String color_name,String status_name){
        this.queue_id = queue_id;
        this.car_member_number=car_member_number;
        this.car_service_name = car_service_name;
        this.color_name=color_name;
        this.status_name = status_name;
    }
}

class QViewHolder{

    public TextView textViewName;
    public TextView textViewNumber;
    public TextView textViewColor;
    public TextView textViewStatus;
    public LinearLayout linearLayout;
    public QViewHolder(View cv){
        textViewName =(TextView)cv.findViewById(R.id.textView);
        textViewNumber =(TextView)cv.findViewById(R.id.textView1);
        textViewColor =(TextView)cv.findViewById(R.id.textView2);
        textViewStatus =(TextView)cv.findViewById(R.id.textView3);
        linearLayout =(LinearLayout)cv.findViewById(R.id.contact);
    }
}

class QcustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<QcustomItem> mITems;
    private QViewHolder mHolder;

    public QcustomAdapter(Context context, ArrayList<QcustomItem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){

        if (convertView==null){
            LayoutInflater inflater= LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.rowqueue,parent,false);

            mHolder=new QViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(QViewHolder)convertView.getTag();
        }
        QcustomItem item =mITems.get(pos);
        mHolder.textViewName.setText(item.car_service_name);
        mHolder.textViewNumber.setText("ทะเบียนรถ "+item.car_member_number);
        mHolder.textViewColor.setText("สีรถ "+item.color_name);
        mHolder.textViewStatus.setText(item.status_name);
        if (item.status_name=="1"){
            mHolder.textViewStatus.setText("สถานะ รอคิว");
            mHolder.linearLayout.setBackground(getContext().getDrawable(R.drawable.shapequeue1));
        }if (item.status_name=="2"){
            mHolder.textViewStatus.setText("สถานะ กำลังล้าง");
            mHolder.linearLayout.setBackground(getContext().getDrawable(R.drawable.shapequeue2));
        }


        return convertView;
    }
}