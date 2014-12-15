package com.example.mobilecommunication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Load_Pic extends Activity {

	Button btn_edit;
	Bitmap img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_pic);
		Intent intent = getIntent();
	}

	public void editClick(View v) {
		Intent i = new Intent(this, Edit.class);
		i.putExtra("img", img);
		startActivity(i);
	}
	public void loadImageClick(View v){
		//TODO start promt to load data browser
	}

}
