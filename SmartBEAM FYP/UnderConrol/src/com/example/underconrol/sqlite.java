package com.example.underconrol;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

public class sqlite extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlite);
		
		
		TextView tvTime = (TextView)findViewById(R.id.tvTime);
		TextView tvChannel = (TextView)findViewById(R.id.tvChannel);
		TextView tvRow = (TextView)findViewById(R.id.tvRow);
		String dataTime = null,dataChannel = null,dataRow = null;
		
		
		boolean didItWork = true;
		try {
			
			scheduleDB info = new scheduleDB(this);
			info.open();
			
			dataRow = info.getDataRow();
			dataTime = info.getDataTime();
			dataChannel = info.getDataChannel();
			
			info.close();
			
			
		} catch (Exception e) {
			didItWork = false;
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Fail");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
			// TODO: handle exception
		}
		finally{
			if(didItWork){
				tvRow.setText(dataRow);
				tvTime.setText(dataTime);
				tvChannel.setText(dataChannel);
			}
		}
		
	}

}
