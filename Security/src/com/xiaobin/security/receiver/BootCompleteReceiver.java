package com.xiaobin.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BootCompleteReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
		boolean isProtected = sp.getBoolean("isProtected", false);
		if (isProtected)
		{
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
			String currentSim = telephonyManager.getSimSerialNumber();
			String protectedSim = sp.getString("simSerial", "");
			if (!currentSim.equals(protectedSim))
			{
				 //拿到一个短信的管理器，要注意不要导错包，是在android.telephony下的  
                SmsManager smsManager = SmsManager.getDefault();  
                String number = sp.getString("number", "");  
                //发送短信，有5个参数，第一个是要发送到的地址，第二个是发送人，可以设置为null，第三个是要发送的信息，第四个是发送状态，第五个是发送后的，都可以置为null  
                smsManager.sendTextMessage(number, null, "Sim卡已经变更了，手机可能被盗", null, null);  
			}
		}
	}

}
