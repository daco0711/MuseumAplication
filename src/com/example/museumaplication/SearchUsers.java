package com.example.museumaplication;

import org.ksoap2.serialization.SoapObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchUsers extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_users);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_users, menu);
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
	public void findUsers(View view){
		EditText txtName = (EditText) findViewById(R.id.ETsearchUsersName);
		String name = txtName.getText().toString();
		EditText txtUserName = (EditText) findViewById(R.id.ETsearchUserName);
		String userName = txtUserName.getText().toString();
		if (name.equals("") && userName.equals("")){
			Toast.makeText(this, "You must enter Name or UserName of the User!", Toast.LENGTH_LONG).show();
		}
		else{
			Intent intent = new Intent(this,UsersActivity.class);
			intent.putExtra(UsersActivity.searchName, name);
			intent.putExtra(UsersActivity.searchUserName, userName);
			startActivity(intent);
		}
	}
	public void backToUsers(View view){
		Intent intent = new Intent(this,UsersActivity.class);
		startActivity(intent);
	}
}
