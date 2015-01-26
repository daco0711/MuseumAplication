package com.example.museumaplication;

import java.io.IOException;
import java.util.HashMap;

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
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class ExhibitsActivity extends ActionBarActivity {
	public static final String exhibitID = "ExhibitId";
	public static final String type = "Type";
	public static final String dimensions = "Dimensions";
	public static final String historicPeriod = "HistoricPeriod";
	public static final String locationid = "LocationIdFK";
	public static  final String orderFormId = "OrderFormIdFK";
	public static final String ip = "192.168.1.16";
	public static final int port = 80;
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getExhibits = "getExhibits";
	public static String getLocationById = "getLocationsByID";
	public static String getExhibitsAction = "MuseumService/GetExhibits";
	public static String getLocationByIDAction = "MuseumService/GetLocationsByID";
	public static String TAG = "ExhibitsActivity";
	public static String findExhibits = "findExhibits";
	public static String findExhibitsAction = "MuseumService/FindExhibits";
	TableLayout table;
	public static String searchType = "SearchType";
	public static String searchHistoricPeriod = "SearchPeriod";
	public static String addType = "addType";
	public static String addDimensions = "addDimensions";
	public static String addPeriod = "addPeriod";
	public static String addLocationID = "addLocationIDFK";
	public static String addOrderFormIDFK = "addOrderFormIDFK";
	public static String addExhibits = "addExhibits";
	public static String addExhibitsAction = "MuseumService/AddExhibits";
	public Integer locationID = null;
	public Integer orderFormID = null;
	
	HashMap<String, String> kliknutExhibit;
	

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
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(addType) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addDimensions) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addPeriod) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addLocationID) && getIntent().getExtras() != null && getIntent().getExtras().containsKey(addOrderFormIDFK)){
			String type = getIntent().getExtras().getString(addType);
			String dimensions = getIntent().getExtras().getString(addDimensions);
			String period = getIntent().getExtras().getString(addPeriod);
			String locationIDFK = getIntent().getExtras().getString(addLocationID);
			String orderFormIDFK = getIntent().getExtras().getString(addOrderFormIDFK);
			addExhibits(type,dimensions,period,locationIDFK,orderFormIDFK);
		}
		else if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(searchType)&& getIntent().getExtras().containsKey(searchHistoricPeriod)) {
			String type = getIntent().getExtras().getString(searchType);
			String historicPeriod = getIntent().getExtras().getString(searchHistoricPeriod);
			searchList(type, historicPeriod);
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
	public void addExhibits(final String type,final String dimensions,final String period,final String locationIDFK,final String orderFormIDFK){
		//locationID = Integer.parseInt(locationIDFK);
		//orderFormID = Integer.parseInt(orderFormIDFK);
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN ADD Exhibits: " + type + dimensions + period + locationIDFK + orderFormIDFK);
				SoapObject request = new SoapObject(namespace,addExhibits);
				PropertyInfo propType = new PropertyInfo();
				propType.name = "type";
				propType.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propType, type);
				
				PropertyInfo propDimensions = new PropertyInfo();
				propDimensions.name = "dimensions";
				propDimensions.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propDimensions,dimensions);
				
				PropertyInfo propHistoricPeriod = new PropertyInfo();
				propHistoricPeriod.name = "historicPeriod";
				propHistoricPeriod.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propHistoricPeriod,historicPeriod);
				
				int locID = Integer.parseInt(locationIDFK);
				PropertyInfo propLocationIDFK = new PropertyInfo();
				propLocationIDFK.name = "locationIdFK";
				propLocationIDFK.type = PropertyInfo.INTEGER_CLASS;
				request.addProperty(propLocationIDFK,locID);
				
				int ordID = Integer.parseInt(orderFormIDFK);
				PropertyInfo proporderFormIDFK = new PropertyInfo();
				proporderFormIDFK.name = "orderFormIdFK";
				proporderFormIDFK.type = PropertyInfo.INTEGER_CLASS;
				request.addProperty(proporderFormIDFK,ordID);
				
				
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(addExhibitsAction, envelope);
					Log.i(TAG, "after call");
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						System.out.println("RETURN: " + response.getPropertyCount());
						for(int i=0; i<response.getPropertyCount(); i++) {
							SoapObject addexhibit = (SoapObject) response.getProperty(i);
							makeRow(addexhibit);
							
						}
					}
					
					Log.i(TAG, "Hist" + period);
					Log.i(TAG, "Type" + type);
					Log.i(TAG, "Dim" + dimensions);
					Log.i(TAG, "location" + locationID);
					Log.i(TAG, "orderForm" + orderFormID);
				} catch (IOException e) {
					e.printStackTrace();

				} catch (XmlPullParserException e) {
					Log.i(LocationsActivity.TAg, "XML pull");
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
	public void searchList(final String type,final String historicPeriod){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN FIND Exhibits: " + type + historicPeriod);
				SoapObject request = new SoapObject(namespace,findExhibits);
				PropertyInfo propType = new PropertyInfo();
				propType.name = "type";
				propType.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propType, type);
				PropertyInfo propHistoricPeriod = new PropertyInfo();
				propHistoricPeriod.name = "historicPeriod";
				propHistoricPeriod.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propHistoricPeriod,historicPeriod);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(findExhibitsAction, envelope);
					Log.i(TAG, "after call");
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						System.out.println("RETURN: " + response.getPropertyCount());
						for(int i=0; i<response.getPropertyCount(); i++) {
							SoapObject exhibit = (SoapObject) response.getProperty(i);
							makeRow(exhibit);
							
						}
					}
					Log.i(TAG, "Hist" + propHistoricPeriod);
					Log.i(TAG, "Type" + propType);
				} catch (IOException e) {
					e.printStackTrace();

				} catch (XmlPullParserException e) {
					Log.i(LocationsActivity.TAg, "XML pull");
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
		
		String exhibitId = object.getProperty(exhibitID).toString();
		tableRow.setId(Integer.parseInt(exhibitId));
		
		
		
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
		
		//String ordId = object.getProperty(orderFormId).toString();
		//tableRow.setId(Integer.parseInt(ordId));
		
		
		registerForContextMenu(tableRow);
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
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v instanceof TableRow){
			TableRow selectedRow = (TableRow) v;
			
			TextView typeTW = (TextView) selectedRow.getChildAt(0);
			TextView dimensionsTW = (TextView) selectedRow.getChildAt(1);
			TextView historicPeriodTw = (TextView) selectedRow.getChildAt(2);
			TextView locationIDTW = (TextView) selectedRow.getChildAt(3);
			TextView orderFormID = (TextView) selectedRow.getChildAt(4);
			
			int exhid = selectedRow.getId();
			String type = typeTW.getText().toString();
			String dimensions = dimensionsTW.getText().toString();
			String period = historicPeriodTw.getText().toString();
			String locationId = locationIDTW.getText().toString();
			String orderForm = orderFormID.getText().toString();
			
			kliknutExhibit = new HashMap<String, String>();
			
			kliknutExhibit.put(exhibitID, String.valueOf(exhid));
			kliknutExhibit.put(ExhibitsActivity.type, type);
			kliknutExhibit.put(ExhibitsActivity.dimensions, dimensions);
			kliknutExhibit.put(historicPeriod, period);
			kliknutExhibit.put(locationid, String.valueOf(locationId));
			kliknutExhibit.put(orderFormId, String.valueOf(orderForm));
			
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.popoutexhibits, menu);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.popup_menu_delete_itemExhibits) {
			if(kliknutExhibit != null) {
				Log.i(TAG, "TREBA OBRISATI: " + kliknutExhibit.get(exhibitID));
				delete();
				
			}	
		}else if(item.getItemId() == R.id.popup_menu_update_itemExhibits) {
			if(kliknutExhibit != null) {
				Log.i(TAG, "TREBA UPDATE [ " + kliknutExhibit.get(exhibitID) + ", " + kliknutExhibit.get(type) + ", " + kliknutExhibit.get(dimensions) + ", " + kliknutExhibit.get(historicPeriod) + ", " + kliknutExhibit.get(locationid) + ","+ kliknutExhibit.get(orderFormId)+ "]");
				update();
			}
		}
		return super.onContextItemSelected(item);
	}
	
	public void update(){
		Intent intent = new Intent (this,UpdateExhibitsActivity.class);
		intent.putExtra(exhibitID, kliknutExhibit.get(exhibitID));
		intent.putExtra(type, kliknutExhibit.get(type));
		intent.putExtra(dimensions, kliknutExhibit.get(dimensions));
		intent.putExtra(historicPeriod, kliknutExhibit.get(historicPeriod));
		intent.putExtra(locationid, kliknutExhibit.get(locationid));
		intent.putExtra(orderFormId, kliknutExhibit.get(orderFormId));
		startActivity(intent);
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
	public void delete(){
		Intent intent = new Intent(this,DeleteExhibitsActivity.class);
		intent.putExtra(exhibitID, kliknutExhibit.get(exhibitID));
		intent.putExtra(type, kliknutExhibit.get(type));
		intent.putExtra(dimensions, kliknutExhibit.get(dimensions));
		intent.putExtra(historicPeriod, kliknutExhibit.get(historicPeriod));
		intent.putExtra(locationid, kliknutExhibit.get(locationid));
		intent.putExtra(orderFormId, kliknutExhibit.get(orderFormId));
		startActivity(intent);
	}
	public void backToMain(View view){
		Intent intent = new Intent(this,MainMenuActivity.class);
		startActivity(intent);
	}
}
