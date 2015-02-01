package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Malte on 28.12.2014.
 */
public class Result extends Activity {
    private Button btn_tranlate;
    private EditText editText_original;
    private EditText editText_translated;
    private String recognized_text;
    private String translated_text;
    private File imgpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        //TODO fill textboxes
        btn_tranlate = (Button)findViewById(R.id.btn_translate_again);
        editText_original = (EditText)findViewById(R.id.editText1);
        editText_translated = (EditText)findViewById(R.id.editText2);


        if(bundle!=null) {
            recognized_text = (String) bundle.get("recognizedtext");
            translated_text = (String) bundle.get("translatedtext");
            imgpath = new File((String) bundle.get("imgpath"));
            editText_original.setText(recognized_text);
            editText_translated.setText(translated_text);
        }

        btn_tranlate = (Button)findViewById(R.id.btn_translate_again);

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

    public void translateResultText(View v){
        //TODO translates the Resulttext
    }

}

