package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.memetix.mst.language.Language;

import java.io.File;

import mobilcom.com.example.com.ocr.LocalRun;
import mobilcom.com.example.com.ocr.Offloading;
import mobilcom.com.example.com.ocr.RemoteRun;
import mobilcom.com.example.com.translation.Translator;

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
    CheckBox chkbox;

    Spinner spinnerFrom;
    Spinner spinnerTo;

    String recognized_text = "";
    String translated_text = "";

    Offloading offload;
    LocalRun localrun;
    Translator translator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if( bundle != null) {
            //TODO Probleme mit Kamera und
            // Intent bezieht sich auf Kamera
            //img = (Bitmap) intent.getExtras().get("img");

            //TODO Bild muss mit View verknüpft werden
            imgpath = new File((String) bundle.get("imgpath"));
            iv = (ImageView) findViewById(R.id.imageEdit);
            img = BitmapFactory.decodeFile(imgpath.getAbsolutePath());
            iv.setImageBitmap(img);

            //set Spinners
            spinnerFrom = (Spinner) findViewById(R.id.lang_from);
            spinnerTo = (Spinner) findViewById(R.id.lang_to);
        }
        //getAlbumStorageDir();
        //File dir =new File(context.getFilesDir());
    }

    public boolean onCreateOptionsMenue ( Menu menu){

//		getMenuInflater().inflate(R.menu.edit, menu);
        return true;
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
        cfrom = langresolve(spinnerFrom);
        cto = langresolve(spinnerTo);
        chkbox = (CheckBox) findViewById(R.id.btn_offloading_enabled);
        offloading = chkbox.isChecked();

        if(offloading) {
            offload = new Offloading(imgpath, getString(R.string.Fu_URL), this, SystemClock.elapsedRealtime(), cfrom, cto);
            Thread thread = new Thread(offload);
            thread.start();

            while(thread.isAlive()) { }     // Busy waiting

            recognized_text = offload.getRecognized();
            translated_text = offload.getTranslated();

            // STATT TOAST MÜSSEN DIE ERGEBNISSE NUN INS NÄCHSTE FENSTER GEPUSHT WERDEN
            Toast.makeText(Edit.this, "R: "+recognized_text+" : "+translated_text, Toast.LENGTH_LONG).show();
        }else {
            localrun = new LocalRun();
            translator = new Translator(this);

            recognized_text = localrun.recognised(imgpath,cfrom.toString());
            if(cto != null) {
                translated_text = translator.translate(recognized_text,cfrom,cto);
            }else translated_text = "";

            // STATT TOAST MÜSSEN DIE ERGEBNISSE NUN INS NÄCHSTE FENSTER GEPUSHT WERDEN
            Toast.makeText(Edit.this, "L: "+recognized_text+" : "+translated_text, Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(Edit.this, "You pressed the button runOCR", Toast.LENGTH_LONG).show();
    }

    public Language langresolve(Spinner spinner){
        String langString = spinner.getSelectedItem().toString();
        Language lang = null;
        switch(langString){
            case "German":
                lang = Language.GERMAN;
                break;
            case "English":
                lang = Language.ENGLISH;
                break;
            case "French":
                lang = Language.FRENCH;
                break;
            case "Italian":
                lang = Language.ITALIAN;
                break;
            case "Spanish":
                lang = Language.SPANISH;
                break;
            case "none":
                lang = null;
        }
        return lang;


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

