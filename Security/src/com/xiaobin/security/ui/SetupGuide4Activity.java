package com.xiaobin.security.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xiaobin.security.R;
import com.xiaobin.security.receiver.MyAdminReceiver;

public class SetupGuide4Activity extends Activity implements OnGestureListener,OnClickListener {
	
	private GestureDetector mGestureDetector;
	private SharedPreferences sp;
	private Button bt_pervious;
	private Button bt_finish;
	private CheckBox cb_protected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide4);
		mGestureDetector = new GestureDetector(this,this);
		mGestureDetector.setIsLongpressEnabled(true);
		
		bt_pervious = (Button)findViewById(R.id.bt_guide_pervious);
		bt_finish = (Button)findViewById(R.id.bt_guide_finish);
		cb_protected = (CheckBox)findViewById(R.id.cb_guide_protected);
		
		bt_finish.setOnClickListener(this);
		
		sp = getSharedPreferences("config",Context.MODE_PRIVATE);
		boolean isProtecting = sp.getBoolean("isProtected", false);
		if (isProtecting)
		{
			cb_protected.setText(R.string.guide4_item2);
			cb_protected.setChecked(true);
		}
		
		cb_protected.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1)
				{
					cb_protected.setText(R.string.guide4_item2);
					Editor editor = sp.edit();
					editor.putBoolean("isProtected", true);
					editor.commit();
				}
				else
				{
					cb_protected.setText(R.string.guide4_item1);
					Editor editor = sp.edit();
					editor.putBoolean("isProtected", false);
					editor.commit();
				}
				
			}
			
		});

	}
	
	 private void finishSetupGuide() 
	 {
			Editor editor = sp.edit();
			editor.putBoolean("setupGuide", true);
			editor.commit();
			
			//拿到一个设备管理器  
			DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE); 
			 //new一个新的组件出来，用来启动注册管理器的界面  
			ComponentName componentName = new ComponentName(this,MyAdminReceiver.class);
			 //判断是否已经注册，没有就进行注册  
			if (!devicePolicyManager.isAdminActive(componentName))
			{
				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
				this.startActivity(intent);
			}
	 }
	
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId())
		{
			case R.id.bt_guide_finish:
			{
				if (cb_protected.isChecked())
				{
					finishSetupGuide();
					finish();
				}
				else
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle(R.string.alert);
					builder.setMessage(R.string.alert_1);
					builder.setCancelable(false);
					builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							finishSetupGuide();
							
							finish();
							
						}
					});
					builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Editor editor = sp.edit();
							editor.putBoolean("setupGuide", true);
							editor.commit();
							
						}
					});
					
					builder.create().show();
				}
			}
		}
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;  
		if (arg1.getX() - arg0.getX() > FLING_MIN_DISTANCE && Math.abs(arg2) > FLING_MIN_VELOCITY)
		{
			Intent intent = new Intent(this,SetupGuide3Activity.class);
			startActivity(intent);
			finish();
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
