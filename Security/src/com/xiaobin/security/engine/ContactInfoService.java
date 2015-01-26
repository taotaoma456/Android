package com.xiaobin.security.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.xiaobin.security.domain.ContactInfo;

public class ContactInfoService {

	private Context context;
	public ContactInfoService(Context context){
		this.context = context;
	}
	
	public List<ContactInfo> getContactInfos(){
		
		 List<ContactInfo> infos = new ArrayList<ContactInfo>();
		 ContactInfo info;
		 
		 ContentResolver  contentResolver = context.getContentResolver();
		 Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		 Uri dataUri = Uri.parse("content://com.android.contacts/data");
		 Cursor cursor = contentResolver.query(uri, null, null, null, null);
		 while (cursor.moveToNext()){
			 info = new ContactInfo();
			 String id = cursor.getString(cursor.getColumnIndex("_id"));
			 String name = cursor.getString(cursor.getColumnIndex("display_name"));
			 info.setName(name);
			 //通过raw_contacts里面的id拿到data中对应的数据
			 Cursor dataCursor = contentResolver.query(dataUri, null, "raw_contact_id = ? ", new String[] {id}, null);
			 while (dataCursor.moveToNext()){
				 String type = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
				 //根据类型，只要电话这种类型的数据  
				 if (type.equals("vnd.android.cursor.item/phone_v2"))
				 {
					 String number = dataCursor.getString(dataCursor.getColumnIndex("data1"));
					 info.setPhone(number);
				 }
			 }
			 dataCursor.close();
			 infos.add(info);
			 info = null;
		 }
		 cursor.close();
		 
		return infos;
		
	}
	
}
