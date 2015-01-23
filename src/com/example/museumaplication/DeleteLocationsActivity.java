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

public class DeleteLocationsActivity extends ActionBarActivity {
	public static final String deleLocation = "deleteLocation";
	public static final String deleteLocationsAction = "MuseumService/DeleteLocation";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_locations);
		
		String id = getIntent().getStringExtra(LocationsActivity.locationId);
		EditText txtID = (EditText) findViewById(R.id.etDeleteLocationID);
		txtID.setText(id);
		txtID.setVisibility(View.INVISIBLE);
		
		String name = getIntent().getStringExtra(LocationsActivity.locationName);
		EditText txtName = (EditText) findViewById(R.id.etDeleteLocationName);
		txtName.setText(name);
		
		String surface = getIntent().getStringExtra(LocationsActivity.surface);
		EditText txtSurface = (EditText) findViewById(R.id.etDeleteSurface);
		txtSurface.setText(surface);
		
		String state = getIntent().getStringExtra(LocationsActivity.state);
		EditText txtState = (EditText) findViewById(R.id.etDeleteState);
		txtState.setText(state);
		
		String lease = getIntent().getStringExtra(LocationsActivity.leasePrice);
		EditText txtLease = (EditText) findViewById(R.id.etDeleteLease);
		txtLease.setText(lease);
		
		String country = getIntent().getStringExtra(LocationsActivity.country);
		EditText txtCountry = (EditText) findViewById(R.id.etDeleteCountry);
		txtCountry.setText(country);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_locations, menu);
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
	public void deleteNO(View view){
		Intent intent = new Intent(this,UsersActivity.class);
		startActivity(intent);
	}
	public void deleteOK(View view){
		EditText txtID = (EditText) findViewById(R.id.etDeleteLocationID);
		final String locID = txtID.getText().toString();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(UsersActivity.TAG, "RUN DELETE Locations: " + locID);
				SoapObject request = new SoapObject(UsersActivity.namespace,deleLocation);
				PropertyInfo propID = new PropertyInfo();
				propID.name = "locationId";
				propID.type = PropertyInfo.INTEGER_CLASS;
				request.addProperty(propID, locID);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.implicitTypes = true;
				envelope.dotNet = true;
				envelope.encodingStyle = SoapSerializationEnvelope.XSD;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(UsersActivity.URL);
				Log.i(UsersActivity.TAG, "Pozivam WCF servis");
				
				Log.i(UsersActivity.TAG, "DELETE: " + locID );
				
				try {
					Log.i(UsersActivity.TAG, "before call");
					transport.debug = true;
					transport.call(deleteLocationsAction, envelope);
					Log.i(UsersActivity.TAG, "after call add");
					
				}  catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(DeleteLocationsActivity.this, LocationsActivity.class);
				startActivity(intent);
			}
		});
		t.start();
		try {
			t.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
		
}

