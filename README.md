# Mobile Communication Project (last update: May 2015)

Then offloading or local method from the existing engine can be used to recognize the picture.
Parameters for offloading should be part of the GUI.
To run an example, the offloading engine should be downloaded:
<code>https://www.mi.fu-berlin.de/offload/downloads/</code>

* Username: katinka
* Password: admin

Visit our OCR web page to understand the interface between mobile client and server.
Upload an image to [offload/ocr](https://www.mi.fu-berlin.de/offload/ocr/)
Device Type: mobile / httpclient

# Git Struktur
* [./app/](mobilcomGui/app/) - Source code
* [./libs/](mobilcomGui/libs) - Libraries

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
* Pfadname: "/storage/sdcard/tessdata/" oder "/storage/sdcard/"

# Fragen 
* The shape of the window can be changed by moving the bottom, the right side or the
lower right corner of the window

# Falls Probleme mit build.gradle auftreten

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

# *.apk erstellen

According to Android: Build Unsigned APK with Gradle you can simply build your application with gradle.

In order to do that:

click on the drop down menu on the toolbar at the top (usually with android icon and name of your application)
select Edit configurations
click plus sign at top left corner or press alt+insert
select Gradle
choose your module as Gradle project
in Tasks: enter assemble
press OK
press play
After that you should find your apk in directory ProjectName\build\outputs\apk

# How to fix ''Unfortunately app has stopped'' 
Bei LocalRun.java folgendes anpassen:
* baseAPI.init("/mnt/sdcard/",lang); //database directory is "/mnt/sdcard/" for Nexus 5, 7
* baseAPI.init("/storage/sdcard1/",lang); //for Huawei y300
* In dem Pfad muss sich der Ordner /tessdata befinden

# How to revert commits
* https://www.atlassian.com/git/tutorials/undoing-changes/git-revert

# Off-Topic
* http://www.pcwelt.de/news/Google_Uebersetzer_jetzt_mit_Echtzeit-Uebersetzung-iOS___Android-9520659.html
* http://www.codeproject.com/Tips/840623/Android-Character-Recognition
