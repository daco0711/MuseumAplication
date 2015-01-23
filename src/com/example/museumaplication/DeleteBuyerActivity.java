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

public class DeleteBuyerActivity extends ActionBarActivity {
	public static final String deleteBuyer = "deleteBuyers";
	public static final String  deleteBuyersAction = "MuseumService/DeleteBuyers";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_buyer);
		String id = getIntent().getStringExtra(BuyersActivity.buyerId);
		EditText txtID = (EditText) findViewById(R.id.etDeleteBuyerID);
		txtID.setText(id);
		txtID.setVisibility(View.INVISIBLE);
		
		String name = getIntent().getStringExtra(BuyersActivity.buyersName);
		EditText txtName = (EditText) findViewById(R.id.etDeleteBuyerName);
		txtName.setText(name);
		
		String surname = getIntent().getStringExtra(BuyersActivity.buyersSurname);
		EditText txtSurname = (EditText) findViewById(R.id.etDeleteBuyerSurname);
		txtSurname.setText(surname);
		
		String address = getIntent().getStringExtra(BuyersActivity.buyersAddress);
		EditText txtaddress = (EditText) findViewById(R.id.etDeleteBuyerAddress);
		txtaddress.setText(address);
		
		String country = getIntent().getStringExtra(BuyersActivity.buyersCountry);
		EditText txtcountry = (EditText) findViewById(R.id.etDeleteBuyerCountry);
		txtcountry.setText(country);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_buyer, menu);
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
		Intent intent = new Intent(this,BuyersActivity.class);
		startActivity(intent);
		
		
	}
	public void deleteYes(View view){
		EditText txtID = (EditText) findViewById(R.id.etDeleteBuyerID);
		final String buyID = txtID.getText().toString();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(BuyersActivity.TAG, "RUN DELETE BUYERS: " + buyID);
				SoapObject request = new SoapObject(BuyersActivity.namespace,deleteBuyer);
				PropertyInfo propID = new PropertyInfo();
				propID.name = "buyerId";
				propID.type = PropertyInfo.INTEGER_CLASS;
				request.addProperty(propID, buyID);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.implicitTypes = true;
				envelope.dotNet = true;
				envelope.encodingStyle = SoapSerializationEnvelope.XSD;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(BuyersActivity.URL);
				Log.i(BuyersActivity.TAG, "Pozivam WCF servis");
				
				Log.i(UsersActivity.TAG, "DELETE: " + buyID );
				
				try {
					Log.i(LocationsActivity.TAg, "before call");
					transport.debug = true;
					transport.call(deleteBuyersAction, envelope);
					Log.i(LocationsActivity.TAg, "after call add");
					
				}  catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(DeleteBuyerActivity.this, BuyersActivity.class);
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

	

