package com.xiaobin.security.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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

public class SetupGuide2Activity extends Activity implements OnGestureListener {
	
	private GestureDetector mGestureDetector; 
	private SharedPreferences sp;  
	private Button button;
	private CheckBox checkBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide2);
		
		mGestureDetector = new GestureDetector(this,this);
		mGestureDetector.setIsLongpressEnabled(true);
		 sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		
		button = (Button)findViewById(R.id.bt_guide_bind);
		checkBox = (CheckBox)findViewById(R.id.cb_guide_check);
		
		//初始化CheckBox状态  
        String sim = sp.getString("simSerial", null);  
        if (sim == null)
        {
        	checkBox.setText(R.string.guide2_item2);  
        	checkBox.setChecked(false);  
            resetSimInfo();  
        }
        else
        {
        	checkBox.setText(R.string.guide2_item1);
        	checkBox.setChecked(true);
        }
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				setSimInfo();  
				checkBox.setText(R.string.guide2_item1);  
				checkBox.setChecked(true);  
				
			}
			
		});
		
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1)
				{
					checkBox.setText(R.string.guide2_item1);
					setSimInfo();
				}
				else
				{
					checkBox.setText(R.string.guide2_item2);
					resetSimInfo();
				}
			}
			
		});
	}
	
	private void setSimInfo()
	{
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String simSerial = telephonyManager.getSimSerialNumber();
		Editor editor = sp.edit();
		editor.putString("simSerial", simSerial);
		editor.commit();
	}
	
	//解除绑定
	private void resetSimInfo()
	{
		Editor editor = sp.edit();
		editor.putString("simSerial", null);
		editor.commit();
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
		if (arg0.getX() - arg1.getX() > FLING_MIN_DISTANCE && Math.abs(arg2) > FLING_MIN_VELOCITY)
		{
			Intent intent = new Intent(this,SetupGuide3Activity.class);
			startActivity(intent);
			finish();
		}
		else if (arg1.getX() - arg0.getX() > FLING_MIN_DISTANCE && Math.abs(arg2) > FLING_MIN_VELOCITY)
		{
			Intent intent = new Intent(this,SetupGuide1Activity.class);
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
