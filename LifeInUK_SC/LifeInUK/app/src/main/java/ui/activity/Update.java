package ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecjaunt.www.lifeinuk.R;

import Org.Utility.Globals;

public class Update extends AppCompatActivity {

    ImageView iv_setting;
    TextView tv_title;
    CheckBox cb_01,cb_02;
    Boolean flag_01=false,flag_02=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findViewby();

        cb_01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    flag_01 = true;
                    Globals.editor.putBoolean("CB_01",flag_01).commit();
                }
                else {
                    flag_01 = false;
                    Globals.editor.putBoolean("CB_01",flag_01).commit();
                }
            }
        });

        cb_02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    flag_02 = true;
                    Globals.editor.putBoolean("CB_02", flag_02).commit();
                }
                else {
                    flag_02 = false;
                    Globals.editor.putBoolean("CB_02",flag_02).commit();
                }
            }
        });

    }

    private void findViewby() {
        iv_setting=(ImageView)findViewById(R.id.iv_action_bar_setting);
        tv_title=(TextView)findViewById(R.id.tv_action_bar_title);
        cb_01=(CheckBox)findViewById(R.id.cb_update_01);
        cb_02=(CheckBox)findViewById(R.id.cb_update_02);

        iv_setting.setVisibility(View.GONE);

        tv_title.setText("Update");
        Globals.pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        Globals.editor = Globals.pref.edit();

        flag_01=Globals.pref.getBoolean("CB_01",false);
        flag_02=Globals.pref.getBoolean("CB_02",false);

        cb_01.setChecked(flag_01);
        cb_02.setChecked(flag_02);

    }
}
