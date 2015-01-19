package com.example.museumaplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import com.example.museumadapters.MuseumListAdapter;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class LocationsActivity extends ActionBarActivity {
	public static final String ip = "192.168.1.5";
	public static final int port = 80;
	public static final String locationName = "LocationName";
	public static final String surface = "Surface";
	public static final String state = "State";
	public static final String leasePrice = "LeasePrice";
	public static final String country = "Country";
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getLocations = "getLocations";
	public static String getLocationsAction = "MuseumService/GetLocations";
	public static String TAg = "LocationsActivity";
	public static String findLocations = "findLocations";
	public static String findLocationsAction = "MuseumService/FindLocations";
	public static String addname = "locationName";
	public static String addsurface = "Surface";
	public static String addstate = "State";
	public static String addLease = "Lease Price";
	public static String addCountry = "Country";
	public static String addLocations = "addLocations";
	public static String addLocationsAction = "MuseumService/AddLocations";
	TableLayout table;
	public static String searchTerm = "Search_Results";
	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations);
		mp = MediaPlayer.create(LocationsActivity.this, R.raw.raptor);
		mp.start();
		tableLayoutParams = new LayoutParams();
		tableRowParams = new TableRow.LayoutParams();
		textViewLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		table = (TableLayout) findViewById(R.id.tableLocations);
		table.setBackgroundColor(Color.BLACK);
		
		tableRowParams.setMargins(1, 1, 1, 1);
		tableLayoutParams.weight = 1;
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(addname) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addsurface) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addstate) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addLease) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addCountry)){
			String dodajname = getIntent().getExtras().getString(addname);
			String dodajsurface = getIntent().getExtras().getString(addsurface);
			String dodajstate = getIntent().getExtras().getString(addstate);
			String dodajleasePrice = getIntent().getExtras().getString(addLease);
			String dodajcountry = getIntent().getExtras().getString(addCountry);
			addLocations(dodajname,dodajsurface,dodajstate,dodajleasePrice,dodajcountry);
		}else if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(searchTerm)) {
			String searchName = getIntent().getExtras().getString(searchTerm);
			searchLocations(searchName);
		} else {
			listOfLocations();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.locations, menu);
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
	public void addLocations(final String name,final String surface,final String state,final String leasePrice,final String country){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				SoapObject request = new SoapObject(namespace, addLocations);
				
				PropertyInfo propName = new PropertyInfo();
				propName.setName("locationName");
				propName.setType(PropertyInfo.STRING_CLASS);
				propName.setValue(name);
				request.addProperty(propName);
				
				
				PropertyInfo propSurface = new PropertyInfo();
				propSurface.setName("surface");
				propSurface.setType(PropertyInfo.STRING_CLASS);
				propSurface.setValue(surface);
				request.addProperty(propSurface);
				
				PropertyInfo propState = new PropertyInfo();
				propState.setName("state");
				propState.setType(PropertyInfo.STRING_CLASS);
				propState.setValue(state);
				request.addProperty(propState);
				
				PropertyInfo propLeasePrice = new PropertyInfo();
				propLeasePrice.setName("leasePrice");
				propLeasePrice.setType(PropertyInfo.STRING_CLASS);
				propLeasePrice.setValue(leasePrice);
				request.addProperty(propLeasePrice);
				
				PropertyInfo propMuseumId = new PropertyInfo();
				propMuseumId.setName("museumIdFK");
				propMuseumId.setType(PropertyInfo.INTEGER_CLASS);
				request.addProperty(propMuseumId);
				
				PropertyInfo propCountry = new PropertyInfo();
				propCountry.setName("country");
				propCountry.setType(PropertyInfo.STRING_CLASS);
				propCountry.setValue(country);
				request.addProperty(propCountry);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.implicitTypes = true;
				envelope.dotNet = true;
				envelope.encodingStyle = SoapSerializationEnvelope.XSD;
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAg, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAg, "before call");
					Log.i(TAg, "RUN ADD LOCATIONS: " + propName.getValue() + ", " + propSurface.getValue() + ", " + propState.getValue() + ", " + propLeasePrice.getValue() + ", " + propMuseumId.getValue() + ", " + propCountry.getValue());
					transport.debug = true;
					transport.call(addLocationsAction, envelope);
					Log.i(TAg, "after call");
					listOfLocations();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					Log.i(LocationsActivity.TAg, "XML pull");
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
	
	public void searchLocations(final String name) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAg, "RUN FIND LOCATIONS: " + name);
				SoapObject request = new SoapObject(namespace,findLocations);
				PropertyInfo prop = new PropertyInfo();
				prop.name = "locationName";
				prop.type = PropertyInfo.STRING_CLASS;
				request.addProperty(prop, name);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAg, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAg, "before call");
					transport.debug = true;
					transport.call(findLocationsAction, envelope);
					Log.i(TAg, "after call");
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						System.out.println("RETURN: " + response.getPropertyCount());
						for(int i=0; i<response.getPropertyCount(); i++) {
							SoapObject location = (SoapObject) response.getProperty(i);
							makeRow(location);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();

				} catch (XmlPullParserException e) {
					Log.i(LocationsActivity.TAg, "XML pull");
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
	
	public void listOfLocations(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAg, "RUN");
				SoapObject request = new SoapObject(namespace, getLocations);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAg, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAg, "before call");
					transport.debug = true;
					transport.call(getLocationsAction, envelope);
					Log.i(TAg, "after call");
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
					Log.i(TAg, "XML pull");
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
		
		TextView nameTextView = new TextView(this);
		nameTextView.setBackgroundColor(Color.WHITE);
		nameTextView.setGravity(Gravity.CENTER);
		nameTextView.setLayoutParams(textViewLayoutParams);
		nameTextView.setText(object.getProperty(locationName).toString().split(";")[0]);
		tableRow.addView(nameTextView, tableRowParams);

		TextView surfaceTextView = new TextView(this);
		surfaceTextView.setBackgroundColor(Color.WHITE);
		surfaceTextView.setGravity(Gravity.CENTER);
		surfaceTextView.setText(object.getProperty(surface).toString().split(";")[0]);
		tableRow.addView(surfaceTextView, tableRowParams);
		
		TextView stateTextView = new TextView(this);
		stateTextView.setBackgroundColor(Color.WHITE);
		stateTextView.setGravity(Gravity.CENTER);
		stateTextView.setText(object.getProperty(state).toString().split(";")[0]);
		tableRow.addView(stateTextView, tableRowParams);
		
		TextView leaseTextView = new TextView(this);
		leaseTextView.setBackgroundColor(Color.WHITE);
		leaseTextView.setGravity(Gravity.CENTER);
		leaseTextView.setText(object.getProperty(leasePrice).toString().split(";")[0]);
		tableRow.addView(leaseTextView, tableRowParams);
		
		TextView countryTextView = new TextView(this);
		countryTextView.setBackgroundColor(Color.WHITE);
		countryTextView.setGravity(Gravity.CENTER);
		countryTextView.setText(object.getProperty(country).toString().split(";")[0]);
		tableRow.addView(countryTextView, tableRowParams);
		
		table.addView(tableRow, tableLayoutParams);
	}

	public void searchLocations(View view)
	{
		Intent intent = new Intent(this,LocationsSearchActivity.class);
		startActivity(intent);
	}
	public void addLocations(View view){
		Intent intent = new Intent(this,AddLocationsActivity.class);
		startActivity(intent);
	}
	protected void onPause() {
		super.onPause();
		mp.release();
	}
}
