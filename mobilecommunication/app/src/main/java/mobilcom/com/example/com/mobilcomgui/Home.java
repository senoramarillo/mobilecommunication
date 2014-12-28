package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void navigateLoadImage(View view) {
        Intent i = new Intent(this, Load_Pic.class);
        startActivity(i);
    }

    public void takePicture(View view){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            Intent i = new Intent(this, Edit.class);
            i.putExtra("img", theImage);
            startActivity(i);

        }
    }
}
