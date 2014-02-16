package com.manish.smshistory;

import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
/**
 * 
 * @author manish
 *
 */

public class MainActivity extends Activity {

	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textview);
		getSMSDetails();

	}

	private void getSMSDetails() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("*********SMS History*************** :");
		Uri uri = Uri.parse("content://sms");
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);

		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				String body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
						.toString();
				String number = cursor.getString(cursor.getColumnIndexOrThrow("address"))
						.toString();
				String date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
						.toString();
				Date smsDayTime = new Date(Long.valueOf(date));
				String type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
						.toString();
				String typeOfSMS = null;
				switch (Integer.parseInt(type)) {
				case 1:
					typeOfSMS = "INBOX";
					break;

				case 2:
					typeOfSMS = "SENT";
					break;

				case 3:
					typeOfSMS = "DRAFT";
					break;
				}

				stringBuffer.append("\nPhone Number:--- " + number + " \nMessage Type:--- "
						+ typeOfSMS + " \nMessage Date:--- " + smsDayTime
						+ " \nMessage Body:--- " + body);
				stringBuffer.append("\n----------------------------------");
				cursor.moveToNext();
			}
			textView.setText(stringBuffer);
		}
		cursor.close();
	}

}
