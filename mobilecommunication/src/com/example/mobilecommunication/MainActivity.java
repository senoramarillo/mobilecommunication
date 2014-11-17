package com.example.mobilecommunication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private final String TAG = "DemoButtonApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupMessageButton();
    }

	private void setupMessageButton() {
		//1. Get a reference to the button.
		Button messageButton = (Button)findViewById(R.id.btnDisplayMessage);
		
		//2. Set the click listener to my code.
		View.OnClickListener myListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "You clicked the button!");
				Toast.makeText(MainActivity.this, 
						      "You clicked it!", 
						      Toast.LENGTH_LONG)
						      .show();
				
			}
		};
		messageButton.setOnClickListener(myListener);
	}
}
