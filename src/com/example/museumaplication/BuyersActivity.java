package com.example.museumaplication;

import java.io.IOException;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
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
	public static final String buyerId = "BuyersId";
	public static final String ip = "192.168.1.5";
	public static final int port = 80;
	public static String URL = "http://" + ip + ":" + port
			+ "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getBuyers = "getBuyers";
	public static String getBuyersAction = "MuseumService/GetBuyers";
	public static String TAG = "BuyersActivity";
	TableLayout table;
	public static String searchName = "Search_Name";
	public static String searchSurname = "Search_Surname";
	public static String findBuyers = "findBuyers";
	public static String findBuyersAction = "MuseumService/FindBuyers";
	public static String addName = "addName";
	public static String addSurname = "addSurname";
	public static String addAddress = "addAddress";
	public static String addCountry = "addAddress";
	public static String addBuyers = "addBuyers";
	public static String addBuyersAction = "MuseumService/AddBuyers";

	HashMap<String, String> kliknutKupac;

	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	Boolean isAdmin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buyers);
		tableLayoutParams = new LayoutParams();
		tableRowParams = new TableRow.LayoutParams();
		textViewLayoutParams = new TableRow.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		table = (TableLayout) findViewById(R.id.tableBuyers);
		table.setBackgroundColor(Color.BLACK);

		tableRowParams.setMargins(1, 1, 1, 1);
		tableLayoutParams.weight = 1;
		
		Boolean admin = getApplicationContext().getSharedPreferences("preferences", 0).getBoolean("user_admin", false);
		if(admin !=null & admin == false){
			View addButton = findViewById(R.id.buttonAddBuyers);
			addButton.setVisibility(View.INVISIBLE);
		}
		isAdmin = admin;
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(addName)
				&& getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(addSurname)
				&& getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(addAddress)
				&& getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(addCountry)) {
			String name = getIntent().getExtras().getString(addName);
			String surname = getIntent().getExtras().getString(addSurname);
			String address = getIntent().getExtras().getString(addAddress);
			String country = getIntent().getExtras().getString(addCountry);
			addBuyers(name, surname, address, country);
		} else if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(searchName)
				&& getIntent().getExtras().containsKey(searchSurname)) {
			String name = getIntent().getExtras().getString(searchName);
			String surname = getIntent().getExtras().getString(searchSurname);
			searchBuyers(name, surname);
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

	public void addBuyers(final String name, final String surname,
			final String address, final String country) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN ADD BUYERS: " + name + ", " + surname + ", "
						+ address + ", " + country);
				SoapObject request = new SoapObject(namespace, addBuyers);
				PropertyInfo propName = new PropertyInfo();
				propName.name = "buyersName";
				propName.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propName, name);
				PropertyInfo propSurname = new PropertyInfo();
				propSurname.name = "buyersSurname";
				propSurname.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propSurname, surname);
				PropertyInfo propAddress = new PropertyInfo();
				propAddress.name = "buyersAddress";
				propAddress.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propAddress, address);
				PropertyInfo propCountry = new PropertyInfo();
				propCountry.name = "buyersCountry";
				propSurname.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propCountry, country);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(addBuyersAction, envelope);
					Log.i(TAG, "after call");
					listOfBuyers();
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

	public void searchBuyers(final String name, final String surname) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN FIND LOCATIONS: " + name + surname);
				SoapObject request = new SoapObject(namespace, findBuyers);
				PropertyInfo propName = new PropertyInfo();
				propName.name = "buyersName";
				propName.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propName, name);
				PropertyInfo propSurname = new PropertyInfo();
				propSurname.name = "buyersSurname";
				propSurname.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propSurname, surname);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(findBuyersAction, envelope);
					Log.i(TAG, "after call");
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						System.out.println("RETURN: "
								+ response.getPropertyCount());
						for (int i = 0; i < response.getPropertyCount(); i++) {
							SoapObject buyers = (SoapObject) response
									.getProperty(i);
							makeRow(buyers);
						}
					}
					Log.i(TAG, "name" + name);
					Log.i(TAG, "Surname" + surname);
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

	public void listOfBuyers() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN");
				SoapObject request = new SoapObject(namespace, getBuyers);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER10);
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

	private void makeRow(SoapObject object) {
		TableRow tableRow = new TableRow(this);

		String buyId = object.getProperty(buyerId).toString();
		tableRow.setId(Integer.parseInt(buyId));

		TextView txtBuyersName = new TextView(this);
		txtBuyersName.setBackgroundColor(Color.WHITE);
		txtBuyersName.setGravity(Gravity.CENTER);
		txtBuyersName.setLayoutParams(textViewLayoutParams);
		txtBuyersName.setText(object.getProperty(buyersName).toString()
				.split(";")[0]);
		tableRow.addView(txtBuyersName, tableRowParams);

		TextView txtBuyersSurname = new TextView(this);
		txtBuyersSurname.setBackgroundColor(Color.WHITE);
		txtBuyersSurname.setGravity(Gravity.CENTER);
		txtBuyersSurname.setText(object.getProperty(buyersSurname).toString()
				.split(";")[0]);
		tableRow.addView(txtBuyersSurname, tableRowParams);

		TextView txtAddress = new TextView(this);
		txtAddress.setBackgroundColor(Color.WHITE);
		txtAddress.setGravity(Gravity.CENTER);
		txtAddress.setText(object.getProperty(buyersAddress).toString()
				.split(";")[0]);
		tableRow.addView(txtAddress, tableRowParams);

		TextView txtBuyersCountry = new TextView(this);
		txtBuyersCountry.setBackgroundColor(Color.WHITE);
		txtBuyersCountry.setGravity(Gravity.CENTER);
		txtBuyersCountry.setText(object.getProperty(buyersCountry).toString()
				.split(";")[0]);
		tableRow.addView(txtBuyersCountry, tableRowParams);

		// TextView txtBuyersID= new TextView(this);
		// txtBuyersID.setBackgroundColor(Color.WHITE);
		// txtBuyersID.setGravity(Gravity.CENTER);
		// txtBuyersID.setText(object.getProperty(buyerId).toString().split(";")[0]);
		// tableRow.addView(txtBuyersID,tableRowParams);
		if (isAdmin){
		registerForContextMenu(tableRow);
		}
		table.addView(tableRow, tableLayoutParams);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v instanceof TableRow) {
			TableRow selectedRow = (TableRow) v;

			TextView nameTW = (TextView) selectedRow.getChildAt(0);
			TextView surnameTW = (TextView) selectedRow.getChildAt(1);
			TextView addressTw = (TextView) selectedRow.getChildAt(2);
			TextView countryTW = (TextView) selectedRow.getChildAt(3);

			int buyerID = selectedRow.getId();
			String buyerName = nameTW.getText().toString();
			String buyerSurname = surnameTW.getText().toString();
			String buyerAddress = addressTw.getText().toString();
			String buyerCountry = countryTW.getText().toString();

			kliknutKupac = new HashMap<String, String>();

			kliknutKupac.put(buyerId, String.valueOf(buyerID));
			kliknutKupac.put(buyersName, buyerName);
			kliknutKupac.put(buyersSurname, buyerSurname);
			kliknutKupac.put(buyersAddress, buyerAddress);
			kliknutKupac.put(buyersCountry, buyerCountry);

			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.popupmenubuyers, menu);

		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.popup_menu_delete_itemBuyers) {
			if (kliknutKupac != null) {
				Log.i(TAG, "TREBA OBRISATI: " + kliknutKupac.get(buyerId));
				delete();

			}
		} else if (item.getItemId() == R.id.popup_menu_update_itemBuyers) {
			if (kliknutKupac != null) {
				Log.i(TAG,
						"TREBA UPDATE [ " + kliknutKupac.get(buyerId) + ", "
								+ kliknutKupac.get(buyersName) + ", "
								+ kliknutKupac.get(buyersSurname) + ", "
								+ kliknutKupac.get(buyersAddress) + ", "
								+ kliknutKupac.get(buyersCountry) + "]");
				update();
			}
		}
		return super.onContextItemSelected(item);
	}

	public void update() {
		Intent intent = new Intent(this, UpdateBuyersActivity.class);
		intent.putExtra(buyerId, kliknutKupac.get(buyerId));
		intent.putExtra(buyersName, kliknutKupac.get(buyersName));
		intent.putExtra(buyersSurname, kliknutKupac.get(buyersSurname));
		intent.putExtra(buyersAddress, kliknutKupac.get(buyersAddress));
		intent.putExtra(buyersCountry, kliknutKupac.get(buyersCountry));
		startActivity(intent);

	}

	public void searchBuyers(View view) {
		Intent intent = new Intent(this, SearchBuyersActivity.class);
		startActivity(intent);
	}

	public void addBuyers(View view) {
		Intent intent = new Intent(this, AddBuyersActivity.class);
		startActivity(intent);
	}

	public void delete() {
		Intent intent = new Intent(this, DeleteBuyerActivity.class);
		intent.putExtra(buyerId, kliknutKupac.get(buyerId));
		intent.putExtra(buyersName, kliknutKupac.get(buyersName));
		intent.putExtra(buyersSurname, kliknutKupac.get(buyersSurname));
		intent.putExtra(buyersAddress, kliknutKupac.get(buyersAddress));
		intent.putExtra(buyersCountry, kliknutKupac.get(buyersCountry));
		startActivity(intent);
	}

	public void back(View view) {
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
	}
}
