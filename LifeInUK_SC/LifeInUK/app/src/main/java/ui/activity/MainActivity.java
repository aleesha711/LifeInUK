package ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tecjaunt.www.lifeinuk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import Org.Tools.CountTimer;
import Org.Tools.FlagQuestions;
import Org.Tools.ItemQuestionDetail;
import Org.Tools.TestQuestion;
import Org.Utility.Globals;
import UtilityClasses.ConnectionDetector;
import UtilityClasses.GetDataFromWebservice;
import ui.fragments.MultipleChoice;
import ui.fragments.Statement;
import ui.fragments.TrueFalse;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "MyActivity";

    Fragment frag = null;

    ImageView iv_setting;
    TextView tv_title;
    TextView tv_time;
    Button btn_play;
    Button btn_pause;
    Button btn_explain;
    Button btn_quit;

    RelativeLayout rl_bottom_bar;
    ConnectionDetector cd;
    Boolean con=false;
    Boolean data=false;
    ArrayList<TestQuestion> local_list;
    int random=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Log.d(TAG, "Started firebase analytics ");


        findViewby();
        if(Globals.flagQes){
            //Globals.timer=new CountTimer();
            //Globals.timer.setContext(this);
            //Globals.timer.setFmg(getSupportFragmentManager());
            Globals.timer.setTv_time(tv_time);
            //Globals.timer.setTime_limit(Globals.time);
            Globals.timer.timerResume();
            firstQuestion();
        }else{
            if(Globals.type==1) {
                checkConnecion();
            }else{
                Globals.glist=new ArrayList<>();
                setQuestions();
                startTime();
                firstQuestion();
            }
        }


        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        getIntent().getExtras();

        //var test = getIntent().getData();
        Object test = getIntent().getExtras();


    }


    private void findViewby() {

        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);
        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);
        rl_bottom_bar=(RelativeLayout)findViewById(R.id.rl_bottom_bar);

        tv_time=(TextView)findViewById(R.id.tv_action_bar_timer);
        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        tv_time.setVisibility(View.VISIBLE);
        btn_quit.setVisibility(View.VISIBLE);

        iv_setting.setVisibility(View.GONE);

        rl_bottom_bar.setVisibility(View.VISIBLE);

        cd = new ConnectionDetector(this);
        local_list=new ArrayList<>();
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

    }

    public void setFrag(){
        FragmentTransaction fm0=getSupportFragmentManager().beginTransaction();
        fm0.add(R.id.fl_main_activity,frag);
        //fm0.addToBackStack("Start");
        fm0.commit();
    }

    public void firstQuestion() {
        //Toast.makeText(this, "Size "+ (Integer)Globals.glist.size(), Toast.LENGTH_SHORT).show();
            if (Globals.glist.get(Globals.index).getCategory1().equalsIgnoreCase("True/False")) {
                frag = new TrueFalse();
                setFrag();
            } else if (Globals.glist.get(Globals.index).getCategory1().equalsIgnoreCase("Statements")) {
                frag = new Statement();
                setFrag();
            } else if (Globals.glist.get(Globals.index).getCategory1().equalsIgnoreCase("Multiple Choice")) {
                frag = new MultipleChoice();
                setFrag();
            } else
                Toast.makeText(getApplicationContext(), "No Question Added Yet", Toast.LENGTH_SHORT).show();
    }

    private class ReadingData extends AsyncTask<String, String, String> {

        private ProgressDialog pdia;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
                //Progress to show reading data process
                pdia = new ProgressDialog(MainActivity.this);
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
            Globals.glist=new ArrayList<>();
            JSONObject mainObject=null;
            JSONArray mainArray=null;
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
                if(Globals.type==2){
                    if(size>Globals.Qnos){
                        size=Globals.Qnos;
                    }
                }
                for(int i=0;i<size;i++){
                    arrayObject = mainArray.getJSONObject(i);
                    String question = arrayObject.getString("question");
                    String option1=arrayObject.getString("op_a");
                    String option2=arrayObject.getString("op_b");
                    String option3=arrayObject.getString("op_c");
                    String option4=arrayObject.getString("op_d");
                    String category1=arrayObject.getString("cat_1");
                    String category2=arrayObject.getString("cat_2");
                    String answer=arrayObject.getString("ans");
                    String explain=arrayObject.getString("exp");
                    //Toast.makeText(getActivity(), " " + question, Toast.LENGTH_SHORT).show();
                    Globals.glist.add(new TestQuestion(question,option1,option2,option3,option4,category1,category2,answer,explain,false));
                    //Toast.makeText(getActivity(), question+" "+option1+" "+option2+" "+option3+" "+option4+" "+category1+" "+category2+" "+answer+" "+explain, Toast.LENGTH_SHORT).show()
                }
                //Toast.makeText(getApplicationContext(),"  "+ ((Integer)mainArray.length()).toString(), Toast.LENGTH_SHORT).show();
                data=true;
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext()," Unable to access data online running app in offline mode!", Toast.LENGTH_LONG).show();
                data=false;
            }
            if(data) {
                startTime();
                firstQuestion();
            }else{
                startTime();
                Globals.glist=new ArrayList<>();
                setCategoryLanguage();
                firstQuestion();
            }
            //else
                //Toast.makeText(getApplicationContext(), "No Data Available Please Come Back Soon!", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean checkConnecion() {
        //Checking Internet Connectivity
        if (cd.isConnectingToInternet()) {
            new ReadingData().execute(Globals.url);
            con=true;
            return true;
        } else {
            startTime();
            Globals.glist=new ArrayList<>();
            /*Toast.makeText(getApplicationContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            if(Globals.language.equalsIgnoreCase("English")){
                Globals.glist=Globals.Englishlist;
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                Globals.glist=Globals.Polishlist;
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                Globals.glist=Globals.Spanishlist;
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                Globals.glist=Globals.Urdulist;
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                Globals.glist=Globals.Arabiclist;
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                Globals.glist=Globals.Farsilist;
            }*/
            setCategoryLanguage();
            firstQuestion();
            con=false;
            return false;
        }
    }

    private void startTime(){

        Globals.pref=getSharedPreferences("MyPref",MODE_PRIVATE);
        Globals.editor=Globals.pref.edit();
        Globals.index = 0;
        Globals.answer=0;
        Globals.flagQes=false;
        Globals.flagReview=true;
        Globals.autoNext=Globals.pref.getBoolean("AutoNext",false);
        Globals.ilist=new ArrayList<ItemQuestionDetail>();
        Globals.ilist.clear();
        Globals.flist=new ArrayList<FlagQuestions>();
        Globals.flist.clear();
        Globals.review=false;
        if(Globals.type==1){
            Globals.editor.putInt("AnswerTLA", 0);
            Globals.editor.putInt("CorrectTLA", 0);
        }
        else {
            Globals.editor.putInt("AnswerPLA", 0);
            Globals.editor.putInt("CorrectPLA", 0);
        }
        Globals.editor.commit();

        Globals.timer=new CountTimer();
        Globals.timer.setContext(this);
        Globals.timer.setFmg(getSupportFragmentManager());
        Globals.timer.setTv_time(tv_time);
        Globals.timer.setTime_limit(45*60);
        Globals.timer.timerStart();
    }

    private void setQuestions(){
        Collections.shuffle(Globals.Englishlist);
        Collections.shuffle(Globals.Polishlist);
        Collections.shuffle(Globals.Spanishlist);
        Collections.shuffle(Globals.Urdulist);
        Collections.shuffle(Globals.Arabiclist);
        Collections.shuffle(Globals.Farsilist);
        if (Globals.type2 == 0) {
            if(Globals.language.equalsIgnoreCase("English")){
                //Globals.glist=Globals.Englishlist;
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                }

            } else if(Globals.language.equalsIgnoreCase("Polish")){
                //Globals.glist=Globals.Polishlist;
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    if(range<Globals.Qnos) {
                        Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        range++;
                    }
                    if(range==Globals.Qnos){
                        i=size;
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                //Globals.glist=Globals.Spanishlist;
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    if(range<Globals.Qnos) {
                        Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        range++;
                    }
                    if(range==Globals.Qnos){
                        i=size;
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    if(range<Globals.Qnos) {
                        Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        range++;
                    }
                    if(range==Globals.Qnos){
                        i=size;
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                //Globals.glist=Globals.Arabiclist;
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    if(range<Globals.Qnos) {
                        Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        range++;
                    }
                    if(range==Globals.Qnos){
                        i=size;
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                //Globals.glist=Globals.Farsilist;
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    if(range<Globals.Qnos) {
                        Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                        range++;
                    }
                    if(range==Globals.Qnos){
                        i=size;
                    }
                }
            }

        } else if (Globals.type2 == 1) {

            if(Globals.language.equalsIgnoreCase("English")){

                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Politics")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Politics")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Politics")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Politics")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Politics")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Politics")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }

        } else if (Globals.type2 == 2) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Law")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Law")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Law")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Law")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Law")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Law")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }


        } else if (Globals.type2 == 3) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("History")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("History")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("History")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                //Toast.makeText(this, "Size"+(Integer)size, Toast.LENGTH_SHORT).show();
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("History")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("History")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("History")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }


        } else if (Globals.type2 == 4) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Popular Culture")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Popular Culture")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Popular Culture")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Popular Culture")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Popular Culture")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Popular Culture")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }


        } else if (Globals.type2 == 5) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Other")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Other")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Other")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Other")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Other")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory2().equalsIgnoreCase("Other")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }


        } else if (Globals.type2 == 6) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Multiple Choice")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }


        } else if (Globals.type2 == 7) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("True/False")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("True/False")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("True/False")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
               // Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("True/False")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("True/False")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("True/False")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }


        } else if (Globals.type2 == 8) {

            if(Globals.language.equalsIgnoreCase("English")){
                int size=Globals.Englishlist.size();
                ArrayList<TestQuestion> list=Globals.Englishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Statements")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Polish")){
                int size=Globals.Polishlist.size();
                ArrayList<TestQuestion> list=Globals.Polishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Statements")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Spanish")){
                int size=Globals.Spanishlist.size();
                ArrayList<TestQuestion> list=Globals.Spanishlist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Statements")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Urdu")){
                //Globals.glist=Globals.Urdulist;
                int size=Globals.Urdulist.size();
                ArrayList<TestQuestion> list=Globals.Urdulist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Statements")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Arabic")){
                int size=Globals.Arabiclist.size();
                ArrayList<TestQuestion> list=Globals.Arabiclist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Statements")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            } else if(Globals.language.equalsIgnoreCase("Farsi")){
                int size=Globals.Farsilist.size();
                ArrayList<TestQuestion> list=Globals.Farsilist;
                if(Globals.Qnos>40){
                    Globals.Qnos=size;
                }
                int range=0;
                for (int i=0;i<size;i++){
                    if(list.get(i).getCategory1().equalsIgnoreCase("Statements")){
                        String question = list.get(i).getQuestion();
                        String option1=list.get(i).getOption1();
                        String option2=list.get(i).getOption2();
                        String option3=list.get(i).getOption3();
                        String option4=list.get(i).getOption4();
                        String category1=list.get(i).getCategory1();
                        String category2=list.get(i).getCategory2();
                        String answer=list.get(i).getAnswer();
                        String explain=list.get(i).getExplain();
                        if(range<Globals.Qnos) {
                            Globals.glist.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                            range++;
                        }
                        if(range==Globals.Qnos){
                            i=size;
                        }
                    }
                }
            }

        }
    }

    @Override
    public void onBackPressed() {

    }

    private void setCategoryLanguage(){

        ArrayList<TestQuestion> arrayList=new ArrayList<>();

        if(Globals.language.equalsIgnoreCase("English")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            }
            Globals.Englishlist.clear();
            setData(arr,"English");
            int size=Globals.Englishlist.size();
            ArrayList<TestQuestion> list=Globals.Englishlist;
            ArrayList<TestQuestion> list_02=new ArrayList<>();
            //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
            int range=0;
            for (int i=0;i<size;i++){
                if(list.get(i).getTest_id()==random){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    int test_id=list.get(i).getTest_id();
                    list_02.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                    range++;
                    if(range==24){
                        //Toast.makeText(this, " Random Size "+(Integer)list_02.size(), Toast.LENGTH_SHORT).show();
                        i=size;
                    }
                }
            }
            Collections.shuffle(list_02);
            Globals.glist=list_02;


        }else if(Globals.language.equalsIgnoreCase("Spanish")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            }
            Globals.Spanishlist.clear();
            setData(arr,"Spanish");
            int size=Globals.Spanishlist.size();
            ArrayList<TestQuestion> list=Globals.Spanishlist;
            ArrayList<TestQuestion> list_02=new ArrayList<>();
            //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
            int range=0;
            for (int i=0;i<size;i++){
                if(list.get(i).getTest_id()==random){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    int test_id=list.get(i).getTest_id();
                    list_02.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                    range++;
                    if(range==24){
                        //Toast.makeText(this, " Random Size "+(Integer)list_02.size(), Toast.LENGTH_SHORT).show();
                        i=size;
                    }
                }
            }
            Collections.shuffle(list_02);
            Globals.glist=list_02;

        }else if(Globals.language.equalsIgnoreCase("Polish")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            }
            Globals.Polishlist.clear();
            setData(arr,"Polish");
            int size=Globals.Polishlist.size();
            ArrayList<TestQuestion> list=Globals.Polishlist;
            ArrayList<TestQuestion> list_02=new ArrayList<>();
            //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
            int range=0;
            for (int i=0;i<size;i++){
                if(list.get(i).getTest_id()==random){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    int test_id=list.get(i).getTest_id();
                    list_02.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                    range++;
                    if(range==24){
                        //Toast.makeText(this, " Random Size "+(Integer)list_02.size(), Toast.LENGTH_SHORT).show();
                        i=size;
                    }
                }
            }
            Collections.shuffle(list_02);
            Globals.glist=list_02;

        }else if(Globals.language.equalsIgnoreCase("Urdu")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            }
            Globals.Urdulist.clear();
            setData(arr,"Urdu");
            int size=Globals.Urdulist.size();
            ArrayList<TestQuestion> list=Globals.Urdulist;
            ArrayList<TestQuestion> list_02=new ArrayList<>();
            //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
            int range=0;
            for (int i=0;i<size;i++){
                if(list.get(i).getTest_id()==random){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    int test_id=list.get(i).getTest_id();
                    list_02.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                    range++;
                    if(range==24){
                       // Toast.makeText(this, " Random Size "+(Integer)list_02.size(), Toast.LENGTH_SHORT).show();
                        i=size;
                    }
                }
            }
            Collections.shuffle(list_02);
            Globals.glist=list_02;

        }else if(Globals.language.equalsIgnoreCase("Arabic")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            }
            Globals.Arabiclist.clear();
            setData(arr,"Arabic");
            int size=Globals.Arabiclist.size();
            ArrayList<TestQuestion> list=Globals.Arabiclist;
            ArrayList<TestQuestion> list_02=new ArrayList<>();
            //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
            int range=0;
            for (int i=0;i<size;i++){
                if(list.get(i).getTest_id()==random){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    int test_id=list.get(i).getTest_id();
                    list_02.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                    range++;
                    if(range==24){
                        //Toast.makeText(this, " Random Size "+(Integer)list_02.size(), Toast.LENGTH_SHORT).show();
                        i=size;
                    }
                }
            }
            Collections.shuffle(list_02);
            Globals.glist=list_02;

        }else if(Globals.language.equalsIgnoreCase("Farsi")){
            JSONArray arr= null;
            try {
                arr = new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Unable To Access Data of "+Globals.language, Toast.LENGTH_LONG).show();
            }
            Globals.Farsilist.clear();
            setData(arr,"Farsi");
            int size=Globals.Farsilist.size();
            ArrayList<TestQuestion> list=Globals.Farsilist;
            ArrayList<TestQuestion> list_02=new ArrayList<>();
            //Toast.makeText(this, "Size "+(Integer)size, Toast.LENGTH_SHORT).show();
            int range=0;
            for (int i=0;i<size;i++){
                if(list.get(i).getTest_id()==random){
                    String question = list.get(i).getQuestion();
                    String option1=list.get(i).getOption1();
                    String option2=list.get(i).getOption2();
                    String option3=list.get(i).getOption3();
                    String option4=list.get(i).getOption4();
                    String category1=list.get(i).getCategory1();
                    String category2=list.get(i).getCategory2();
                    String answer=list.get(i).getAnswer();
                    String explain=list.get(i).getExplain();
                    int test_id=list.get(i).getTest_id();
                    list_02.add(new TestQuestion(question, option1, option2, option3, option4, category1, category2, answer, explain, false));
                    range++;
                    if(range==24){
                       // Toast.makeText(this, " Random Size "+(Integer)list_02.size(), Toast.LENGTH_SHORT).show();
                        i=size;
                    }
                }
            }
            Collections.shuffle(list_02);
            Globals.glist=list_02;
        }

        Globals.editor.commit();
    }

    private boolean setData(JSONArray arr, String language){
        JSONArray localArray=arr;
        ArrayList<TestQuestion> arrayList=new ArrayList<>();
        int size = localArray.length();
        int max =0;
        if(Globals.type==1) {
             max = size / 24;
            //Toast.makeText(this, "max : "+ (Integer)max, Toast.LENGTH_SHORT).show();
            int min = 1;
            random = (int )(Math.random() * max + min);
            //Toast.makeText(this, "Random : "+ (Integer)random, Toast.LENGTH_SHORT).show();
        }

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
                String test_id= arrayObject.getString("test_id");

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
