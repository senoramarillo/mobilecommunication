package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;

import com.memetix.mst.language.Language;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import mobilcom.com.example.com.ocr.LocalRun;
import mobilcom.com.example.com.ocr.Offloading;
import mobilcom.com.example.com.translation.Translator;


public class Home extends Activity {

    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void navigateLoadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    //@TargetApi(8)
    public void takePicture(View view) {
        //Hier wird die eigene Kamera verwendet
        Intent i = new Intent(this, CameraActivity.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String imgpath = getImagePath(data.getData());
            Intent intent = new Intent(this, Edit.class);
            intent.putExtra("imgpath", imgpath);
            startActivity(intent);
        }
        else if (stream != null)
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else {
            //Toast.makeText(getApplicationContext(), "Operation aborted", Toast.LENGTH_LONG).show();
        }
    }

    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
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