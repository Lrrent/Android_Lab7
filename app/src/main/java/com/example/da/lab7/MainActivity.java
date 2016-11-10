package com.example.da.lab7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText new_pass;
    private EditText confirm_pass;
    private Button ok;
    private Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllView(); //绑定控件
        passwordExit();
    }
    private void findAllView(){
        new_pass = (EditText) findViewById(R.id.password);
        confirm_pass = (EditText) findViewById(R.id.confirm);
        ok = (Button) findViewById(R.id.ok);
        clear = (Button)findViewById(R.id.clear);
    }
    //ok按钮的监控
    public class ok_listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String new_text = new_pass.getText().toString();
            String confirm_text = confirm_pass.getText().toString();
            if (new_text.equals("")||confirm_text.equals("")){
                Toast.makeText(getApplicationContext(),R.string.empty,Toast.LENGTH_LONG).show();
            }
            else if (!new_text.equals(confirm_text)){
                Toast.makeText(getApplicationContext(),R.string.mismatch,Toast.LENGTH_LONG).show();
            }
            else if(new_text.equals(confirm_text)){
                //当密码确认无误时,将密码写入文件
                Context context = getApplicationContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences("pass_save",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();  //声明editor对象来对SharePreferences进行修改
                editor.putString("password",new_text); //将输入大的密码存入文件中
                editor.apply();
                Log.i("Save Password", "onClick: ");
            }
        }
    }
    //clear按钮的监听事件
    private class clear_listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            new_pass.setText("");
            confirm_pass.setText("");
        }
    }
    //刚开始进入主界面时判断
    //是否已经有密码存在文件里面,如果有,则输入密码登陆,否则创建密码
    private void passwordExit(){
        SharedPreferences getShared = getSharedPreferences("pass_save",MODE_PRIVATE);
        final String answer = getShared.getString("password",null);
        Log.i(answer, "passwordExit: ");
        if(answer==null){
            Log.i("answer null", "passwordExit: ");
            ok.setOnClickListener(new ok_listener());
            clear.setOnClickListener(new clear_listener());
        }
        else{
            Log.i("not null", "passwordExit: ");
            confirm_pass.setVisibility(View.GONE);//三种属性:invisible：不显示,但保留所占的空 visible：正常显示 gone：不显示,且不保留所占的空间
            new_pass.setHint("Password");
           ok.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String input = new_pass.getText().toString();
                    if (answer.equals(input)){
                        Intent intent = new Intent(MainActivity.this,File.class);
                        MainActivity.this.startActivity(intent);
                    }
                   else{
                        Toast.makeText(getApplicationContext(),"Invaild Password",Toast.LENGTH_LONG).show();
                    }
               }
           });
            clear.setOnClickListener(new clear_listener());
        }
    }
}
