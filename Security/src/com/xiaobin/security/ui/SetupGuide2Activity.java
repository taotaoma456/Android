package com.xiaobin.security.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.xiaobin.security.R;

public class SetupGuide2Activity extends Activity implements OnGestureListener {
	
	private GestureDetector mGestureDetector; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide2);
		
		mGestureDetector = new GestureDetector(this,this);
		mGestureDetector.setIsLongpressEnabled(true);
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
