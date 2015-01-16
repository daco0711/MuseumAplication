package com.example.museumaplication;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenuActivity extends ActionBarActivity {
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		mp = MediaPlayer.create(MainMenuActivity.this, R.raw.maintheme);
		mp.start();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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
	public void enterMuseum(View view)
	{
		Intent intent = new Intent(this,MuseumActivity.class);
		startActivity(intent);
	}
	public void enterLocations(View view)
	{
		Intent intent = new Intent(this,LocationsActivity.class);
		startActivity(intent);
	}
	public void enterExhibits(View view)
	{
		Intent intent = new Intent(this,ExhibitsActivity.class);
		startActivity(intent);
	}
	public void enterBuyers(View view)
	{
		Intent intent = new Intent(this,BuyersActivity.class);
		startActivity(intent);
	}
	public void enterUsers(View view)
	{
		Intent intent = new Intent(this,UsersActivity.class);
		startActivity(intent);
	}
	public void utisaj(View view){
		mp.setVolume(0, 0);
		
	}
	public void pojacaj(View view){
		mp.setVolume(1, 1);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
	}
}
