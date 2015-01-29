package com.xiaobin.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

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
		}
		
		
	}

}
