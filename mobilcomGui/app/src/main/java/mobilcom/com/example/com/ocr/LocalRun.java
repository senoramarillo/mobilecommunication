package mobilcom.com.example.com.ocr;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

/**
 * Created by christianbruns on 14.01.15.
 */
public class LocalRun {
    private String recognised_text;

    public String recognised(File image){

        TessBaseAPI baseAPI = new TessBaseAPI();
        baseAPI.init("/mnt/sdcard/","eng");         //database directory is "/mnt/sdcard/"
        baseAPI.setImage(image);
        recognised_text = baseAPI.getUTF8Text();
        baseAPI.clear();
        baseAPI.end();
        return recognised_text;
    }
}
