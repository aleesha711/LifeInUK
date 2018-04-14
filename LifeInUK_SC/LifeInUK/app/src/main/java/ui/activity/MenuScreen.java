package ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tecjaunt.www.lifeinuk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Org.Tools.TestQuestion;
import Org.Utility.Globals;
import UtilityClasses.ConnectionDetector;
import UtilityClasses.GetDataFromWebservice;
import modules.AnalyticsEventsSender;

public class MenuScreen extends AppCompatActivity implements View.OnClickListener {

    Boolean flag=false;


    ImageView iv_setting;
    TextView tv_title;

    String lang;
    TextView tv_time;
    Button btn_play;
    Button btn_pause;
    Button btn_quit;

    RelativeLayout rl_test;
    RelativeLayout rl_practice;
    RelativeLayout rl_book;
    RelativeLayout rl_progress;
    RelativeLayout rl_language;
    RelativeLayout rl_update;

    Button btn_cancel;
    Button btn_ok;
    ConnectionDetector cd;
    Boolean con=true;
    int btn_count=0;
    public AnalyticsEventsSender sAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        sAnalytics = new AnalyticsEventsSender(this);
       // sAnalytics.sendScreenName("Menu Screen");

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                String qwerty = key + " Value: " + value;
            }
        }

        findViewby();
        if(!Globals.pref.getBoolean("FirstTime",false)){
            Globals.editor.putBoolean("FirstTime",true);
            //checkConnecion();
            setCategoryLanguage();
        }else{
            setCategoryLanguage();
        }

        rl_test.setOnClickListener(this);
        rl_practice.setOnClickListener(this);

        rl_progress.setOnClickListener(this);
        rl_language.setOnClickListener(this);
        rl_update.setOnClickListener(this);
        rl_book.setOnClickListener(this);
        iv_setting.setOnClickListener(this);


        //btn_exit.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                String qwerty = key + " Value: " + value;
            }
        }

        getIntent().getExtras();

        //var test = getIntent().getData();
        Object test = getIntent().getExtras();


    }

    private void findViewby(){

        Globals.menuScreen=this;

        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);
        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);

        rl_test=(RelativeLayout)findViewById(R.id.rl_menu_screen_test);
        rl_practice=(RelativeLayout)findViewById(R.id.rl_menu_screen_practice);

        //rl_category=(RelativeLayout)rootView.findViewById(R.id.rl_menu_screen_category);
        rl_progress=(RelativeLayout)findViewById(R.id.rl_menu_screen_progress);
        rl_language=(RelativeLayout)findViewById(R.id.rl_menu_screen_language);
        rl_book=(RelativeLayout)findViewById(R.id.rl_menu_screen_book);
        rl_update=(RelativeLayout)findViewById(R.id.rl_menu_screen_update);
        tv_time=(TextView)findViewById(R.id.tv_action_bar_timer);
        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);

        iv_setting.setVisibility(View.VISIBLE);
        cd=new ConnectionDetector(this);

        tv_title.setText("HOME");
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_menu_screen_test:
                if(cd.isConnectingToInternet()) {
                    startActivity(new Intent(getApplicationContext(), StartTest.class));
                }else{
                    //Toast.makeText(this, "No Connection Available!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StartTest.class));
                }
                break;
            case R.id.rl_menu_screen_practice:
                    startActivity(new Intent(getApplicationContext(), Category.class));
                break;
            case R.id.rl_menu_screen_update:
                startActivity(new Intent(getApplicationContext(), Update.class));
                break;
            case R.id.rl_menu_screen_book:
                if(cd.isConnectingToInternet()) {
                    startActivity(new Intent(getApplicationContext(), WebView.class));
                }else{
                    Toast.makeText(this, "No Connection Available!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rl_menu_screen_progress:
                startActivity(new Intent(getApplicationContext(), Progress.class));
                break;
            case R.id.iv_action_bar_setting:
                startActivity(new Intent(getApplicationContext(), Settings.class));
                break;
            case R.id.rl_menu_screen_language:
                showLanguages();
                break;
            default:
                break;
        }
    }

    public void showLanguages() {

        RadioGroup rg_language;
        final RadioButton rb_english;
        final RadioButton rb_polish;
        final RadioButton rb_spanish;
        final RadioButton rb_arabic;
        final RadioButton rb_urdu;
        final RadioButton rb_farsi;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_language_selector, null);
        builder.setView(popupView);
        String l=Globals.pref.getString("language",null);
        //Toast.makeText(getApplicationContext(), "language "+ l, Toast.LENGTH_SHORT).show();
        //builder.setCancelable(false);
        rg_language=(RadioGroup)popupView.findViewById(R.id.rg_language_selector);
        rb_english=(RadioButton)popupView.findViewById(R.id.rb_language_selector_english);
        rb_polish=(RadioButton)popupView.findViewById(R.id.rb_language_selector_polish);
        rb_spanish=(RadioButton)popupView.findViewById(R.id.rb_language_selector_spanish);
        rb_urdu=(RadioButton)popupView.findViewById(R.id.rb_language_selector_urdu);
        rb_arabic=(RadioButton)popupView.findViewById(R.id.rb_language_selector_arabic);
        rb_farsi=(RadioButton)popupView.findViewById(R.id.rb_language_selector_farsi);
        Button btn_cancel=(Button)popupView.findViewById(R.id.btn_popup_language_cancel);
        Button btn_ok=(Button)popupView.findViewById(R.id.btn_popup_language_ok);

        if(rb_english.getText().equals(l)){
            rg_language.check(rb_english.getId());
            lang =rb_english.getText().toString();
        }else if(rb_polish.getText().equals(l)){
            rg_language.check(rb_polish.getId());
            lang=rb_polish.getText().toString();
        }else if(rb_spanish.getText().equals(l)){
            rg_language.check(rb_spanish.getId());
            lang=rb_spanish.getText().toString();
        }else if(rb_urdu.getText().equals(l)){
            rg_language.check(rb_urdu.getId());
            lang=rb_urdu.getText().toString();
        }else if(rb_farsi.getText().equals(l)){
            rg_language.check(rb_farsi.getId());
            lang=rb_farsi.getText().toString();
        }else if(rb_arabic.getText().equals(l)){
            rg_language.check(rb_arabic.getId());
            lang=rb_arabic.getText().toString();
        }



        rg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_language_selector_english){
                    lang =rb_english.getText().toString();
                }
                else if(i==R.id.rb_language_selector_polish){
                    lang=rb_polish.getText().toString();
                }
                else if(i==R.id.rb_language_selector_spanish){
                    lang=rb_spanish.getText().toString();
                }
                else if(i==R.id.rb_language_selector_urdu){
                    lang=rb_urdu.getText().toString();
                }
                else if(i==R.id.rb_language_selector_arabic){
                    lang=rb_arabic.getText().toString();
                }
                else if(i==R.id.rb_language_selector_farsi){
                    lang=rb_farsi.getText().toString();
                }
            }

        });

        final AlertDialog dialog = builder.create();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.editor.putString("language", lang);
                Globals.editor.commit();
                Globals.language = lang;

                sAnalytics.sendEvent("Select Language",lang,"selected");
                //checkConnecion();
                setCategoryLanguage();
                dialog.dismiss();
                //Toast.makeText(getApplicationContext(), "Selected Language is "+ Globals.language, Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        //dialog.setCancelable(false);

    }

    public void onBackPressed() {
        if (!flag) {
            showPopup();
        } else {
            super.onBackPressed();
        }
    }

    private void showPopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.popup_exit_confirm, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        btn_cancel=(Button)popupView.findViewById(R.id.btn_popup_exit_no);
        btn_ok=(Button)popupView.findViewById(R.id.btn_popup_exit_yes);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                onBackPressed();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private class ReadingData extends AsyncTask<String, String, String> {

        private ProgressDialog pdia;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Progress to show reading data process
            pdia = new ProgressDialog(MenuScreen.this);
            pdia.setMessage("Loading Data...");
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
            ArrayList<TestQuestion> arrayList=new ArrayList<>();
            JSONObject mainObject=null;
            JSONArray mainArray=null;
            JSONArray localArray=null;
            JSONObject arrayObject=null;
            //Toast.makeText(getApplicationContext()," Before Try ", Toast.LENGTH_SHORT).show();
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

                if(Globals.language.equalsIgnoreCase("English")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.Englishlist=new ArrayList<>();
                        Globals.editor.putString("EnglishJSONArray", mainArray.toString()).commit();
                    }
                    localArray=new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));

                } else if(Globals.language.equalsIgnoreCase("Polish")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.Polishlist=new ArrayList<>();
                        Globals.editor.putString("PolishJSONArray", mainArray.toString()).commit();
                    }
                    localArray=new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));

                } else if(Globals.language.equalsIgnoreCase("Spanish")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.Spanishlist=new ArrayList<>();
                        Globals.editor.putString("SpanishJSONArray", mainArray.toString()).commit();
                    }
                    localArray=new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));

                } else if(Globals.language.equalsIgnoreCase("Urdu")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.Urdulist=new ArrayList<>();
                        Globals.editor.putString("UrduJSONArray", mainArray.toString()).commit();
                    }
                    localArray=new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));

                } else if(Globals.language.equalsIgnoreCase("Arabic")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.Arabiclist=new ArrayList<>();
                        Globals.editor.putString("ArabicJSONArray", mainArray.toString()).commit();
                    }
                    localArray=new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));

                } else if(Globals.language.equalsIgnoreCase("Farsi")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.Farsilist=new ArrayList<>();
                        Globals.editor.putString("FarsiJSONArray", mainArray.toString()).commit();
                    }
                    localArray=new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
                }

                size=localArray.length();
                for(int i=0;i<size;i++){
                    arrayObject = localArray.getJSONObject(i);
                    String question = arrayObject.getString("question");
                    String option1=arrayObject.getString("op_a");
                    String option2=arrayObject.getString("op_b");
                    String option3=arrayObject.getString("op_c");
                    String option4=arrayObject.getString("op_d");
                    String category1=arrayObject.getString("cat_1");
                    String category2=arrayObject.getString("cat_2");
                    String answer=arrayObject.getString("ans");
                    String explain=arrayObject.getString("exp");

                    if(Globals.language.equalsIgnoreCase("English")){
                        if(Globals.Englishlist.isEmpty()) {
                            arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        }

                    } else if(Globals.language.equalsIgnoreCase("Polish")){
                        if(Globals.Polishlist.isEmpty()) {
                            arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        }
                    } else if(Globals.language.equalsIgnoreCase("Spanish")){
                        if(Globals.Spanishlist.isEmpty()) {
                            arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        }
                    } else if(Globals.language.equalsIgnoreCase("Urdu")){
                        if(Globals.Urdulist.isEmpty()) {
                            arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        }
                    } else if(Globals.language.equalsIgnoreCase("Arabic")){
                        if(Globals.Arabiclist.isEmpty()) {
                            arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        }

                    } else if(Globals.language.equalsIgnoreCase("Farsi")){
                        if(Globals.Farsilist.isEmpty()) {
                            arrayList.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        }
                    }
                }
                if(Globals.language.equalsIgnoreCase("English")){
                    if(Globals.Englishlist.isEmpty()) {
                        Globals.Englishlist=arrayList;
                        //Toast.makeText(getApplicationContext(),"  "+ (Integer)Globals.Englishlist.size(), Toast.LENGTH_SHORT).show();
                    }
                } else if(Globals.language.equalsIgnoreCase("Polish")){
                    if(Globals.Polishlist.isEmpty()) {
                        Globals.Polishlist=arrayList;
                    }
                } else if(Globals.language.equalsIgnoreCase("Spanish")){
                    if(Globals.Spanishlist.isEmpty()) {
                        Globals.Spanishlist=arrayList;
                    }
                } else if(Globals.language.equalsIgnoreCase("Urdu")){
                    if(Globals.Urdulist.isEmpty()) {
                        Globals.Urdulist=arrayList;
                    }
                } else if(Globals.language.equalsIgnoreCase("Arabic")){
                    if(Globals.Arabiclist.isEmpty()) {
                        Globals.Arabiclist=arrayList;
                    }
                } else if(Globals.language.equalsIgnoreCase("Farsi")){
                    if(Globals.Farsilist.isEmpty()) {
                        Globals.Farsilist=arrayList;
                    }
                }

                if(arrayList.size()>=0) {

                    Globals.editor.putInt("TotalAll", arrayList.size());
                    int politics=0;
                    int law=0;
                    int history=0;
                    int popular=0;
                    int other=0;
                    int mcqs=0;
                    int tf=0;
                    int st=0;


                    for(int i=0;i<arrayList.size();i++){
                        //Toast.makeText(Category.this, "Loop", Toast.LENGTH_SHORT).show();
                        if (arrayList.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")) {
                            //Toast.makeText(Category.this, "MCQs", Toast.LENGTH_SHORT).show();
                            mcqs++;
                        } else if (arrayList.get(i).getCategory1().equalsIgnoreCase("True/False")) {
                            tf++;
                        } else if (arrayList.get(i).getCategory1().equalsIgnoreCase("Statements")) {
                            st++;
                        }

                        if (arrayList.get(i).getCategory2().equalsIgnoreCase("Politics")) {
                            politics++;
                        } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("History")) {
                            history++;
                        } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("Law")) {
                            law++;
                        } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("Popular Culture")) {
                            popular++;
                        } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("Other")) {
                            other++;
                        }
                    }
                    Globals.editor.putInt("TotalPoli",politics);
                    Globals.editor.putInt("TotalLaw",law);
                    Globals.editor.putInt("TotalHistory",history);
                    Globals.editor.putInt("TotalPop",popular);
                    Globals.editor.putInt("TotalOther",other);
                    Globals.editor.putInt("TotalMC",mcqs);
                    Globals.editor.putInt("TotalTF",tf);
                    Globals.editor.putInt("TotalSt",st);
                    Globals.editor.commit();
                }
                con=true;
            } catch (JSONException e) {
                //Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Unable To Retrive Data of this language", Toast.LENGTH_LONG).show();
                con=false;
            }
        }
    }

    public boolean checkConnecion() {
        //Checking Internet Connectivity
        if (cd.isConnectingToInternet()) {
            new MenuScreen.ReadingData().execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=" + Globals.language + "&access=" + Globals.access);
            con=true;
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            con=false;
            return false;
        }
    }

    private void setCategoryLanguage(){

        ArrayList<TestQuestion> arrayList=new ArrayList<>();

        if(Globals.language.equalsIgnoreCase("English")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of English", Toast.LENGTH_LONG).show();
            }
            setData(arr,"English");
            arrayList=Globals.Englishlist;
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","Politics");
            Globals.editor.putString("CatHistory","History");
            Globals.editor.putString("CatPopular","Popular Culture");
            Globals.editor.putString("CatOther","Other");
            Globals.editor.putString("CatMCQs","Multiple Choice");
            Globals.editor.putString("CatTF","True / False");
            Globals.editor.putString("CatSt","Statements");

        }else if(Globals.language.equalsIgnoreCase("Spanish")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of Spanish", Toast.LENGTH_LONG).show();
            }
            setData(arr,"Spanish");
            arrayList=Globals.Spanishlist;
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","Política");
            Globals.editor.putString("CatHistory","Historia");
            Globals.editor.putString("CatPopular","Cultura Popular");
            Globals.editor.putString("CatOther","Otros");
            Globals.editor.putString("CatMCQs","Preguntas Múltiples");
            Globals.editor.putString("CatTF","Verdad / Mentira");
            Globals.editor.putString("CatSt","Afirmaciones");

        }else if(Globals.language.equalsIgnoreCase("Polish")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of Polish", Toast.LENGTH_LONG).show();
            }
            setData(arr,"Polish");
            arrayList=Globals.Polishlist;
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","Polityka");
            Globals.editor.putString("CatHistory","Historia");
            Globals.editor.putString("CatPopular","Kultura Popularna");
            Globals.editor.putString("CatOther","Inne");
            Globals.editor.putString("CatMCQs","Wielokrotny Wybor");
            Globals.editor.putString("CatTF","Prawda/Falsz");
            Globals.editor.putString("CatSt","Sprawozdania");

        }else if(Globals.language.equalsIgnoreCase("Urdu")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of Urdu", Toast.LENGTH_LONG).show();
            }
            setData(arr,"Urdu");
            arrayList=Globals.Urdulist;
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","سیاست");
            Globals.editor.putString("CatHistory","تاریخ");
            Globals.editor.putString("CatPopular","مقبول ثقافت");
            Globals.editor.putString("CatOther","دیگر");
            Globals.editor.putString("CatMCQs","ملٹیپل چوایئس");
            Globals.editor.putString("CatTF","صحیح / غلط");
            Globals.editor.putString("CatSt","بیان");

        }else if(Globals.language.equalsIgnoreCase("Arabic")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of Arabic", Toast.LENGTH_LONG).show();
            }
            setData(arr,"Arabic");
            arrayList=Globals.Arabiclist;
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","سياسة/قانون");
            Globals.editor.putString("CatHistory","تاريخ");
            Globals.editor.putString("CatPopular","ثقافة");
            Globals.editor.putString("CatOther","غير ذلك");
            Globals.editor.putString("CatMCQs","اختيار من متعدد");
            Globals.editor.putString("CatTF","صح أم خطأ");
            Globals.editor.putString("CatSt","عبارات");

        }else if(Globals.language.equalsIgnoreCase("Farsi")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of Farsi", Toast.LENGTH_LONG).show();
            }
            setData(arr,"Farsi");
            arrayList=Globals.Farsilist;
            Globals.editor.putString("CatAll","All Categories");
            Globals.editor.putString("CatPolitics","سیاست/قوانین");
            Globals.editor.putString("CatHistory","تاریخ");
            Globals.editor.putString("CatPopular","کلتور");
            Globals.editor.putString("CatOther","متفرقه");
            Globals.editor.putString("CatMCQs","چند جوابه");
            Globals.editor.putString("CatTF","صحیح یا غلط");
            Globals.editor.putString("CatSt","توضیح");

        }



        if(arrayList.size()>=0) {

            Globals.editor.putInt("TotalAll", arrayList.size());
            int politics=0;
            int law=0;
            int history=0;
            int popular=0;
            int other=0;
            int mcqs=0;
            int tf=0;
            int st=0;


            for(int i=0;i<arrayList.size();i++){
                //Toast.makeText(Category.this, "Loop", Toast.LENGTH_SHORT).show();
                if (arrayList.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")) {
                    //Toast.makeText(Category.this, "MCQs", Toast.LENGTH_SHORT).show();
                    mcqs++;
                } else if (arrayList.get(i).getCategory1().equalsIgnoreCase("True/False")) {
                    tf++;
                } else if (arrayList.get(i).getCategory1().equalsIgnoreCase("Statements")) {
                    st++;
                }

                if (arrayList.get(i).getCategory2().equalsIgnoreCase("Politics")) {
                    politics++;
                } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("History")) {
                    history++;
                } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("Law")) {
                    law++;
                } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("Popular Culture")) {
                    popular++;
                } else if (arrayList.get(i).getCategory2().equalsIgnoreCase("Other")) {
                    other++;
                }
            }
            Globals.editor.putInt("TotalPoli",politics);
            Globals.editor.putInt("TotalLaw",law);
            Globals.editor.putInt("TotalHistory",history);
            Globals.editor.putInt("TotalPop",popular);
            Globals.editor.putInt("TotalOther",other);
            Globals.editor.putInt("TotalMC",mcqs);
            Globals.editor.putInt("TotalTF",tf);
            Globals.editor.putInt("TotalSt",st);
            Globals.editor.commit();
        }

        Globals.editor.commit();
    }

    private boolean setData(JSONArray arr, String language){

        try {
        JSONArray localArray=arr;
        ArrayList<TestQuestion> arrayList=new ArrayList<>();
        int size = localArray.length();
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
                String test_id= arrayObject.getString("test_id");
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
                    //Toast.makeText(getApplicationContext(), " English " + (Integer) Globals.Englishlist.size(), Toast.LENGTH_SHORT).show();
                }
            } else if (language.equalsIgnoreCase("Polish")) {
                if (Globals.Polishlist.isEmpty()) {
                    Globals.Polishlist = arrayList;
                    //Toast.makeText(getApplicationContext(), " Polish " + (Integer) Globals.Polishlist.size(), Toast.LENGTH_SHORT).show();
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
                    //finish();
                }
            } else if (language.equalsIgnoreCase("Farsi")) {
                if (Globals.Farsilist.isEmpty()) {
                    Globals.Farsilist = arrayList;
                    //Toast.makeText(getApplicationContext(), " Farsi " + (Integer) Globals.Farsilist.size(), Toast.LENGTH_SHORT).show();
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
