package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tecjaunt.www.lifeinuk.R;

import java.util.ArrayList;

import Org.Tools.ItemCategory;
import Org.Utility.Globals;

/**
 * Created by Omer Habib on 12/1/2016.
 */

public class CategoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<ItemCategory> list;

    public CategoryAdapter() {
    }

    public CategoryAdapter(Context context, ArrayList<ItemCategory> list) {
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
        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.activity_item_category,null);
        }

        TextView tv_title=(TextView)view.findViewById(R.id.tv_item_category_title);
        TextView tv_total=(TextView)view.findViewById(R.id.tv_item_category_total);
        TextView tv_answered=(TextView)view.findViewById(R.id.tv_item_category_answered);
        TextView tv_correct=(TextView)view.findViewById(R.id.tv_item_category_correct);
        TextView tv_progress=(TextView)view.findViewById(R.id.tv_item_category_progress);
        ProgressBar pb=(ProgressBar)view.findViewById(R.id.pb_item_category_progress);

        tv_title.setText(list.get(i).getTitle());
        tv_total.setText(((Integer)0).toString());
        tv_answered.setText(((Integer)0).toString());
        tv_correct.setText(((Integer)0).toString());
        tv_progress.setText(((Integer)0).toString());
        pb.setProgress(0);


        Globals.pref=context.getSharedPreferences("MyPref",Context.MODE_PRIVATE);



        if(getItemId(i)==0) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalAll",0);
            int ans=Globals.pref.getInt("AnswerAll",0);
            int cor=Globals.pref.getInt("CorrectAll",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==1) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalPoli",0);
            int ans=Globals.pref.getInt("AnswerPoli",0);
            int cor=Globals.pref.getInt("CorrectPoli",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(list.get(i).getTitle().equalsIgnoreCase("Law")) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalLaw",0);
            int ans=Globals.pref.getInt("AnswerLaw",0);
            int cor=Globals.pref.getInt("CorrectLaw",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==2) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalHistory",0);
            int ans=Globals.pref.getInt("AnswerHistory",0);
            int cor=Globals.pref.getInt("CorrectHistory",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==3) {

            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalPop",0);
            int ans=Globals.pref.getInt("AnswerPop",0);
            int cor=Globals.pref.getInt("CorrectPop",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==4) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalOther",0);
            int ans=Globals.pref.getInt("AnswerOther",0);
            int cor=Globals.pref.getInt("CorrectOther",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==5) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalMC",0);
            int ans=Globals.pref.getInt("AnswerMC",0);
            int cor=Globals.pref.getInt("CorrectMC",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==6) {

            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalTF",0);
            int ans=Globals.pref.getInt("AnswerTF",0);
            int cor=Globals.pref.getInt("CorrectTF",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }else if(getItemId(i)==7) {
            tv_title.setText(list.get(i).getTitle());
            int total=Globals.pref.getInt("TotalSt",0);
            int ans=Globals.pref.getInt("AnswerSt",0);
            int cor=Globals.pref.getInt("CorrectSt",0);
            int per=0;
            if(ans>0 ) {
                per = ((cor * 100) / ans);
            }
            tv_total.setText(((Integer)total).toString());
            tv_answered.setText(((Integer)ans).toString());
            tv_correct.setText(((Integer)cor).toString());
            tv_progress.setText(((Integer)per).toString()+" %");
            pb.setProgress(per);

        }


        return view;
    }


    public String getTitle(int i) {
        return list.get(i).getTitle().toString();
    }

}
