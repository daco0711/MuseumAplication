package com.example.museumaplication;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class BuyersActivity extends ActionBarActivity {
	public static final String buyersName = "BuyersName";
	public static final String buyersSurname = "BuyersSurname";
	public static final String buyersAddress = "BuyersAddress";
	public static final String buyersCountry = "BuyersCountry";
	public static final String ip = "192.168.1.8";
	public static final int port = 80;
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getBuyers = "getBuyers";
	public static String getBuyersAction = "MuseumService/GetBuyers";
	public static String TAG = "BuyersActivity";
	TableLayout table;
	public static String searchResults = "Search_Results";

	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buyers);
		tableLayoutParams = new LayoutParams();
		tableRowParams = new TableRow.LayoutParams();
		textViewLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		table = (TableLayout) findViewById(R.id.tableBuyers);
		table.setBackgroundColor(Color.BLACK);
		
		tableRowParams.setMargins(1, 1, 1, 1);
		tableLayoutParams.weight = 1;
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(searchResults)) {
		} else {
			listOfBuyers();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buyers, menu);
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
	public void listOfBuyers(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN");
				SoapObject request = new SoapObject(namespace, getBuyers);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(getBuyersAction, envelope);
					Log.i(TAG, "after call");
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
					Log.i(TAG, "XML pull");
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
	private void makeRow(SoapObject object)
	{
		TableRow tableRow = new TableRow(this);
		
		TextView txtBuyersName = new TextView(this);
		txtBuyersName.setBackgroundColor(Color.WHITE);
		txtBuyersName.setGravity(Gravity.CENTER);
		txtBuyersName.setLayoutParams(textViewLayoutParams);
		txtBuyersName.setText(object.getProperty(buyersName).toString().split(";")[0]);
		tableRow.addView(txtBuyersName,tableRowParams);
		
		TextView txtBuyersSurname = new TextView(this);
		txtBuyersSurname.setBackgroundColor(Color.WHITE);
		txtBuyersSurname.setGravity(Gravity.CENTER);
		txtBuyersSurname.setText(object.getProperty(buyersSurname).toString().split(";")[0]);
		tableRow.addView(txtBuyersSurname,tableRowParams);
		
		TextView txtAddress = new TextView(this);
		txtAddress.setBackgroundColor(Color.WHITE);
		txtAddress.setGravity(Gravity.CENTER);
		txtAddress.setText(object.getProperty(buyersAddress).toString().split(";")[0]);
		tableRow.addView(txtAddress,tableRowParams);
		
		TextView txtBuyersCountry = new TextView(this);
		txtBuyersCountry.setBackgroundColor(Color.WHITE);
		txtBuyersCountry.setGravity(Gravity.CENTER);
		txtBuyersCountry.setText(object.getProperty(buyersCountry).toString().split(";")[0]);
		tableRow.addView(txtBuyersCountry,tableRowParams);
		
		table.addView(tableRow, tableLayoutParams);
		
		
	}
	public void searchBuyers(View view){
		Intent intent = new Intent(this,SearchBuyersActivity.class);
		startActivity(intent);
	}
	public void addBuyers(View view){
		Intent intent = new Intent(this,AddBuyersActivity.class);
		startActivity(intent);
	}
}
