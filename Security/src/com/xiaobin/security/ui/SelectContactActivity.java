package com.xiaobin.security.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.xiaobin.security.R;
import com.xiaobin.security.adapter.SelectContactAdapter;
import com.xiaobin.security.domain.ContactInfo;
import com.xiaobin.security.engine.ContactInfoService;

public class SelectContactActivity extends Activity implements OnItemClickListener{

	private ListView lv;
	private SelectContactAdapter sa;
	private List<ContactInfo> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_contact);
		infos = new ContactInfoService(this).getContactInfos();
		sa = new SelectContactAdapter(this,infos);
		lv = (ListView)findViewById(R.id.lv_select_contact);
		lv.setAdapter(sa);
		lv.setOnItemClickListener(this);
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String number = infos.get(arg2).getPhone();
		Intent intent = new Intent();
		intent.putExtra("number", number);
		 //把要返回的数据设置进去，便通过onActivityResult(int, int, Intent)拿到  
		setResult(1,intent);
		finish();
		
	}

	
}
