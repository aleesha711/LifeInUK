package ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tecjaunt.www.lifeinuk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Org.Tools.TestQuestion;
import Org.Utility.Globals;
import UtilityClasses.ConnectionDetector;
import UtilityClasses.Constants;
import UtilityClasses.GetDataFromWebservice;

public class SplashScreen extends AppCompatActivity {


    ConnectionDetector cd;
    Boolean con=true;
    Boolean flage=false;
    Thread thread;
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "IN SPLASH");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("Aleesha event");

        cd=new ConnectionDetector(this);
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();
        Globals.editor.putInt("Answer",0);
        Globals.editor.putInt("Correct",0);
        Globals.autoNext=Globals.pref.getBoolean("AutoNext",false);

      //  reverseTimer(20,tv_time);

        //insertInDB();
        //Toast.makeText(this, Globals.glist.get(1).getQuestion(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,md.get(0).toString() , Toast.LENGTH_SHORT).show();
        boolean lang=Globals.pref.getBoolean("flag", false);
        if (lang == false){
            Globals.language = "English";
            Globals.editor.putString("language", "English");
            Globals.editor.putBoolean("flag",true);
            Globals.Englishlist=new ArrayList<>();
            Globals.Spanishlist=new ArrayList<>();
            Globals.Polishlist=new ArrayList<>();
            Globals.Urdulist=new ArrayList<>();
            Globals.Farsilist=new ArrayList<>();
            Globals.Arabiclist=new ArrayList<>();
            Globals.editor.putString("TJSONArray","[]");
            Globals.editor.putString("PJSONArray","[]");

            Globals.editor.putString("EnglishJSONArray", "[]");
            Globals.editor.putString("PolishJSONArray", "[]");
            Globals.editor.putString("SpanishJSONArray", "[]");
            Globals.editor.putString("UrduJSONArray", "[]");
            Globals.editor.putString("ArabicJSONArray", "[]");
            Globals.editor.putString("FarsiJSONArray", "[]");

            //Practice Total Achieved Score
            Globals.editor.putInt("AnswerPrac",0);
            Globals.editor.putInt("CorrectPrac",0);
            //Test Total Achieved Score
            Globals.editor.putInt("AnswerTest",0);
            Globals.editor.putInt("CorrectTest",0);

            //Practice All Category Score
            Globals.editor.putInt("TotalAll",0);
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","Politics");
            Globals.editor.putString("CatHistory","History");
            Globals.editor.putString("CatPopular","Popular Culture");
            Globals.editor.putString("CatOther","Other");
            Globals.editor.putString("CatMCQs","Multiple Choice");
            Globals.editor.putString("CatTF","True False");
            Globals.editor.putString("CatSt","Statements");
            Globals.editor.putInt("AnswerAll",0);
            Globals.editor.putInt("CorrectAll",0);

            //Practice Politics Score

            Globals.editor.putInt("TotalPoli",0);
            Globals.editor.putInt("AnswerPoli",0);
            Globals.editor.putInt("CorrectPoli",0);

            //Practice Law Score
            Globals.editor.putInt("TotalLaw",0);
            Globals.editor.putInt("AnswerLaw",0);
            Globals.editor.putInt("CorrectLaw",0);

            //Practice History Score
            Globals.editor.putInt("TotalHistory",0);
            Globals.editor.putInt("AnswerHistory",0);
            Globals.editor.putInt("CorrectHistory",0);

            //Practice Popular Culture Score
            Globals.editor.putInt("TotalPop",0);
            Globals.editor.putInt("AnswerPop",0);
            Globals.editor.putInt("CorrectPop",0);

            //Practice Other Score
            Globals.editor.putInt("TotalOther",0);
            Globals.editor.putInt("AnswerOther",0);
            Globals.editor.putInt("CorrectOther",0);

            //Practice MCQs Score
            Globals.editor.putInt("TotalMC",0);
            Globals.editor.putInt("AnswerMC",0);
            Globals.editor.putInt("CorrectMC",0);

            //Practice True/False Score
            Globals.editor.putInt("TotalTF",0);
            Globals.editor.putInt("AnswerTF",0);
            Globals.editor.putInt("CorrectTF",0);

            //Practice Statements Score
            Globals.editor.putInt("TotalSt",0);
            Globals.editor.putInt("AnswerSt",0);
            Globals.editor.putInt("CorrectSt",0);;

            //Test Last Achieved Score
            Globals.editor.putInt("AnswerTLA",0);
            Globals.editor.putInt("CorrectTLA",0);

            //Practice Last Achieved Score
            Globals.editor.putInt("AnswerPLA",0);
            Globals.editor.putInt("CorrectPLA",0);

            //Auto Next Question
            Globals.editor.putBoolean("AutoNext",false);

            Globals.editor.commit();

            Toast.makeText(this, "Lang : "+ Globals.language, Toast.LENGTH_SHORT).show();


            checkConnecion();


        } else {
            Globals.language=Globals.pref.getString("language","English");
            Toast.makeText(this, "Language : "+ Globals.language, Toast.LENGTH_SHORT).show();
            checkConnecion();


        }

        //boolean foundUrl = false;
        if (getIntent().getExtras() != null) {
            Log.d(TAG,"post execute intent is not null");
            for (String key : getIntent().getExtras().keySet()) {
                final String value = getIntent().getExtras().getString(key);
                //String qwerty = key + " Value: " + value;
                Log.d(TAG, key);
                if (key.contains("urlAddress")){
                    getIntent().removeExtra(key);

                    //if (foundUrl == false) {
                    Log.d(TAG, "found url");
                    //Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(value));
                    //startActivity(intent);

                    Thread timer = new Thread() {
                        public void run(){
                            try {

                                sleep(3000);

                                Intent i = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(value));
                                startActivity(i);
                                finish();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    timer.start();

                    //}
                    //foundUrl = true;
                    //open webpage
                }

                //Log.d(TAG, qwerty);
            }
        }



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
         else {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            Log.d("Tag", "1");
            List<Address> addresses;

            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0)

                {
                    Log.e("cityname",addresses.get(0).getLocality().toString());
                    Constants.CITY = addresses.get(0).getLocality().toString();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    private class ReadingData extends AsyncTask<String, String, String> {

        private ProgressDialog pdia;
        private String language;
        private Boolean flag = false;

        public ReadingData(String language) {
            this.language = language;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Progress to show reading data process
            pdia = new ProgressDialog(SplashScreen.this);
            pdia.setMessage("Loading Data...");
            pdia.setCancelable(false);
            pdia.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            return GetDataFromWebservice.GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (pdia.isShowing()) {
                pdia.dismiss();
            }



            //if (foundUrl == false){
                JSONObject mainObject = null;
                JSONArray mainArray = null;
                JSONArray localArray = null;
                JSONObject arrayObject = null;
                //Toast.makeText(getApplicationContext()," Before Try "+ result, Toast.LENGTH_SHORT).show();
                try {
                    mainObject = new JSONObject(result);
                    mainArray = mainObject.getJSONArray("Questions");
                    //Toast.makeText(getApplicationContext()," In Try ", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), mainObject.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), mainArray.toString(), Toast.LENGTH_SHORT).show();
                    int size = mainArray.length();
                    //Globals.glist=new ArrayList<>();
                    //Toast.makeText(getActivity(),"  "+ mainArray.length(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(),"  "+ size, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"  "+ mainArray.getJSONObject(0).toString(), Toast.LENGTH_SHORT).show();

                    if (language.equalsIgnoreCase("English")) {
                        JSONArray arrSize = new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
                        if (size > 0 && !mainArray.toString().equalsIgnoreCase(arrSize.toString())) {
                            Globals.editor.putString("EnglishJSONArray", mainArray.toString()).commit();
                            flag = true;
                            localArray = new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
                        }

                    } else if (language.equalsIgnoreCase("Polish")) {
                        JSONArray arrSize = new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
                        if (size > 0 && !mainArray.toString().equalsIgnoreCase(arrSize.toString())) {
                            Globals.editor.putString("PolishJSONArray", mainArray.toString()).commit();
                            flag = true;
                            localArray = new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
                        }

                    } else if (language.equalsIgnoreCase("Spanish")) {
                        JSONArray arrSize = new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
                        if (size > 0 && !mainArray.toString().equalsIgnoreCase(arrSize.toString())) {
                            Globals.editor.putString("SpanishJSONArray", mainArray.toString()).commit();
                            flag = true;
                            localArray = new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
                        }

                    } else if (language.equalsIgnoreCase("Urdu")) {
                        JSONArray arrSize = new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
                        if (size > 0 && !mainArray.toString().equalsIgnoreCase(arrSize.toString())) {
                            Globals.editor.putString("UrduJSONArray", mainArray.toString()).commit();
                            flag = true;
                            localArray = new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
                        }

                    } else if (language.equalsIgnoreCase("Arabic")) {
                        JSONArray arrSize = new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
                        if (size > 0 && !mainArray.toString().equalsIgnoreCase(arrSize.toString())) {
                            Globals.editor.putString("ArabicJSONArray", mainArray.toString()).commit();
                            flag = true;
                            localArray = new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
                        }

                    } else if (language.equalsIgnoreCase("Farsi")) {
                        JSONArray arrSize = new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
                        if (size > 0 && !mainArray.toString().equalsIgnoreCase(arrSize.toString())) {
                            Globals.editor.putString("FarsiJSONArray", mainArray.toString()).commit();
                            flag = true;
                            localArray = new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
                        }
                    }

                    if (flag) {
                        con = setData(localArray, language);
                    }
                    if(language.equalsIgnoreCase("Arabic")) {

                        thread = new Thread() {
                            @Override
                            public void run() {
                                try {

                                    sleep(1000);
                                    Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                                    startActivity(intent);
                                    finish();

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                    }
                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(),e.toString()+language, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Unable To Access Data of " + language, Toast.LENGTH_LONG).show();
                    con = false;
                    if(language.equalsIgnoreCase("Arabic")) {

                        thread = new Thread() {
                            @Override
                            public void run() {
                                try {

                                    sleep(1000);
                                    Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                                    startActivity(intent);
                                    finish();

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                    }
                }
            //}



        }
    }

    public boolean checkConnecion() {
        //Checking Internet Connectivity
        Globals.Englishlist=new ArrayList<>();
        Globals.Spanishlist=new ArrayList<>();
        Globals.Polishlist=new ArrayList<>();
        Globals.Urdulist=new ArrayList<>();
        Globals.Arabiclist=new ArrayList<>();
        Globals.Farsilist=new ArrayList<>();
        if (cd.isConnectingToInternet()) {
            for(int i=0;i<6;i++) {
                if(i==0) {
                    new SplashScreen.ReadingData("English").execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=English" + "&access=" + Globals.access);
                }else if(i==1){
                    new SplashScreen.ReadingData("Polish").execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=Polish" + "&access=" + Globals.access);
                }else if(i==2){
                    new SplashScreen.ReadingData("Spanish").execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=Spanish" + "&access=" + Globals.access);
                }else if(i==3){
                    new SplashScreen.ReadingData("Urdu").execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=Urdu" + "&access=" + Globals.access);
                }else if(i==4){
                    new SplashScreen.ReadingData("Farsi").execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=Farsi" + "&access=" + Globals.access);
                }else if(i==5){
                    new SplashScreen.ReadingData("Arabic").execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=Arabic" + "&access=" + Globals.access);
                }
            }
            con=true;
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            for(int i=0;i<6;i++) {
                if(i==0) {
                    JSONArray arr= null;
                    try {
                        arr = new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Unable To Access Data of English", Toast.LENGTH_LONG).show();
                    }
                    setData(arr,"English");
                }else if(i==1){
                    JSONArray arr= null;
                    try {
                        arr = new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Unable To Access Data of Polish", Toast.LENGTH_LONG).show();
                    }
                    setData(arr,"Polish");
                }else if(i==2){
                    JSONArray arr= null;
                    try {
                        arr = new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Unable To Access Data of Spanish", Toast.LENGTH_LONG).show();
                    }
                    setData(arr,"Spanish");
                }else if(i==3){
                    JSONArray arr= null;
                    try {
                        arr = new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Unable To Access Data of Urdu", Toast.LENGTH_LONG).show();
                    }
                    setData(arr,"Urdu");
                }else if(i==4){
                    JSONArray arr= null;
                    try {
                        arr = new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Unable To Access Data of Farsi", Toast.LENGTH_LONG).show();
                    }
                    setData(arr,"Farsi");
                }else if(i==5){
                    JSONArray arr= null;
                    try {
                        arr = new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Unable To Access Data of Arabic", Toast.LENGTH_LONG).show();
                    }
                    setData(arr,"Arabic");
                }
            }
            thread = new Thread() {
                @Override
                public void run() {
                    try {

                        sleep(1000);
                        Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            con=false;
            return false;
        }
    }

    private boolean setData(JSONArray arr, String language){
        JSONArray localArray=arr;
        ArrayList<TestQuestion> arrayList=new ArrayList<>();
        int size = localArray.length();
        try {
            for (int i = 0; i < size; i++) {
                JSONObject arrayObject = localArray.getJSONObject(i);
                String question = arrayObject.getString("question");
                String option1 = arrayObject.getString("op_a");
                String option2 = arrayObject.getString("op_b");
                String option3 = arrayObject.getString("op_c");
                String option4 = arrayObject.getString("op_d");
                String category1 = arrayObject.getString("cat_1");
                String category2 = arrayObject.getString("cat_2");
                String answer = arrayObject.getString("ans");
                String explain = arrayObject.getString("exp");
                String test_id = arrayObject.getString("test_id");
                //Toast.makeText(this, "test_id : " + test_id, Toast.LENGTH_SHORT).show();

                if (language.equalsIgnoreCase("English")) {
                    if (Globals.Englishlist.isEmpty()) {
                        arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false,Integer.parseInt(test_id)));
                    }
                } else if (language.equalsIgnoreCase("Polish")) {
                    if (Globals.Polishlist.isEmpty()) {
                        arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false,Integer.parseInt(test_id)));
                    }
                } else if (language.equalsIgnoreCase("Spanish")) {
                    if (Globals.Spanishlist.isEmpty()) {
                        arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false,Integer.parseInt(test_id)));
                    }
                } else if (language.equalsIgnoreCase("Urdu")) {
                    if (Globals.Urdulist.isEmpty()) {
                        arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false,Integer.parseInt(test_id)));
                    }
                } else if (language.equalsIgnoreCase("Arabic")) {
                    if (Globals.Arabiclist.isEmpty()) {
                        arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false,Integer.parseInt(test_id)));
                    }
                } else if (language.equalsIgnoreCase("Farsi")) {
                    if (Globals.Farsilist.isEmpty()) {
                        arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false,Integer.parseInt(test_id)));
                    }
                }
            }
            if (language.equalsIgnoreCase("English")) {
                if (Globals.Englishlist.isEmpty()) {
                    Globals.Englishlist = arrayList;
                   // Toast.makeText(getApplicationContext(), " English " + (Integer) Globals.Englishlist.size(), Toast.LENGTH_SHORT).show();
                }
            } else if (language.equalsIgnoreCase("Polish")) {
                if (Globals.Polishlist.isEmpty()) {
                    Globals.Polishlist = arrayList;
                  //  Toast.makeText(getApplicationContext(), " Polish " + (Integer) Globals.Polishlist.size(), Toast.LENGTH_SHORT).show();
                }
            } else if (language.equalsIgnoreCase("Spanish")) {
                if (Globals.Spanishlist.isEmpty()) {
                    Globals.Spanishlist = arrayList;
                    //Toast.makeText(getApplicationContext(), " Spanish " + (Integer) Globals.Spanishlist.size(), Toast.LENGTH_SHORT).show();
                }
            } else if (language.equalsIgnoreCase("Urdu")) {
                if (Globals.Urdulist.isEmpty()) {
                    Globals.Urdulist = arrayList;
                    //Toast.makeText(getApplicationContext(), " Urdu " + (Integer) Globals.Urdulist.size(), Toast.LENGTH_SHORT).show();
                }
            } else if (language.equalsIgnoreCase("Arabic")) {
                if (Globals.Arabiclist.isEmpty()) {
                    Globals.Arabiclist = arrayList;
                    //Toast.makeText(getApplicationContext(), " Arabic " + (Integer) Globals.Arabiclist.size(), Toast.LENGTH_SHORT).show();
                }
            } else if (language.equalsIgnoreCase("Farsi")) {
                if (Globals.Farsilist.isEmpty()) {
                    Globals.Farsilist = arrayList;
                   // Toast.makeText(getApplicationContext(), " Farsi " + (Integer) Globals.Farsilist.size(), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }catch (JSONException e) {
            //Toast.makeText(getApplicationContext(),e.toString()+Globals.language, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
