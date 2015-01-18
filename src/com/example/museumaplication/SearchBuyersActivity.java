package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchBuyersActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_buyers);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_buyers, menu);
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
	
	public void findBuyers(View view){
			EditText txtSearchName = (EditText) findViewById(R.id.etSearchBuyersName);
			String name = txtSearchName.getText().toString();
			EditText txtSearchSurname = (EditText) findViewById(R.id.etSearchBuyersSurname);
			String surName = txtSearchSurname.getText().toString();
			if (name.equals("") && surName.equals("")) {
					Toast.makeText(this, "You must enter name or surname of the Buyer!", Toast.LENGTH_LONG).show();
				return;
			}else {
				Intent intent = new Intent(this, BuyersActivity.class);
				Log.i(SearchBuyersActivity.this.getClass().getSimpleName(), "IME: " + name + ", SURNAME: " + surName);
				intent.putExtra(BuyersActivity.searchName, name);
				intent.putExtra(BuyersActivity.searchSurname, surName);
				startActivity(intent);
			}
		}
}
