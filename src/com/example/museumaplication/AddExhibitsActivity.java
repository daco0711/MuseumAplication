package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddExhibitsActivity extends ActionBarActivity {
	//private Integer locationID = null;
	//private Integer orderFormID = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exhibits);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_exhibits, menu);
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
	public void addExhibits(View view){
		EditText txttype = (EditText) findViewById(R.id.etAddExhibitsType);
		String type = txttype.getText().toString(); 
		EditText txtDimensions = (EditText) findViewById(R.id.etAddExhibitsDimensions);
		String dimensions = txtDimensions.getText().toString();
		EditText txtHistoricPeriod = (EditText) findViewById(R.id.etAddExhibitsJurassicPeriod);
		String period = txtHistoricPeriod.getText().toString();
		EditText txtLocationIDFK = (EditText) findViewById(R.id.etAddExhibitsLocationIDFK);
		String locationIDFK = txtLocationIDFK.getText().toString();
		EditText txtOrderFormIDFK = (EditText) findViewById(R.id.etAddExhibitsOrderFormsIDFK);
		String orderForm = txtOrderFormIDFK.getText().toString();
		if(type.equals("") && dimensions.equals("") && period.equals("") && locationIDFK.equals("")){
			Toast.makeText(this, "You must enter all required fields!",	Toast.LENGTH_LONG).show();
		}
		else{
			//locationID = Integer.parseInt(locationIDFK);
			//orderFormID = Integer.parseInt(orderForm);
			
			Intent intent = new Intent(this,ExhibitsActivity.class);
			intent.putExtra(ExhibitsActivity.addType, type);
			intent.putExtra(ExhibitsActivity.addDimensions, dimensions);
			intent.putExtra(ExhibitsActivity.addPeriod, period);
			intent.putExtra(ExhibitsActivity.addLocationID, locationIDFK);
			intent.putExtra(ExhibitsActivity.addOrderFormIDFK, orderForm);
			startActivity(intent);
		}
		
	}
}
