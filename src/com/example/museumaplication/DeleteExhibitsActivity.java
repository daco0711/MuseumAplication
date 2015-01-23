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

public class DeleteExhibitsActivity extends ActionBarActivity {
	
	public static final String deleteExhibits = "deleteExhibits";
	public static final String deleteExhibitsAction = "MuseumService/DeleteExhibits";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_exhibits);
		
		String id = getIntent().getStringExtra(ExhibitsActivity.exhibitID);
		EditText txtID = (EditText) findViewById(R.id.etDeleteExhID);
		txtID.setText(id);
		txtID.setVisibility(View.INVISIBLE);
		
		String type = getIntent().getStringExtra(ExhibitsActivity.type);
		EditText txtType = (EditText) findViewById(R.id.etDeleteExhType);
		txtType.setText(type);
		
		String dimensions = getIntent().getStringExtra(ExhibitsActivity.dimensions);
		EditText txtdimensions = (EditText) findViewById(R.id.etDeleteExhDimen);
		txtdimensions.setText(dimensions);
		
		String period = getIntent().getStringExtra(ExhibitsActivity.historicPeriod);
		EditText txtPeriod = (EditText) findViewById(R.id.etDeleteExhPeriod);
		txtPeriod.setText(period);
		
		String locID = getIntent().getStringExtra(ExhibitsActivity.locationid);
		EditText txtLocID = (EditText) findViewById(R.id.etDeleteExhLocationID);
		txtLocID.setText(locID);
		
		String orderF = getIntent().getStringExtra(ExhibitsActivity.orderFormId);
		EditText txtOrder = (EditText) findViewById(R.id.etDeleteExhOrderFormID);
		txtOrder.setText(orderF);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_exhibits, menu);
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
		Intent intent = new Intent(this,ExhibitsActivity.class);
		startActivity(intent);
	}
	public void deleteOK(View view){
		
		EditText txtID = (EditText) findViewById(R.id.etDeleteExhID);
		final String exhID = txtID.getText().toString();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(UsersActivity.TAG, "RUN DELETE exhibits: " + exhID);
				SoapObject request = new SoapObject(UsersActivity.namespace,deleteExhibits);
				PropertyInfo propID = new PropertyInfo();
				propID.name = "exhibitId";
				propID.type = PropertyInfo.INTEGER_CLASS;
				request.addProperty(propID, exhID);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.implicitTypes = true;
				envelope.dotNet = true;
				envelope.encodingStyle = SoapSerializationEnvelope.XSD;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(UsersActivity.URL);
				Log.i(UsersActivity.TAG, "Pozivam WCF servis");
				
				Log.i(UsersActivity.TAG, "DELETE: " + exhID );
				
				try {
					Log.i(UsersActivity.TAG, "before call");
					transport.debug = true;
					transport.call(deleteExhibitsAction, envelope);
					Log.i(UsersActivity.TAG, "after call add");
					
				}  catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(DeleteExhibitsActivity.this, ExhibitsActivity.class);
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
	

