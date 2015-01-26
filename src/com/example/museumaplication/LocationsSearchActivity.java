package com.example.museumaplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.Toast;

public class LocationsSearchActivity extends ActionBarActivity {
	public static final String ip = "192.168.1.8";
	public static final int port = 80;
	public static String  URL = "http://" + ip + ":" + port + "/WcfServiceMuseumNew/Service1.svc";
	public static String namespace = "WcfServiceMuseumNew";
	//public static String getLocations = "getLocations";
	//public static String getLocationsAction = "MuseumService/GetLocations";
	public static String TAg = "LocationsActivity";
	public static String findLocations = "findLocations";
	public static String findLocationsAction = "MuseumService/FindLocations";
	public static final String locationName = "LocationName";
	public static final String surface = "Surface";
	public static final String state = "State";
	public static final String leasePrice = "LeasePrice";
	public static final String country = "Country";
	TableLayout table;

	LayoutParams tableLayoutParams;
	TableRow.LayoutParams tableRowParams;
	TableRow.LayoutParams textViewLayoutParams;
	public static String searchResults = "Search_Results";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations_search);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.locations_search, menu);
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
	
	public void findLocation(View view){
		EditText txtSearchLocatioName = (EditText) findViewById(R.id.editTextSearchLocationsName);
		String locationName = txtSearchLocatioName.getText().toString();
		if (locationName.equals("")) {
				Toast.makeText(this, "You must enter name of the location!", Toast.LENGTH_LONG).show();
			return;
		}else {
			Intent intent = new Intent(this, LocationsActivity.class);
			intent.putExtra(LocationsActivity.searchTerm, locationName);
			startActivity(intent);

		}
	}
	public void backToLocations(View view){
		Intent intent = new Intent(this,LocationsActivity.class);
		startActivity(intent);
	}
	
}

