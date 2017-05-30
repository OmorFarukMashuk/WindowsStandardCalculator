package com.calc.mashuk.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class HistoryActivity extends Activity {


    private TextView TextHst = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        this.TextHst= (TextView)this.findViewById(R.id.TextHst);
        //TextHst.setText("efferf");

    }

    public void finishActivity(View v) {

        //back = (Button) findViewById(R.id.buttonBack);
        finish();
    }

    public void ClearHistory(View v) {

        try {
            File myFile = new File(getFilesDir(),"calcHistory.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(myFile);
            //fileOutputStream.write("".getBytes());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        LoadHistory();


    }

    public void LoadHistory() {
        try {
            FileInputStream fis = openFileInput("calcHistory.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                TextHst.setText(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadHistory();
    }


}
