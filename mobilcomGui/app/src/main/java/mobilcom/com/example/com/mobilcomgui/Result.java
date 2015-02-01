package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.memetix.mst.language.Language;

import java.io.File;

import mobilcom.com.example.com.ocr.Offloading;
import mobilcom.com.example.com.translation.Translator;

/**
 * Created by Malte on 28.12.2014.
 */
public class Result extends Activity {
    private Button btn_tranlate;
    private EditText editText_original;
    private EditText editText_translated;
    private String recognized_text = "";
    private String translated_text = "";
    private Language cfrom;
    private Language cto;
    private File imgpath;

    private Translator translator;
    private Offloading offload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();

        btn_tranlate = (Button)findViewById(R.id.btn_translate_again);
        btn_tranlate = (Button)findViewById(R.id.btn_translate_again);
        editText_original = (EditText)findViewById(R.id.editText1);
        editText_translated = (EditText)findViewById(R.id.editText2);


        if(bundle!=null) {
            recognized_text = (String) bundle.get("recognizedtext");
            translated_text = (String) bundle.get("translatedtext");
            imgpath = new File((String) bundle.get("imgpath"));
            cfrom = Language.fromString((String)bundle.get("cfrom"));
            String tmp_cto = ((String)bundle.get("cto"));
            if(tmp_cto.equals("none")) {
                cto = null;
            }else {
                cto = Language.fromString(tmp_cto);
            }
            setEditTextFields();
        }
    }

    public boolean onCreateOptionsMenue ( Menu menu){

//		getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }


    public void repeatWithOffloading(View v){
        offload = new Offloading(imgpath, getString(R.string.Fu_URL), this, SystemClock.elapsedRealtime(), cfrom, cto);
        Thread thread = new Thread(offload);
        thread.start();

        while(thread.isAlive()) { }     // Busy waiting

        recognized_text = offload.getRecognized();
        translated_text = offload.getTranslated();
        setEditTextFields();
        Toast.makeText(Result.this,"Offloading complete", Toast.LENGTH_LONG).show();

    }

    public void nextPic(View v){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void translateResultText(View v){
        if(cto != null) {
            if(!recognized_text.equals("")) {
                translator = new Translator(this);
                translated_text = translator.translate(recognized_text,cfrom,cto);
                editText_translated.setText(translated_text);
                Toast.makeText(Result.this,"Re-translation complete", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void setEditTextFields() {
        editText_original.setText(recognized_text);
        editText_translated.setText(translated_text);
    }

}

