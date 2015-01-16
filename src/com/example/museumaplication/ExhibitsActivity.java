package com.example.museumaplication;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class ExhibitsActivity extends ActionBarActivity {
	public static final String type = "Type";
	public static final String dimensions = "Dimensions";
	public static final String historicPeriod = "HistoricPeriod";
	public static final String locationid = "LocationIdFK";
	public static  final String orderFormId = "OrderFormIdFK";
	public static final String ip = "192.168.1.8";
	public static final int port = 80;
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getExhibits = "getExhibits";
	public static String getLocationById = "getLocationsByID";
	public static String getExhibitsAction = "MuseumService/GetExhibits";
	public static String getLocationByIDAction = "MuseumService/GetLocationsByID";
	public static String TAG = "ExhibitsActivity";
	TableLayout table;
	public static String searchResults = "Search_Results";

	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibits);
		mp=MediaPlayer.create(ExhibitsActivity.this,R.raw.eksponati);
		mp.start();
		tableLayoutParams = new LayoutParams();
		tableRowParams = new TableRow.LayoutParams();
		textViewLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		table = (TableLayout) findViewById(R.id.tableExhibits);
		table.setBackgroundColor(Color.BLACK);
		
		tableRowParams.setMargins(1, 1, 1, 1);
		tableLayoutParams.weight = 1;
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(searchResults)) {
		} else {
			listOfLocations();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exhibits, menu);
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
	
	public void listOfLocations()
	{
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN");
				SoapObject request = new SoapObject(namespace, getExhibits);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(getExhibitsAction, envelope);
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
	private void makeRow(SoapObject object){
		TableRow tableRow = new TableRow(this);
		
		TextView txtType = new TextView(this);
		txtType.setBackgroundColor(Color.WHITE);
		txtType.setGravity(Gravity.CENTER);
		txtType.setLayoutParams(textViewLayoutParams);
		txtType.setText(object.getProperty(type).toString().split(";")[0]);
		tableRow.addView(txtType,tableRowParams);
		
		Log.i(TAG, "EXIBIT: " + object.getProperty(type).toString().split(";")[0]);
		
		TextView txtDimensions = new TextView(this);
		txtDimensions.setBackgroundColor(Color.WHITE);
		txtDimensions.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtDimensions.setText(object.getProperty(dimensions).toString().split(";")[0]);
		tableRow.addView(txtDimensions,tableRowParams);
		
		TextView txtHistoricPeriod = new TextView(this);
		txtHistoricPeriod.setBackgroundColor(Color.WHITE);
		txtHistoricPeriod.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtHistoricPeriod.setText(object.getProperty(historicPeriod).toString().split(";")[0]);
		tableRow.addView(txtHistoricPeriod,tableRowParams);
		
		TextView txtLocatioId = new TextView(this);
		txtLocatioId.setBackgroundColor(Color.WHITE);
		txtLocatioId.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		String locationID = object.getProperty(locationid).toString().split(";")[0];
		getLocationName(locationID, txtLocatioId);
		tableRow.addView(txtLocatioId,tableRowParams);
		
		TextView txtOrderFormID = new TextView(this);
		txtOrderFormID.setBackgroundColor(Color.WHITE);
		txtOrderFormID.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtOrderFormID.setText(object.getProperty(orderFormId).toString().split(";")[0]);
		tableRow.addView(txtOrderFormID,tableRowParams);
		
		table.addView(tableRow, tableLayoutParams);
		
	}
	
	public void getLocationName(final String ID, final TextView textview) {
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				SoapObject request = new SoapObject(namespace, getLocationById);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				try {
					int locationID = Integer.parseInt(ID);
					PropertyInfo prop = new PropertyInfo();
					prop.name = "locationID";
					prop.type = PropertyInfo.INTEGER_CLASS;
					request.addProperty(prop, locationID);
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(getLocationByIDAction, envelope);
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						SoapObject returned = ((SoapObject) response);
						textview.setText(returned.getProperty("LocationName").toString().split(";")[0]);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
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
	
	public void searchExhibits(View view){
		Intent intent = new Intent(this,SearchExhibitsActivity.class);
		startActivity(intent);
	}
	public void addExhibits(View view){
		Intent intent = new Intent(this,AddExhibitsActivity.class);
		startActivity(intent);
	}
	public void orderExhibits(View view){
		Intent intent = new Intent(this,BuyersActivity.class);
		startActivity(intent);
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
	}
}
