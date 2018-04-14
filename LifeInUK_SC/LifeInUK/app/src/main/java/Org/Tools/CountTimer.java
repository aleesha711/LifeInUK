package Org.Tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tecjaunt.www.lifeinuk.R;

import Adapters.ItemDetailAdapter;
import Org.Utility.Globals;
import ui.fragments.MultipleChoice;
import ui.fragments.Statement;
import ui.fragments.TrueFalse;

/**
 * Created by Omer Habib on 12/17/2016.
 */

public class CountTimer {

    private long time_limit;
    private long time;
    private long spend_time;
    private String strtime;
    private Boolean flag=false;
    private Boolean running=false;
    private TextView tv_time;
    private CountDownTimer countDownTimer;
    private Context context;
    private View view;
    private Activity activity;
    private FragmentManager fmg;

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Boolean getRunning() {
        return this.running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public FragmentManager getFmg() {
        return this.fmg;
    }

    public void setFmg(FragmentManager fmg) {
        this.fmg = fmg;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public long getTime_limit() {
        return this.time_limit;
    }

    public void setTime_limit(long time_limit) {
        this.time_limit = time_limit;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getSpend_time() {
        return this.spend_time;
    }

    public void setSpend_time(long spend_time) {
        this.spend_time = spend_time;
    }

    public String getStrtime() {
        return this.strtime;
    }

    public void setStrtime(String strtime) {
        this.strtime = strtime;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public TextView getTv_time() {
        return this.tv_time;
    }

    public void setTv_time(TextView tv_time) {
        this.tv_time = tv_time;
    }

    public void reverseTimer(long Seconds){

        countDownTimer=new android.os.CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {

                int seconds = (int) (millisUntilFinished / 1000);
                time=seconds;
                int minutes = seconds / 60;
                seconds = seconds % 60;
                strtime=" " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds);
                tv_time.setText(strtime);

            }

            public void onFinish() {
                setRunning(false);
                setFlag(true);
                changeProgress();
                showResult();
            }

        }.start();

    }

    public void timerPause() {
        setRunning(false);
        countDownTimer.cancel();
        spend_time=time_limit-time;
        int seconds= (int) spend_time;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        strtime=" " + String.format("%02d", minutes)
                + ":" + String.format("%02d", seconds);
        tv_time.setText(strtime);
    }

    public void timerResume() {
        setRunning(true);
        reverseTimer(time);
    }

    public void timerStart() {
        setRunning(true);
        reverseTimer(time_limit);
    }

    public void showResult() {

        Globals.timer.timerPause();
        String time=Globals.timer.getStrtime();
        tv_time.setVisibility(View.GONE);
        //ll_pause.setVisibility(View.GONE);
        //ll_play.setVisibility(View.GONE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_result_sheet, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        TextView tv_time=(TextView)popupView.findViewById(R.id.tv_popup_result_sheet_time);
        TextView tv_percent=(TextView)popupView.findViewById(R.id.tv_popup_result_sheet_percentage);
        TextView tv_progress=(TextView)popupView.findViewById(R.id.tv_popup_result_sheet_percent_text);

        int ans=0;
        int cor=0;
        int per=0;

        if(Globals.type==1){
            ans=Globals.pref.getInt("AnswerTLA",0);
            cor=Globals.pref.getInt("CorrectTLA",0);
        }else if(Globals.type==2){
            ans=Globals.pref.getInt("AnswerPLA",0);
            cor=Globals.pref.getInt("CorrectPLA",0);
        }

        if(ans>0 ) {
            per = ((cor * 100) / ans);
        }

        tv_time.setText("Time : "+time);
        tv_percent.setText(((Integer)per).toString()+" %");
        if(Globals.type==1) {
            tv_progress.setText("Total Questions : " + Globals.glist.size() + "\nYour Score is " + ((Integer) cor).toString() + " out of " + ((Integer) ans).toString());
        }else{
            tv_progress.setText("Total Questions : " + Globals.glist.size() + "\nYour Score is " + ((Integer) cor).toString() + " out of " + ((Integer) ans).toString());
        }

        TextView tv_remark=(TextView)popupView.findViewById(R.id.tv_popup_result_sheet_review);
        if(per>=75){
            tv_remark.setText("PASS!");
            tv_remark.setTextColor(getActivity().getResources().getColor(R.color.green));
        }else{
            tv_remark.setText("FAIL!");
            tv_remark.setTextColor(getActivity().getResources().getColor(R.color.red));
        }

        Button btn_review=(Button)popupView.findViewById(R.id.btn_popup_result_sheet_review);
        Button btn_menu=(Button)popupView.findViewById(R.id.btn_popup_result_sheet_menu);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showReview();
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Globals.index=0;
                //context.startActivity(new Intent(context, MenuScreen.class));
                activity.finish();

            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private void changeProgress(){

        int size=Globals.answer;
        int correct=0;
        for (int i=0;i<size;i++){
            if(Globals.ilist.get(i).getRemarks()){
                correct++;
            }
        }
        Toast.makeText(context, "size : "+size + " correct : "+correct , Toast.LENGTH_SHORT).show();

        if (Globals.type == 1) {
            //Answered
            int ans1 =size+ Globals.pref.getInt("AnswerTest", 0);
            Globals.editor.putInt("AnswerTest", ans1).commit();

            int ans2 =24+ Globals.pref.getInt("AnswerTLA", 0);
            Globals.editor.putInt("AnswerTLA", ans2).commit();

            //Correct
            int j =correct+ Globals.pref.getInt("CorrectTest", 0);
            Globals.editor.putInt("CorrectTest", j).commit();

            int l = correct + Globals.pref.getInt("CorrectTLA", 0);
            Globals.editor.putInt("CorrectTLA", l).commit();

        } else if (Globals.type == 2) {
            //Answered
            int ans1 = size+Globals.pref.getInt("AnswerPrac", 0);
            Globals.editor.putInt("AnswerPrac", ans1).commit();

            int ans2 =size+ Globals.pref.getInt("AnswerPLA", 0);
            Globals.editor.putInt("AnswerPLA", ans2).commit();

            //correct
            int j =correct+ Globals.pref.getInt("CorrectPrac", 0);
            Globals.editor.putInt("CorrectPrac", j).commit();

            int l =correct+ Globals.pref.getInt("CorrectPLA", 0);
            Globals.editor.putInt("CorrectPLA", l).commit();

            if (Globals.type2 == 0) {
                //Answer
                int ans =size + Globals.pref.getInt("AnswerAll", 0);
                Globals.editor.putInt("AnswerAll", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectAll", 0);
                Globals.editor.putInt("CorrectAll", k).commit();

            } else if (Globals.type2 == 1) {
                //Answer
                int ans =size+ Globals.pref.getInt("AnswerPoli", 0);
                Globals.editor.putInt("AnswerPoli", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectPoli", 0);
                Globals.editor.putInt("CorrectPoli", k).commit();

            } else if (Globals.type2 == 2) {
                //Answer
                int ans = size+Globals.pref.getInt("AnswerLaw", 0);
                Globals.editor.putInt("AnswerLaw", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectLaw", 0);
                Globals.editor.putInt("CorrectLaw", k).commit();

            } else if (Globals.type2 == 3) {
                //Answer
                int ans =size+ Globals.pref.getInt("AnswerHistory", 0);
                Globals.editor.putInt("AnswerHistory", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectHistory", 0);
                Globals.editor.putInt("CorrectHistory", k).commit();

            } else if (Globals.type2 == 4) {
                //Answer
                int ans =size + Globals.pref.getInt("AnswerPop", 0);
                Globals.editor.putInt("AnswerPop", ans).commit();

                //Correct
                int k =correct+ Globals.pref.getInt("CorrectPop", 0);
                Globals.editor.putInt("CorrectPop", k).commit();

            } else if (Globals.type2 == 5) {
                //Answer
                int ans =size+ Globals.pref.getInt("AnswerOther", 0);
                Globals.editor.putInt("AnswerOther", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectOther", 0);
                Globals.editor.putInt("CorrectOther", k).commit();

            } else if (Globals.type2 == 6) {
                //Answer
                int ans =size + Globals.pref.getInt("AnswerMC", 0);
                Globals.editor.putInt("AnswerMC", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectMC", 0);
                Globals.editor.putInt("CorrectMC", k).commit();

            } else if (Globals.type2 == 7) {
                //Answer
                int ans = size+Globals.pref.getInt("AnswerTF", 0);
                Globals.editor.putInt("AnswerTF", ans).commit();

                //Correct
                int k =correct+ Globals.pref.getInt("CorrectTF", 0);
                Globals.editor.putInt("CorrectTF", k).commit();

            } else if (Globals.type2 == 8) {
                //Answer
                int ans =size + Globals.pref.getInt("AnswerSt", 0);
                Globals.editor.putInt("AnswerSt", ans).commit();

                //Correct
                int k = correct+Globals.pref.getInt("CorrectSt", 0);
                Globals.editor.putInt("CorrectSt", k).commit();
            }
        }

    }

    private void showReview() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_detail_sheet, null);

        builder.setView(popupView);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ItemDetailAdapter adptr=new ItemDetailAdapter(context,Globals.ilist);
        String time=Globals.timer.getStrtime();
        //builder.setCancelable(false);
        TextView tv_time=(TextView)popupView.findViewById(R.id.tv_popup_detail_sheet_time);
        tv_time.setText("Time : "+time);
        Button btn_ok=(Button)popupView.findViewById(R.id.btn_popup_detail_sheet_ok);
        ListView lv=(ListView)popupView.findViewById(R.id.lv_detail_sheet);
        lv.setAdapter(adptr);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                Globals.index=i;
                Globals.review=true;
                if (Globals.ilist.get(Globals.index).getCategory().equals("True/False")||Globals.ilist.get(Globals.index).getCategory().equals("Verdad/ Mentira")||Globals.ilist.get(Globals.index).getCategory().equals("صح أم خطأ")) {
                    Fragment frag = new TrueFalse();
                    FragmentTransaction fm0=fmg.beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("Timer");
                    fm0.commit();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Statements") || Globals.ilist.get(Globals.index).getCategory().equals("Afirmaciones") || Globals.ilist.get(Globals.index).getCategory().equals("عبارات")) {
                    Fragment frag = new Statement();
                    FragmentTransaction fm0=fmg.beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("Timer");
                    fm0.commit();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Multiple Choice") || Globals.ilist.get(Globals.index).getCategory().equals("Opci?n M?ltiple") || Globals.ilist.get(Globals.index).getCategory().equals("اختيار من متعدد")) {
                    Fragment frag = new MultipleChoice();
                    FragmentTransaction fm0=fmg.beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("Timer");
                    fm0.commit();
                }

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showResult();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }
}

