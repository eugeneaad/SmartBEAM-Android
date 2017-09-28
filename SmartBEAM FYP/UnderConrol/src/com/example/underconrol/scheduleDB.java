package com.example.underconrol;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class scheduleDB {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TIME = "time_hour";
	public static final String KEY_CHANNEL = "channel";
	
	private static final String DATABASE_NAME = "Time_Channel";
	private static final String DATABASE_TABLE = "Time_Channel_table";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_TIME, KEY_CHANNEL};
	
	
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
						KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_TIME + " TEXT NOT NULL, " + 
						KEY_CHANNEL + " TEXT NOT NULL);"
			);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public scheduleDB (Context C){
		ourContext =C;
		
	}
	public scheduleDB open(){
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	public void close() {
		ourHelper.close();
	}
	public long createEntry(String time, String channel) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TIME, time);
		cv.put(KEY_CHANNEL, channel);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}
	public String getDataTime() {
		// TODO Auto-generated method stub
		String[] columns = new String[]{ KEY_ROWID,KEY_TIME,KEY_CHANNEL};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result ="";
		
		int iTime = c.getColumnIndex(KEY_TIME);
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){

				result = result + c.getString(iTime) + "\n";
		}
		
		return result;
	}
	public String getDataChannel() {
		// TODO Auto-generated method stub
		String[] columns = new String[]{ KEY_ROWID,KEY_TIME,KEY_CHANNEL};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result ="";

		int iChannel = c.getColumnIndex(KEY_CHANNEL);
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result = result + c.getString(iChannel) +"\n";
		}
		
		return result;
	}
	public String getDataRow() {
		// TODO Auto-generated method stub
		String[] columns = new String[]{ KEY_ROWID,KEY_TIME,KEY_CHANNEL};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		String result ="";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result = result + c.getString(iRow) + "." + "\n";
		}
		
		return result;
	}
	
	 public String getchanneltopress(){

		   
		    String selectQuery = "SELECT KEY_CHANNEL FROM " + DATABASE_TABLE+ " WHERE " + KEY_TIME + " = '";
		          
		    Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		    String data = null;
		    if (cursor.moveToFirst()) {
		        do {
		           // get  the  data into array,or class variable
		        } while (cursor.moveToNext());
		    }
		    ourDatabase.close();
		    return data;
		}
	
	
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	ourDatabase.query( DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null);
		
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return ourDatabase.delete(DATABASE_TABLE, where, null) != 0;
	}
}
