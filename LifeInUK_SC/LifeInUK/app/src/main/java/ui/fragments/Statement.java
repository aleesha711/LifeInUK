package ui.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tecjaunt.www.lifeinuk.R;

import org.json.JSONArray;
import org.json.JSONException;

import Adapters.ItemDetailAdapter;
import Org.Tools.FlagQuestions;
import Org.Tools.ItemQuestionDetail;
import Org.Utility.Globals;
import ui.activity.MainActivity;

public class Statement extends Fragment {

    View rootView;
    Fragment frag = null;

    LinearLayout rl_flag;
    LinearLayout rl_unflag;

    Button btn_play;
    Button btn_pause;
    Button btn_quit;
    Button btn_explain;
    Button btn_previous;
    Button btn_next;

    ImageView iv_setting;

    TextView tv_title;
    TextView tv_timer;


    TextView tv_question;
    Button btn_option1;
    Button btn_option2;

    Boolean flag1=false;
    Boolean flag2=false;
    Boolean skip=false;

    String user_ans=null;

    int id=0;


    public Statement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_statement, container, false);
        Globals.timer.setActivity(getActivity());
        findViewby();
        setLayout();
        if (Globals.glist.get(Globals.index).getPass()) {
            if(!Globals.flagQes) {
                if (Globals.ilist.get(Globals.index).getFlag()) {
                    rl_flag.setVisibility(View.GONE);
                    rl_unflag.setVisibility(View.VISIBLE);
                    skip = true;
                    user_ans = Globals.ilist.get(Globals.index).getUser_ans();
                }
            }else {
                skip=false;
                rl_flag.setVisibility(View.VISIBLE);
                rl_unflag.setVisibility(View.GONE);
                user_ans="Z";
            }
            checkPressed();
        }


        rl_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Globals.review) {
                    id=0;
                    changeState();
                    rl_flag.setVisibility(View.GONE);
                    rl_unflag.setVisibility(View.VISIBLE);
                    skip = true;
                    user_ans = "Z";
                }
            }
        });

        rl_unflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Globals.review) {
                    rl_flag.setVisibility(View.VISIBLE);
                    rl_unflag.setVisibility(View.GONE);
                    skip = false;
                    user_ans = "Z";
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onNextClick();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Globals.review) {
                    if (Globals.glist.get(Globals.index).getPass()) {
                        Globals.ilist.get(Globals.index).setUser_ans(user_ans);
                    }
                }
                if (Globals.index > 0)
                {
                    if(Globals.flagQes){
                        if(Globals.findex>0){
                            Globals.findex--;
                            Globals.index=Globals.flist.get(Globals.findex).getIndex();
                            previousQuestion();
                        }
                    }
                    else {
                        Globals.index--;
                        previousQuestion();
                    }
                }
            }
        });
        btn_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExplain(Globals.glist.get(Globals.index).getExplain());
            }
        });

        btn_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Globals.review) {
                    id = 1;
                    changeState();
                    if (Globals.autoNext) {
                        onNextClick();
                    }
                }
            }
        });

        btn_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Globals.review) {
                    id = 2;
                    changeState();
                    if (Globals.autoNext) {
                        onNextClick();
                    }
                }
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_play.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                setPause();
            }
        });

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_quit.setVisibility(View.GONE);
                showResult();
            }
        });



        return rootView;
    }

    private void checkPressed() {
        if(Globals.ilist.get(Globals.index).getUser_ans().equals("A")){
            if(!Globals.review) {
                id = 1;
                changeState();
            }else{
                changeState();
            }
        }else if(Globals.ilist.get(Globals.index).getUser_ans().equals("B")){
            if(!Globals.review) {
                id = 2;
                changeState();
            }else{
                changeState();
            }
        }else if(Globals.ilist.get(Globals.index).getUser_ans().equalsIgnoreCase("Z")){
            if(Globals.review){
                changeState();
            }
        }

    }

    private void changeState() {
        if(!Globals.review) {
            if (id == 1) {
                if (flag1 == false) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    user_ans = "A";
                    flag1 = true;
                    flag2 = false;

                } else {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    flag1 = false;
                }

            } else if (id == 2) {

                if (flag2 == false) {
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    user_ans = "B";
                    flag2 = true;
                    flag1 = false;
                } else {
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    flag2 = false;
                }

            }else{
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                btn_option1.setTextColor(getResources().getColor(R.color.black));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                btn_option2.setTextColor(getResources().getColor(R.color.black));

                user_ans = null;
                flag1 = false;
                flag2 = false;

            }
        }else{
            int ind=Globals.index;
            if(Globals.ilist.get(ind).getRemarks()){
                if(Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("A")){
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));


                }else {
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));

                }
            }else{
                if(Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("B")){
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));


                }else if(Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("A")) {
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));

                }else{
                    if(Globals.ilist.get(ind).getAnswer().contains("A")) {
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option1.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option2.setTextColor(getResources().getColor(R.color.white));

                    }

                }
            }
        }
    }

    private void findViewby() {

        iv_setting=(ImageView)getActivity().findViewById(R.id.iv_action_bar_setting);

        tv_title=(TextView)getActivity().findViewById(R.id.tv_action_bar_title);
        tv_timer=(TextView)getActivity().findViewById(R.id.tv_action_bar_timer);
        btn_explain=(Button) getActivity().findViewById(R.id.btn_bottom_bar_explain);
        btn_previous=(Button) getActivity().findViewById(R.id.btn_bottom_bar_previous);
        btn_next=(Button)getActivity().findViewById(R.id.btn_bottom_bar_next);
        rl_flag=(LinearLayout)getActivity().findViewById(R.id.ll_bottom_bar_flag);
        rl_unflag=(LinearLayout)getActivity().findViewById(R.id.ll_bottom_bar_flagged);
        btn_play=(Button) getActivity().findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button) getActivity().findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button) getActivity().findViewById(R.id.btn_action_bar_quit);

        if(!Globals.review) {
            btn_pause.setVisibility(View.VISIBLE);
            btn_play.setVisibility(View.GONE);
            btn_quit.setVisibility(View.GONE);
        }else{
            btn_quit.setVisibility(View.VISIBLE);
            btn_pause.setVisibility(View.GONE);
            btn_play.setVisibility(View.GONE);

        }

        RelativeLayout rl_bottom = (RelativeLayout) getActivity().findViewById(R.id.rl_bottom_bar);
        rl_bottom.setVisibility(View.VISIBLE);

        tv_question=(TextView)rootView.findViewById(R.id.tv_statement_question);
        btn_option1=(Button)rootView.findViewById(R.id.btn_statement_option1);
        btn_option2=(Button)rootView.findViewById(R.id.btn_statement_option2);

        tv_timer.setVisibility(View.VISIBLE);
        iv_setting.setVisibility(View.GONE);

        tv_title.setText("Question # "+(Globals.index+1));

        if(Globals.type==1) {
            rl_unflag.setVisibility(View.GONE);
            rl_flag.setVisibility(View.VISIBLE);
            btn_explain.setVisibility(View.GONE);

        }else if(Globals.flag) {
            rl_unflag.setVisibility(View.GONE);
            rl_flag.setVisibility(View.GONE);
            btn_explain.setVisibility(View.VISIBLE);

        }else{
            rl_unflag.setVisibility(View.GONE);
            rl_flag.setVisibility(View.GONE);
            btn_explain.setVisibility(View.VISIBLE);
        }

    }

    public void showExplain(String s) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_explain, null);
        builder.setView(popupView);
        //builder.setCancelable(false);

        TextView exp= (TextView) popupView.findViewById(R.id.tv_popup_explain);
        Button btn_cancel1 = (Button) popupView.findViewById(R.id.btn_popup_explain);

        final AlertDialog dialog = builder.create();

        btn_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        exp.setText(s);


        dialog.show();
        //dialog.setCancelable(false);

    }

    public void setLayout(){
        tv_question.setText(Globals.glist.get(Globals.index).getQuestion());
        btn_option1.setText(Globals.glist.get(Globals.index).getOption1());
        btn_option2.setText(Globals.glist.get(Globals.index).getOption2());

    }

    public void nextQuestion(){
        if(!Globals.review) {
            if (!Globals.flagQes) {
                if (Globals.index < Globals.glist.size()) {

                    if (Globals.glist.get(Globals.index).getCategory1().equals("True/False")) {
                        frag = new TrueFalse();
                        setFrag();
                    } else if (Globals.glist.get(Globals.index).getCategory1().equals("Statements")) {
                        frag = new Statement();
                        setFrag();
                    } else if (Globals.glist.get(Globals.index).getCategory1().equals("Multiple Choice") ) {
                        frag = new MultipleChoice();
                        setFrag();
                    }
                }else if(Globals.flagReview) {
                    Globals.flagReview=false;
                    for (int j = 0; j < Globals.answer; j++) {
                        if (Globals.ilist.get(j).getFlag()) {
                            Globals.flist.add(new FlagQuestions(j));
                            Globals.ilist.get(j).setFlag(false);
                        }
                    }
                    if (Globals.flist.size() > 0) {
                       //Toast.makeText(getActivity(), "Flag Questions are " + Globals.flist.size(), Toast.LENGTH_SHORT).show();
                        //changeProgress();
                        //Globals.timer.timerResume();
                        reviewFlag();
                    }else {
                        changeProgress();
                        showResult();
                    }
                }else {
                    changeProgress();
                    showResult();
                }

            }else if(Globals.flagQes){
                if (Globals.index < Globals.ilist.size()) {
                    if (Globals.ilist.get(Globals.index).getCategory().equals("True/False")) {
                        frag = new TrueFalse();
                        setFrag();
                    } else if (Globals.ilist.get(Globals.index).getCategory().equals("Statements")) {
                        frag = new Statement();
                        setFrag();
                    } else if (Globals.ilist.get(Globals.index).getCategory().equals("Multiple Choice")) {
                        frag = new MultipleChoice();
                        setFrag();
                    }
                }else
                {
                    changeProgress();
                    showResult();
                }
            }
        }else{

            if (Globals.index < Globals.ilist.size()) {

                if (Globals.ilist.get(Globals.index).getCategory().equals("True/False")) {
                    frag = new TrueFalse();
                    setFrag();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Statements")) {
                    frag = new Statement();
                    setFrag();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Multiple Choice")) {
                    frag = new MultipleChoice();
                    setFrag();
                }
            } else {
                showResult();
            }
        }
    }

    public void previousQuestion(){
        if(!Globals.review) {
            if (Globals.index >= 0) {
                if (Globals.glist.get(Globals.index).getCategory1().equals("True/False") || Globals.glist.get(Globals.index).getCategory1().equals("Verdad/ Mentira") || Globals.glist.get(Globals.index).getCategory1().equals("صح أم خطأ")) {
                    frag = new TrueFalse();
                    setFrag();
                } else if (Globals.glist.get(Globals.index).getCategory1().equals("Statements") || Globals.glist.get(Globals.index).getCategory1().equals("Afirmaciones") || Globals.glist.get(Globals.index).getCategory1().equals("عبارات")) {
                    frag = new Statement();
                    setFrag();
                } else if (Globals.glist.get(Globals.index).getCategory1().equals("Multiple Choice") || Globals.glist.get(Globals.index).getCategory1().equals("Opci?n M?ltiple") || Globals.glist.get(Globals.index).getCategory1().equals("اختيار من متعدد")) {
                    frag = new MultipleChoice();
                    setFrag();
                }
            }
        }else {
            if (Globals.index >= 0) {
                if (Globals.ilist.get(Globals.index).getCategory().equals("True/False") || Globals.ilist.get(Globals.index).getCategory().equals("Verdad/ Mentira") || Globals.ilist.get(Globals.index).getCategory().equals("صح أم خطأ")) {
                    frag = new TrueFalse();
                    setFrag();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Statements") || Globals.ilist.get(Globals.index).getCategory().equals("Afirmaciones") || Globals.ilist.get(Globals.index).getCategory().equals("عبارات")) {
                    frag = new Statement();
                    setFrag();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Multiple Choice") || Globals.ilist.get(Globals.index).getCategory().equals("Opci?n M?ltiple") || Globals.ilist.get(Globals.index).getCategory().equals("اختيار من متعدد")) {
                    frag = new MultipleChoice();
                    setFrag();
                }
            }
        }
    }

    public void setFrag(){

        FragmentTransaction fm0=getActivity().getSupportFragmentManager().beginTransaction();
        fm0.replace(R.id.fl_main_activity,frag);
        //fm0.addToBackStack(null);
        fm0.commit();
    }

    private void showReview() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_detail_sheet, null);
        builder.setView(popupView);
        ItemDetailAdapter adptr=new ItemDetailAdapter(getActivity(),Globals.ilist);
        String time=Globals.timer.getStrtime();
        //builder.setCancelable(false);
        TextView tv_time=(TextView)popupView.findViewById(R.id.tv_popup_detail_sheet_time);
        tv_time.setText("Time : "+time);
        Button btn_ok=(Button)popupView.findViewById(R.id.btn_popup_detail_sheet_ok);
        ListView lv=(ListView)popupView.findViewById(R.id.lv_detail_sheet);
        lv.setAdapter(adptr);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                Globals.index=i;
                Globals.review=true;
                if (Globals.ilist.get(Globals.index).getCategory().equals("True/False")||Globals.ilist.get(Globals.index).getCategory().equals("Verdad/ Mentira")||Globals.ilist.get(Globals.index).getCategory().equals("صح أم خطأ")) {
                    frag = new TrueFalse();
                    FragmentTransaction fm0=getActivity().getSupportFragmentManager().beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("ST");
                    fm0.commit();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Statements") || Globals.ilist.get(Globals.index).getCategory().equals("Afirmaciones") || Globals.ilist.get(Globals.index).getCategory().equals("عبارات")) {
                    frag = new Statement();
                    FragmentTransaction fm0=getActivity().getSupportFragmentManager().beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("ST");
                    fm0.commit();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Multiple Choice") || Globals.ilist.get(Globals.index).getCategory().equals("Opci?n M?ltiple") || Globals.ilist.get(Globals.index).getCategory().equals("اختيار من متعدد")) {
                    frag = new MultipleChoice();
                    FragmentTransaction fm0=getActivity().getSupportFragmentManager().beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("ST");
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

    private void changeProgress(){

        int size=Globals.answer;
        int correct=0;
        for (int i=0;i<size;i++){
            if(Globals.ilist.get(i).getRemarks()){
                correct++;
            }
        }
        //Toast.makeText(getActivity(), "size : "+size + " correct : "+correct , Toast.LENGTH_SHORT).show();

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
            int per=0;
            if(ans2>0 ) {
                per = ((l * 100) / ans2);
            }
            storeIntArray(per);

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
            int per=0;
            if(ans2>0 ) {
                per = ((l * 100) / ans2);
            }
            storeIntArray(per);

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

    private void onNextClick(){
        if(!Globals.review) {
            if (flag1 || flag2 || skip) {
                if (!Globals.flagQes) {

                    if (Globals.index <Globals.glist.size() && !Globals.glist.get(Globals.index).getPass()) {
                        Globals.ilist.add(new ItemQuestionDetail(Globals.index + 1, Globals.glist.get(Globals.index).getQuestion(), Globals.glist.get(Globals.index).getAnswer(), user_ans, Globals.glist.get(Globals.index).getCategory1(), skip, false));
                        Globals.answer++;
                    } else if(Globals.index <Globals.ilist.size()) {
                        Globals.ilist.get(Globals.index).setUser_ans(user_ans);
                    }
                    if(Globals.index <Globals.glist.size()) {
                        Globals.glist.get(Globals.index).setPass(true);

                        if (Globals.ilist.get(Globals.index).getAnswer().contains(user_ans)) {
                            Globals.ilist.get(Globals.index).setRemarks(true);
                        } else {
                            Globals.ilist.get(Globals.index).setRemarks(false);
                        }

                        Globals.ilist.get(Globals.index).setFlag(skip);

                        Globals.index++;
                        nextQuestion();
                    }

                }else if(Globals.flagQes){
                    Globals.ilist.get(Globals.index).setUser_ans(user_ans);
                    if (Globals.ilist.get(Globals.index).getAnswer().contains(user_ans)) {
                        Globals.ilist.get(Globals.index).setRemarks(true);
                    } else {
                        Globals.ilist.get(Globals.index).setRemarks(false);
                    }
                    Globals.findex++;
                    if(Globals.findex<Globals.flist.size()){
                        if(Globals.ilist.size()>Globals.flist.get(Globals.findex).getIndex()) {
                            Globals.index = Globals.flist.get(Globals.findex).getIndex();
                            nextQuestion();
                        }
                        else {
                            Globals.index=Globals.ilist.size();
                            nextQuestion();
                        }
                    }else {
                        Globals.index=Globals.ilist.size();
                        nextQuestion();
                    }
                }

            } else{
                if(Globals.index <Globals.glist.size())
                    Toast.makeText(getActivity(), "Please Select Any Option", Toast.LENGTH_SHORT).show();
            }
        }else {
            Globals.index++;
            nextQuestion();
        }
    }

    private void showResult() {
        Globals.timer.timerPause();
        String time=Globals.timer.getStrtime();
        tv_timer.setVisibility(View.GONE);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_result_sheet, null);
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

        tv_time.setText(time);
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
                //startActivity(new Intent(getActivity(),MenuScreen.class));
                getActivity().finish();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private void setPause() {

        Globals.timer.timerPause();
        String time=Globals.timer.getStrtime();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_pause, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        TextView tv_time=(TextView)popupView.findViewById(R.id.tv_popup_pause_time);

        tv_time.setText("Time : "+time);

        Button btn_resume=(Button)popupView.findViewById(R.id.btn_popup_pause_resume);
        Button btn_quit=(Button)popupView.findViewById(R.id.btn_popup_pause_quit);

        final AlertDialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                btn_play.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);
                Globals.timer.timerResume();
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeProgress();
                showResult();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private void reviewFlag() {
        Globals.timer.timerPause();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_review_flag, null);
        builder.setView(popupView);
        //builder.setCancelable(false);
        Button btn_cancel=(Button)popupView.findViewById(R.id.btn_popup_review_flag_no);
        Button btn_ok=(Button)popupView.findViewById(R.id.btn_popup_review_flag_yes);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Globals.flagQes=true;
                Globals.findex=0;
                Globals.index=Globals.flist.get(Globals.findex).getIndex();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.flagQes=false;
                dialog.dismiss();
                changeProgress();
                showResult();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    public void storeIntArray(int value){
        if(Globals.type==1){
            try {
                JSONArray arr = new JSONArray(Globals.pref.getString("TJSONArray", "[]"));
                arr.put(value);
                Globals.editor.putString("TJSONArray", arr.toString()).commit();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error Adding Data In Progress", Toast.LENGTH_SHORT).show();
            }

        }else if(Globals.type==2) {
            try {
                JSONArray arr = new JSONArray(Globals.pref.getString("PJSONArray", "[]"));
                arr.put(value);
                Globals.editor.putString("PJSONArray", arr.toString()).commit();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error Adding Data In Progress", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
