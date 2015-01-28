package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.memetix.mst.language.Language;

import java.io.File;

import mobilcom.com.example.com.ocr.LocalRun;
import mobilcom.com.example.com.ocr.Offloading;
import mobilcom.com.example.com.ocr.RemoteRun;
import mobilcom.com.example.com.translation.Translator;


public class Home extends Activity {

    private static int IMAGE_CAPTURE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        testing();

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


    /**
     * Diese Methode dient dem Testen und der Veranschaulichung der Benutzung von Tesseract und dem
     * Translator. Damit ocr (in dieser Methode) funktioniert, müssen auf der SD-Card des Telefons zwei Bilddateien sein:
     * ein Tiff namens "test.tif" und ein JPG names "test.jpg". Wenn OCR und der Translator funktionieren
     * und die Verbindung zur GUI komplett hergestellt wurde, kann diese Methode ersatzlos gelöscht werden.
     * OCR lokal, also ohne offloadingm sollte problemlos mit JPGs funktionieren. Der Server, also mit
     * offloading, kann glaube ich, bis jetzt nur mit TIFFs umgehen.
     */

    private void testing() {

        // OCR mit Offloading (Hierfür muss in der strings.xml die richtige URL zum Server eingetragen sein):

        // Bild von der sdcard einlesen (ggf. Name ändern):
        File image = new File("/mnt/sdcard/test.tif");

        // Offloading-Objekt als Thread starten
        Offloading remoteRun = new Offloading(image, getString(R.string.Fu_URL), this, SystemClock.elapsedRealtime(), Language.ENGLISH, Language.GERMAN);
        Thread thread = new Thread(remoteRun);
        thread.start();

        // Solange der Thread mit dem Verbindungsaufbau und dem Empfangen des Ergebnisses beschäftigt ist,
        // wird gewartet
        while(thread.isAlive()) {
            // do nothing
        }

        // Ergebnis auslesen
        String ocrOffloading = remoteRun.getRecognized();

        // Translator-Objekt zum übersetzen (braucht für die Datenbank "this" als Kontext)
        Translator translator = new Translator(this);

        // mit der translate()-Funktion wird der Text übsetzt
        String translatetText = translator.translate(ocrOffloading ,Language.ENGLISH, Language.GERMAN);


        // OCR ohne offloading (Funktioniert NUR mit JPGs und BMP-encoded Files)

        // Bild von der sdcard einlesen (ggf. Name ändern):
        File imageJPG = new File("/mnt/sdcard/test.jpg");

        // LocalRun-Objekt
        LocalRun localRun = new LocalRun();

        // Methode zum Texterkennen
        String ocrOffline = localRun.recognised(imageJPG, "en");


    }
}


