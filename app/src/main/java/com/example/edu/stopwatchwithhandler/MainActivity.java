package com.example.edu.stopwatchwithhandler;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mButtonStart, mButtonPause, mButtonReset, mButtonLap;
    TextView mTextview, mTextviewLap;
    Handler handler = new Handler();
    private long millisecondTime, timeBuff, updateTime;
    private int seconds, minutes, milliSeconds;
    private long startTime= 0;
    private String time=null;
    private StringBuffer lap=new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonStart = findViewById(R.id.buttonStart);
        mButtonPause = findViewById(R.id.buttonPause);
        mButtonReset = findViewById(R.id.buttonReset);
        mTextview=findViewById(R.id.textView);
        mButtonLap = findViewById(R.id.buttonLap);
        mTextviewLap = findViewById(R.id.textviewLap);

        mButtonStart.setOnClickListener(this);
        mButtonPause.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtonLap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                startTime = SystemClock.uptimeMillis();
                handler.post(runnable);
                break;
            case R.id.buttonPause:
                timeBuff += millisecondTime;
                handler.removeCallbacks(runnable);
                break;
            case R.id.buttonReset:
                timeBuff=0;
                mTextview.setText("00 : 00 : 000");
                //mButtonStart.callOnClick();
                break;
            case R.id.buttonLap:

                lap.append(time + "\n");
                mTextviewLap.setText(lap);
                break;
        }

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);
            time = String.format("%02d : %02d : %03d ", minutes, seconds, milliSeconds);
            mTextview.setText(time);
           handler.post(this);
        }
    };
}

