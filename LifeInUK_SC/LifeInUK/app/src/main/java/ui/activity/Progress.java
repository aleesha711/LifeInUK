package ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kyleduo.switchbutton.SwitchButton;
import com.tecjaunt.www.lifeinuk.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Org.Utility.Globals;

public class Progress  extends AppCompatActivity{


    ImageView iv_setting;

    TextView tv_title;
    TextView tv_time;
    TextView tv_progress;
    Button btn_play;
    Button btn_pause;
    Button btn_quit;;


    TextView tv_answer;
    TextView tv_correct;
    TextView tv_percent;
    SwitchButton sw_view;
    ProgressBar pb_view;
    LineChart mChart;
    String progress="Practice Progress";



    Button btn_ok;
    Button btn_cancel;

    Button btn_reset;
    ArrayList<Integer> dataList;
    ArrayList<Entry> entries;
    Boolean flag=false;
    Boolean further=false;

    public Progress() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        findViewby();
        practiceProgress();
        getFromPrefs();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        sw_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tv_progress.setText("Test Progress Chart");
                    testProgress();
                    flag=true;
                    getFromPrefs();
                    mChart.invalidate();
                    //testProgress();
                }
                else{
                    tv_progress.setText("Practice Progress Chart");
                    practiceProgress();
                    flag=false;
                    getFromPrefs();
                    mChart.invalidate();
                    //practiceProgress();
                }

            }
        });

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });

    }

    private void showPopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_erase_data, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        btn_cancel=(Button)popupView.findViewById(R.id.btn_popup_erase_no);
        btn_ok=(Button)popupView.findViewById(R.id.btn_popup_erase_yes);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                eraseData();
                mChart.invalidate();
                showPopupReset();
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

    private void showPopupReset() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_reset, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        Button btn=(Button)popupView.findViewById(R.id.btn_popup_reset_back);


        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private void findViewby() {

        //iv_menu=(ImageView)getActivity().findViewById(R.id.iv_action_bar_menu);
        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);
        //iv_option=(ImageView)getActivity().findViewById(R.id.iv_action_bar_option);
        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);
        btn_reset=(Button)findViewById(R.id.btn_progress_reset);
        tv_answer=(TextView)findViewById(R.id.tv_progress_answer);
        tv_correct=(TextView)findViewById(R.id.tv_progress_correct);
        tv_percent=(TextView)findViewById(R.id.tv_progress_percent);
        sw_view=(SwitchButton)findViewById(R.id.sw_progress_view);
        pb_view=(ProgressBar)findViewById(R.id.pb_progress_last_achieve);
        mChart=(LineChart) findViewById(R.id.chart_practice) ;
        tv_time=(TextView)findViewById(R.id.tv_action_bar_timer);
        tv_progress=(TextView)findViewById(R.id.tv_progress_piechart);
        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        tv_time.setVisibility(View.GONE);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);
        iv_setting.setVisibility(View.GONE);

        tv_title.setText("PROGRESS");
        tv_progress.setText(progress+" Chart");
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

    }

    private void practiceProgress(){
        int ans=Globals.pref.getInt("AnswerPLA",0);
        int cor=Globals.pref.getInt("CorrectPLA",0);
        int per=0;
        if(ans>0 ) {
            per = ((cor * 100) / ans);
        }
        tv_answer.setText(((Integer)ans).toString());
        tv_correct.setText(((Integer)cor).toString());
        tv_percent.setText(((Integer)per).toString()+" %");
        pb_view.setProgress(per);


    }

    private void testProgress(){
        int ans=Globals.pref.getInt("AnswerTLA",0);
        int cor=Globals.pref.getInt("CorrectTLA",0);
        int per=0;
        if(ans>0) {
            per = ((cor * 100) / ans);
        }
        tv_answer.setText(((Integer)ans).toString());
        tv_correct.setText(((Integer)cor).toString());
        tv_percent.setText(((Integer)per).toString()+" %");
        pb_view.setProgress(per);

    }

    private void eraseData(){

        Globals.editor.putString("TJSONArray","[]");
        Globals.editor.putString("PJSONArray","[]");

        Globals.editor.putInt("Answer",0);
        Globals.editor.putInt("Correct",0);
        //Practice Total Achieved Score
        Globals.editor.putInt("AnswerPrac",0);
        Globals.editor.putInt("CorrectPrac",0);
        //Test Total Achieved Score
        Globals.editor.putInt("AnswerTest",0);
        Globals.editor.putInt("CorrectTest",0);

        //Practice All Category Score
        Globals.editor.putInt("TotalAll",0);
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

        Globals.editor.commit();
        mChart.invalidate();
    }

     public void getFromPrefs(){

         entries = new ArrayList<>();
         dataList=new ArrayList<>();
         further=false;

         YAxis yAxis = mChart.getAxisLeft();
         XAxis xAxis = mChart.getXAxis();

         yAxis.setEnabled(true);
         xAxis.setEnabled(true);

        JSONArray arr= null;
        try {
            if(flag) {
                arr = new JSONArray(Globals.pref.getString("TJSONArray", "[]"));
            }else{
                arr = new JSONArray(Globals.pref.getString("PJSONArray", "[]"));
            }
            if(arr.length()>0) {
                dataList.add(0);
                for(int i=0;i<arr.length();i++) {
                    dataList.add(arr.getInt(i));
                }
            }else{
                entries.clear();
            }

            //Toast.makeText(this, "Array : "+ dataList.toString(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

         if(dataList.size()>0 && dataList.size()<15){
             xAxis.setAxisMaxValue(15);
             for(int i=0;i<15;i++) {

                 if(i<dataList.size()) {
                     entries.add(new Entry(i, dataList.get(i)));
                  //   labels.add("Test"+((Integer)i).toString());
                 }
                 else {
                     //entries.add(new Entry(i,0));
                 }
             }
             further=true;
         }else if(dataList.size()>=15){
             xAxis.setAxisMaxValue(dataList.size()+1);
             for(int i=0;i<dataList.size();i++) {
                 entries.add(new Entry(i,dataList.get(i)));
             }
             further=true;
         }

         yAxis.setAxisMaxValue(100);
         //xAxis.setAxisMaxValue(15);
         yAxis.setAxisMinimum(0);

         yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
         xAxis.setPosition(XAxis.XAxisPosition.TOP);
         //leftAxis.setSpaceTop(80);
         mChart.getAxisRight().setEnabled(false);
         if(further) {
             LineDataSet linedataset = new LineDataSet(entries, "Progress Over Time");

             LineData data = new LineData(linedataset);
             mChart.setData(data);
             linedataset.setDrawFilled(true); // to fill the below area of line in graph
             linedataset.setColors(ColorTemplate.COLORFUL_COLORS); // to change the color scheme
             // set the data and list of lables into chart
         }else{
             mChart.clear();
         }

         mChart.setDescription(null);
         mChart.setNoDataText("Currently No Data Available");

    }
}



