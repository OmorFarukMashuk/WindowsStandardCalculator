package com.calc.mashuk.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



public class CalculatorActivity extends Activity {
    /** Called when the activity is first created. */

    private TextView tDisplay = null;
    private EditText eDisplay = null;


    double val1, val2, ans = 0;
    double mem = 0;
    String calc = null, op = "";
    boolean isDot = false, isMR = false;
    boolean enEdt = false; // the variable keeps tracking  consequent double operator pressing

    boolean oPress = true;
    Button btnHst;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("Clicked", "super on create called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Clicked", "super on resume called");
        this.tDisplay = (TextView)this.findViewById((R.id.tDisplay));
        this.eDisplay = (EditText)this.findViewById(R.id.eDisplay);
        this.eDisplay.setText("0");
        View v = null;
        ClearAll(v);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.main);
        Log.d("Clicked", "super on start called");
    }

    public void newActivity(View v) {
        this.btnHst = (Button)findViewById(R.id.buttonHst);
        Intent i = new Intent(CalculatorActivity.this,HistoryActivity.class);
        startActivity(i);
    }


    public void Clicked (View v) {
        Button temp = (Button)v;

        if(oPress) {
            this.eDisplay.setText("");
            oPress = false;

        }

        if (isMR) {
            this.eDisplay.setText("");
            isMR = false;
        }
        enEdt = true;
        String txt = this.eDisplay.getText().toString()+temp.getText().toString();
        this.eDisplay.setText(txt);
    }

    public void ChangeText(View v) {
        Button temp = (Button)v;
        String txt;
        txt = this.tDisplay.getText() + this.eDisplay.getText().toString() + temp.getText().toString();
        this.tDisplay.setText(txt);
        enEdt = false;

    }


    public void Dot(View v) {

        if(!isDot) {
            Clicked(v);
            isDot = true;
        }

    }

    public void Operation(View v) {

        try {

            Button temp = (Button)v;

            Log.d("op press",temp.getText().toString());
            oPress = true;

            if(!PrevOperation()) {

                val1 = Double.parseDouble(this.eDisplay.getText().toString());
                ChangeText(v);
            }

            else {
                if(!enEdt) {
                    String tTxt = this.tDisplay.getText().toString();
                    //Log.d("tText",tTxt);
                    tTxt = (tTxt.substring(0, tTxt.length()-1));
                    //Log.d("tText",tTxt);
                    tTxt = (tTxt+temp.getText().toString());
                    //Log.d("tText",tTxt);
                    this.tDisplay.setText(tTxt);
                }
                else {
                    ChangeText(v);
                    Equalop(v);
                    val1 = ans;
                }

            }
            op =  temp.getText().toString();

            isDot = false;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void NegNum(View v) {

        try {
            this.eDisplay.setText(String.valueOf(Double.parseDouble(this.eDisplay.getText().toString()) * -(1)));

        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void OnebyX(View v) {
        try {
            this.eDisplay.setText(String.valueOf(1/Double.parseDouble(this.eDisplay.getText().toString())));
            enEdt = true;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SqrofX(View v) {
        try {
            double sqr;
            sqr = Double.parseDouble(this.eDisplay.getText().toString());
            this.eDisplay.setText(String.valueOf(sqr * sqr));
            enEdt = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void MemoryPlus(View v) {
        try {

            mem = mem + Double.parseDouble(this.eDisplay.getText().toString());
            isMR = true;
            Button btnMR = (Button) findViewById(R.id.buttonMemRead);
            btnMR.setEnabled(true);
            Button btnMC = (Button) findViewById(R.id.buttonMemClr);
            btnMC.setEnabled(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void MemoryRead(View v) {

        ClearAll(v);
        this.eDisplay.setText(String.valueOf(mem));
        isMR = true;


    }

    public void MemoryClear(View v) {
        mem = 0;
        Button btnMR = (Button) findViewById(R.id.buttonMemRead);
        btnMR.setEnabled(false);
        Button btnMC = (Button) findViewById(R.id.buttonMemClr);
        btnMC.setEnabled(false);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Clicked", "super on stop called");
    }

    /*
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Clicked", "super on pause called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Clicked", "super on Restore called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Clicked", "super on savedInstance called");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Clicked", "super on restart called");
    }*/

    public void SaveToHistory(View v) {

        File myFile = new File(getFilesDir(),"calcHistory.txt");
        FileOutputStream fileOutputStream = null;

        try
        {

            fileOutputStream = new FileOutputStream(myFile,true);
            fileOutputStream.write(calc.getBytes());
            fileOutputStream.write("\n".getBytes());
            //Toast.makeText(getApplicationContext(),"saved dir: " + myFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        calc = null;
    }



    public boolean PrevOperation() {
        return this.tDisplay.getText().equals("") ? false : true;
    }
    public boolean NoOperation() {
        return op.equals("") ? true : false;

    }
    public boolean IncOperation() {
        return (!op.equals("") && this.tDisplay.getText().equals("")) ? true : false;
    }
    public void Equalop(View v) {
        Button temp=(Button)v;

        try {
            if (!this.tDisplay.getText().equals("")) {
                val2 = Double.parseDouble(this.eDisplay.getText().toString());
            }
            //if Textview is empty then val1 is ans val2 remain unchanged for incremental operation
            else {
                val1 = ans;
            }


            switch (op) {
                case "+":
                    ans = val1 + val2;
                    break;
                case "-":
                    ans = val1 - val2;
                    break;
                case "×":
                    ans = val1 * val2;
                    break;
                case "÷":
                    ans = val1 / val2;
                    break;
                default:
                    break;

            }


            //Log.d("val1",String.valueOf(val1));
            //Log.d("val2",String.valueOf(val2));
            oPress = true;

            if (temp.getText().equals("=")) {

                //if(!this.tDisplay.getText().equals(""))
                if (PrevOperation())
                    calc = this.tDisplay.getText().toString() + this.eDisplay.getText().toString() + "=" + String.valueOf(ans);
                    //else if(op.equals(""))
                else if (NoOperation()) {
                    calc = this.eDisplay.getText() + "=" + this.eDisplay.getText();
                    ans = Double.parseDouble(this.eDisplay.getText().toString());
                } else if (IncOperation())
                    calc = String.valueOf(val1) + op + String.valueOf(val2) + "=" + String.valueOf(ans);

                this.tDisplay.setText(null);
                SaveToHistory(v);
            }
            this.eDisplay.setText(String.valueOf(ans));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //op = null;


    }


    public void ClearAll(View v) {

        isDot = isMR = false;
        this.eDisplay.setText("0");
        oPress = true;
        op = "";
        this.tDisplay.setText("");
    }


}


