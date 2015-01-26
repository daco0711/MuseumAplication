package com.example.museumaplication;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.renderscript.Type;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class UpdateMuseumActivity extends ActionBarActivity {
	
	public static final String updateMuseum = "updateMuseum";
	public static final String updateMuseumAction = "MuseumService/UpdateMuseum";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_museum);
		
		String museumID = getIntent().getStringExtra(MuseumActivity.MuseumID);
		EditText txtID = (EditText) findViewById(R.id.etUpdatemuseumID);
		txtID.setText(museumID);
		
		String name = getIntent().getStringExtra(MuseumActivity.Museum_name);
		EditText txtName = (EditText) findViewById(R.id.etUpdatemuseumName);
		txtName.setText(name);
		
		String museumAddress = getIntent().getStringExtra(MuseumActivity.Museum_address);
		EditText txtAddress = (EditText) findViewById(R.id.etUpdatemuseumAddress);
		txtAddress.setText(museumAddress);
		
		String established = getIntent().getStringExtra(MuseumActivity.Established);
		EditText txtEst = (EditText) findViewById(R.id.etUpdatemuseumEstablished);
		txtEst.setText(established);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_museum, menu);
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
	
	public void updateOK(View view){
		EditText txtid = (EditText) findViewById(R.id.etUpdatemuseumID);
		final String id = txtid.getText().toString();
		EditText txtName = (EditText) findViewById(R.id.etUpdatemuseumName);
		final String name= txtName.getText().toString();
		
		EditText txtAddress = (EditText) findViewById(R.id.etUpdatemuseumAddress);
		final String address = txtAddress.getText().toString();
		
		EditText txtEstab = (EditText) findViewById(R.id.etUpdatemuseumEstablished);
		final String estab = txtEstab.getText().toString();
		
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Log.i(LocationsActivity.TAg, "Update muzeja");
					SoapObject request = new SoapObject(MuseumActivity.NAMESPACE, updateMuseum);
					
					PropertyInfo propID = new PropertyInfo();
					propID.name = "museumId";
					propID.type = PropertyInfo.INTEGER_CLASS;
					request.addProperty(propID, id);
					
					PropertyInfo propName = new PropertyInfo();
					propName.name = "museumName";
					propName.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propName, name);
					
					PropertyInfo propAddress = new PropertyInfo();
					propAddress.name = "museumAddress";
					propAddress.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propAddress, address);
					
					PropertyInfo propEstab = new PropertyInfo();
					propEstab.name = "established";
					propEstab.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propEstab, estab);
					
					
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
					envelope.implicitTypes = true;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transport = new HttpTransportSE(MuseumActivity.URL);
					Log.i(LocationsActivity.TAg, "Pozivam WCF servis");
					
					Log.i(MuseumActivity.TAG, "UPDATE: " + name + ", " + address + ", " + estab);
					
					try {
						Log.i(LocationsActivity.TAg, "before call");
						transport.debug = true;
						transport.call(updateMuseumAction, envelope);
						Log.i(LocationsActivity.TAg, "after call add");
						
					}  catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(UpdateMuseumActivity.this, MuseumActivity.class);
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
	public void updateNO(View view){
		Intent intent = new Intent(this,MuseumActivity.class);
		startActivity(intent);
	}
}
