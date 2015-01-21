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
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class LocationsActivity extends ActionBarActivity {
	public static final String ip = "192.168.1.3";
	public static final int port = 80;
	public static final String locationName = "LocationName";
	public static final String surface = "Surface";
	public static final String state = "State";
	public static final String leasePrice = "LeasePrice";
	public static final String country = "Country";
	public static final String MuseumIdFK = "MuseumIdFK";
	public static final String locationId = "LocationId";
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
	public static String addMuseumIDFK = "museumIDFK";
	TableLayout table;
	public static String searchTerm = "Search_Results";
	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	MediaPlayer mp;

	HashMap<String, String> kliknutaLokacija;
	
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
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(searchTerm)) {
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
	
	//public void addLocations(final String name,final String surface,final String state,final String leasePrice,final String country,final String museumID){
		//Thread t = new Thread(new Runnable() {
			//@Override
			//public void run() {
				//Log.i(TAg, "RUN ADD Locations: " + name + surface + state + leasePrice + country + museumID);
				//SoapObject request = new SoapObject(namespace, addLocations);
				//PropertyInfo propName = new PropertyInfo();
				//propName.name = "name";
				//propName.type = PropertyInfo.STRING_CLASS;
				//request.addProperty(propName, name);
				//PropertyInfo propSurface = new PropertyInfo();
				//propSurface.name = "surface";
				//propSurface.type = PropertyInfo.STRING_CLASS;
				//request.addProperty(propSurface,surface);
				//PropertyInfo propState = new PropertyInfo();
				//propState.name = "state";
				//propState.type = PropertyInfo.STRING_CLASS;
				//request.addProperty(propState,state);
				//PropertyInfo propLeasePrice = new PropertyInfo();
				//propLeasePrice.name = "leasePrice";
				//propLeasePrice.type = PropertyInfo.STRING_CLASS;
				//request.addProperty(propLeasePrice,leasePrice);
				
				//PropertyInfo propCountry = new PropertyInfo();
				//propCountry.name = "country";
				//propCountry.type = PropertyInfo.STRING_CLASS;
				//request.addProperty(propCountry,country);
				
				//PropertyInfo propMuseumIDFK = new PropertyInfo();
				//propMuseumIDFK.name = "museumIdFK";
				//propLeasePrice.type = PropertyInfo.INTEGER_CLASS;
				//request.addProperty(propMuseumIDFK,museumID);
				
				//SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				//envelope.dotNet = true;
				//envelope.setOutputSoapObject(request);
				//HttpTransportSE transport = new HttpTransportSE(URL);
				//Log.i(TAg, "Pozivam WCF... [" + URL + "]");
				//try {
					//Log.i(TAg, "before call");
					//transport.debug = true;
					//transport.call(addLocationsAction, envelope);
					//Log.i(TAg, "after call");
					//listOfLocations();
				//} catch (IOException e) {
					//e.printStackTrace();
				//} catch (XmlPullParserException e) {
					//Log.i(LocationsActivity.TAg, "XML pull");
					//e.printStackTrace();
				//}
			//}
		//});
		//t.start();
		//try {
			//t.join();
		//} catch (InterruptedException e) {
			//e.printStackTrace();
		//}
	//}
	
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v instanceof TableRow) {
			TableRow selectedRow = (TableRow) v;
			
			TextView nameTW = (TextView) selectedRow.getChildAt(0);
			TextView surfaceTW = (TextView) selectedRow.getChildAt(1);
			TextView stateTw = (TextView) selectedRow.getChildAt(2);
			TextView lPriceTW = (TextView) selectedRow.getChildAt(3);
			TextView countryTW = (TextView) selectedRow.getChildAt(4);
			
			int locID = selectedRow.getId();
			String locName = nameTW.getText().toString();
			String locSurface = surfaceTW.getText().toString();
			String locState =  stateTw.getText().toString();
			String locLease = lPriceTW.getText().toString();
			String locCountry = countryTW.getText().toString();
			
			
			kliknutaLokacija = new HashMap<String, String>();
			kliknutaLokacija.put(locationId, String.valueOf(locID));
			kliknutaLokacija.put(locationName, locName);
			kliknutaLokacija.put(surface, locSurface);
			kliknutaLokacija.put(state, locState);
			kliknutaLokacija.put(leasePrice, locLease);
			kliknutaLokacija.put(country, locCountry);
			
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.popup_menu, menu);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.popup_menu_delete_item) {
			if(kliknutaLokacija != null) {
				Log.i(TAg, "TREBA OBRISATI: " + kliknutaLokacija.get(locationId));
				
			}	
		}else if(item.getItemId() == R.id.popup_menu_update_item) {
			if(kliknutaLokacija != null) {
				Log.i(TAg, "TREBA UPDATE [ " + kliknutaLokacija.get(locationId) + ", " + kliknutaLokacija.get(locationName) + ", " + kliknutaLokacija.get(surface) + ", " + kliknutaLokacija.get(surface) + ", " + kliknutaLokacija.get(leasePrice) + "]");
				update();
			}
		}
		return super.onContextItemSelected(item);
	}

	private void makeRow(SoapObject object) {
		TableRow tableRow = new TableRow(this);
		
		String locId = object.getProperty(locationId).toString();
		tableRow.setId(Integer.parseInt(locId));
		
		//TextView txtMuseum = new TextView(this);
		//txtMuseum.setBackgroundColor(Color.WHITE);
		//txtMuseum.setGravity(Gravity.CENTER);
		//txtMuseum.setLayoutParams(textViewLayoutParams);
		//txtMuseum.setText(object.getProperty(locationId).toString().split(";")[0]);
		//tableRow.addView(txtMuseum,tableRowParams);
		
		TextView nameTextView = new TextView(this);
		nameTextView.setBackgroundColor(Color.WHITE);
		nameTextView.setGravity(Gravity.CENTER);
		//nameTextView.setLayoutParams(textViewLayoutParams);
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
		
		//TextView museumIDTextView = new TextView(this);
		//museumIDTextView.setBackgroundColor(Color.WHITE);
		//museumIDTextView.setGravity(Gravity.CENTER);
		//museumIDTextView.setText(object.getProperty(MuseumIdFK).toString().split(";")[0]);
		//tableRow.addView(museumIDTextView, tableRowParams);
		
		TextView countryTextView = new TextView(this);
		countryTextView.setBackgroundColor(Color.WHITE);
		countryTextView.setGravity(Gravity.CENTER);
		countryTextView.setText(object.getProperty(country).toString().split(";")[0]);
		tableRow.addView(countryTextView, tableRowParams);
		
		registerForContextMenu(tableRow);
		
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
	public void update(){
		Intent intent = new Intent(this,UpdateLocationActivity.class);
		
		intent.putExtra(locationId, kliknutaLokacija.get(locationId));
		intent.putExtra(locationName,kliknutaLokacija.get(locationName));
		intent.putExtra(surface, kliknutaLokacija.get(surface));
		intent.putExtra(state, kliknutaLokacija.get(state));
		intent.putExtra(leasePrice,kliknutaLokacija.get(leasePrice));
		intent.putExtra(country,kliknutaLokacija.get(country));
		
		startActivity(intent);
	}
}
