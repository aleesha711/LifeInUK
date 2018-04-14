package ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.util.ArrayList;

import Org.Tools.CountTimer;
import Org.Tools.FlagQuestions;
import Org.Tools.ItemQuestionDetail;
import Org.Tools.TestQuestion;
import Org.Utility.Globals;
import UtilityClasses.ConnectionDetector;

public class StartPractice  extends AppCompatActivity {


    Fragment frag = null;

    String setUrl;
    RadioGroup rg_language;
    RadioButton rb_english;
    RadioButton rb_polish;
    RadioButton rb_spanish;
    RadioButton rb_arabic;
    RadioButton rb_urdu;
    RadioButton rb_farsi;
    String lang;


    ImageView iv_setting;

    TextView tv_title;
    TextView tv_time;
    TextView tv_heading;

    Button btn_play;
    Button btn_pause;
    Button btn_quit;

    RelativeLayout rl_bottom;
    Button btn_cancel;
    Button btn_ok;
    private SwipeRefreshLayout swipeContainer;
    String swipeCheck = "noSwipe";
    ConnectionDetector cd;
    Boolean con=false;

    Button btn_start;
    Button btn_main_menu;
    ArrayList<TestQuestion> list=Globals.glist;
    int size=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_practice);

        Globals.glist=new ArrayList<>();
        findViewby();
        //pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        //editor = pref.edit();
        //setingUrl();
        //Toast.makeText(getActivity(), "A : "+ Globals.pref.getInt("AnswerMC",0)+" C : "+ Globals.pref.getInt("CorrectMC",0), Toast.LENGTH_SHORT).show();
        cd = new ConnectionDetector(this);
        setCategoryLanguage();
        checkConnecion();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Globals.type=2;
                    Globals.category.finish();
                    Globals.flagQes=false;
                if(size>0) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(StartPractice.this, "No Question Available for this language", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.category.finish();
                finish();
            }
        });

    }

    private void findViewby() {



        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);
        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);


        btn_start=(Button)findViewById(R.id.btn_start_practice_start_test);
        btn_main_menu=(Button)findViewById(R.id.btn_start_practice_main_menu);
        tv_time=(TextView)findViewById(R.id.tv_action_bar_timer);
        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        tv_heading=(TextView)findViewById(R.id.tv_start_practice_heading);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        iv_setting.setVisibility(View.GONE);

        tv_title.setText("Practice");
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();
        String Qno=String.valueOf(Globals.Qnos);

        if(Globals.Qnos>40){
            Qno="All";
        }

        if (Globals.type2 == 0) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from All Categories");
        } else if (Globals.type2 == 1) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from Politics");
        } else if (Globals.type2 == 2) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from Law");
        } else if (Globals.type2 == 3) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from History");
        } else if (Globals.type2 == 4) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from Popular Culture");
        } else if (Globals.type2 == 5) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from Others");
        } else if (Globals.type2 == 6) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from Multiple Choice");
        } else if (Globals.type2 == 7) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from True False");
        } else if (Globals.type2 == 8) {
            tv_heading.setText(Qno + " revision questions have been selected to complete this practice session, The list of revision questions will be from Statements");
        }


    }


    public boolean checkConnecion() {
        //Checking Internet Connectivity
        if (cd.isConnectingToInternet()) {
            //new ReadingData().execute(Globals.url);
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
        Globals.ilist=new ArrayList<ItemQuestionDetail>();
        Globals.ilist.clear();
        Globals.flist=new ArrayList<FlagQuestions>();
        Globals.flist.clear();
        Globals.review=false;

        Globals.editor.putInt("AnswerPLA",0);
        Globals.editor.putInt("CorrectPLA",0);
        Globals.editor.commit();

        Globals.timer=new CountTimer();
        Globals.timer.setContext(this);
        Globals.timer.setFmg(getSupportFragmentManager());
        Globals.timer.setTv_time(tv_time);
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