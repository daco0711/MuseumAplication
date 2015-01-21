package com.example.museumaplication;

import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
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
	public static final String ip = "192.168.1.3";
	public static final int port = 80;
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	public static String getUsers = "getUsers";
	public static String getusersAction = "MuseumService/GetUsers";
	public static String TAG = "UsersActivity";
	public static String findUsers = "findUsers";
	public static String findusersAction = "MuseumService/FindUsers";
	TableLayout table;
	public static String searchName = "SearchName";
	public static String searchUserName = "SearchUserName";
	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	public static String addName = "Name";
	public static String addUserName = "UserName";
	public static String addPassword = "Password";
	public static String addIsAdministrator = "IsAdministartor";
	public static String addUsers = "addUsers";
	public static String addUsersAction = "MuseumService/AddUsers";
	HashMap<String, String> kliknutUser;
	
	

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
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(addName)&& getIntent().getExtras() != null && getIntent().getExtras().containsKey(addUserName)&& getIntent().getExtras() != null && getIntent().getExtras().containsKey(addPassword)&& getIntent().getExtras() != null && getIntent().getExtras().containsKey(addIsAdministrator)){
			String name = getIntent().getExtras().getString(addPassword);
			String userName = getIntent().getExtras().getString(addUserName);
			String password = getIntent().getExtras().getString(addPassword);
			String isAdministartor = getIntent().getExtras().getString(addIsAdministrator);
			addUsers(name,userName,password,isAdministartor);
		}
		else if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(searchName)&& getIntent().getExtras().containsKey(searchUserName)) {
		String name = getIntent().getExtras().getString(searchName);
		String userName = getIntent().getExtras().getString(searchUserName);
		findUsers(name,userName);
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
	public void addUsers(final String name,final String userName,final String password,final String isAdministartor){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN ADD USERS: " + name + ", " + userName + ", " + password + ", " + isAdministartor);
				SoapObject request = new SoapObject(namespace, addUsers);
				PropertyInfo propName = new PropertyInfo();
				propName.name = "name";
				propName.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propName, name);
				PropertyInfo propUserName = new PropertyInfo();
				propUserName.name = "userName";
				propUserName.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propUserName,userName);
				PropertyInfo propPassword = new PropertyInfo();
				propPassword.name = "password";
				propPassword.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propPassword,password);
				PropertyInfo propIsAdmin = new PropertyInfo();
				propIsAdmin.name = "isAdministrator";
				propIsAdmin.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propIsAdmin,isAdministartor);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(addUsersAction, envelope);
					Log.i(TAG, "after call");
					listOfUsers();
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
	
	public void findUsers(final String name,final String userName){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "RUN FIND Exhibits: " + name	 + userName);
				SoapObject request = new SoapObject(namespace,findUsers);
				PropertyInfo propName = new PropertyInfo();
				propName.name = "name";
				propName.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propName, name);
				PropertyInfo propUserName = new PropertyInfo();
				propUserName.name = "userName";
				propUserName.type = PropertyInfo.STRING_CLASS;
				request.addProperty(propUserName,userName);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(URL);
				Log.i(TAG, "Pozivam WCF... [" + URL + "]");
				try {
					Log.i(TAG, "before call");
					transport.debug = true;
					transport.call(findusersAction, envelope);
					Log.i(TAG, "after call");
					SoapObject response = (SoapObject) envelope.getResponse();
					if (response != null && response.getPropertyCount() > 0) {
						System.out.println("RETURN: " + response.getPropertyCount());
						for(int i=0; i<response.getPropertyCount(); i++) {
							SoapObject exhibit = (SoapObject) response.getProperty(i);
							makeRow(exhibit);
							
						}
					}
					Log.i(TAG, "Name" + propName);
					Log.i(TAG, "UserName" + propUserName);
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
		
		String userId = object.getProperty(userID).toString();
		tableRow.setId(Integer.parseInt(userId));
		
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
		
		registerForContextMenu(tableRow);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v instanceof TableRow){
			TableRow selectedRow = (TableRow) v;
			
			TextView nameTW = (TextView) selectedRow.getChildAt(0);
			TextView userNameTW = (TextView) selectedRow.getChildAt(1);
			TextView passwordTw = (TextView) selectedRow.getChildAt(2);
			TextView isAdministratorTW = (TextView) selectedRow.getChildAt(3);
			
			int userid = selectedRow.getId();
			String name = nameTW.getText().toString();
			String userName = userNameTW.getText().toString();
			String password = passwordTw.getText().toString();
			String isAdministrator = isAdministratorTW.getText().toString();
			
			kliknutUser = new HashMap<String, String>();
			
			kliknutUser.put(userID, String.valueOf(userid));
			kliknutUser.put(UsersActivity.name, name);
			kliknutUser.put(UsersActivity.userName, userName);
			kliknutUser.put(UsersActivity.password, password);
			kliknutUser.put(UsersActivity.isAdministrator, isAdministrator);
			
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.popupuser, menu);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.popup_menu_delete_itemUsers) {
			if(kliknutUser != null) {
				Log.i(TAG, "TREBA OBRISATI: " + kliknutUser.get(userID));
				
			}	
		}else if(item.getItemId() == R.id.popup_menu_update_itemUsers) {
			if(kliknutUser != null) {
				Log.i(TAG, "TREBA UPDATE [ " + kliknutUser.get(userID) + ", " + kliknutUser.get(name) + ", " + kliknutUser.get(userName) + ", " + kliknutUser.get(password) + ", " + kliknutUser.get(isAdministrator) + "]");
				update();
			}
		}
		return super.onContextItemSelected(item);
	}
	public void update(){
		Intent intent = new Intent(this,UpdateUsersActivity.class);
		
		intent.putExtra(userID, kliknutUser.get(userID));
		intent.putExtra(name, kliknutUser.get(name));
		intent.putExtra(userName, kliknutUser.get(userName));
		intent.putExtra(password, kliknutUser.get(password));
		intent.putExtra(isAdministrator, kliknutUser.get(isAdministrator));
		startActivity(intent);
	}
}
