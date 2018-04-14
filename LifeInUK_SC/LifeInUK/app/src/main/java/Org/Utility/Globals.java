package Org.Utility;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;

import Org.Tools.CountTimer;
import Org.Tools.FlagQuestions;
import Org.Tools.ItemQuestionDetail;
import Org.Tools.TestQuestion;

/**
 * Created by Omer Habib on 12/7/2016.
 */

public class Globals {
    public static int index;
    public static ArrayList<TestQuestion> glist;
    public static ArrayList<TestQuestion> Englishlist;
    public static ArrayList<TestQuestion> Spanishlist;
    public static ArrayList<TestQuestion> Polishlist;
    public static ArrayList<TestQuestion> Urdulist;
    public static ArrayList<TestQuestion> Farsilist;
    public static ArrayList<TestQuestion> Arabiclist;
    public static ArrayList<FlagQuestions> flist;
    public static Boolean review;
    public static int findex=0;
    public static Boolean flagQes=false;
    public static Boolean flagReview=false;
    public static int flagSize=0;
    public static int type;
    public static int type2;
    public static int answer;
    public static int correct;
    public static int Qnos=0;
    public static String language="English";
    public static String cat_01;
    public static String cat_02;
    public static String url="http://sparkmultimedia.net/";
    public static long time;
    public static ArrayList<ItemQuestionDetail> ilist;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static Boolean autoNext=false;
    public static Boolean alert=false;
    public static Boolean flag=false;
    public static CountTimer timer;
    //this needs to match the password in the php files
    public static String access = "";
    public static Activity menuScreen;
    public static Activity category;


}
