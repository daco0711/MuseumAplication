package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddBuyersActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_buyers);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_buyers, menu);
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
	public void addBuyers(View view){
		EditText txtName = (EditText) findViewById(R.id.etAddBuyersName);
		String name = txtName.getText().toString();
		EditText txtSurname = (EditText) findViewById(R.id.etAddBuyersSurname);
		String surname = txtSurname.getText().toString();
		EditText txtAddress = (EditText) findViewById(R.id.etAddBuyersAddress);
		String address = txtAddress.getText().toString();
		EditText txtCountry = (EditText) findViewById(R.id.etAddBuyersCountry);
		String country = txtCountry.getText().toString();
		if(name.equals("") && surname.equals("") && address.equals("") && country.equals("")){
			Toast.makeText(this, "You must enter required fields!", Toast.LENGTH_LONG).show();
		}else{
			Intent intent = new Intent(this,BuyersActivity.class);
			intent.putExtra(BuyersActivity.addName, name);
			intent.putExtra(BuyersActivity.addSurname, surname);
			intent.putExtra(BuyersActivity.addAddress, address);
			intent.putExtra(BuyersActivity.addCountry, country);
			startActivity(intent);
		}
	}
}
