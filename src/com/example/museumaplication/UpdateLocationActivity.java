package com.example.museumaplication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class UpdateLocationActivity extends ActionBarActivity {
	private Integer locationID = null;
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
	public static String updateLocations = "updateLocations";
	public static String updateLocationsAction = "MuseumService/UpdateLocations";
	public static String TAG = "UpdateLocationsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_location);
		
		String ID = getIntent().getStringExtra(LocationsActivity.locationId);
		EditText txtID = (EditText) findViewById(R.id.updateLocationID);
		txtID.setText(ID);
		txtID.setVisibility(View.INVISIBLE);
		
		
		String name = getIntent().getStringExtra(LocationsActivity.locationName);
		EditText txtName = (EditText) findViewById(R.id.updateLocationName);
		txtName.setText(name);
		
		
		String surface = getIntent().getStringExtra(LocationsActivity.surface);
		EditText txtSurface = (EditText) findViewById(R.id.updateSurface);
		txtSurface.setText(surface);
		
		String state = getIntent().getStringExtra(LocationsActivity.state);
		EditText txtState = (EditText) findViewById(R.id.updateState);
		txtState.setText(state);
		
		String leasePrice = getIntent().getStringExtra(LocationsActivity.leasePrice);
		EditText txtLeasePrice = (EditText) findViewById(R.id.updateLeasePrice);
		txtLeasePrice.setText(leasePrice);
		
		String country = getIntent().getStringExtra(LocationsActivity.country);
		EditText txtCountry = (EditText) findViewById(R.id.updateCountry);
		txtCountry.setText(country);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_location, menu);
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
	
	public void updateLocation(View view){
		EditText txtID = (EditText) findViewById(R.id.updateLocationID);
		final String ID = txtID.getText().toString();
		EditText txtName = (EditText) findViewById(R.id.updateLocationName);
		final String name = txtName.getText().toString();
		EditText txtSurface = (EditText) findViewById(R.id.updateSurface);
		final String surface = txtSurface.getText().toString();
		EditText txtstate = (EditText) findViewById(R.id.updateState);
		final String state = txtstate.getText().toString();
		EditText txtLease = (EditText) findViewById(R.id.updateLeasePrice);
		final String lease = txtLease.getText().toString();
		EditText txtCountry = (EditText) findViewById(R.id.updateCountry);
		final String country = txtCountry.getText().toString();
		
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Log.i(LocationsActivity.TAg, "Update lokacije");
					SoapObject request = new SoapObject(namespace, updateLocations);
					
					PropertyInfo propID = new PropertyInfo();
					propID.name = "locationId";
					propID.type = PropertyInfo.INTEGER_CLASS;
					request.addProperty(propID, ID);
					
					PropertyInfo propName = new PropertyInfo();
					propName.name = "locationName";
					propName.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propName, name);
					
					PropertyInfo propSurface = new PropertyInfo();
					propSurface.name = "surface";
					propSurface.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propSurface, surface);
					
					PropertyInfo propState = new PropertyInfo();
					propState.name = "state";
					request.addProperty(propState, state);
					
					PropertyInfo propLease = new PropertyInfo();
					propLease.name = "leasePrice";
					propLease.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propLease, lease);
					
					PropertyInfo propMuseumID = new PropertyInfo();
					propMuseumID.setName("museumIdFK");
					propMuseumID.setType(PropertyInfo.INTEGER_CLASS);
					propMuseumID.setValue(1);
					request.addProperty(propMuseumID);
					
					PropertyInfo propCountry = new PropertyInfo();
					propCountry.name = "country";
					propCountry.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propCountry, country);
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
					envelope.implicitTypes = true;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transport = new HttpTransportSE(URL);
					Log.i(LocationsActivity.TAg, "Pozivam WCF servis");
					
					Log.i(TAG, "UPDATE: " + name + ", " + surface + ", " + state + ", " + lease + ", " + country);
					
					try {
						Log.i(LocationsActivity.TAg, "before call");
						transport.debug = true;
						transport.call(updateLocationsAction, envelope);
						Log.i(LocationsActivity.TAg, "after call add");
						
					}  catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(UpdateLocationActivity.this, LocationsActivity.class);
					startActivity(intent);
				}
			});
			t.start();
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void backToLocations(View view){
		Intent intent = new Intent(this,LocationsActivity.class);
		startActivity(intent);
	}
}


		
	

