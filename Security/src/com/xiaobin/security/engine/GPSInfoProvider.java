package com.xiaobin.security.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/** 
 * 这个类，我们要做成了单例模式，因为手机里面只有一个gps所以免得每次都新开一个对象 
 * @author Administrator 
 * 
 */  
public class GPSInfoProvider {
	private static GPSInfoProvider gpsInfoProvider;
	private static Context         context;
	private LocationManager 	   locationManager;  
	private static MyLocationListener listener;
	
	private GPSInfoProvider()
	{
		
	}
	
	   /** 
     * 为了让这个方法一定执行完，所以我们加入了synchronized来修饰 
     * @return 
     */  
	  public static synchronized GPSInfoProvider getInstance(Context context) 
	  {
		  if (gpsInfoProvider == null)
		  {
			  gpsInfoProvider = new GPSInfoProvider();
			  gpsInfoProvider.context = context;
		  }
		  return gpsInfoProvider;
	  }
	  public String getLocation() 
	  {
		  locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		  String provider = getBestProvider();  
		  locationManager.requestLocationUpdates(provider, 60000, 100, getListener());
		  
		  SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);  
	      String location = sp.getString("lastLocation", "");  
	      return location;    
	  }
	  private String getBestProvider()  
	  {
		  Criteria criteria = new Criteria();
		  criteria.setAccuracy(Criteria.ACCURACY_HIGH);
		  criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		  criteria.setCostAllowed(true);
		  criteria.setAltitudeRequired(false);
		  criteria.setSpeedRequired(true);
		  return locationManager.getBestProvider(criteria, true);
	  }
	  
	    //停止gps  
	    public void stopGPSListener()  
	    {  
	        if(locationManager != null)  
	        {  
	            locationManager.removeUpdates(getListener());  
	        }  
	    }  
	  
	    //做成单例模式  
	    private synchronized MyLocationListener getListener()  
	    {  
	        if(listener == null)  
	        {  
	            listener = new MyLocationListener();  
	        }  
	        return listener;  
	    }  
	      
	  
	  
	  /////////////////////////////////////////////////////////////////////////////////////////////////////
	  private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			String latitude = "纬度" + arg0.getLatitude();
			String longitudu = "经度" + arg0.getLongitude();
			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("lastLocation", latitude + "-" + longitudu);
			editor.commit();
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		  
	  }

}

