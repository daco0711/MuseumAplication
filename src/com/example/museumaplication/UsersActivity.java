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
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class UsersActivity extends ActionBarActivity {
	public static final String userID = "UserId";
	public static final String name = "Name";
	public static final String userName = "UserName";
	public static final String password = "Password";
	public static final String isAdministrator = "IsAdministrator";
	public static final String ip = "192.168.1.8";
	public static final int port = 80;
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getUsers = "getUsers";
	public static String getusersAction = "MuseumService/GetUsers";
	public static String TAG = "UsersActivity";
	TableLayout table;
	public static String searchResults = "Search_Results";
	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
		tableLayoutParams = new LayoutParams();
		tableRowParams = new TableRow.LayoutParams();
		textViewLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		table = (TableLayout) findViewById(R.id.tableUsers);
		table.setBackgroundColor(Color.BLACK);
		
		tableRowParams.setMargins(1, 1, 1, 1);
		tableLayoutParams.weight = 1;
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().containsKey(searchResults)) {
		} else {
			listOfUsers();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.users, menu);
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
	public void listOfUsers(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN");
				SoapObject request = new SoapObject(namespace, getUsers);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(getusersAction, envelope);
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
	public void makeRow(SoapObject object){
		TableRow tableRow = new TableRow(this);
		
		TextView txtId = new TextView(this);
		txtId.setBackgroundColor(Color.WHITE);
		txtId.setGravity(Gravity.CENTER);
		txtId.setLayoutParams(textViewLayoutParams);
		txtId.setText(object.getProperty(userID).toString().split(";")[0]);
		tableRow.addView(txtId,tableRowParams);
		
		//Log.i(TAG, "EXIBIT: " + object.getProperty(type).toString().split(";")[0]);
		
		TextView txtName = new TextView(this);
		txtName.setBackgroundColor(Color.WHITE);
		txtName.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtName.setText(object.getProperty(name).toString().split(";")[0]);
		tableRow.addView(txtName,tableRowParams);
		
		TextView txtUserName = new TextView(this);
		txtUserName.setBackgroundColor(Color.WHITE);
		txtUserName.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtUserName.setText(object.getProperty(userName).toString().split(";")[0]);
		tableRow.addView(txtUserName,tableRowParams);
		
		
		
		TextView txtPassword = new TextView(this);
		txtPassword.setBackgroundColor(Color.WHITE);
		txtPassword.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtPassword.setText(object.getProperty(password).toString().split(";")[0]);
		tableRow.addView(txtPassword,tableRowParams);
		
		TextView txtIsAdministrator = new TextView(this);
		txtIsAdministrator.setBackgroundColor(Color.WHITE);
		txtIsAdministrator.setGravity(Gravity.CENTER);
		//txtType.setLayoutParams(textViewLayoutParams);
		txtIsAdministrator.setText(object.getProperty(isAdministrator).toString().split(";")[0]);
		tableRow.addView(txtIsAdministrator,tableRowParams);
		
		table.addView(tableRow, tableLayoutParams);
		
	}
	public void searchUsers(View view){
		Intent intent = new Intent(this,SearchUsers.class);
		startActivity(intent);
	}
	public void addUsers(View view){
		Intent intent = new Intent(this,AddUsers.class);
		startActivity(intent);
	}
	
}
