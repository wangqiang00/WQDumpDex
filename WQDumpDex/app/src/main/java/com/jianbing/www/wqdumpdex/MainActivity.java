package com.jianbing.www.wqdumpdex;

import android.app.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public EditText et_application;
    public EditText et_activity;
    public TextView tv_dump;
    public SharedPrefUtils spu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_activity = findViewById(R.id.et);
        tv_dump = findViewById(R.id.tv_dump);
        et_application = findViewById(R.id.et_app);

        tv_dump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_activity.getText().toString())||TextUtils.isEmpty((et_application.getText().toString()))){
                    Toast.makeText(MainActivity.this,"填全信息",Toast.LENGTH_LONG).show();
                }else{
                    spu.setSharePref("activityName",et_activity.getText().toString().replace(" ","").trim());
                    spu.setSharePref("applicationName",et_application.getText().toString().replace(" ","").trim());
                    Log.e("wq","input info finish");
                }
            }
        });

        spu = new SharedPrefUtils(MainActivity.this.getApplicationContext());
    }
}
