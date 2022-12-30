package com.example.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    private EditText location;
    private TextView unit_text;
    private TextView send_text;
    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        location = (EditText) findViewById(R.id.location);
        LinearLayout unit = (LinearLayout) findViewById(R.id.unit);
        LinearLayout page = (LinearLayout) findViewById(R.id.page);
        unit_text = (TextView)findViewById(R.id.unit_text);
        LinearLayout send = (LinearLayout) findViewById(R.id.send);
        send_text = (TextView)findViewById(R.id.send_text);

        SharedPreferences pref = getSharedPreferences("setting",MODE_PRIVATE);
        location.setText(pref.getString("city","广州"));
        unit_text.setText(pref.getString("unit","摄氏度"));
        send_text.setText(pref.getString("send","是"));

        page.setOnClickListener(v -> location.clearFocus());

        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                city = s.toString();
                SharedPreferences.Editor editor = getSharedPreferences("setting",MODE_PRIVATE).edit();
                editor.putString("city",city);
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        unit.setOnClickListener(v -> click());

        send.setOnClickListener(v -> click2());
    }

    //点击按钮弹出一个单选对话框
    public void click() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择温度单位");
        final String[] items = {"摄氏度","华氏度"};

//-1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, (dialog, which) -> {
            //1.把选中的条目取出来
            String item = items[which];
            Toast.makeText(getApplicationContext(), item,Toast.LENGTH_LONG).show();
            unit_text.setText(item);
            SharedPreferences.Editor editor = getSharedPreferences("setting",MODE_PRIVATE).edit();
            editor.putString("unit", item);
            editor.apply();
            //2.然后把对话框关闭
            dialog.dismiss();
        });
//一样要show
        builder.show();
    }

    public void click2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择是否开启通知");
        final String[] items = {"是","否"};

//-1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, (dialog, which) -> {
            //1.把选中的条目取出来
            String item = items[which];
            Toast.makeText(getApplicationContext(), item,Toast.LENGTH_LONG).show();
            send_text.setText(item);
            SharedPreferences.Editor editor = getSharedPreferences("setting",MODE_PRIVATE).edit();
            editor.putString("send", item);
            editor.apply();
            //2.然后把对话框关闭
            dialog.dismiss();
        });
//一样要show
        builder.show();
    }
}
