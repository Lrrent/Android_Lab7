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

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class File extends AppCompatActivity {
    private EditText content;
    private Button save;
    private Button load;
    private Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        findAllView();
        clear.setOnClickListener(new clear_edit());
        save.setOnClickListener(new save_listener());
        load.setOnClickListener(new load_listener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //如果intent不指定category，那么无论intent filter的内容是什么都应该是匹配的。
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void findAllView(){
        content = (EditText) findViewById(R.id.content);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        clear = (Button) findViewById(R.id.clear_edit);
    }
    private class save_listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String input = content.getText().toString();
            String fileName = "saveContent";
            FileOutputStream output;
            try{
                output = openFileOutput(fileName,Context.MODE_PRIVATE);
                output.write(input.getBytes());
                output.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            Toast.makeText(getApplication(),"Save successfuly",Toast.LENGTH_LONG).show();
            //Log.i("Save content", "onClick: ");
        }
    }
    private class load_listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            FileInputStream inputfile;
            String fileName = "saveContent";
            try{
                inputfile = openFileInput(fileName);
                byte[] inputontent = new byte[inputfile.available()];
                inputfile.read(inputontent);
                String s = new String(inputontent);  //将byte数组转为String
                content.setText(s);
                Toast.makeText(getApplication(),"Load successfully",Toast.LENGTH_LONG).show();
               // Log.i(s, "onClick:load ");
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplication(),"Fail to load file",Toast.LENGTH_LONG).show();
            }
        }
    }
    private class clear_edit implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            content.setText(null);
        }
    }
}
