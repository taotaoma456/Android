package com.xiaobin.security.ui;

import android.app.Activity;
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
import android.widget.EditText;

import com.xiaobin.security.R;

public class SetupGuide3Activity extends Activity implements OnGestureListener{
	
	private SharedPreferences sp;
	private GestureDetector mGestureDetector; 
	private Button button;
	private EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide3);
		mGestureDetector = new GestureDetector(this,this);
		mGestureDetector.setIsLongpressEnabled(true);
		sp = getSharedPreferences("config",MODE_PRIVATE);
		
		button = (Button)findViewById(R.id.bt_guide_select);
		editText = (EditText)findViewById(R.id.et_guide_phoneNumber);
		
		String number = sp.getString("number", null);
		if (number != null)
		{
			editText.setText(number);
		}
		
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SetupGuide3Activity.this,SelectContactActivity.class);
				startActivityForResult(intent,1);
				
			}
			
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null)
		{
			String number = data.getStringExtra("number");
			editText.setText(number);
			Editor editor = sp.edit();
			editor.putString("number", number);
			editor.commit();
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
		if (arg0.getX() - arg1.getX() > FLING_MIN_DISTANCE && Math.abs(arg2) > FLING_MIN_VELOCITY)
		{
			Intent intent = new Intent(this,SetupGuide4Activity.class);
			startActivity(intent);
			finish();
		}
		else if (arg1.getX() - arg0.getX() > FLING_MIN_DISTANCE && Math.abs(arg2) > FLING_MIN_VELOCITY)
		{
			Intent intent = new Intent(this,SetupGuide2Activity.class);
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
