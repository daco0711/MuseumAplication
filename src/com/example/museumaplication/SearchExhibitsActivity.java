package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchExhibitsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_exhibits);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_exhibits, menu);
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
	public void findExhibits(View view){
		EditText txtType = (EditText) findViewById(R.id.EtsearchExhibitsType);
		String type = txtType.getText().toString();
		EditText txtHistoricPeriod = (EditText) findViewById(R.id.EtsearchExhibitsHistoricPeriod);
		String historicPeriod = txtHistoricPeriod.getText().toString();
		
		if (type.equals("") && historicPeriod.equals("")) {
			Toast.makeText(this, "You must enter Type or Historic Period of the Exhibit!", Toast.LENGTH_LONG).show();
			return;
		}else {
			Intent intent = new Intent(this,ExhibitsActivity.class);
			intent.putExtra(ExhibitsActivity.searchType,type);
			intent.putExtra(ExhibitsActivity.searchHistoricPeriod, historicPeriod);
			startActivity(intent);
			
		}
		
	}
	public void backToExhibits(View view){
		Intent intent = new Intent(this,ExhibitsActivity.class);
		startActivity(intent);
	}
}
