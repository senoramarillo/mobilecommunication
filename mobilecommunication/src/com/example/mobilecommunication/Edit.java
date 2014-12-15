package com.example.mobilecommunication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class Edit extends Activity{
	ImageView iv;
	boolean offloading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		Intent intent = getIntent();
		if( intent != null){
			Bitmap img = (Bitmap) intent.getExtras().get("img");
			iv = (ImageView) findViewById(R.id.imageEdit);
		}
		
		
		

	}
	
	public boolean onCreateOptionsMenue ( Menu menu){
		
//		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}


	public void translateClick(View v) {
		Intent i = new Intent(this, Result.class);
		startActivity(i);
	}

	public void toggleClicked(View view){
		offloading = ((ToggleButton) view).isChecked();
	}




}
