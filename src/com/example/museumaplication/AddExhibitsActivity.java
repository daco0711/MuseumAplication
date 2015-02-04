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

public class AddExhibitsActivity extends ActionBarActivity {
	public static final String addExhibits = "addExhibits";
	public static final String addExhibitsAction = "MuseumService/AddExhibits";
	//private Integer locationID = null;
	//private Integer orderFormID = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exhibits);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_exhibits, menu);
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
	public void addExhibits(View view){
		EditText txttype = (EditText) findViewById(R.id.etAddExhibitsType);
		final String type = txttype.getText().toString(); 
		EditText txtDimensions = (EditText) findViewById(R.id.etAddExhibitsDimensions);
		final String dimensions = txtDimensions.getText().toString();
		EditText txtHistoricPeriod = (EditText) findViewById(R.id.etAddExhibitsJurassicPeriod);
		final String period = txtHistoricPeriod.getText().toString();
		EditText txtLocationIDFK = (EditText) findViewById(R.id.etAddExhibitsLocationIDFK);
		final String locationIDFK = txtLocationIDFK.getText().toString();
		EditText txtOrderFormIDFK = (EditText) findViewById(R.id.etAddExhibitsOrderFormsIDFK);
		final String orderForm = txtOrderFormIDFK.getText().toString();
		
		try {
			 Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						Log.i(ExhibitsActivity.TAG, "RUN ADD Exhibits: " + type + ", " + dimensions + ", " + period + ", " + locationIDFK + orderForm);
						SoapObject request = new SoapObject(LocationsActivity.namespace,addExhibits);
						PropertyInfo propType = new PropertyInfo();
						propType.name = "type";
						propType.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propType, type);
						
						PropertyInfo propDimensions = new PropertyInfo();
						propDimensions.name = "dimensions";
						propDimensions.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propDimensions,dimensions);
						
						PropertyInfo propPeriod = new PropertyInfo();
						propPeriod.name = "historicPeriod";
						propPeriod.type = PropertyInfo.STRING_CLASS;
						request.addProperty(propPeriod,period);
						
						PropertyInfo propLocationIDFK = new PropertyInfo();
						propLocationIDFK.name = "locationIdFK";
						propLocationIDFK.type = PropertyInfo.INTEGER_CLASS;
						request.addProperty(propLocationIDFK,locationIDFK);
						
						
						
						PropertyInfo propOrderFormIDFK = new PropertyInfo();
						propOrderFormIDFK.name = "orderFormIdFK";
						propOrderFormIDFK.type = PropertyInfo.INTEGER_CLASS;
						request.addProperty(propOrderFormIDFK,orderForm);
						
						
						
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE transport = new HttpTransportSE(ExhibitsActivity.URL);
						Log.i(ExhibitsActivity.TAG, "Pozivam WCF... [" + ExhibitsActivity.URL + "]");
						try {
							Log.i(ExhibitsActivity.TAG, "before call");
							transport.debug = true;
							transport.call(addExhibitsAction, envelope);
							Log.i(ExhibitsActivity.TAG, "after call");
						
						} catch (IOException e) {
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							Log.i(LocationsActivity.TAg, "XML pull");
							e.printStackTrace();
							
							
						}
						Log.i(ExhibitsActivity.TAG, "type" + type);
						Log.i(ExhibitsActivity.TAG, "dimensions" + dimensions);
						Log.i(ExhibitsActivity.TAG, "period" + period);
						Log.i(ExhibitsActivity.TAG, "locationIDFK" + locationIDFK);
						Log.i(ExhibitsActivity.TAG, "orderFormIDFK" + orderForm);
						Intent intent = new Intent(AddExhibitsActivity.this,
								ExhibitsActivity.class);
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
	public void backToExhibits(View view){
		Intent intent = new Intent(this,ExhibitsActivity.class);
		startActivity(intent);
	}
}
