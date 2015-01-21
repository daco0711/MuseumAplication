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

public class UpdateUsersActivity extends ActionBarActivity {
	
	static final String updateUsers = "updateUsers";
	static final String updateUsersAction = "MuseumService/UpdateUsers";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_users);
		
	String id = getIntent().getStringExtra(UsersActivity.userID);
	EditText txtID = (EditText) findViewById(R.id.etUpdateUserID);
	txtID.setText(id);
	txtID.setVisibility(View.INVISIBLE);
	
	String name = getIntent().getStringExtra(UsersActivity.name);
	EditText txtName = (EditText) findViewById(R.id.etUpdateUsersName);
	txtName.setText(name);
	
	String userName = getIntent().getStringExtra(UsersActivity.userName);
	EditText txtUserName = (EditText) findViewById(R.id.etUpdateUsername);
	txtUserName.setText(userName);
	
	String password = getIntent().getStringExtra(UsersActivity.password);
	EditText txtPassword = (EditText) findViewById(R.id.etUpdatePassword);
	txtPassword.setText(password);
	
	String isAdmin = getIntent().getStringExtra(UsersActivity.isAdministrator);
	EditText txtIsAdmin = (EditText) findViewById(R.id.etUpdateIsAdministrator);
	txtIsAdmin.setText(isAdmin);
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_users, menu);
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
	public void updateUsers(View view){
		EditText txtID = (EditText) findViewById(R.id.etUpdateUserID);
		final String iD = txtID.getText().toString();
		EditText txtname = (EditText) findViewById(R.id.etUpdateUsersName);
		final String name = txtname.getText().toString();
		EditText txtUsername = (EditText) findViewById(R.id.etUpdateUsername);
		final String userName = txtUsername.getText().toString();
		EditText txtPassword = (EditText) findViewById(R.id.etUpdatePassword);
		final String password = txtPassword.getText().toString();
		EditText txtIsAdministrator = (EditText) findViewById(R.id.etUpdateIsAdministrator);
		final String isAdministrator = txtIsAdministrator.getText().toString();
		
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Log.i(LocationsActivity.TAg, "Update usera");
					SoapObject request = new SoapObject(UsersActivity.namespace, updateUsers);
					
					PropertyInfo propID = new PropertyInfo();
					propID.name = "userID";
					propID.type = PropertyInfo.INTEGER_CLASS;
					request.addProperty(propID, iD);
					
					PropertyInfo propName = new PropertyInfo();
					propName.name = "name";
					propName.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propName, name);
					
					PropertyInfo propUserName = new PropertyInfo();
					propUserName.name = "userName";
					propUserName.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propUserName, userName);
					
					PropertyInfo propPassword = new PropertyInfo();
					propPassword.name = "password";
					propPassword.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propPassword, password);
					
					PropertyInfo propisAdmini = new PropertyInfo();
					propisAdmini.name = "isAdministrator";
					propisAdmini.type = PropertyInfo.BOOLEAN_CLASS;
					request.addProperty(propisAdmini, isAdministrator);
					
					
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
					envelope.implicitTypes = true;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transport = new HttpTransportSE(UsersActivity.URL);
					Log.i(LocationsActivity.TAg, "Pozivam WCF servis");
					
					Log.i(UsersActivity.TAG, "UPDATE: " + name + ", " + userName + ", " + password + ", " + isAdministrator + ", " );
					
					try {
						Log.i(LocationsActivity.TAg, "before call");
						transport.debug = true;
						transport.call(updateUsersAction, envelope);
						Log.i(LocationsActivity.TAg, "after call add");
						
					}  catch (IOException e) {
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(UpdateUsersActivity.this, UsersActivity.class);
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

		
		
		
		
	}

