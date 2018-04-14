package ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tecjaunt.www.lifeinuk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Org.Tools.ItemCategory;
import Org.Tools.TestQuestion;
import Org.Utility.Globals;
import UtilityClasses.ConnectionDetector;
import UtilityClasses.GetDataFromWebservice;

public class  Category  extends AppCompatActivity {

    View rootView;
    Fragment frag = null;
    boolean flag = false;
    boolean flag1=false;


    ImageView iv_setting;

    TextView tv_title;
    String setUrl;
    TextView tv_time;
    Button btn_play;
    Button btn_pause;
    Button btn_quit;

    ConnectionDetector cd;
    Boolean con=false;
    Boolean data=false;
    int Qno = 0;

    public ArrayList<ItemCategory> list;
    CategoryAdapter adapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        findViewby();
        //checkConnecion();

        list=new ArrayList<>();
        list.add(new ItemCategory(Globals.pref.getString("CatAll","All Categories"),0,"1,234","11122"));
        list.add(new ItemCategory(Globals.pref.getString("CatPolitics","Politics"),0,"0","0"));
        //list.add(new ItemCategory("Law",0,"0","0"));
        list.add(new ItemCategory(Globals.pref.getString("CatHistory","History"),0,"0","0"));
        list.add(new ItemCategory(Globals.pref.getString("CatPopular","Popular Culture"),0,"0","0"));
        list.add(new ItemCategory(Globals.pref.getString("CatOther","Other"),0,"0","0"));
        list.add(new ItemCategory(Globals.pref.getString("CatMCQs","Multiple Choice"),0,"0","0"));
        list.add(new ItemCategory(Globals.pref.getString("CatTF","True / False"),0,"0","0"));
        list.add(new ItemCategory(Globals.pref.getString("CatSt","Statements"),0,"0","0"));

        adapter = new CategoryAdapter(Category.this,list);
        lv.setAdapter(adapter);





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Globals.cat_02=null;
                Globals.cat_01=null;
                //Toast.makeText(getActivity(), "Language : "+Globals.language, Toast.LENGTH_SHORT).show();
                long cat= adapter.getItemId(i);
              //  if(Globals.language.equalsIgnoreCase("English")) {
                    if (cat==0) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/language.php?language=" + Globals.language;
                        Globals.type2=0;
                        //Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    } else if (cat==5) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat1.php?cat_1=multiple%20choice&language=" + Globals.language;
                        Globals.type2=6;
                        //setUrl="http://sparkmultimedia.net/cat2.php?cat_2=History&language=english";
                        //Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    } else if (cat==6) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat1.php?cat_1=true/false&language=" + Globals.language;
                        Globals.type2=7;
                        //setUrl="http://sparkmultimedia.net/cat2.php?cat_2=History&language=english";
                        //Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    } else if (cat==7) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat1.php?cat_1=statements&language=" + Globals.language;
                        Globals.type2=8;
                        //setUrl="http://sparkmultimedia.net/cat2.php?cat_2=History&language=english";
                       // Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    } else if (cat==1) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat2.php?cat_2=politics&language=" + Globals.language;
                        Globals.type2=1;
                        //setUrl="http://sparkmultimedia.net/cat2.php?cat_2=History&language=english";
                        //Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    } else if (cat==2) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat2.php?cat_2=History&language=" + Globals.language;
                        Globals.type2=3;
                        //setUrl="http://sparkmultimedia.net/cat2.php?cat_2=History&language=english";
                      //  Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    }  else if (cat==3) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat2.php?cat_2=popular%20culture&language=" + Globals.language;
                        Globals.type2=4;
                        //setUrl="http://sparkmultimedia.net/cat2.php?cat_2=History&language=english";
                       // Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    }
                    else if (cat==4) {
                        //setUrl = "http://sparkmultimedia.net/lifeUk/services/cat2.php?cat_2=other&language=" + Globals.language;
                        Globals.type2=5;
                        //Toast.makeText(getActivity(), setUrl, Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "No Data Available for this language", Toast.LENGTH_SHORT).show();
                        flag = true;
                    }

                if(flag==false) {
                    //Globals.url = setUrl;
                    showPopupReset();
                    //Toast.makeText(getActivity(), "Cat_01 : "+Globals.cat_01+" Cat_02 : "+Globals.cat_02, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findViewby() {
        Globals.category=this;
        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);

        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);
        tv_time=(TextView)findViewById(R.id.tv_action_bar_timer);

        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        lv=(ListView)findViewById(R.id.lv_category);

        iv_setting.setVisibility(View.GONE);

        tv_title.setText("CATEGORY");
        cd = new ConnectionDetector(this);
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

    }

    private class ReadingData extends AsyncTask<String, String, String> {

        private ProgressDialog pdia;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Progress to show reading data process
            pdia = new ProgressDialog(Category.this);
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
                        Globals.editor.putString("EnglishJSONArray", mainArray.toString()).commit();
                    }
                } else if(Globals.language.equalsIgnoreCase("Polish")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("PolishJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.editor.putString("PolishJSONArray", mainArray.toString()).commit();
                    }
                } else if(Globals.language.equalsIgnoreCase("Spanish")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("SpanishJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.editor.putString("SpanishJSONArray", mainArray.toString()).commit();
                    }
                } else if(Globals.language.equalsIgnoreCase("Urdu")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("UrduJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.editor.putString("UrduJSONArray", mainArray.toString()).commit();
                    }
                } else if(Globals.language.equalsIgnoreCase("Arabic")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("ArabicJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.editor.putString("ArabicJSONArray", mainArray.toString()).commit();
                    }
                } else if(Globals.language.equalsIgnoreCase("Farsi")){
                    JSONArray arrSize=new JSONArray(Globals.pref.getString("FarsiJSONArray", "[]"));
                    if(size>arrSize.length()){
                        Globals.editor.putString("FarsiJSONArray", mainArray.toString()).commit();
                    }
                }

                JSONArray arr=new JSONArray(Globals.pref.getString("EnglishJSONArray", "[]"));
                arrayObject = arr.getJSONObject(0) ;
                String question = arrayObject.getString("question");
                String option1=arrayObject.getString("op_a");
                String option2=arrayObject.getString("op_b");
                String option3=arrayObject.getString("op_c");
                String option4=arrayObject.getString("op_d");
                String category1=arrayObject.getString("cat_1");
                String category2=arrayObject.getString("cat_2");
                String answer=arrayObject.getString("ans");
                String explain=arrayObject.getString("exp");
                Toast.makeText(getApplicationContext(), question+" "+option1+" "+option2+" "+option3+" "+option4+" "+category1+" "+category2+" "+answer+" "+explain, Toast.LENGTH_SHORT).show();
                /*for(int i=0;i<size;i++){
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
                    arrayList.add(new TestQuestion(question,option1,option2,option3,option4,category1,category2,answer,explain,false));
                    //Toast.makeText(getActivity(), question+" "+option1+" "+option2+" "+option3+" "+option4+" "+category1+" "+category2+" "+answer+" "+explain, Toast.LENGTH_SHORT).show()
                }
                if(arrayList.size()>0) {

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
                }*/
                con=true;
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Unable To Retrive Data of this language", Toast.LENGTH_LONG).show();
                con=false;
            }
        }
    }

    public boolean checkConnecion() {
        //Checking Internet Connectivity
        if (cd.isConnectingToInternet()) {
            new ReadingData().execute("http://sparkmultimedia.net/lifeUk/services/language.php?language=" + Globals.language + "&access=" + Globals.access);
            con=true;
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            con=false;
            return false;
        }
    }

    private void showPopupReset() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_question_selector, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        Button btn=(Button)popupView.findViewById(R.id.btn_popup_question_no_ok);
        RadioGroup rg_question=(RadioGroup)popupView.findViewById(R.id.rg_popup_question_no);


        rg_question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.cb_popup_question_no_10){
                    flag1=true;
                    Qno=10;
                }
                else if(i==R.id.cb_popup_question_no_20){
                    flag1=true;
                    Qno=20;
                }
                else if(i==R.id.cb_popup_question_no_30){
                    flag1=true;
                    Qno=30;
                }
                else if(i==R.id.cb_popup_question_no_40){
                    flag1=true;
                    Qno=40;
                }
                else if(i==R.id.cb_popup_question_no_50){
                    flag1=true;
                    Qno=50;
                }else{
                    flag1=false;
                }
            }

        });


        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1) {
                    Globals.Qnos = Qno;
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), StartPractice.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Please select one option", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
        //dialog.setCancelable(false);
    }

}
