package com.example.mobilecommunication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Result extends Activity{
	Button btn_tranlate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		Intent intent = getIntent();
		//TODO fill textboxes 
		
		
		
		btn_tranlate = (Button)findViewById(R.id.b_translate);
	}
	
	public boolean onCreateOptionsMenue ( Menu menu){
		
//		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	
    public void repeatWithOffloading(View v){
    
    	
    }

    public void nextPic(View v){
    	Intent i = new Intent(this, Home.class);
		startActivity(i);
    }

}
