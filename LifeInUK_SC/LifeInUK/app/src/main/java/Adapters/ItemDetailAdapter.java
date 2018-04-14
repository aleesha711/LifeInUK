package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tecjaunt.www.lifeinuk.R;

import java.util.ArrayList;

import Org.Tools.ItemQuestionDetail;
import Org.Utility.Globals;

/**
 * Created by Omer Habib on 12/13/2016.
 */

public class ItemDetailAdapter extends BaseAdapter {
    Context context;
    ArrayList<ItemQuestionDetail> list;

    public ItemDetailAdapter(Context context, ArrayList<ItemQuestionDetail> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_item_question_detail, null);
        }
        Boolean flag=false;
        TextView tv_question = (TextView) view.findViewById(R.id.tv_item_question_detail);
        tv_question.setText("Question # "+ ((Integer)(i+1)) +" : "+Globals.ilist.get(i).getQuestion());

        if(Globals.ilist.get(i).getRemarks()){
            tv_question.setBackgroundColor(context.getResources().getColor(R.color.btn_green));
        }else{
            tv_question.setBackgroundColor(context.getResources().getColor(R.color.red));
        }




        return view;
    }
}

/*if//(Globals.ilist.get(i).getAnswer().equalsIgnoreCase(Globals.ilist.get(i).getUser_ans()) || flag){
            (Globals.ilist.get(i).getAnswer().matches(Globals.ilist.get(i).getUser_ans()) || Globals.ilist.get(i).getAnswer().matches("(.*)"+Globals.ilist.get(i).getUser_ans()+"(.*)") ||Globals.ilist.get(i).getAnswer().matches("(.*)"+Globals.ilist.get(i).getUser_ans()) ||Globals.ilist.get(i).getAnswer().matches(Globals.ilist.get(i).getUser_ans()+"(.*)")) {
                tv_question.setBackgroundColor(context.getResources().getColor(R.color.btn_green));
                //Globals.correct=Globals.pref.getInt("CorrectMC",0);
                //Globals.correct++;
                //Globals.editor.putInt("CorrectMC",Globals.correct).commit();
                if (Globals.type == 1) {
                    int j = Globals.pref.getInt("CorrectTest", 0);
                    j++;
                    Globals.editor.putInt("CorrectTest", j).commit();

                    int l = Globals.pref.getInt("CorrectTLA", 0);
                    l++;
                    Globals.editor.putInt("CorrectTLA", l).commit();

                } else if (Globals.type == 2) {
                    int j = Globals.pref.getInt("CorrectPrac", 0);
                    j++;
                    Globals.editor.putInt("CorrectPrac", j).commit();

                    int l = Globals.pref.getInt("CorrectPLA", 0);
                    l++;
                    Globals.editor.putInt("CorrectPLA", l).commit();

                    if (Globals.type2 == 0) {
                        int k = Globals.pref.getInt("CorrectAll", 0);
                        k++;
                        Globals.editor.putInt("CorrectAll", k).commit();
                    } else if (Globals.type2 == 1) {
                        int k = Globals.pref.getInt("CorrectPoli", 0);
                        k++;
                        Globals.editor.putInt("CorrectPoli", k).commit();
                    } else if (Globals.type2 == 2) {
                        int k = Globals.pref.getInt("CorrectLaw", 0);
                        k++;
                        Globals.editor.putInt("CorrectLaw", k).commit();
                    } else if (Globals.type2 == 3) {
                        int k = Globals.pref.getInt("CorrectHistory", 0);
                        k++;
                        Globals.editor.putInt("CorrectHistory", k).commit();
                    } else if (Globals.type2 == 4) {
                        int k = Globals.pref.getInt("CorrectPop", 0);
                        k++;
                        Globals.editor.putInt("CorrectPop", k).commit();
                    } else if (Globals.type2 == 5) {
                        int k = Globals.pref.getInt("CorrectOther", 0);
                        k++;
                        Globals.editor.putInt("CorrectOther", k).commit();
                    } else if (Globals.type2 == 6) {
                        int k = Globals.pref.getInt("CorrectMC", 0);
                        k++;
                        Globals.editor.putInt("CorrectMC", k).commit();
                    } else if (Globals.type2 == 7) {
                        int k = Globals.pref.getInt("CorrectTF", 0);
                        k++;
                        Globals.editor.putInt("CorrectTF", k).commit();
                    } else if (Globals.type2 == 8) {
                        int k = Globals.pref.getInt("CorrectSt", 0);
                        k++;
                        Globals.editor.putInt("CorrectSt", k).commit();
                    }
                }

            } else if (!Globals.ilist.get(i).getAnswer().matches(Globals.ilist.get(i).getUser_ans()) && !Globals.ilist.get(i).getAnswer().matches("(.*)"+Globals.ilist.get(i).getUser_ans()+"(.*)") && !Globals.ilist.get(i).getAnswer().matches("(.*)"+Globals.ilist.get(i).getUser_ans()) && !Globals.ilist.get(i).getAnswer().matches(Globals.ilist.get(i).getUser_ans()+"(.*)")){
            tv_question.setBackgroundColor(context.getResources().getColor(R.color.red));
            }*/
