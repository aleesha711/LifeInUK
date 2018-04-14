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

public class MultipleChoice extends Fragment implements View.OnClickListener {

    View rootView;
    Fragment frag = null;

    LinearLayout rl_flag;
    LinearLayout rl_unflag;

    Button btn_play;
    Button btn_pause;
    Button btn_quit;

    ImageView iv_setting;

    TextView tv_title;
    TextView tv_timer;

    RelativeLayout rl_bottom;
    Button btn_cancel;
    Button btn_ok;

    Button btn_explain;
    Button btn_previous;
    Button btn_next;

    TextView tv_question;
    TextView tv_option;
    Button btn_option1;
    Button btn_option2;
    Button btn_option3;
    Button btn_option4;

    Boolean flag1=false;
    Boolean flag2=false;
    Boolean flag3=false;
    Boolean flag4=false;
    Boolean skip=false;
    Boolean flagTwo=false;
    String user_ans=null;
    String user_ans1=null;
    String user_ans2=null;
    String cor_ans1=null;
    String cor_ans2=null;
    int id=0;
    int id1=0;
    int id2=0;
    int multi=0;
    int btn_count=0;



    public MultipleChoice() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_multiple_choice, container, false);
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



        rl_flag.setOnClickListener(this);

        rl_unflag.setOnClickListener(this);

        btn_next.setOnClickListener(this);

        btn_previous.setOnClickListener(this);

        //showExplain(Globals.glist.get(Globals.index).getExplain());

        btn_explain.setOnClickListener(this);


        btn_option1.setOnClickListener(this);

        btn_option2.setOnClickListener(this);

        btn_option3.setOnClickListener(this);

        btn_option4.setOnClickListener(this);

        btn_play.setOnClickListener(this);

        btn_pause.setOnClickListener(this);

        btn_quit.setOnClickListener(this);


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
        }else if(Globals.ilist.get(Globals.index).getUser_ans().equals("C")){
            if(!Globals.review) {
                id = 3;
                changeState();
            }else{
                changeState();
            }
        }else if(Globals.ilist.get(Globals.index).getUser_ans().equals("D")){
            if(!Globals.review) {
                id = 4;
                changeState();
            }else{
                changeState();
            }
        }else if(Globals.ilist.get(Globals.index).getUser_ans().length()>2){
            if(!Globals.review) {
                flagTwo=true;
                setIds();
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
            if (!flagTwo) {
                if (id == 1) {
                    if (flag1 == false) {
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option1.setTextColor(getResources().getColor(R.color.white));
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));
                        user_ans = "A";
                        flag1 = true;
                        flag2 = false;
                        flag3 = false;
                        flag4 = false;

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
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));
                        user_ans = "B";
                        flag2 = true;
                        flag1 = false;
                        flag3 = false;
                        flag4 = false;
                    } else {
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        flag2 = false;
                    }

                } else if (id == 3) {
                    if (flag3 == false) {
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option3.setTextColor(getResources().getColor(R.color.white));
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option1.setTextColor(getResources().getColor(R.color.black));
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));
                        user_ans = "C";
                        flag3 = true;
                        flag1 = false;
                        flag2 = false;
                        flag4 = false;

                    } else {
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));
                        flag3 = false;
                    }

                } else if (id == 4) {
                    if (flag4 == false) {
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option4.setTextColor(getResources().getColor(R.color.white));
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option1.setTextColor(getResources().getColor(R.color.black));
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));
                        user_ans = "D";
                        flag4 = true;
                        flag1 = false;
                        flag2 = false;
                        flag3 = false;
                    } else {
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));
                        flag4 = false;
                    }
                } else{
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));

                    user_ans = null;
                    flag4 = false;
                    flag1 = false;
                    flag2 = false;
                    flag3 = false;
                }
            }else{
                doubleClick();
            }
        }
        else {
            if (!flagTwo) {
                int ind = Globals.index;
                //Toast.makeText(getActivity(), "Ans : " + Globals.ilist.get(ind).getAnswer(), Toast.LENGTH_SHORT).show();
                if (Globals.ilist.get(ind).getRemarks()) {
                    if (Globals.ilist.get(ind).getAnswer().contains("A")) {
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option1.setTextColor(getResources().getColor(R.color.white));
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));


                    } else if (Globals.ilist.get(ind).getAnswer().contains("B")) {
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option2.setTextColor(getResources().getColor(R.color.white));
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option1.setTextColor(getResources().getColor(R.color.black));
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));


                    } else if (Globals.ilist.get(ind).getAnswer().contains("C")) {
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option3.setTextColor(getResources().getColor(R.color.white));
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option1.setTextColor(getResources().getColor(R.color.black));
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option4.setTextColor(getResources().getColor(R.color.black));


                    } else if (Globals.ilist.get(ind).getAnswer().contains("D")) {
                        btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                        btn_option4.setTextColor(getResources().getColor(R.color.white));
                        btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option1.setTextColor(getResources().getColor(R.color.black));
                        btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option2.setTextColor(getResources().getColor(R.color.black));
                        btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                        btn_option3.setTextColor(getResources().getColor(R.color.black));

                    }
                } else {
                    if (Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("A")) {
                        if (Globals.ilist.get(ind).getAnswer().contains("B")) {
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option3.setTextColor(getResources().getColor(R.color.black));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option4.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("C")) {
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option2.setTextColor(getResources().getColor(R.color.black));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option4.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("D")) {
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option3.setTextColor(getResources().getColor(R.color.black));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option2.setTextColor(getResources().getColor(R.color.black));
                        }

                    } else if (Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("B")) {
                        if (Globals.ilist.get(ind).getAnswer().contains("A")) {
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option3.setTextColor(getResources().getColor(R.color.black));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option4.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("C")) {
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option1.setTextColor(getResources().getColor(R.color.black));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option4.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("D")) {
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option3.setTextColor(getResources().getColor(R.color.black));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option1.setTextColor(getResources().getColor(R.color.black));
                        }
                    } else if (Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("C")) {
                        if (Globals.ilist.get(ind).getAnswer().contains("A")) {
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option2.setTextColor(getResources().getColor(R.color.black));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option4.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("B")) {
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option1.setTextColor(getResources().getColor(R.color.black));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option4.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("D")) {
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option2.setTextColor(getResources().getColor(R.color.black));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option1.setTextColor(getResources().getColor(R.color.black));
                        }
                    } else if (Globals.ilist.get(ind).getUser_ans().equalsIgnoreCase("D")) {
                        if (Globals.ilist.get(ind).getAnswer().contains("A")) {
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option3.setTextColor(getResources().getColor(R.color.black));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option2.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("B")) {
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option1.setTextColor(getResources().getColor(R.color.black));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option3.setTextColor(getResources().getColor(R.color.black));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("C")) {
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option2.setTextColor(getResources().getColor(R.color.black));
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                            btn_option1.setTextColor(getResources().getColor(R.color.black));
                        }

                    } else {
                        if (Globals.ilist.get(ind).getAnswer().contains("A")) {
                            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option1.setTextColor(getResources().getColor(R.color.white));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("B")) {
                            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option2.setTextColor(getResources().getColor(R.color.white));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("C")) {
                            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option3.setTextColor(getResources().getColor(R.color.white));
                        } else if (Globals.ilist.get(ind).getAnswer().contains("D")) {
                            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                            btn_option4.setTextColor(getResources().getColor(R.color.white));

                        }

                    }
                }

            }else{
                doubleClick();
            }
        }
    }

    private void setIds(){
        String ans1=Character.toString(Globals.ilist.get(Globals.index).getUser_ans().charAt(0));
        String ans2=Character.toString(Globals.ilist.get(Globals.index).getUser_ans().charAt(4));
        btn_count=2;
        if(ans1.equalsIgnoreCase("A") && ans2.equalsIgnoreCase("B")){
            id1=1;
            id2=2;
            flag1=true;
            flag2=true;
        }else if(ans1.equalsIgnoreCase("A") && ans2.equalsIgnoreCase("C")){
            id1=1;
            id2=3;
            flag1=true;
            flag3=true;
        }else if(ans1.equalsIgnoreCase("A") && ans2.equalsIgnoreCase("D")){
            id1=1;
            id2=4;
            flag1=true;
            flag4=true;
        }else if(ans1.equalsIgnoreCase("B") && ans2.equalsIgnoreCase("C")){
            id1=2;
            id2=3;
            flag2=true;
            flag3=true;
        }else if(ans1.equalsIgnoreCase("B") && ans2.equalsIgnoreCase("D")){
            id1=2;
            id2=4;
            flag2=true;
            flag4=true;
        }else if(ans1.equalsIgnoreCase("C") && ans2.equalsIgnoreCase("D")){
            id1=3;
            id2=4;
            flag3=true;
            flag4=true;
        }
    }

    private void getReviewIds(){
        user_ans1=Character.toString(Globals.ilist.get(Globals.index).getUser_ans().charAt(0));
        user_ans2=Character.toString(Globals.ilist.get(Globals.index).getUser_ans().charAt(4));
        cor_ans1=Character.toString(Globals.ilist.get(Globals.index).getAnswer().charAt(0));
        cor_ans2=Character.toString(Globals.ilist.get(Globals.index).getAnswer().charAt(4));
    }

    private void findViewby() {

        iv_setting=(ImageView)getActivity().findViewById(R.id.iv_action_bar_setting);
        tv_title=(TextView)getActivity().findViewById(R.id.tv_action_bar_title);
        tv_timer=(TextView)getActivity().findViewById(R.id.tv_action_bar_timer);
        btn_play=(Button)getActivity().findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)getActivity().findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)getActivity().findViewById(R.id.btn_action_bar_quit);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);
        tv_timer.setVisibility(View.VISIBLE);
        iv_setting.setVisibility(View.GONE);

        rl_bottom=(RelativeLayout)getActivity().findViewById(R.id.rl_bottom_bar);
        rl_bottom.setVisibility(View.VISIBLE);

        btn_explain=(Button)getActivity().findViewById(R.id.btn_bottom_bar_explain);
        btn_previous=(Button) getActivity().findViewById(R.id.btn_bottom_bar_previous);
        btn_next=(Button) getActivity().findViewById(R.id.btn_bottom_bar_next);
        rl_flag=(LinearLayout)getActivity().findViewById(R.id.ll_bottom_bar_flag);
        rl_unflag=(LinearLayout)getActivity().findViewById(R.id.ll_bottom_bar_flagged);

        tv_question=(TextView)rootView.findViewById(R.id.tv_multiple_choice_question);
        tv_option=(TextView)rootView.findViewById(R.id.tv_multiple_choice_selection);
        btn_option1=(Button)rootView.findViewById(R.id.btn_multiple_choice_option1);
        btn_option2=(Button)rootView.findViewById(R.id.btn_multiple_choice_option2);
        btn_option3=(Button)rootView.findViewById(R.id.btn_multiple_choice_option3);
        btn_option4=(Button)rootView.findViewById(R.id.btn_multiple_choice_option4);

        if(!Globals.review) {
            btn_pause.setVisibility(View.VISIBLE);
            btn_play.setVisibility(View.GONE);
            btn_quit.setVisibility(View.GONE);
        }else{
            btn_quit.setVisibility(View.VISIBLE);
            btn_pause.setVisibility(View.GONE);
            btn_play.setVisibility(View.GONE);
        }

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

        if(!Globals.flagQes) {
            if (Globals.glist.get(Globals.index).getAnswer().length() > 1) {
                tv_option.setText("Please Select Two Options");
                flagTwo=true;
            } else {
                tv_option.setText("Please Select One Option");
                flagTwo=false;
            }
        }else{
            if (Globals.ilist.get(Globals.index).getAnswer().length() > 1) {
                tv_option.setText("Please Select Two Options");
                flagTwo=true;
            } else {
                tv_option.setText("Please Select One Option");
                flagTwo=false;
            }
        }

        tv_title.setText("Question # "+(Globals.index+1));


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
        btn_option3.setText(Globals.glist.get(Globals.index).getOption3());
        btn_option4.setText(Globals.glist.get(Globals.index).getOption4());
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
                       // Toast.makeText(getActivity(), "Flag Questions are " + Globals.flist.size(), Toast.LENGTH_SHORT).show();
                        //changeProgress();
                        reviewFlag();
                    }else {
                        changeProgress();
                        showResult();
                    }
                }else{
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

    private void showReview() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_detail_sheet, null);
        builder.setView(popupView);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ItemDetailAdapter adptr=new ItemDetailAdapter(getActivity(),Globals.ilist);
        String time=Globals.timer.getStrtime();
        //builder.setCancelable(false);

        TextView tv_time=(TextView)popupView.findViewById(R.id.tv_popup_detail_sheet_time);
        tv_time.setText("Time : "+time);
        btn_ok=(Button)popupView.findViewById(R.id.btn_popup_detail_sheet_ok);
        ListView lv=(ListView)popupView.findViewById(R.id.lv_detail_sheet);
        lv.setAdapter(adptr);

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
                    //fm0.addToBackStack("MC");
                    fm0.commit();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Statements") || Globals.ilist.get(Globals.index).getCategory().equals("Afirmaciones") || Globals.ilist.get(Globals.index).getCategory().equals("عبارات")) {
                    frag = new Statement();
                    FragmentTransaction fm0=getActivity().getSupportFragmentManager().beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("MC");
                    fm0.commit();
                } else if (Globals.ilist.get(Globals.index).getCategory().equals("Multiple Choice") || Globals.ilist.get(Globals.index).getCategory().equals("Opci?n M?ltiple") || Globals.ilist.get(Globals.index).getCategory().equals("اختيار من متعدد")) {
                    frag = new MultipleChoice();
                    FragmentTransaction fm0=getActivity().getSupportFragmentManager().beginTransaction();
                    fm0.replace(R.id.fl_main_activity,frag);
                    //fm0.addToBackStack("MC");
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

    private void changeProgress(){

        int size=Globals.answer;
        int correct=0;
        for (int i=0;i<size;i++){
            if(Globals.ilist.get(i).getRemarks()){
                correct++;
            }
        }
       // Toast.makeText(getActivity(), "size : "+size + " correct : "+correct , Toast.LENGTH_SHORT).show();

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
            if(!flagTwo){
                if (flag1 || flag2 || flag3 || flag4 || skip) {
                    if (!Globals.flagQes) {

                        if (Globals.index <Globals.glist.size() && !Globals.glist.get(Globals.index).getPass()) {
                            Globals.ilist.add(new ItemQuestionDetail(Globals.index + 1, Globals.glist.get(Globals.index).getQuestion(), Globals.glist.get(Globals.index).getAnswer(), user_ans, Globals.glist.get(Globals.index).getCategory1(), skip, false));
                            Globals.answer++;
                        } else if(Globals.index <Globals.ilist.size()){
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
            }else{
                if ((flag1 && flag2) ||(flag1 && flag3) ||(flag1 && flag4) ||(flag2 && flag3) ||(flag2 && flag4) ||(flag3 && flag4) || skip) {
                    if (!Globals.flagQes) {

                        if (Globals.index <Globals.glist.size() && !Globals.glist.get(Globals.index).getPass()) {
                            Globals.ilist.add(new ItemQuestionDetail(Globals.index + 1, Globals.glist.get(Globals.index).getQuestion(), Globals.glist.get(Globals.index).getAnswer(), user_ans, Globals.glist.get(Globals.index).getCategory1(), skip, false));
                            Globals.answer++;
                        } else if(Globals.index <Globals.ilist.size()){
                            Globals.ilist.get(Globals.index).setUser_ans(user_ans);
                        }
                        if(Globals.index <Globals.glist.size()) {
                            Globals.glist.get(Globals.index).setPass(true);

                            if (Globals.ilist.get(Globals.index).getAnswer().equalsIgnoreCase(user_ans)) {
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
                        if (Globals.ilist.get(Globals.index).getAnswer().equalsIgnoreCase(user_ans)) {
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
                        Toast.makeText(getActivity(), "Please Select Two Options", Toast.LENGTH_SHORT).show();
                }

            }

        }else {
            Globals.index++;
            nextQuestion();
        }
    }

    private void increment() {
        if (Globals.type == 1) {
            int i = Globals.pref.getInt("AnswerTest", 0);
            i++;
            Globals.editor.putInt("AnswerTest", i).commit();

            int k = Globals.pref.getInt("AnswerTLA", 0);
            k++;
            Globals.editor.putInt("AnswerTLA", k).commit();

        } else {
            int j = Globals.pref.getInt("AnswerPrac", 0);
            j++;
            Globals.editor.putInt("AnswerPrac", j).commit();

            int k = Globals.pref.getInt("AnswerPLA", 0);
            k++;
            Globals.editor.putInt("AnswerPLA", k).commit();

            if (Globals.type2 == 0) {
                int i = Globals.pref.getInt("AnswerAll", 0);
                i++;
                Globals.editor.putInt("AnswerAll", i).commit();
            } else if (Globals.type2 == 1) {
                int i = Globals.pref.getInt("AnswerPoli", 0);
                i++;
                Globals.editor.putInt("AnswerPoli", i).commit();
            } else if (Globals.type2 == 2) {
                int i = Globals.pref.getInt("AnswerLaw", 0);
                i++;
                Globals.editor.putInt("AnswerLaw", i).commit();
            } else if (Globals.type2 == 3) {
                int i = Globals.pref.getInt("AnswerHistory", 0);
                i++;
                Globals.editor.putInt("AnswerHistory", i).commit();
            } else if (Globals.type2 == 4) {
                int i = Globals.pref.getInt("AnswerPop", 0);
                i++;
                Globals.editor.putInt("AnswerPop", i).commit();
            } else if (Globals.type2 == 5) {
                int i = Globals.pref.getInt("AnswerOther", 0);
                i++;
                Globals.editor.putInt("AnswerOther", i).commit();
            } else if (Globals.type2 == 6) {
                int i = Globals.pref.getInt("AnswerMC", 0);
                i++;
                Globals.editor.putInt("AnswerMC", i).commit();
            } else if (Globals.type2 == 7) {
                int i = Globals.pref.getInt("AnswerTF", 0);
                i++;
                Globals.editor.putInt("AnswerTF", i).commit();
            } else if (Globals.type2 == 8) {
                int i = Globals.pref.getInt("AnswerSt", 0);
                i++;
                Globals.editor.putInt("AnswerSt", i).commit();
            }
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

    private void doubleClick() {
        if (!Globals.review) {
            if(id1==1 && id2==2){
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));
                    user_ans = "A / B";
                    flag1 = true;
                    flag2 = true;
                    flag3 = false;
                    flag4 = false;
            }
            if(id1==1 && id2==3){
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option3.setTextColor(getResources().getColor(R.color.white));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));
                    user_ans = "A / C";
                    flag1 = true;
                    flag2 = false;
                    flag3 = true;
                    flag4 = false;
            }
            if(id1==1 && id2==4){
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option4.setTextColor(getResources().getColor(R.color.white));
                    user_ans = "A / D";
                    flag1 = true;
                    flag2 = false;
                    flag3 = false;
                    flag4 = true;
            }
            if(id1==2 && id2==3){
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option3.setTextColor(getResources().getColor(R.color.white));
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));
                    user_ans = "B / C";
                    flag1 = false;
                    flag2 = true;
                    flag3 = true;
                    flag4 = false;
            } if(id1==2 && id2==4){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                btn_option1.setTextColor(getResources().getColor(R.color.black));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                btn_option3.setTextColor(getResources().getColor(R.color.black));
                user_ans = "B / D";
                flag1 = false;
                flag2 = true;
                flag3 = false;
                flag4 = true;
            }
            if(id1==3 && id2==4){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                btn_option1.setTextColor(getResources().getColor(R.color.black));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                btn_option2.setTextColor(getResources().getColor(R.color.black));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
                user_ans = "C / D";
                flag1 = false;
                flag2 = false;
                flag3 = true;
                flag4 = true;
            }

        }else{
            int ind = Globals.index;
           // Toast.makeText(getActivity(), "Ans : " + Globals.ilist.get(ind).getAnswer(), Toast.LENGTH_SHORT).show();
            if(Globals.ilist.get(ind).getRemarks()){
                if (Globals.ilist.get(ind).getAnswer().contains("A / B")) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));


                } else if (Globals.ilist.get(ind).getAnswer().contains("A / C")) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option3.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));


                } else if (Globals.ilist.get(ind).getAnswer().contains("A / D")) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option1.setTextColor(getResources().getColor(R.color.white));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option4.setTextColor(getResources().getColor(R.color.white));


                } else if (Globals.ilist.get(ind).getAnswer().contains("B / C")) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option3.setTextColor(getResources().getColor(R.color.white));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));

                } else if (Globals.ilist.get(ind).getAnswer().contains("B / D")) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option2.setTextColor(getResources().getColor(R.color.white));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option4.setTextColor(getResources().getColor(R.color.white));

                } else if (Globals.ilist.get(ind).getAnswer().contains("C / D")) {
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option3.setTextColor(getResources().getColor(R.color.white));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                    btn_option4.setTextColor(getResources().getColor(R.color.white));

                }
            }else{
                getReviewIds();
                wrongQuestions();
            }
        }
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

    private void wrongQuestions(){
        if(user_ans1.equalsIgnoreCase("A") && user_ans2.equalsIgnoreCase("B")){
            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option1.setTextColor(getResources().getColor(R.color.white));
            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option2.setTextColor(getResources().getColor(R.color.white));
            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option3.setTextColor(getResources().getColor(R.color.black));
            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option4.setTextColor(getResources().getColor(R.color.black));
            if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("C")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("D")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("C")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("D")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("C") && cor_ans2.equalsIgnoreCase("D")){
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }
        }else if(user_ans1.equalsIgnoreCase("A") && user_ans2.equalsIgnoreCase("C")){
            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option1.setTextColor(getResources().getColor(R.color.white));
            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option3.setTextColor(getResources().getColor(R.color.white));
            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option2.setTextColor(getResources().getColor(R.color.black));
            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option4.setTextColor(getResources().getColor(R.color.black));
            if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("B")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("D")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("C")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("D")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("C") && cor_ans2.equalsIgnoreCase("D")){
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }
        }else if(user_ans1.equalsIgnoreCase("A") && user_ans2.equalsIgnoreCase("D")){
            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option1.setTextColor(getResources().getColor(R.color.white));
            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option4.setTextColor(getResources().getColor(R.color.white));
            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option2.setTextColor(getResources().getColor(R.color.black));
            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option3.setTextColor(getResources().getColor(R.color.black));
            if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("B")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("C")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("C")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("D")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("C") && cor_ans2.equalsIgnoreCase("D")){
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }
        }else if(user_ans1.equalsIgnoreCase("B") && user_ans2.equalsIgnoreCase("C")){
            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option2.setTextColor(getResources().getColor(R.color.white));
            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option3.setTextColor(getResources().getColor(R.color.white));
            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option1.setTextColor(getResources().getColor(R.color.black));
            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option4.setTextColor(getResources().getColor(R.color.black));
            if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("B")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("C")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("D")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("D")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("C") && cor_ans2.equalsIgnoreCase("D")){
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }
        }else if(user_ans1.equalsIgnoreCase("B") && user_ans2.equalsIgnoreCase("D")){
            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option2.setTextColor(getResources().getColor(R.color.white));
            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option4.setTextColor(getResources().getColor(R.color.white));
            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option1.setTextColor(getResources().getColor(R.color.black));
            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option3.setTextColor(getResources().getColor(R.color.black));
            if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("B")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("C")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("D")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("C")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("C") && cor_ans2.equalsIgnoreCase("D")){
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }
        }else if(user_ans1.equalsIgnoreCase("C") && user_ans2.equalsIgnoreCase("D")){
            btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option3.setTextColor(getResources().getColor(R.color.white));
            btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_wrong));
            btn_option4.setTextColor(getResources().getColor(R.color.white));
            btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option1.setTextColor(getResources().getColor(R.color.black));
            btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
            btn_option2.setTextColor(getResources().getColor(R.color.black));
            if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("B")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("C")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("A") && cor_ans2.equalsIgnoreCase("D")){
                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option1.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("C")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option3.setTextColor(getResources().getColor(R.color.white));
            }else if(cor_ans1.equalsIgnoreCase("B") && cor_ans2.equalsIgnoreCase("D")){
                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option2.setTextColor(getResources().getColor(R.color.white));
                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                btn_option4.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_action_bar_play:
                btn_play.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_action_bar_pause:
                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                setPause();
                break;

            case R.id.btn_action_bar_quit:
                btn_quit.setVisibility(View.GONE);
                showResult();
                break;

            case R.id.btn_bottom_bar_next:
                onNextClick();
                break;

            case R.id.btn_bottom_bar_previous:
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
                break;

            case R.id.btn_bottom_bar_explain:
                showExplain(Globals.glist.get(Globals.index).getExplain());
                break;

            case R.id.ll_bottom_bar_flag:
                if(!Globals.review) {
                    id=0;
                    btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option1.setTextColor(getResources().getColor(R.color.black));
                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                    btn_option4.setTextColor(getResources().getColor(R.color.black));
                    btn_count=0;
                    flag1=false;
                    flag2=false;
                    flag3=false;
                    flag4=false;
                    rl_flag.setVisibility(View.GONE);
                    rl_unflag.setVisibility(View.VISIBLE);
                    skip = true;
                    user_ans = "Z";
                }
                break;

            case R.id.ll_bottom_bar_flagged:
                if(!Globals.review) {
                    rl_flag.setVisibility(View.VISIBLE);
                    rl_unflag.setVisibility(View.GONE);
                    skip = false;
                    user_ans = "Z";
                }
                break;

            case R.id.btn_multiple_choice_option1:
                if(!Globals.review) {
                    if(rl_unflag.getVisibility()==View.VISIBLE){
                        rl_flag.setVisibility(View.VISIBLE);
                        rl_unflag.setVisibility(View.GONE);
                        skip = false;
                    }
                    if(!flagTwo) {
                        id = 1;
                        changeState();
                        if (Globals.autoNext) {
                            onNextClick();
                        }
                    }else {
                        if(btn_count==0){
                            btn_count++;
                            if (flag1 == false) {
                                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                                btn_option1.setTextColor(getResources().getColor(R.color.white));
                                flag1 = true;
                            }
                            id1=1;
                            id2=0;
                            //doubleClick();
                        }else if(btn_count==1) {
                            if(flag1){
                                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                btn_option1.setTextColor(getResources().getColor(R.color.black));
                                flag1 = false;
                                btn_count--;
                                if(id1==1){
                                    id1=0;
                                }else if(id2==1){
                                    id2=0;
                                }
                            }else {
                                btn_count++;
                                if (id1 != 0 && id2 == 0) {
                                    if(id1>1){
                                        id2=id1;
                                        id1=1;
                                    }
                                } else if (id1 == 0 && id2 != 0) {
                                    if(id2>1){
                                        id1=1;
                                    }else{

                                    }
                                }
                                doubleClick();
                                if (Globals.autoNext) {
                                    onNextClick();
                                }
                                //btn_count++;
                            }
                        }else if(btn_count==2){
                            if(id1==1){
                                btn_option1.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                btn_option1.setTextColor(getResources().getColor(R.color.black));
                                flag1 = false;
                                btn_count--;
                                id1=id2;
                                id2=0;
                            }else{
                                Toast.makeText(getActivity(), "You can choose only two option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;

            case R.id.btn_multiple_choice_option2:
                if(!Globals.review) {
                    if(rl_unflag.getVisibility()==View.VISIBLE){
                        rl_flag.setVisibility(View.VISIBLE);
                        rl_unflag.setVisibility(View.GONE);
                        skip = false;
                    }
                    if(!flagTwo) {
                        id = 2;
                        changeState();
                        if (Globals.autoNext) {
                            onNextClick();
                        }
                    }else {
                        if(btn_count==0){
                            btn_count++;
                            if (flag2 == false) {
                                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                                btn_option2.setTextColor(getResources().getColor(R.color.white));
                                flag2 = true;
                            }
                            id1=2;
                            id2=0;
                            //doubleClick();
                        }else if(btn_count==1) {
                            if(flag2){
                                btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                btn_option2.setTextColor(getResources().getColor(R.color.black));
                                flag2 = false;
                                btn_count--;
                                if(id1==2){
                                    id1=0;
                                }else if(id2==2){
                                    id2=0;
                                }
                            }else {
                                btn_count++;
                                if (id1 != 0 && id2 == 0) {
                                    if(id1>2){
                                        id2=id1;
                                        id1=2;
                                    }else{
                                        id1=1;
                                        id2=2;
                                    }
                                } else if (id1 == 0 && id2 != 0) {
                                    if(id2>2){
                                        id1=2;
                                    }else{
                                        id1=id2;
                                        id2=2;
                                    }
                                }
                                doubleClick();
                                if (Globals.autoNext) {
                                    onNextClick();
                                }
                                //btn_count++;
                            }
                        }else if(btn_count==2){
                            if(id1==2 || id2==2){
                                if(id1==2) {
                                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                                    flag2 = false;
                                    btn_count--;
                                    id1 = id2;
                                    id2 = 0;
                                }else{
                                    btn_option2.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                    btn_option2.setTextColor(getResources().getColor(R.color.black));
                                    flag2 = false;
                                    btn_count--;
                                    id2 = 0;
                                }
                            }else{
                                Toast.makeText(getActivity(), "You can choose only two option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;

            case R.id.btn_multiple_choice_option3:
                if(!Globals.review) {
                    if(rl_unflag.getVisibility()==View.VISIBLE){
                        rl_flag.setVisibility(View.VISIBLE);
                        rl_unflag.setVisibility(View.GONE);
                        skip = false;
                    }
                    if(!flagTwo) {
                        id = 3;
                        changeState();
                        if (Globals.autoNext) {
                            onNextClick();
                        }
                    }else{
                        if(btn_count==0){
                            btn_count++;
                            if (flag3 == false) {
                                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                                btn_option3.setTextColor(getResources().getColor(R.color.white));
                                flag3 = true;
                            }
                            id1=3;
                            id2=0;
                            //doubleClick();
                        }else if(btn_count==1) {
                            if(flag3){
                                btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                btn_option3.setTextColor(getResources().getColor(R.color.black));
                                flag3 = false;
                                btn_count--;
                                if(id1==3){
                                    id1=0;
                                }else if(id2==3){
                                    id2=0;
                                }
                            }else {
                                btn_count++;
                                if (id1 != 0 && id2 == 0) {
                                    if(id1>3){
                                        id2=id1;
                                        id1=3;
                                    }else{
                                        id2=3;
                                    }
                                } else if (id1 == 0 && id2 != 0) {
                                    if(id2>3){
                                        id1=3;
                                    }else{
                                        id1=id2;
                                        id2=3;
                                    }
                                }
                                doubleClick();
                                if (Globals.autoNext) {
                                    onNextClick();
                                }
                                //btn_count++;
                            }
                        }else if(btn_count==2){
                            if(id1==3 || id2==3){
                                if(id1==3) {
                                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                                    flag3 = false;
                                    btn_count--;
                                    id1 = id2;
                                    id2 = 0;
                                }else{
                                    btn_option3.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                    btn_option3.setTextColor(getResources().getColor(R.color.black));
                                    flag3 = false;
                                    btn_count--;
                                    id2 = 0;
                                }
                            }else{
                                Toast.makeText(getActivity(), "You can choose only two option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;

            case R.id.btn_multiple_choice_option4:
                if(!Globals.review) {
                    if(rl_unflag.getVisibility()==View.VISIBLE){
                        rl_flag.setVisibility(View.VISIBLE);
                        rl_unflag.setVisibility(View.GONE);
                        skip = false;
                    }
                    if(!flagTwo) {
                        id = 4;
                        changeState();
                        if (Globals.autoNext) {
                            onNextClick();
                        }
                    }else {
                        if(btn_count==0){
                            btn_count++;
                            if (flag4 == false) {
                                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_pressed));
                                btn_option4.setTextColor(getResources().getColor(R.color.white));
                                flag4 = true;
                            }
                            id1=4;
                            id2=0;
                            //doubleClick();
                        }else if(btn_count==1) {
                            if(flag4){
                                btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                btn_option4.setTextColor(getResources().getColor(R.color.black));
                                flag4 = false;
                                btn_count--;
                                if(id1==4){
                                    id1=0;
                                }else if(id2==4){
                                    id2=0;
                                }
                            }else {
                                btn_count++;
                                if (id1 != 0 && id2 == 0) {
                                    if(id1<4){
                                        id2=4;
                                    }else{
                                        id1=4;
                                        id2=0;
                                    }
                                } else if (id1 == 0 && id2 != 0) {
                                    if(id2!=4){
                                        id1=id2;
                                        id2=4;
                                    }else{
                                        id1=4;
                                        id2=0;
                                    }
                                }
                                doubleClick();
                                if (Globals.autoNext) {
                                    onNextClick();
                                }
                                //btn_count++;
                            }
                        }else if(btn_count==2){
                            if(id2==4){
                                    btn_option4.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_image_red));
                                    btn_option4.setTextColor(getResources().getColor(R.color.black));
                                    flag4 = false;
                                    btn_count--;
                                    id2 = 0;
                            }else{
                                Toast.makeText(getActivity(), "You can choose only two option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;

            default:
                break;
        }
    }
}
