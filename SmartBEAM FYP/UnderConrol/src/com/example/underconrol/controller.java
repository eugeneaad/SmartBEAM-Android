package com.example.underconrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class controller extends Activity implements OnClickListener {
	
	Button btnPower,btnVolumeUp,btnVolumeDown,btnMute,btnPhoto,btnUp,btnMusic,
		   btnLeft,btnOK,btnRight,btnVideo,btnDown,btnText,btnMenu,btnExit,
		   btnPlayBack,btnPlayPause,btnPlayForward,btnPrevious,btnStop,btnNext;

	Intent i;
	String msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controller);
		
		i = getIntent();
		msg = i.getStringExtra("control");
		
		btnPower = (Button) findViewById(R.id.btnPower);
		btnVolumeUp = (Button) findViewById(R.id.btnVolumeUp);
		btnVolumeDown = (Button) findViewById(R.id.btnVolumeDown);
		btnMute = (Button) findViewById(R.id.btnMute);
		btnPhoto = (Button) findViewById(R.id.btnPhoto);
		btnUp = (Button) findViewById(R.id.btnUp);
		btnMusic = (Button) findViewById(R.id.btnMusic);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnOK = (Button) findViewById(R.id.btnOK);
		btnRight = (Button) findViewById(R.id.btnRight);
		btnVideo = (Button) findViewById(R.id.btnVideo);
		btnDown = (Button) findViewById(R.id.btnDown);
		btnText = (Button) findViewById(R.id.btnText);
		btnMenu = (Button) findViewById(R.id.btnMenu);
		btnExit = (Button) findViewById(R.id.btnExit);
		btnPlayBack = (Button) findViewById(R.id.btnPlayBack);
		btnPlayPause = (Button) findViewById(R.id.btnPlayPause);
		btnPlayForward = (Button) findViewById(R.id.btnPlayForward);
		btnPrevious = (Button) findViewById(R.id.btnPrevious);
		btnStop = (Button) findViewById(R.id.btnStop);
		btnNext = (Button) findViewById(R.id.btnNext);
		
		btnPower.setOnClickListener(this);
		btnVolumeUp.setOnClickListener(this);
		btnVolumeDown.setOnClickListener(this);
		btnMute.setOnClickListener(this);
		btnPhoto.setOnClickListener(this);
		btnUp.setOnClickListener(this);
		btnMusic.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnVideo.setOnClickListener(this);
		btnDown.setOnClickListener(this);
		btnText.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		btnPlayBack.setOnClickListener(this);
		btnPlayPause.setOnClickListener(this);
		btnPlayForward.setOnClickListener(this);
		btnPrevious.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnPower:
			i.putExtra("channelInfo", "Power");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;

		case R.id.btnVolumeUp:
			i.putExtra("channelInfo", "VolumeUp");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnVolumeDown:
			i.putExtra("channelInfo", "VolumeDown");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnMute:
			i.putExtra("channelInfo", "Mute");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnPhoto:
			i.putExtra("channelInfo", "Photo");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnUp:
			i.putExtra("channelInfo", "Up");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnMusic:
			i.putExtra("channelInfo", "Music");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnLeft:
			i.putExtra("channelInfo", "Left");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnOK:
			i.putExtra("channelInfo", "OK");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnRight:
			i.putExtra("channelInfo", "Right");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnVideo:
			i.putExtra("channelInfo", "Video");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnText:
			i.putExtra("channelInfo", "Text");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnDown:
			i.putExtra("channelInfo", "Down");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnMenu:
			i.putExtra("channelInfo", "Menu");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnExit:
			i.putExtra("channelInfo", "Exit");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnPlayBack:
			i.putExtra("channelInfo", "PlayBack");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnPlayPause:
			i.putExtra("channelInfo", "PlayPause");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnPlayForward:
			i.putExtra("channelInfo", "PlayForward");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnPrevious:
			i.putExtra("channelInfo", "Previous");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnStop:
			i.putExtra("channelInfo", "Stop");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		case R.id.btnNext:
			i.putExtra("channelInfo", "Next");
	  		setResult(RESULT_OK, i);
	  		finish();
			break;
		
		}
	}
}
