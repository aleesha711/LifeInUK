package ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.tecjaunt.www.lifeinuk.R;

import Org.Utility.Globals;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    String lang;

    ImageView iv_setting;

    TextView tv_title;

    Button btn_cancel;
    Button btn_ok;

    Button btn_cancel1;
    Button btn_ok1;
    TextView tv_timer;
    Button btn_play;
    Button btn_pause;
    Button btn_quit;

    LinearLayout rl_langauge;
    LinearLayout rl_sound;
    LinearLayout rl_rate;
    LinearLayout rl_notifications;
    LinearLayout rl_update;

    LinearLayout ll_sound;
    LinearLayout ll_rate;
    LinearLayout ll_notifications;
    SwitchButton sw_autoNext;
    SwitchButton sw_alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewby();
        //pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        //editor = pref.edit();

        rl_langauge.setOnClickListener(this);

        rl_notifications.setOnClickListener(this);
        rl_update.setOnClickListener(this);

        ll_notifications.setOnClickListener(this);

        sw_autoNext.setChecked(Globals.pref.getBoolean("AutoNext",false));

        sw_autoNext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    Globals.autoNext = true;
                    Globals.editor.putBoolean("AutoNext",true).commit();
                }

                else{
                    Globals.autoNext=false;
                    Globals.editor.putBoolean("AutoNext",false).commit();
                }
            }
        });

//        tv_tone.setOnClickListener(this);
    }

    private void findViewby() {



        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);
        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);

        iv_setting.setVisibility(View.GONE);
        tv_title.setText("SETTINGS");

        tv_timer=(TextView)findViewById(R.id.tv_action_bar_timer);
        btn_play=(Button)findViewById(R.id.btn_action_bar_play);
        btn_pause=(Button)findViewById(R.id.btn_action_bar_pause);
        btn_quit=(Button)findViewById(R.id.btn_action_bar_quit);
        tv_timer.setVisibility(View.GONE);
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.GONE);
        btn_quit.setVisibility(View.GONE);

        rl_langauge=(LinearLayout) findViewById(R.id.rl_settings_langauge);
        rl_notifications=(LinearLayout) findViewById(R.id.rl_settings_notification);
        rl_update=(LinearLayout) findViewById(R.id.rl_settings_update);
        sw_autoNext=(SwitchButton)findViewById(R.id.sw_settings_notifications_next);

        //rb=(RatingBar)rootView.findViewById(R.id.rating_bar);
        ll_notifications=(LinearLayout)findViewById(R.id.ll_settings_notifications_show);
        //tv_tone=(TextView)rootView.findViewById(R.id.tv_settings_sound_select);

        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_settings_langauge:
                showLanguages();
                break;

            case R.id.rl_settings_notification:
                if(ll_notifications.getVisibility()==View.GONE) {
                    ll_notifications.setVisibility(View.VISIBLE);
                }
                else if(ll_notifications.getVisibility()==View.VISIBLE) {
                    ll_notifications.setVisibility(View.GONE);
                }

                break;

            case R.id.rl_settings_update:
                startActivity(new Intent(getApplicationContext(),Update.class));
                break;


        }
    }

    public void showLanguages() {

        RadioGroup rg_language;
        final RadioButton rb_english;
        final RadioButton rb_polish;
        final RadioButton rb_spanish;
        final RadioButton rb_arabic;
        final RadioButton rb_urdu;
        final RadioButton rb_farsi;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_language_selector, null);
        builder.setView(popupView);
        String l= Globals.pref.getString("language",null);
        //Toast.makeText(this, "language "+ l, Toast.LENGTH_SHORT).show();
        //builder.setCancelable(false);
        rg_language=(RadioGroup)popupView.findViewById(R.id.rg_language_selector);
        rb_english=(RadioButton)popupView.findViewById(R.id.rb_language_selector_english);
        rb_polish=(RadioButton)popupView.findViewById(R.id.rb_language_selector_polish);
        rb_spanish=(RadioButton)popupView.findViewById(R.id.rb_language_selector_spanish);
        rb_urdu=(RadioButton)popupView.findViewById(R.id.rb_language_selector_urdu);
        rb_arabic=(RadioButton)popupView.findViewById(R.id.rb_language_selector_arabic);
        rb_farsi=(RadioButton)popupView.findViewById(R.id.rb_language_selector_farsi);
        btn_cancel=(Button)popupView.findViewById(R.id.btn_popup_language_cancel);
        btn_ok=(Button)popupView.findViewById(R.id.btn_popup_language_ok);

        if(rb_english.getText().equals(l)){
            rg_language.check(rb_english.getId());
        }else if(rb_polish.getText().equals(l)){
            rg_language.check(rb_polish.getId());
        }else if(rb_spanish.getText().equals(l)){
            rg_language.check(rb_spanish.getId());
        }else if(rb_urdu.getText().equals(l)){
            rg_language.check(rb_urdu.getId());
        }else if(rb_farsi.getText().equals(l)){
            rg_language.check(rb_farsi.getId());
        }else if(rb_arabic.getText().equals(l)){
            rg_language.check(rb_arabic.getId());
        }



        rg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_language_selector_english){
                    lang =rb_english.getText().toString();
                }
                else if(i==R.id.rb_language_selector_polish){
                    lang=rb_polish.getText().toString();
                }
                else if(i==R.id.rb_language_selector_spanish){
                    lang=rb_spanish.getText().toString();
                }
                else if(i==R.id.rb_language_selector_urdu){
                    lang=rb_urdu.getText().toString();
                }
                else if(i==R.id.rb_language_selector_arabic){
                    lang=rb_arabic.getText().toString();
                }
                else if(i==R.id.rb_language_selector_farsi){
                    lang=rb_farsi.getText().toString();
                }
            }

        });

        final AlertDialog dialog = builder.create();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.editor.putString("language", lang);
                Globals.language = lang;
                Globals.editor.commit();
                dialog.dismiss();
               // Toast.makeText(getApplicationContext(), "Selected Language is "+ Globals.language, Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        //dialog.setCancelable(false);

    }

}
