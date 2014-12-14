package com.example.mobilecommunication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
//		Intent intent = getIntent();

	}
	
	
	public void navigateLoadImage(View view) {
		Intent i = new Intent(this, Load_Pic.class);
		startActivity(i);
	}

}
