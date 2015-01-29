package com.xiaobin.security.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.xiaobin.security.R;
import com.xiaobin.security.engine.GPSInfoProvider;

public class SmsReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Object[] pdus = (Object[]) arg1.getExtras().get("pdus"); 
		for (Object pdu : pdus)
		{
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
			String content = smsMessage.getMessageBody();
			String sender = smsMessage.getOriginatingAddress();
			if (content.equals("#*location*#"))
			{
				this.abortBroadcast();
				GPSInfoProvider gpsInfoProvider = GPSInfoProvider.getInstance(arg0);
				String location = gpsInfoProvider.getLocation();
				if (!location.equals(""))
				{
					//发送短信
					SmsManager  smsManager  = SmsManager.getDefault();
					smsManager.sendTextMessage(sender, null, location, null, null);
				}
			}
			else if (content.equals("#*lockscreen*#"))
			{
				 DevicePolicyManager manager = (DevicePolicyManager) arg0.getSystemService(Context.DEVICE_POLICY_SERVICE);
				 manager.resetPassword("372156", 0);
				 manager.lockNow();
				 this.abortBroadcast();
			}
			else if (content.equals("#*wipe*#"))
			{
				 DevicePolicyManager manager = (DevicePolicyManager) arg0.getSystemService(Context.DEVICE_POLICY_SERVICE);
				 manager.wipeData(0);
				 this.abortBroadcast();
			}
			else if(content.equals("#*alarm*#")) 
			{
				//这个方法已经调用的prepare这个方法的啦，所以不用自己调用prepare这个方法  
                MediaPlayer mediaPlayer = MediaPlayer.create(arg0, 1424);  
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
                this.abortBroadcast();
			}
		}
		
		
	}

}
