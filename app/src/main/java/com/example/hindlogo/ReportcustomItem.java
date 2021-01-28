package com.example.hindlogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ReportcustomItem {
    public String permissions_id;
    public String content;
    public ReportcustomItem(String permissions_id, String content){
        this.permissions_id = permissions_id;
        this.content=content;
    }
}

class RViewHolder {
    public TextView textViewContent;
    public TextView textViewContent1;
    public RViewHolder(View cv){
        textViewContent=(TextView)cv.findViewById(R.id.textView1);
        textViewContent1=(TextView)cv.findViewById(R.id.textView2);
    }
}

class ReportcustomAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<ReportcustomItem> mITems;
    private RViewHolder mHolder;

    public ReportcustomAdapter(Context context, ArrayList<ReportcustomItem> items){
        super(context,0,items);
        mContext=context;
        mITems=items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater inflater= LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.row,parent,false);
            mHolder=new RViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder=(RViewHolder)convertView.getTag();
        }
        ReportcustomItem item =mITems.get(pos);
        mHolder.textViewContent1.setText("วัทที่ "+item.permissions_id);
        mHolder.textViewContent.setText("จำนวน "+item.content+" คิว");

        return convertView;
    }
}