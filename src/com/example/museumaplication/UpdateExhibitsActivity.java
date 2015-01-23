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
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class UpdateExhibitsActivity extends ActionBarActivity {

	public static final String updateExhibits = "updateExhibits";
	public static final String  updateExhibitsAction = "MuseumService/UpdateExhibits";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_exhibits);
		
		String txtID = getIntent().getStringExtra(ExhibitsActivity.exhibitID);
		EditText exhID = (EditText) findViewById(R.id.etUpdateExhibitsID);
		exhID.setText(txtID);
		
		String txtType = getIntent().getStringExtra(ExhibitsActivity.type);
		EditText type = (EditText) findViewById(R.id.etUpdateExhibitsType);
		type.setText(txtType);
		
		String txtDimensions = getIntent().getStringExtra(ExhibitsActivity.dimensions);
		EditText dimensions = (EditText) findViewById(R.id.etUpdateExhibitsDimensions);
		dimensions.setText(txtDimensions);
		
		String txtPeriod = getIntent().getStringExtra(ExhibitsActivity.historicPeriod);
		EditText period = (EditText) findViewById(R.id.etUpdateExhibitsPeriod);
		period.setText(txtPeriod);
		
		String txtLocation = getIntent().getStringExtra(ExhibitsActivity.locationid);
		EditText location = (EditText) findViewById(R.id.etUpdateExhibitsLocation);
		location.setText(txtLocation);
		
		String txtOrderForm = getIntent().getStringExtra(ExhibitsActivity.orderFormId);
		EditText order = (EditText) findViewById(R.id.etUpdateExhibitsOrderForm);
		order.setText(txtOrderForm);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_exhibits, menu);
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
	public void updateExhibits(View view){
		EditText txtID = (EditText) findViewById(R.id.etUpdateExhibitsID);
		final String exhibID = txtID.getText().toString();
		
		EditText txtType = (EditText) findViewById(R.id.etUpdateExhibitsType);
		final String type = txtType.getText().toString();
		
		EditText txtDimen = (EditText) findViewById(R.id.etUpdateExhibitsDimensions);
		final String dime = txtDimen.getText().toString();
		
		EditText txtPeriod = (EditText) findViewById(R.id.etUpdateExhibitsPeriod);
		final String period = txtPeriod.getText().toString();
		
		EditText txtLocationID = (EditText) findViewById(R.id.etUpdateExhibitsLocation);
		final String locOD = txtLocationID.getText().toString();
		
		EditText txtOrderFID = (EditText) findViewById(R.id.etUpdateExhibitsOrderForm);
		final String orderFID = txtOrderFID.getText().toString();
		
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Log.i(LocationsActivity.TAg, "Update exhibita, namespace: " + ExhibitsActivity.namespace);
					SoapObject request = new SoapObject(ExhibitsActivity.namespace, updateExhibits);
					
					PropertyInfo propID = new PropertyInfo();
					propID.setName("exhibitId");
					propID.setType(PropertyInfo.INTEGER_CLASS);
					propID.setValue(exhibID);
					request.addProperty(propID);
					
					PropertyInfo proptype = new PropertyInfo();
					proptype.setName("type");
					proptype.setType(PropertyInfo.STRING_CLASS);
					proptype.setValue(type);
					request.addProperty(proptype);
					
					PropertyInfo propDimensions = new PropertyInfo();
					propDimensions.setName("dimensions");
					propDimensions.setType(PropertyInfo.STRING_CLASS);
					propDimensions.setValue(dime);
					request.addProperty(propDimensions);
					
					PropertyInfo propPeriod = new PropertyInfo();
					propPeriod.setName("historicPeriod");
					propPeriod.setType(PropertyInfo.STRING_CLASS);
					propPeriod.setValue(period);
					request.addProperty(propPeriod);
					
					PropertyInfo propLocations = new PropertyInfo();
					propLocations.setName("locationIdFK");
					propLocations.setType(PropertyInfo.STRING_CLASS);
					propLocations.setValue(locOD);
					request.addProperty(propLocations);
					
					PropertyInfo propOrderForm = new PropertyInfo();
					propOrderForm.setName("orderFormIdFK");
					propOrderForm.setType(PropertyInfo.INTEGER_CLASS);
					propOrderForm.setValue(orderFID);
					request.addProperty(propOrderForm);
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
					envelope.implicitTypes = true;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transport = new HttpTransportSE(ExhibitsActivity.URL);
					Log.i(LocationsActivity.TAg, "Pozivam WCF servis");
					
					Log.i(ExhibitsActivity.TAG, "UPDATE: " + type + ", " + dime + ", " + period + ", " + locOD + ", " + orderFID);
					
					try {
						Log.i(LocationsActivity.TAg, "before call");
						transport.debug = true;
						transport.call(updateExhibitsAction, envelope);
						Log.i(LocationsActivity.TAg, "after call add");
					}  catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(UpdateExhibitsActivity.this, ExhibitsActivity.class);
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
		
}
	

