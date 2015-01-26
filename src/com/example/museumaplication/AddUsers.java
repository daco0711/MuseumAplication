package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddUsers extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_users);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_users, menu);
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
	public void addUsers(View view){
		EditText txtName = (EditText) findViewById(R.id.ETaddUsersName);
		String name = txtName.getText().toString();
		EditText txtUserName = (EditText) findViewById(R.id.ETaddUserName);
		String userName = txtUserName.getText().toString();
		EditText txtPassword = (EditText) findViewById(R.id.ETaddPassword);
		String password = txtPassword.getText().toString();
		EditText txtIsAdministrator = (EditText) findViewById(R.id.ETaddIsAdministrator);
		String isAdmin = txtIsAdministrator.getText().toString();
		
		if (name.equals("") && userName.equals("") && password.equals("") && isAdmin.equals("")){
			Toast.makeText(this, "Enter all fields", Toast.LENGTH_LONG).show();
		}
		else{
			Intent intent = new Intent(this,UsersActivity.class);
			intent.putExtra(UsersActivity.addName, name);
			intent.putExtra(UsersActivity.addUserName, userName);
			intent.putExtra(UsersActivity.addPassword, password);
			intent.putExtra(UsersActivity.addIsAdministrator, isAdmin);
			startActivity(intent);
		}
	}
	public void backToMain(View view){
		Intent intent = new Intent(this,UsersActivity.class);
		startActivity(intent);
	}
}
