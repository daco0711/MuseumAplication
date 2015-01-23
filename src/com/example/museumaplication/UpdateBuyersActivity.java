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

public class UpdateBuyersActivity extends ActionBarActivity {
	
	static final String updateBuyers = "updateBuyers";
	static final String updateBuyersAction = "MuseumService/UpdateBuyers";
	public static String namespace = "WcfServiceMuseumNew";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_buyers);
		
		String buyID = getIntent().getStringExtra(BuyersActivity.buyerId);
		EditText txtbuyID = (EditText) findViewById(R.id.etUpdateBuyerID);
		txtbuyID.setText(buyID);
		txtbuyID.setVisibility(View.INVISIBLE);
		
		String buyName = getIntent().getStringExtra(BuyersActivity.buyersName);
		EditText txtbuyName = (EditText) findViewById(R.id.etUpdateBuyerName);
		txtbuyName.setText(buyName);
		
		String buySurname = getIntent().getStringExtra(BuyersActivity.buyersSurname);
		EditText txtbuySurname = (EditText) findViewById(R.id.etUpdateBuyerSurname);
		txtbuySurname.setText(buySurname);
		
		String buyAddress = getIntent().getStringExtra(BuyersActivity.buyersAddress);
		EditText txtbuyAddress = (EditText) findViewById(R.id.etUpdateBuyerAddress);
		txtbuyAddress.setText(buyAddress);
		
		String buyCountry = getIntent().getStringExtra(BuyersActivity.buyersCountry);
		EditText txtbuyCountry = (EditText) findViewById(R.id.etUpdateBuyerCountry);
		txtbuyCountry.setText(buyCountry);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_buyers, menu);
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
	public void updateBuyers(View view){
		EditText txtBuyerID = (EditText) findViewById(R.id.etUpdateBuyerID);
		final String buyerID = txtBuyerID.getText().toString();
		
		EditText txtbuyerName = (EditText) findViewById(R.id.etUpdateBuyerName);
		final String buyerName = txtbuyerName.getText().toString();
		
		EditText txtbuyerSurname = (EditText) findViewById(R.id.etUpdateBuyerSurname);
		final String buyerSurname = txtbuyerSurname.getText().toString();
		
		EditText txtbuyerAddress = (EditText) findViewById(R.id.etUpdateBuyerAddress);
		final String buyerAddress = txtbuyerAddress.getText().toString();
		
		EditText txtbuyerCountry = (EditText) findViewById(R.id.etUpdateBuyerCountry);
		final String buyerCountry = txtbuyerCountry.getText().toString();
		
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Log.i(LocationsActivity.TAg, "Update kupaca");
					SoapObject request = new SoapObject(namespace, updateBuyers);
					
					PropertyInfo propID = new PropertyInfo();
					propID.name = "buyersId";
					propID.type = PropertyInfo.INTEGER_CLASS;
					request.addProperty(propID, buyerID);
					
					PropertyInfo propName = new PropertyInfo();
					propName.name = "buyersName";
					propName.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propName, buyerName);
					
					PropertyInfo propSurname = new PropertyInfo();
					propSurname.name = "buyersSurname";
					propSurname.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propSurname, buyerSurname);
					
					PropertyInfo propAddress = new PropertyInfo();
					propAddress.name = "buyersAddress";
					propAddress.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propAddress, buyerAddress);
					
					PropertyInfo propCountry = new PropertyInfo();
					propCountry.name = "buyersCountry";
					propCountry.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propCountry, buyerCountry);
					
					
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
					envelope.implicitTypes = true;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transport = new HttpTransportSE(BuyersActivity.URL);
					Log.i(LocationsActivity.TAg, "Pozivam WCF servis");
					
					Log.i(BuyersActivity.TAG, "UPDATE: " + buyerName + ", " + buyerSurname + ", " + buyerAddress + ", " + buyerAddress + ", " + buyerCountry);
					
					try {
						Log.i(LocationsActivity.TAg, "before call");
						transport.debug = true;
						transport.call(updateBuyersAction, envelope);
						Log.i(LocationsActivity.TAg, "after call add");
						
					}  catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(UpdateBuyersActivity.this,BuyersActivity.class);
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
		
		public void cancel(View view){
			Intent intent = new Intent(this,BuyersActivity.class);
			startActivity(intent);
		}
		
}

