package com.example.underconrol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.spi.CharsetProvider;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import com.example.underconrol.R;
import javax.xml.datatype.DatatypeFactory;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.DataUsageFeedback;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class view extends Activity implements OnClickListener,OnItemLongClickListener{
	
	scheduleDB entry = new scheduleDB(this);
	
	
	
	private TimePicker timePicker;
	private int hour;
	private int minute;
	int chour;
	int cminute;
	int time;
	static final int TIME_DIALOG_ID = 999;

	String timehour;
	String timeminute;
	
	boolean enable = true;
	boolean stoptimer = true;
	
	Button btnPower,btnVolumeUp,btnVolumeDown,btnMute,btnPhoto,btnUp,btnMusic,
    btnLeft,btnOK,btnRight,btnVideo,btnDown,btnText,btnMenu,btnExit,
    btnPlayBack,btnPlayPause,btnPlayForward,btnPrevious,btnStop,btnNext;
	
   String CodePower ; 
   String CodeMute ; 
   String CodeVolumeUp ; 
   String CodeVolumeDown ; 
   String CodeUp ; 
   String CodeDown ; 
   String CodeLeft ; 
   String CodeRight ; 
   String CodeOK ; 
   String CodePhoto; 
   String CodeMusic ;
   String CodeVideo ; 
   String CodeText ; 
   String CodeMenu ; 
   String CodeExit; 
   String CodePlayBack ; 
   String CodePlayPause ; 
   String CodePlayForward ; 
   String CodePrevious ; 
   String CodeStop ; 
   String CodeNext ; 
	
	TabHost thmenu;
	
	long start,stop;
	
	Button btnTP,btnChannel,btnAdd,btnSmart;
	
	ListView mylist;
	
	Handler h;
	final int RECIEVE_MESSAGE = 1;	
	private static final String TAG = "bluetooth1";  
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private StringBuilder sb = new StringBuilder();
	  
	private ConnectedThread mConnectedThread;
	String sbprint;
	  // SPP UUID service 
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB" );
	  
	  // MAC-address of Bluetooth module (you must edit this line)
	private static String address = "22:13:02:01:03:17";
	//private static String address = "00:12:10:19:02:12";
	String channeldata; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		
		thmenu = (TabHost)findViewById(R.id.thmenu);

		thmenu.setup();
		TabSpec specs = thmenu.newTabSpec("tag1");
		specs.setContent(R.id.Smart);
		specs.setIndicator("Smart");
		thmenu.addTab(specs);
		
		specs = thmenu.newTabSpec("tag2");
		specs.setContent(R.id.Favorate);
		specs.setIndicator("Favorate");
		thmenu.addTab(specs);
		
		specs = thmenu.newTabSpec("tag3");
		specs.setContent(R.id.Day);
		specs.setIndicator("Day");
		thmenu.addTab(specs);
		
		specs = thmenu.newTabSpec("tag4");
		specs.setContent(R.id.Remote);
		specs.setIndicator("Remote");
		thmenu.addTab(specs);
		
		start = 0;
		
		setCurrentTimeOnView();
		addButtonListener();
		h = new Handler() {
	    	public void handleMessage(android.os.Message msg) {
	    		switch (msg.what) {
	            case RECIEVE_MESSAGE:													// if receive massage
	            	byte[] readBuf = (byte[]) msg.obj;
	            	String strIncom = new String(readBuf, 0, msg.arg1);					// create string from bytes array
	            	sb.append(strIncom);												// append string
	            	int endOfLineIndex = sb.indexOf("\r\n");							// determine the end-of-line
	            	if (endOfLineIndex > 0) { 											// if end-of-line,
	            		sbprint = sb.substring(0, endOfLineIndex);				// extract string
	                    sb.delete(0, sb.length());										// and clear
	                	        // update TextView
	                
	            	}
	            	
	            	//Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
	            	break;
	    		}
	    		
	        };
	        
		};
		
		Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						
						
						final Calendar c = Calendar.getInstance();
						
						hour = c.get(Calendar.HOUR_OF_DAY);
						minute = c.get(Calendar.MINUTE);
						
						String timenow = null,dataTime,change,hourString,minString;
						hourString = String.valueOf(hour);
						minString =String.valueOf(minute);
						
						if(hour<10&&minute<10)		timenow = "0" + hourString + ":" + "0" + minString;
						if(hour>=10&&minute>=10)	timenow = hourString + ":" + minString;
						if(minute<10&&hour>=10)		timenow = hourString + ":" + "0" + minString;
						if(hour<10&&minute>=10)		timenow = "0" + hourString + ":" + minString;
						
						dataTime = entry.getDataTime();
						
						change = entry.getDataChannel();
						String[] dataTimes = dataTime.split("\n");						
						String[] changes = change.split("\n");
						String channelno = null;
						int channelpress = 0;
					
						for (int i = 0; i < dataTimes.length; i++){
						
						if(dataTimes[i].equals(timenow) && stoptimer == true && channeldata != null)
						{
							
							
								channelno = changes[i];
								
								
								if(channelno.equals("Power"))
								{
									channelpress = 1;
								}
								else if(channelno.equals("VolumeUp"))
								{
									channelpress = 2;
								}
								if(channelno.equals("VolumeDown"))
								{
									channelpress = 3;
								}
								if(channelno.equals("Mute"))
								{
									channelpress = 4;
								}
								if(channelno.equals("Photo"))
								{
									channelpress = 5;
								}
								if(channelno.equals("Up"))
								{
									channelpress = 6;
								}
								if(channelno.equals("Music"))
								{
									channelpress = 7;
								}
								
								
								if(channelno.equals("Left"))
								{
									channelpress = 8;
								}
								if(channelno.equals("OK"))
								{
									channelpress = 9;
								}
								if(channelno.equals("Right"))
								{
									channelpress = 10;
								}
								
								
								if(channelno.equals("Video"))
								{
									channelpress = 11;
								}
								if(channelno.equals("Down"))
								{
									channelpress = 12;
								}
								if(channelno.equals("Text"))
								{
									channelpress = 13;
								}
								if(channelno.equals("Menu"))
								{
									channelpress = 14;
								}
								if(channelno.equals("Exit"))
								{
									channelpress = 15;
								}
								if(channelno.equals("PlayBack"))
								{
									channelpress = 16;
								}
								if(channelno.equals("PlayPause"))
								{
									channelpress = 17;
								}
								if(channelno.equals("PlayForward"))
								{
									channelpress = 18;
								}
								if(channelno.equals("Previous"))
								{
									channelpress = 19;
								}
								if(channelno.equals("Stop"))
								{
									channelpress = 20;
								}
								if(channelno.equals("Next"))
								{
									channelpress = 21;
								}

						
						
						switch (channelpress) {
						case 1:
							btnPower.performClick();
							break;
						case 2:
							btnVolumeUp.performClick();
							break;
						case 3:
							btnVolumeDown.performClick();
							break;
						case 4:
							btnMute.performClick();
							break;
						case 5:
							btnPhoto.performClick();
							break;
						case 6:
							btnUp.performClick();
							break;
						case 7:
							btnMusic.performClick();
							break;

						case 8:
							btnLeft.performClick();
							break;
						case 9:
							btnOK.performClick();
							break;
						case 10:
							btnRight.performClick();
							break;

							

						case 11:
							btnVideo.performClick();
							break;

						case 12:
							btnDown.performClick();
							break;
						case 13:
							btnText.performClick();
							break;
						case 14:
							btnMenu.performClick();
							break;
						case 15:
							btnExit.performClick();
							break;
						case 16:
							btnPlayBack.performClick();
							break;
						case 17:
							btnPlayPause.performClick();
							break;
							
						case 18:
							btnPlayForward.performClick();
							break;
						case 19:
							btnPrevious.performClick();
							break;
						case 20:
							btnStop.performClick();
							break;
						case 21:
							btnNext.performClick();
							break;
						}
						stoptimer = false;
					}
						}
					}
					
				});
			}
        	
        }, 0, 1000);

        
		 btAdapter = BluetoothAdapter.getDefaultAdapter();
		    checkBTState();
		    
		    CodePower = getString(R.string.CodePower); 
		    CodeMute = getString(R.string.CodeMute); 
		    CodeVolumeUp = getString(R.string.CodeVolumeUp); 
		    CodeVolumeDown = getString(R.string.CodeVolumeDown); 
		    CodeUp = getString(R.string.CodeUp); 
		    CodeDown = getString(R.string.CodeDown); 
		    CodeLeft = getString(R.string.CodeLeft); 
		    CodeRight = getString(R.string.CodeRight); 
		    CodeOK = getString(R.string.CodeOK); 
		    CodePhoto = getString(R.string.CodePhoto); 
		    CodeMusic = getString(R.string.CodeMusic);
		    CodeVideo = getString(R.string.CodeVideo); 
		    CodeText = getString(R.string.CodeText); 
		    CodeMenu = getString(R.string.CodeMenu); 
		    CodeExit = getString(R.string.CodeExit); 
		    CodePlayBack = getString(R.string.CodePlayBack); 
		    CodePlayPause = getString(R.string.CodePlayPause); 
		    CodePlayForward = getString(R.string.CodePlayForward); 
		    CodePrevious = getString(R.string.CodePrevious); 
		    CodeStop = getString(R.string.CodeStop); 
		    CodeNext = getString(R.string.CodeNext); 

		    
		    
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

		    entry.open();
		    populateListviewFromDB();
		    mylist.setOnItemLongClickListener(this);
		   
		  
		    
	}

	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btnPower:
			sendData(CodePower);
	        Toast.makeText(getBaseContext(), "Power", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnVolumeUp:
			sendData(CodeVolumeUp);
	        Toast.makeText(getBaseContext(), "Volume Up", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnVolumeDown:
			sendData(CodeVolumeDown);
	        Toast.makeText(getBaseContext(), "Volume Down", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnMute:
			sendData(CodeMute);
	        Toast.makeText(getBaseContext(), "Mute", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnPhoto:
			sendData(CodePhoto);
	        Toast.makeText(getBaseContext(), "Photo", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnUp:
			sendData(CodeUp);
	        Toast.makeText(getBaseContext(), "Up", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnMusic:
			sendData(CodeMusic);
	        Toast.makeText(getBaseContext(), "Music", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnLeft:
			sendData(CodeLeft);
	        Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnOK:
			sendData(CodeOK);
	        Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnRight:
			sendData(CodeRight);
	        Toast.makeText(getBaseContext(), "Right", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnVideo:
			sendData(CodeVideo);
	        Toast.makeText(getBaseContext(), "Video", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnDown:
			sendData(CodeDown);
	        Toast.makeText(getBaseContext(), "Down", Toast.LENGTH_SHORT).show();
			break;

		case R.id.btnText:
			sendData(CodeText);
	        Toast.makeText(getBaseContext(), "Text", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnMenu:
			sendData(CodeMenu);
	        Toast.makeText(getBaseContext(), "Menu", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnExit:
			sendData(CodeExit);
	        Toast.makeText(getBaseContext(), "Exit", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnPlayBack:
			sendData(CodePlayBack);
	        Toast.makeText(getBaseContext(), "PlayBack", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnPlayPause:
			sendData(CodePlayPause);
	        Toast.makeText(getBaseContext(), "Play/Pause", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnPlayForward:
			sendData(CodePlayForward);
	        Toast.makeText(getBaseContext(), "PlayForward", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnPrevious:
			sendData(CodePrevious);
	        Toast.makeText(getBaseContext(), "Previous", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnStop:
			sendData(CodeStop);
	        Toast.makeText(getBaseContext(), "Stop", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnNext:
			sendData(CodeNext);
	        Toast.makeText(getBaseContext(), "Next", Toast.LENGTH_SHORT).show();
			break;
		
		}
	}
	
	
	private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
	      if(Build.VERSION.SDK_INT >= 10)
	      {
	          try 
	          {
	              final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
	              return (BluetoothSocket) m.invoke(device, MY_UUID);
	          } 
	          catch (Exception e) 
	          {
	              Log.e(TAG, "Could not create Insecure RFComm Connection",e);
	          }
	      }
	      return  device.createRfcommSocketToServiceRecord(MY_UUID);
	  }
	    
	  @Override
	  public void onResume() {
	    super.onResume();
	  
	    Log.d(TAG, "...onResume - try connect...");
	    
	    // Set up a pointer to the remote node using it's address.
	    BluetoothDevice device = btAdapter.getRemoteDevice(address);
	    
	    // Two things are needed to make a connection:
	    //   A MAC address, which we got above.
	    //   A Service ID or UUID.  In this case we are using the
	    //     UUID for SPP.
	    
	    try 
	    {
	        btSocket = createBluetoothSocket(device);
	    } 
	    catch (IOException e1) 
	    {
	        errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
	    }
	        
	    // Discovery is resource intensive.  Make sure it isn't going on
	    // when you attempt to connect and pass your message.
	    btAdapter.cancelDiscovery();
	    
	    // Establish the connection.  This will block until it connects.
	    Log.d(TAG, "...Connecting...");
	    try 
	    {
	      btSocket.connect();
	      Log.d(TAG, "...Connection ok...");
	    } 
	    catch (IOException e) 
	    {
	      try 
	      {
	        btSocket.close();
	      } catch (IOException e2) 
	      {
	        errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
	      }
	    }
	      
	    // Create a data stream so we can talk to server.
	    Log.d(TAG, "...Create Socket...");
	  
	    try 
	    {
	      outStream = btSocket.getOutputStream();
	    } catch (IOException e) {
	      errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
	    }
	  }
	  
	  @Override
	  public void onPause() {
	    super.onPause();
	  
	    Log.d(TAG, "...In onPause()...");
	  
	    if (outStream != null) {
	      try {
	        outStream.flush();
	      } catch (IOException e) {
	        errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
	      }
	    }
	  
	    try     {
	      btSocket.close();
	    } catch (IOException e2) {
	      errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
	    }
	  }
	    
	  private void checkBTState() {
	    // Check for Bluetooth support and then check to make sure it is turned on
	    // Emulator doesn't support Bluetooth and will return null
	    if(btAdapter==null) { 
	      errorExit("Fatal Error", "Bluetooth not support");
	    } else {
	      if (btAdapter.isEnabled()) {
	        Log.d(TAG, "...Bluetooth ON...");
	      } else {
	        //Prompt user to turn on Bluetooth
	        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	        startActivityForResult(enableBtIntent, 1);
	      }
	    }
	  }
	  
	  private void errorExit(String title, String message){
	    Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
	    finish();
	  }
	  
	  private void sendData(String message) {
	    byte[] msgBuffer = message.getBytes();
	  
	    Log.d(TAG, "...Send data: " + message + "...");
	  
	    try {
	      outStream.write(msgBuffer);
	    } catch (IOException e) {
	      String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
	      if (address.equals("00:00:00:00:00:00")) 
	        msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
	        msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
	        
	        errorExit("Fatal Error", msg);       
	    }
	  }

	  private class ConnectedThread extends Thread {
		    private final InputStream mmInStream;
		    private final OutputStream mmOutStream;
		 
		    public ConnectedThread(BluetoothSocket socket) {
		        InputStream tmpIn = null;
		        OutputStream tmpOut = null;
		 
		        // Get the input and output streams, using temp objects because
		        // member streams are final
		        try {
		            tmpIn = socket.getInputStream();
		            tmpOut = socket.getOutputStream();
		        } catch (IOException e) { }
		 
		        mmInStream = tmpIn;
		        mmOutStream = tmpOut;
		    }
		 
		    public void run() {
		        byte[] buffer = new byte[256];  // buffer store for the stream
		        int bytes; // bytes returned from read()

		        // Keep listening to the InputStream until an exception occurs
		        while (true) {
		        	try {
		                // Read from the InputStream
		                bytes = mmInStream.read(buffer);		// Get number of bytes and message in "buffer"
	                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();		// Send to message queue Handler
		            } catch (IOException e) {
		                break;
		            }
		        }
		    }
		 
		    /* Call this from the main activity to send data to the remote device */
		    public void write(String message) {
		    	Log.d(TAG, "...Data to send: " + message + "...");
		    	byte[] msgBuffer = message.getBytes();
		    	try {
		            mmOutStream.write(msgBuffer);
		        } catch (IOException e) {
		            Log.d(TAG, "...Error data send: " + e.getMessage() + "...");     
		          }
		    }
		}
	  
	  public void setCurrentTimeOnView() {

			
			timePicker = (TimePicker) findViewById(R.id.timePicker);

			//final Calendar c = Calendar.getInstance();
			//hour = c.get(Calendar.HOUR_OF_DAY);
			//minute = c.get(Calendar.MINUTE);

			// set current time into textview
			//textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));

			// set current time into timepicker
			timePicker.setCurrentHour(hour);
			timePicker.setCurrentMinute(minute);
			
		}

		public void addButtonListener() {

			btnTP = (Button) findViewById(R.id.btnTP);
			
			btnTP.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					showDialog(TIME_DIALOG_ID);
					
					
				}

			});
			btnChannel = (Button) findViewById(R.id.btnChannel);
			btnChannel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent getdata = new Intent("android.intent.action.controller");
					getdata.putExtra("control", "code");
					startActivityForResult(getdata, 1);
					
					
					}
			});
			btnAdd = (Button) findViewById(R.id.btnAdd);
			btnAdd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					btnTP.performClick();
					btnChannel.performClick();
					enable = true;
				}
			});
			btnSmart = (Button) findViewById(R.id.btnSmart);
			btnSmart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnUp.performClick();
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
					  @Override
					  public void run() {
					    //Do something after 100ms
						  btnUp.performClick();
					  }
					}, 5000);
				}
			});
			
			//mylist.setOnItemLongClickListener((OnItemLongClickListener) this);
		}

		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case TIME_DIALOG_ID:
				// set time picker as current time
				return new TimePickerDialog(this, timePickerListener, hour, minute,false);

			}
			return null;
		}
		
		
		
		
		
		private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
				hour = selectedHour;
				minute = selectedMinute;

				// set current time into textview
				//textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));

				// set current time into timepicker
				timePicker.setCurrentHour(hour);
				timePicker.setCurrentMinute(minute);
				
				String timehour = String.valueOf(hour);
				String timeminute = String.valueOf(minute);
				
				stoptimer = true;
				
				if(channeldata!=null &&enable == true){
					
					if(hour<10&&minute>=10)
					{
						
						String time = "0" + timehour + ":" + timeminute;
						String channel = channeldata;
						entry.createEntry(time,channel);
						populateListviewFromDB();
						
						
					}
					if(minute<10&&hour>=10)
					{
						String time =  timehour + ":" + "0" + timeminute;
						String channel = channeldata;
						entry.createEntry(time,channel);
						populateListviewFromDB();
						
						
					}
					if(hour<10&&minute<10)
					{
						String time = "0" + timehour + ":" + "0" + timeminute;
						String channel = channeldata;
						entry.createEntry(time,channel);
						populateListviewFromDB();
						
					}
					if (hour>=10&&minute>=10){
						String time = timehour + ":" + timeminute;
						String channel = channeldata;
						entry.createEntry(time,channel);
						populateListviewFromDB();
					}
					enable = false;
				}
			}
		};

		/*private static String padding_str(int c) {
			if (c >= 10)
			   return String.valueOf(c);
			else
			   return "0" + String.valueOf(c);
		}*/
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			if(data.getExtras().containsKey("channelInfo"));
			{
				
				channeldata = data.getStringExtra("channelInfo");
				
			}
			
		}
		
		public void populateListviewFromDB() {
			// TODO Auto-generated method stub
			
			Cursor cursor = entry.getAllRows();
			startManagingCursor(cursor);
			
			
			
			String [] fromFiled = new String[]
					{scheduleDB.KEY_ROWID,scheduleDB.KEY_TIME,scheduleDB.KEY_CHANNEL};
			int[] toFiled = new int[] 
					{R.id.tvRow,R.id.tvTime,R.id.tvChannel};
			
			
			SimpleCursorAdapter myCursorAdapter =  
					new SimpleCursorAdapter(this, R.layout.sqlite, cursor, fromFiled, toFiled);
			
			
			mylist = (ListView)findViewById(R.id.lvtime);
			mylist.setAdapter(myCursorAdapter);
		}

		
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg0.getId()) {
			case R.id.lvtime:
				entry.deleteRow(arg3);
				populateListviewFromDB();
				break;
			}
				
			// TODO Auto-generated method stub
			return true;
		}
		
		protected void onDestroy() {
			super.onDestroy();	
			entry.close();
		}

}
