package com.example.hindlogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustomItem {
    public String permissions_id;
    public String content;
    public CustomItem(String permissions_id, String content){
        this.permissions_id = permissions_id;
        this.content=content;
    }
}

class ViewHolder{

    public TextView textViewContent;
    public ViewHolder(View cv){
        textViewContent=(TextView)cv.findViewById(R.id.textView);
    }
}

class CustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<CustomItem> mITems;
    private AViewHolder mHolder;

    public CustomAdapter(Context context, ArrayList<CustomItem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater= LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.listmember,parent,false);
            mHolder=new AViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(AViewHolder)convertView.getTag();
        }
        CustomItem item =mITems.get(pos);
        mHolder.textViewName.setText(item.content);

        return convertView;
    }
}