package com.example.hindlogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class AcustomItem {
    public String attributeid;
    public String attributename;
    public String sizes;
    public String sizem;
    public String sizel;
    public String sizexl;
    public String sizexxl;
    public AcustomItem(String id, String name, String s, String m, String l, String xl, String xxl){
        this.attributeid = id;
        this.attributename = name;
        this.sizes = s;
        this.sizem = m;
        this.sizel = l;
        this.sizexl = xl;
        this.sizexxl = xxl;
    }
}

class AViewHolder {

    public TextView textViewName;
    public TextView textViewContent;
    public AViewHolder(View cv){
        textViewName =(TextView)cv.findViewById(R.id.textView);
        textViewContent =(TextView)cv.findViewById(R.id.textView2);
    }
}

class AcustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<AcustomItem> mITems;
    private AViewHolder mHolder;

    public AcustomAdapter(Context context, ArrayList<AcustomItem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater= LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.listattri,parent,false);
            mHolder=new AViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(AViewHolder)convertView.getTag();
        }
        AcustomItem item =mITems.get(pos);
        mHolder.textViewName.setText(item.attributename);
        mHolder.textViewContent.setText("เวลาที่ใช้ ไซต์ s "+item.sizes+" นาที ไซต์ m "+item.sizem+" นาที ไซต์ l "+item.sizel+" นาที ไซต์ xl "+item.sizexl+" นาที ไซต์ xxl "+item.sizexxl+" นาที");

        return convertView;
    }
}