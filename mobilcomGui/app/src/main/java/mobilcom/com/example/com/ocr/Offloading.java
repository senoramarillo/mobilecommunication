package mobilcom.com.example.com.ocr;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;

/**
 * Created by wangqs on 10/1/14.
 *
 *
 * Diese Klasse wurde aus dem ursprünglichen Offloading-Projekt übernommen und von uns angepasst
 * angepasst.
 *
 */

public class Offloading implements Runnable {

    private String temp_received;
    private String temp_recognized;
    private long   offloading_period;
    private File image;
    private String webURL;
    private Context current;
    private String notes;
    private long StartTime;
    private boolean Network_State;

    public Offloading(File f, String web, Context c, long s, boolean offloading){
        this.image = f;
        this.webURL = web;
        this.current = c;
        this.StartTime = s;
        this.notes = null;
        this.Network_State = false;

    }

    public void run() {

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND);
        RemoteRun remoterun = new RemoteRun();

        if(remoterun.checkNetworkInfo()) {

            temp_received = null;
            temp_recognized = "Offloading has started";

            try {
                temp_received = remoterun.run(image, webURL, current, StartTime);
                GetElement getelement = new GetElement();
                try {
                    temp_recognized = getelement.getElementValueFromXML(temp_received, "Identifiedtext");
                    String server_received_time = getelement.getElementValueFromXML(temp_received, "ReceivedTime");
                    String server_return_finish_time = getelement.getElementValueFromXML(temp_received, "ReturnFinish");
                    offloading_period = Long.parseLong(server_return_finish_time) - Long.parseLong(server_received_time);


                } catch (Exception e) {
                    notes = "Offloading Get Element Failure";

                    temp_recognized = "Offloading Content Catch Failure";
                }

            } catch (Exception e) {
                notes = "Offloading Recognition Failure";
                Looper.prepare();
                Toast toast_o_1 = Toast.makeText(current, notes, Toast.LENGTH_SHORT);
                toast_o_1.show();
                Looper.loop();
                temp_received = "Offloading Failure";

                temp_recognized = temp_received;
            }
        }else{
            temp_recognized = "No Network";
        }
    }

    public String content_get(){
        return temp_recognized;
    }

    public long period_get(){
        return offloading_period;
    }

}
