package com.example.museumaplication;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;



import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocationsActivity extends ActionBarActivity {
	
	public static final String ip = "192.168.1.5";
	public static final int port = 80;
	public static final String locationName = "LocationName";
	public static final String surface = "Surface";
	public static final String state = "State";
	public static final String leasePrice = "LeasePrice";
	public static final String country = "Country";
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String addLocations = "addLocations";
	public static String addLocationsAction = "MuseumService/AddLocations";
	public static String TAG = "LocationsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_locations);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_locations, menu);
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
	public void aDDLocations(View view){
		EditText txtLocationName = (EditText) findViewById(R.id.editTextAddLocationName);
		final String name = txtLocationName.getText().toString().trim();
		EditText txtSurface = (EditText) findViewById(R.id.editTextAddSurface);
		final String surface = txtSurface.getText().toString().trim();
		EditText txtState = (EditText) findViewById(R.id.editTextAddState);
		final String state = txtState.getText().toString().trim();
		EditText txtLease = (EditText) findViewById(R.id.editTextAddLeasePrice);
		final String lease = txtLease.getText().toString().trim();
		EditText txtCountry = (EditText) findViewById(R.id.editTextAddCountry);
		 final String country = txtCountry.getText().toString().trim();
		EditText txtMuseumID = (EditText) findViewById(R.id.editTextAddMuseumID);
		 final String musemIDFK = txtMuseumID.getText().toString();
		 try {
			 Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						Log.i(TAG, "RUN ADD USERS: " + name + ", " + surface + ", " + state + ", " + lease + country + musemIDFK);
						SoapObject request = new SoapObject(namespace, addLocations);
						PropertyInfo propName = new PropertyInfo();
						propName.name = "locationName";
						
						propName.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propName, name);
						
						PropertyInfo propSurface = new PropertyInfo();
						propSurface.name = "surface";
						propSurface.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propSurface,surface);
						
						PropertyInfo propState = new PropertyInfo();
						propState.name = "state";
						propState.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propState,state);
						
						PropertyInfo propLeasePrice = new PropertyInfo();
						propLeasePrice.name = "leasePrice";
						propLeasePrice.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propLeasePrice,lease);
						
						
						
						PropertyInfo propMuseumID = new PropertyInfo();
						propMuseumID.name = "museumIdFK";
						propMuseumID.type = PropertyInfo.INTEGER_CLASS;
						propMuseumID.setValue(1);
						request.addProperty(propMuseumID,musemIDFK);
						
						PropertyInfo propCountry = new PropertyInfo();
						propCountry.name = "country";
						propCountry.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propCountry,country);
						
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE transport = new HttpTransportSE(URL);
						Log.i(TAG, "Pozivam WCF... [" + URL + "]");
						try {
							Log.i(TAG, "before call");
							transport.debug = true;
							transport.call(addLocationsAction, envelope);
							Log.i(TAG, "after call");
						
						} catch (IOException e) {
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							Log.i(LocationsActivity.TAg, "XML pull");
							e.printStackTrace();
							
							
						}
						Log.i(TAG, "name" + name);
						Log.i(TAG, "Surface" + surface);
						Log.i(TAG, "state" + state);
						Intent intent = new Intent(AddLocationsActivity.this,
								LocationsActivity.class);
						startActivity(intent);
					}
					
				});
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		 } catch (Exception e) {
				e.printStackTrace();
			}
			 
		 
		 
	}
}
