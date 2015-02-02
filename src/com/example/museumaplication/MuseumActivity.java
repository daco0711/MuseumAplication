package com.example.museumaplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;



import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class MuseumActivity extends ActionBarActivity {
	MediaPlayer mp;
	public static final String ip = "192.168.1.5";
	public static final int port = 80;
	public static final String MuseumID = "MuseumId";
	public static final String Museum_name = "MuseumName";
	public static final String Museum_address = "MuseumAddress";
	public static final String Established = "Established";
	public static String NAMESPACE = "WcfServiceMuseumNew";
	public static String GET_MUSEUM = "getMuseum";
	public static String GET_MUSEUM_ACTION = "MuseumService/GetMuseum";
	public static String URL = "http://" + ip + ":" + port
			+ "/WcfServiceMuseumNew/Service1.svc";
	public static String TAG = "MuseumActivity";
	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	TableLayout table;
	
	HashMap<String, String> kliknutMuzej;
	
	
	
	public static String SEARCH_RESULT = "SEARCH_RESULT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_museum);
		mp = MediaPlayer.create(MuseumActivity.this,R.raw.welcome);
		mp.start();
		tableLayoutParams = new LayoutParams();
		tableRowParams = new TableRow.LayoutParams();
		textViewLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		table = (TableLayout) findViewById(R.id.tableMuseum);
		table.setBackgroundColor(Color.BLACK);
		
		tableRowParams.setMargins(1, 1, 1, 1);
		tableLayoutParams.weight = 1;
		
		museumList();
	}
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.museum, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void museumList(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN");
				SoapObject request = new SoapObject(NAMESPACE, GET_MUSEUM);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(GET_MUSEUM_ACTION, envelope);
					Log.i(TAG, "after call");
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						for (int i = 0; i < response.getPropertyCount(); i++) {
							SoapObject returned = ((SoapObject) response.getProperty(i));
							makeRow(returned);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();

				} catch (XmlPullParserException e) {
					Log.i(TAG, "XML pull");
					e.printStackTrace();
				}
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}
	
	private void makeRow(SoapObject object) {
		TableRow tableRow = new TableRow(this);
		
		
		String museumId = object.getProperty(MuseumID).toString();
		tableRow.setId(Integer.parseInt(museumId));
		
		
		TextView nameTextView = new TextView(this);
		nameTextView.setBackgroundColor(Color.WHITE);
		nameTextView.setGravity(Gravity.CENTER);
		nameTextView.setLayoutParams(textViewLayoutParams);
		nameTextView.setText(object.getProperty(Museum_name).toString().split(";")[0]);
		tableRow.addView(nameTextView, tableRowParams);

		TextView addressTextView = new TextView(this);
		addressTextView.setBackgroundColor(Color.WHITE);
		addressTextView.setGravity(Gravity.CENTER);
		addressTextView.setText(object.getProperty(Museum_address).toString().split(";")[0]);
		tableRow.addView(addressTextView, tableRowParams);
		
		TextView establishedTextView = new TextView(this);
		establishedTextView.setBackgroundColor(Color.WHITE);
		establishedTextView.setGravity(Gravity.CENTER);
		establishedTextView.setText(object.getProperty(Established).toString().split(";")[0]);
		tableRow.addView(establishedTextView, tableRowParams);
		
		
		registerForContextMenu(tableRow);
		
		
		table.addView(tableRow, tableLayoutParams);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v instanceof TableRow) {
			TableRow selectedRow = (TableRow) v;
			
			TextView nameTW = (TextView) selectedRow.getChildAt(0);
			TextView addressTW = (TextView) selectedRow.getChildAt(1);
			TextView establishedTw = (TextView) selectedRow.getChildAt(2);
			
			
			int museumID = selectedRow.getId();
			String museumName = nameTW.getText().toString();
			String museumAddress = addressTW.getText().toString();
			String established =  establishedTw.getText().toString();
			
			
			
			kliknutMuzej = new HashMap<String, String>();
			kliknutMuzej.put(MuseumID, String.valueOf(museumID));
			kliknutMuzej.put(Museum_name, museumName);
			kliknutMuzej.put(Museum_address, museumAddress);
			kliknutMuzej.put(Established, established);
			
			
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.popupmuseum, menu);
		}
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		 if(item.getItemId() == R.id.popup_menu_update_itemMuseum) {
			if(kliknutMuzej != null) {
				Log.i(TAG, "TREBA UPDATE [ " + kliknutMuzej.get(MuseumID) + ", " + kliknutMuzej.get(Museum_name) + ", " + kliknutMuzej.get(Museum_address) + ", " + kliknutMuzej.get(Established)  + "]");
				update();
			}
		}
		return super.onContextItemSelected(item);
	}
	
	public void update(){
		Intent intent = new Intent(this,UpdateMuseumActivity.class);
		intent.putExtra(MuseumID, kliknutMuzej.get(MuseumID));
		intent.putExtra(Museum_name, kliknutMuzej.get(Museum_name));
		intent.putExtra(Museum_address, kliknutMuzej.get(Museum_address));
		intent.putExtra(Established, kliknutMuzej.get(Established));
		startActivity(intent);
		
	}

	
	public void exitMuseumActivity(View view)
	{
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
		
	}
}
