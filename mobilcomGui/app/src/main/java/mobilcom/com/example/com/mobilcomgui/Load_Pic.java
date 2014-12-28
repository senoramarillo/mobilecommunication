package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Malte on 28.12.2014.
 */
public class Load_Pic extends Activity {

    Button btn_edit;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pic);
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
