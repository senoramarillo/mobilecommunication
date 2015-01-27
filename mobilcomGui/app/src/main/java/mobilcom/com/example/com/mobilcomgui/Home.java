package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.memetix.mst.language.Language;

import mobilcom.com.example.com.ocr.LocalRun;
import mobilcom.com.example.com.translation.Translator;


public class Home extends Activity {

    private static int IMAGE_CAPTURE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Translator translator = new Translator(this);
        String text = translator.translate("Hallo", Language.GERMAN, Language.ENGLISH);

    }


    public void navigateLoadImage(View view) {
        Intent i = new Intent(this, Load_Pic.class);
        startActivity(i);
    }

    //@TargetApi(8)
    public void takePicture(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_REQUEST);
    }


    //TODO ActivityNoResult for Camera


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST) {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            //TODO direkt speichern nach Foto machen , URL übergeben an den Intent
            Intent i = new Intent(this, Edit.class);
            i.putExtra("img", theImage);
            startActivity(i);
            Toast.makeText(Home.this, "Picture saved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Operation Failed", Toast.LENGTH_LONG).show();
        }
    }
}


