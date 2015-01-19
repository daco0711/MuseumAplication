package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocationsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_locations);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_locations, menu);
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
	public void aDDLocations(View view){
		EditText txtLocationName = (EditText) findViewById(R.id.editTextAddLocationName);
		String name = txtLocationName.getText().toString().trim();
		EditText txtSurface = (EditText) findViewById(R.id.editTextAddSurface);
		String surface = txtSurface.getText().toString().trim();
		EditText txtState = (EditText) findViewById(R.id.editTextAddState);
		String state = txtState.getText().toString().trim();
		EditText txtLease = (EditText) findViewById(R.id.editTextAddLeasePrice);
		String lease = txtLease.getText().toString().trim();
		EditText txtCountry = (EditText) findViewById(R.id.editTextAddCountry);
		String country = txtCountry.getText().toString().trim();
		if (name.equals("") && surface.equals("") && state.equals("") && lease.equals("") && country.equals("")){
			Toast.makeText(this, "You must enter all required fields!", Toast.LENGTH_LONG).show();
		}
		else{
			Intent intent = new Intent(this,LocationsActivity.class);
			intent.putExtra(LocationsActivity.addname, name);
			intent.putExtra(LocationsActivity.addsurface, surface);
			intent.putExtra(LocationsActivity.addstate, state);
			intent.putExtra(LocationsActivity.addLease, lease);
			intent.putExtra(LocationsActivity.addCountry, country);
			startActivity(intent);
		}
				
	}
}
