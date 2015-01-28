package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.memetix.mst.language.Language;

import java.io.File;

/**
 * Created by Malte on 28.12.2014.
 */
public class Edit extends Activity {
    ImageView iv;
    boolean offloading;
    Language cfrom;
    Language cto;
    Bitmap img;
    File imgpath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if( bundle != null) {
            //img = (Bitmap) intent.getExtras().get("img");
            img = (Bitmap) bundle.get("data");
            Log.e("img: ", img.toString());
            //this.iv.setImageBitmap(img);
            //iv = (ImageView) findViewById(R.id.result);
            //iv.setImageBitmap(img);
        }
        //getAlbumStorageDir();
        //File dir =new File(context.getFilesDir());




    }

    public boolean onCreateOptionsMenue ( Menu menu){

//		getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }


    public void doStuff(File img,Language from,Language to,Boolean offloading ){

    }

    public void toggleClicked(View view){
        offloading = ((ToggleButton) view).isChecked();
    }

    /*
     * Returns a string array with the Tring from the ocr and the result of the translation
     */
    // File tif String countryfrom String country to Type offloading
    public void runOCR(View v){
        Intent intent = new Intent();
    //TODO this should run the OCR modul and redict to the result screen
        doStuff(imgpath,cfrom,cto,offloading);

        Toast.makeText(Edit.this, "You pressed the button runOCR", Toast.LENGTH_LONG).show();
    }


    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {

        }
        return file;
    }


}

