# Mobile Communcation Project

Then offloading or local method from the existing engine can be used to recognize the picture.
Parameters for offloading should be part of the GUI.
To run an example, the offloading engine should be downloaded:
https://www.mi.fu-berlin.de/offload/downloads/

* Username: katinka
* Password: admin

Visit our OCR web page to understand the interface between mobile client and server.
Upload an image to [offload/ocr](https://www.mi.fu-berlin.de/offload/ocr/)
Device Type: mobile / httpclient

# Organisation
geplante Organisation:
* Über git
* Ablauf anfertigen
* Einarbeitung ins Thema und Quellcode 
* API Kamera Android einarbeiten 
* main window erzeugen
* grobstruktur festlegen
* Altes Projekt in das neue Projekt einbinden 


# Funktionalität Kamera erzeugen: 
* libary suchen
* auto focus 
* user focus 


#Speicherung der Bilder: momentaner Pfad (../sdcard0/DCIM/)
* Buttons erzeugen
* Schön machen

#Work-offloading 
* Code muss noch in das Projekt eingebunden werden.
* https://github.com/carlosmn/work-offload

#Tesseract Language Files 

Damit tesseract lokal funktioniert müsst ihr noch die Sprachdateien runterladen:
"[...] Data files must be extracted to a subdirectory named tessdata." - https://github.com/rmtheis/tess-two

In unserem Fall: auf der SDCard des Handys ein Verzeichnis "tessdata" erstellen und da dann die Language-Files rein.
Die Files gibt's hier: https://code.google.com/p/tesseract-ocr/downloads/list
#Pfadname: "/storage/sdcard/tessdata/"

# Fragen 
* The shape of the window can be changed by moving the bottom, the right side or the
lower right corner of the window

# Probleme mit build.gradle

<p>Bei app/build.gradle folgendes bei dependencies korrigieren:

Pfad muss für die Bibliotheken angepasst werden. Projekt schliessen und dann Android Studio neustarten.</p>
<pre><code>dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:21.0.3'
    compile project(':libs:tesstwo')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/commons-codec-1.6.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/commons-logging-1.1.3.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/fluent-hc-4.3.6.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/httpclient-4.3.6.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/httpclient-cache-4.3.6.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/httpcore-4.3.3.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/httpmime-4.3.6.jar')
    compile files('C:/xx/mobilecommunication/mobilcomGui/libs/microsoft-translator-java-api-0.6.2-jar-with-dependencies.jar')
}</code></pre>

