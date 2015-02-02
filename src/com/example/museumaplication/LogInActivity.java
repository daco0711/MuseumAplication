package com.example.museumaplication;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends ActionBarActivity {
	public static final String userID = "UserId";
	public static final String name = "UserName";
	public static final String userName = "UserName";
	public static final String password = "Password";
	public static final String isAdmin = "IsAdministrator";
	public static final String getUsers = "getUsersByusername";
	public static final String getUsersAction = "MuseumService/LogIn";

	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		mp = MediaPlayer.create(LogInActivity.this, R.raw.roar);
		mp.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
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

	public void enterMainMenu(View view) {
		EditText txtName = (EditText) findViewById(R.id.editTextUserName);
		final String userName = txtName.getText().toString();
		EditText txtPass = (EditText) findViewById(R.id.editTextPassword);
		final String password = txtPass.getText().toString();

		final Toast message = Toast.makeText(getBaseContext(),
				"Wrong username or password!",
				Toast.LENGTH_LONG);
		
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Log.i(LocationsActivity.TAg, "Log IN");
					SoapObject request = new SoapObject(
							MuseumActivity.NAMESPACE, getUsers);

					PropertyInfo propUserName = new PropertyInfo();
					propUserName.name = "userName";
					propUserName.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propUserName, userName);

					PropertyInfo propPass = new PropertyInfo();
					propPass.name = "password";
					propPass.type = PropertyInfo.STRING_CLASS;
					request.addProperty(propPass, password);

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER10);
					envelope.implicitTypes = true;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.XSD;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transport = new HttpTransportSE(
							MuseumActivity.URL);
					Log.i(LocationsActivity.TAg, "Pozivam WCF servis");

					Log.i(MuseumActivity.TAG, "LOG IN: " + userName + ", "
							+ password);

					try {
						Log.i(LocationsActivity.TAg, "before call");
						transport.debug = true;
						transport.call(getUsersAction, envelope);
						Log.i(LocationsActivity.TAg, "after call");
						SoapObject response = (SoapObject) envelope
								.getResponse();
						if (response != null && response.getPropertyCount() > 0) {
							String username = response
									.getPropertyAsString("UserName");
							String name = response.getPropertyAsString("Name");
							String userID = response.getPropertyAsString("UserId");
							String isAdmin = response.getPropertyAsString("IsAdministrator");
							Boolean admin = Boolean.parseBoolean(isAdmin);

							SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", 0); // 0 - for private mode
							Editor editor = pref.edit();
							editor.putString("user_id", userID);
							editor.putString("user_name", name);
							editor.putString("user_username", username);
							editor.putBoolean("user_admin", admin);
							editor.commit();

							Intent intent = new Intent(LogInActivity.this, MainMenuActivity.class);
							startActivity(intent);
						} else {
							message.show();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
	}

	public void muteRoar(View view) {
		mp.setVolume(0, 0);
	}

	public void unMuteRoar(View view) {
		mp.setVolume(1, 1);
	}

}
