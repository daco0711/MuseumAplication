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

public class DeleteUsersActivity extends ActionBarActivity {
	public static final String deleteUsers = "deleteUsers";
	public static final String deleteUsersActivity = "MuseumService/DeleteUsers";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_users);
		
		String id = getIntent().getStringExtra(UsersActivity.userID);
		EditText txtID = (EditText) findViewById(R.id.etDeleteUserID);
		txtID.setText(id);
		txtID.setVisibility(View.INVISIBLE);
		
		String name = getIntent().getStringExtra(UsersActivity.name);
		EditText txtName = (EditText) findViewById(R.id.etDeleteUsersName);
		txtName.setText(name);
		
		String userName = getIntent().getStringExtra(UsersActivity.userName);
		EditText txtUserName = (EditText) findViewById(R.id.etDeleteUserName);
		txtUserName.setText(userName);
		
		String password = getIntent().getStringExtra(UsersActivity.password);
		EditText txtPass = (EditText) findViewById(R.id.etDeleteUserPassword);
		txtPass.setText(password);
		
		String isAdmin = getIntent().getStringExtra(UsersActivity.isAdministrator);
		EditText txtIsAdmin = (EditText) findViewById(R.id.etDeleteUserIsAdministrator);
		txtIsAdmin.setText(isAdmin);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_users, menu);
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
		Intent intent = new Intent(this,UsersActivity.class);
		startActivity(intent);
	}
	public void deleteOK(View view){
		EditText txtID = (EditText) findViewById(R.id.etDeleteUserID);
		final String id = txtID.getText().toString();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(UsersActivity.TAG, "RUN DELETE USERS: " + id);
				SoapObject request = new SoapObject(UsersActivity.namespace,deleteUsers);
				PropertyInfo propID = new PropertyInfo();
				propID.name = "userId";
				propID.type = PropertyInfo.INTEGER_CLASS;
				request.addProperty(propID, id);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.implicitTypes = true;
				envelope.dotNet = true;
				envelope.encodingStyle = SoapSerializationEnvelope.XSD;
				envelope.setOutputSoapObject(request);
				HttpTransportSE transport = new HttpTransportSE(UsersActivity.URL);
				Log.i(UsersActivity.TAG, "Pozivam WCF servis");
				
				Log.i(UsersActivity.TAG, "DELETE: " + id );
				
				try {
					Log.i(UsersActivity.TAG, "before call");
					transport.debug = true;
					transport.call(deleteUsersActivity, envelope);
					Log.i(UsersActivity.TAG, "after call add");
					
				}  catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(DeleteUsersActivity.this, UsersActivity.class);
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

