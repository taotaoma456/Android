package com.xiaobin.security.adapter;

import java.util.List;

import com.xiaobin.security.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaobin.security.domain.ContactInfo;

public class SelectContactAdapter extends BaseAdapter{

	private Context context;
	private List<ContactInfo> infos;
	public SelectContactAdapter(Context context,List<ContactInfo> infos){
		this.context = context;
		this.infos = infos;
	}
	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view;
		ContactViews views;
		ContactInfo info = infos.get(arg0);
		if (arg1 == null)
		{
			views = new ContactViews();
			view = View.inflate(context, R.layout.contact_item, null);
			views.tv_name = (TextView)view.findViewById(R.id.tv_contact_name);
			views.tv_number = (TextView)view.findViewById(R.id.tv_contact_number);
			views.tv_name.setText("联系人："+info.getName());
			views.tv_number.setText("联系电话："+info.getPhone());
			
			view.setTag(views);
		}
		else
		{
			view = arg1;
			views = (ContactViews)view.getTag();
			views.tv_name.setText("联系人："+info.getName());
			views.tv_number.setText("联系电话："+info.getPhone());
		}
		
		return view;
	}
	
	private class ContactViews
	{
		TextView tv_name;
		TextView tv_number;
	}

}
