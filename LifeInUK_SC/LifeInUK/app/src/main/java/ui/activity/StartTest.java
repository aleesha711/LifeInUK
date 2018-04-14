package ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tecjaunt.www.lifeinuk.R;

import java.util.ArrayList;

import Org.Tools.CountTimer;
import Org.Tools.FlagQuestions;
import Org.Tools.TestQuestion;
import Org.Utility.Globals;
import UtilityClasses.ConnectionDetector;
import ui.fragments.MultipleChoice;
import ui.fragments.Statement;
import ui.fragments.TrueFalse;

public class StartTest extends AppCompatActivity {

    Fragment frag = null;
    boolean flag = false;

    Button btn_play;
    Button btn_pause;
    Button btn_quit;

    ImageView iv_setting;
    ImageView iv_option;
    TextView tv_title;
    TextView tv_timer;
    RelativeLayout rl_bottom;
    Button btn_cancel;
    Button btn_ok;

    Button btn_start;
    Button btn_main_menu;
    ConnectionDetector cd;
    Boolean con=false;
    int size=0;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        findViewby();
        Globals.glist=new ArrayList<>();
        cd = new ConnectionDetector(this);
        setCategoryLanguage();
        checkConnecion();
        //UpdateList();


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Globals.type=1;
                    Globals.flagQes=false;
                    //Globals.menuScreen.finish();
                    Globals.url = "http://sparkmultimedia.net/lifeUk/services/random.php?language=" + Globals.language + "&access=" + Globals.access;
                    //Globals.url="http://sparkmultimedia.net/cat1.php?cat_1=true/false&language="+Globals.language;
                    //Globals.url="http://sparkmultimedia.net/cat1.php?cat_1=multiple%20choice&language=" + Globals.language;
                if(size>0) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(StartTest.this, "No Question Available", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void findViewby() {


        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);

        tv_title = (TextView)findViewById(R.id.tv_action_bar_title);
        tv_timer = (TextView)findViewById(R.id.tv_action_bar_timer);

        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        tv_timer.setVisibility(View.GONE);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);

        btn_start = (Button)findViewById(R.id.btn_start_test_start_test);
        btn_main_menu = (Button)findViewById(R.id.btn_start_test_main_menu);

        tv_timer.setVisibility(View.GONE);

        iv_setting.setVisibility(View.GONE);

        tv_title.setText("Test");
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

    }

    public void firstQuestion() {

        Globals.type = 1;
        startTime();

        //Toast.makeText(getActivity(), list.get(0).getQuestion(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), Globals.glist.get(0).getQuestion(), Toast.LENGTH_SHORT).show();
        Globals.index = 0;
        if (Globals.glist.get(0).getCategory1().equals("True/False") || Globals.glist.get(0).getCategory1().equals("Verdad/ Mentira")|| Globals.glist.get(0).getCategory1().equals("صح أم خطأ")) {
            frag = new TrueFalse();
            setFrag();
        }
        else if (Globals.glist.get(0).getCategory1().equals("Statements") || Globals.glist.get(0).getCategory1().equals("Afirmaciones")|| Globals.glist.get(0).getCategory1().equals("عبارات")) {
            frag = new Statement();
            setFrag();
        }
        else if (Globals.glist.get(0).getCategory1().equals("Multiple Choice") || Globals.glist.get(0).getCategory1().equals("Opci?n M?ltiple")|| Globals.glist.get(0).getCategory1().equals("اختيار من متعدد")) {
            frag = new MultipleChoice();
            setFrag();
        } else
            Toast.makeText(getApplicationContext(), "No Question Added Yet", Toast.LENGTH_SHORT).show();
    }

    public void setFrag() {
        FragmentTransaction fm0=getSupportFragmentManager().beginTransaction();
        fm0.replace(R.id.fl_main_activity,frag);
        fm0.addToBackStack("Test");
        fm0.commit();
    }


   /* private class ReadingData extends AsyncTask<String, String, String> {

        private ProgressDialog pdia;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!(swipeCheck.equals("swipe"))) {
                //Progress to show reading data process
                pdia = new ProgressDialog(getApplicationContext());
                pdia.setMessage("Loading Data...");
                pdia.show();
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            return GetDataFromWebservice.GET(urls[0]); }
        @Override
        protected void onPostExecute(String result) {
            if (!(swipeCheck.equals("swipe"))) {
                pdia.dismiss();
            } else {
                swipeContainer.setRefreshing(false);
            }
            JSONObject mainObject;
            JSONArray mainArray;
            JSONObject arrayObject;

            try {
                mainObject = new JSONObject(result);
                status=mainObject.getString("status");
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
            }
            if(status.equalsIgnoreCase("success"))
            {
                try {
                    mainObject = new JSONObject(result);
                    mainArray = mainObject.getJSONArray("Questions");
                    //Toast.makeText(getActivity(), mainObject.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), mainArray.toString(), Toast.LENGTH_SHORT).show();
                    int size = mainArray.length();
                    Globals.glist=new ArrayList<>();
                   //Toast.makeText(getActivity(),"  "+ mainArray.length(), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getActivity(),"  "+ size, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getActivity(),"  "+ mainArray.getJSONObject(0).toString(), Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(getActivity(), question+" "+option1+" "+option2+" "+option3+" "+option4+" "+category1+" "+category2+" "+answer+" "+explain, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Unable to access Data", Toast.LENGTH_SHORT).show();
                flag=true;
            }

        }
    }*/

    public boolean checkConnecion() {
        //Checking Internet Connectivity
        if (cd.isConnectingToInternet()) {
            //new ReadingData().execute("http://sparkmultimedia.net/language.php?language="+Globals.language);
            con=true;
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            con=false;
            return false;
        }
    }

    private void startTime(){
        Globals.answer=0;
        Globals.editor.putInt("AnswerTLA",0);
        Globals.editor.putInt("CorrectTLA",0);
        Globals.editor.commit();
        Globals.ilist=new ArrayList<>();
        Globals.ilist.clear();
        Globals.flist=new ArrayList<FlagQuestions>();
        Globals.flist.clear();
        Globals.review=false;

        Globals.timer=new CountTimer();
        Globals.timer.setTv_time(tv_timer);
        Globals.timer.setTime_limit(45*60);
        Globals.timer.timerStart();
    }

    private void setCategoryLanguage(){

        ArrayList<TestQuestion> arrayList=new ArrayList<>();

        if(Globals.language.equalsIgnoreCase("English")){
            size=Globals.Englishlist.size();

        }else if(Globals.language.equalsIgnoreCase("Spanish")){
            size =Globals.Spanishlist.size();

        }else if(Globals.language.equalsIgnoreCase("Polish")){
            size=Globals.Polishlist.size();

        }else if(Globals.language.equalsIgnoreCase("Urdu")){
            size=Globals.Urdulist.size();

        }else if(Globals.language.equalsIgnoreCase("Arabic")){
            size=Globals.Arabiclist.size();

        }else if(Globals.language.equalsIgnoreCase("Farsi")){
            size=Globals.Farsilist.size();

        }



    }
}

