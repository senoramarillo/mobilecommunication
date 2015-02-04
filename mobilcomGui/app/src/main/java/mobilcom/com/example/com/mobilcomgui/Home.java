package mobilcom.com.example.com.mobilcomgui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.memetix.mst.language.Language;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import mobilcom.com.example.com.ocr.LocalRun;
import mobilcom.com.example.com.ocr.Offloading;
import mobilcom.com.example.com.translation.Translator;


public class Home extends Activity {

    private static final int REQUEST_CODE = 1;

    //für die Standard Kamera
    //private static int IMAGE_CAPTURE_REQUEST = 2;

    private Bitmap bitmap;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }


    public void navigateLoadImage(View view) {
        //Intent i = new Intent(this, Load_Pic.class);
        //startActivity(i);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    //@TargetApi(8)
    public void takePicture(View view) {
        //Verwendung der Standardkamera
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, IMAGE_CAPTURE_REQUEST);

        //Hier wird die eigene Kamera verwendet
        Intent i = new Intent(this, CameraActivity.class);
        startActivity(i);
        //Toast.makeText(Home.this, "Kamera öffnen", Toast.LENGTH_LONG).show();
    }


    //TODO ActivityNoResult for Camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // recyle unused bitmaps
            //if (bitmap != null) {
            //    bitmap.recycle();
            //}
            //TODO copy and save file in img directory of the app and get the file path
            //stream = getContentResolver().openInputStream(data.getData());
            //bitmap = BitmapFactory.decodeStream(stream);

            //Anzeigen des ausgewählten Bildes in Home Activity
            //imageView = (ImageView) findViewById(R.id.result);
            //imageView.setImageBitmap(bitmap);
            String imgpath = getImagePath(data.getData());




            Intent intent = new Intent(this, Edit.class);
            //intent.putExtra("img", bitmap);
            intent.putExtra("imgpath", imgpath);
            //intent.fillIn(data, Intent.FILL_IN_DATA);
            startActivity(intent);

            //Use with fillIn(Intent, int) to allow the current data or type value overwritten, even if it is already set.
            //intent.fillIn(data, Intent.FILL_IN_DATA);
            //intent.putExtra("data", bitmap);
            //startActivity(intent);

        }
        else if (stream != null)
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        /* bezieht sich auf Standard Kamera
        else if (requestCode == IMAGE_CAPTURE_REQUEST) {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            Intent i = new Intent(this, Edit.class);
            i.putExtra("img", theImage);
            startActivity(i);
            Toast.makeText(Home.this, "Picture saved", Toast.LENGTH_LONG).show();*/
        else {
            Toast.makeText(getApplicationContext(), "Operation aborted", Toast.LENGTH_LONG).show();
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


}


