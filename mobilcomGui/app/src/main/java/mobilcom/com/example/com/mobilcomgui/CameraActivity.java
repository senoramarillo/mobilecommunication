package mobilcom.com.example.com.mobilcomgui;

/**
 * Die Camera Activity hat zwei Buttons: Take Picture und abbrechen. Fokussieren erfolgt automatisch
 * Bilder werden abgespeichert: momentan sind die Bilder im Pfad sdcard0/DCIM/ zu finden
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;
    private int counter = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Beide Views camerapreview, control werden aufgerufen und angezeigt
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.control, null);
        LayoutParams layoutParamsControl = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);
    }

    public void takePicture(View view) {
        camera.takePicture(myShutterCallback, myPictureCallback_RAW, myPictureCallback_JPG);
    }

    ShutterCallback myShutterCallback = new ShutterCallback() {

        @Override
        public void onShutter() {

        }
    };

    PictureCallback myPictureCallback_RAW = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {

        }
    };

    PictureCallback myPictureCallback_JPG = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            try {
                //Methode savePicture wird aufgerufen
                savePicture(arg0);
                //Intent i = new Intent(CameraActivity.this, Edit.class);
                //startActivity(i);
            }
            catch (Exception except) {
                except.getMessage();
                // Log.d("CameraActivity , scanButton.setOnClickListener(): exception = ", exc.getMessage());
            }
            finally
            {
                camera.stopPreview();
                //start previewing again on the SurfaceView in case use wants to take another pic/scan
                camera.startPreview();
            }
        }
    };

    //Speicherung der Bilder
    public void savePicture(byte[] data) throws IOException {
        Bitmap imageCamera = null;
        //Bezeichnung der Bilder
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + counter + "_" + date + ".jpg";

        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String filename = sdDir + File.separator + photoFile;
        File pictureFile = new File(filename);

        try {
            //Liefert bitmap von Kamerabild
            imageCamera = BitmapFactory.decodeByteArray(data, 0, data.length);

            Bitmap bm;
            //gespeichertes Bild wird hier gedreht. Anderenfalls wird das Bild verkehrt herum gespeichert
            bm = rotateImage(imageCamera, -270);

            //Speichern der Bilder
            FileOutputStream fos = new FileOutputStream(pictureFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bm.compress(CompressFormat.JPEG, 100, bos);

            bos.write(data);
            bos.close();
            Toast.makeText(this, "Image saved:" + photoFile, Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Log.d("File not saved: ", error.getMessage());
            Toast.makeText(this, "Image could not be saved: "+ error.getMessage(), Toast.LENGTH_LONG).show();
        }
        counter++;
    }



    //Zum Rotieren für die gespeicherten Bilder
    public static Bitmap rotateImage(Bitmap src, float degree)
    {
        Matrix matrix = new Matrix();
        //Rotationsgrad wird festgelegt
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (previewing) {
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();

        //Für die Orientierung der Kamera zuständig
        setCameraDisplayOrientation(CameraActivity.this, CameraInfo.CAMERA_FACING_BACK, camera);
        Camera.Parameters params = camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(params);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    // Zuständig für die Rotation der Kamera
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    public void finishActivity(View v){
        finish();
    }
}