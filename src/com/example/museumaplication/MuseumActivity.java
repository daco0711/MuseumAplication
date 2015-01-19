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

import com.example.museumadapters.MuseumListAdapter;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class MuseumActivity extends ActionBarActivity {
	MediaPlayer mp;
	public static final String ip = "192.168.1.5";
	public static final int port = 80;

	public static final String Museum_name = "MuseumName";
	public static final String Museum_address = "MuseumAddress";
	public static final String Established = "Established";
	public static String NAMESPACE = "WcfServiceMuseumNew";
	public static String GET_MUSEUM = "getMuseum";
	public static String GET_MUSEUM_ACTION = "MuseumService/GetMuseum";
	public static String URL = "http://" + ip + ":" + port
			+ "/WcfServiceMuseumNew/Service1.svc";
	public static String TAG = "MuseumActivity";
	GridView museumList;
	ArrayList<HashMap<String, String>> museum_list = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> map;
	private MuseumListAdapter adapter;
	public static String SEARCH_RESULT = "SEARCH_RESULT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_museum);
		mp = MediaPlayer.create(MuseumActivity.this,R.raw.welcome);
		mp.start();
		
		museumList = (GridView) findViewById(R.id.gridView1);
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(SEARCH_RESULT)) {
			museum_list = (ArrayList<HashMap<String, String>>) getIntent()
					.getExtras().get(SEARCH_RESULT);

		} else {
			museumList();
		}
		adapter = new MuseumListAdapter(this, museum_list);
		museumList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		registerForContextMenu(museumList);
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

	public void museumList() {
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
						// Log.i(Tag,"broj studenata u bazi:"+
						// response.getPropertyCount());

						for (int i = 0; i < response.getPropertyCount(); i++) {
							Log.i(TAG, "napuni listu");
							 //Log.i(TAG, "property" +
							 //response.getProperty(i).toString());
							SoapObject returned = ((SoapObject) response
									.getProperty(i));
							fillHashMap(museum_list, returned);
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

	public static void fillHashMap(
			ArrayList<HashMap<String, String>> museumList, SoapObject returned) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Museum_name, returned.getProperty(Museum_name).toString()
				.split(";")[0]);
		map.put(Museum_address, returned.getProperty(Museum_address).toString()
				.split(";")[0]);
		map.put(Established, returned.getProperty(Established).toString()
				.split(";")[0]);

		museumList.add(map);
	}
	public void exitMuseumActivity(View view)
	{
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
		
	}
}
