package com.example.john.rff;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DataFlowActivity extends Activity
{
    Button refreshButton;
    ProgressBar progressBar;
    int i = 0;

    TextView voltage;
    TextView temperature;
    static int volt;
    static int tempr;
    boolean ischarging;
    int isc;

    TextView energy;
    TextView distance;
    TextView calorie;
    TextView gas;
    TextView discharge;

    static int batteryAtFirst = 0;
    static int batteryCurrent = 0;
    static int batteryDifference = 0;
    static int cycle = 1;
    static double gasSaved = 0;
    static double kJoule = 0;
    static double dischargeTl = 0;

    private TextView batteryCurrentTxt;
    private TextView batteryAtFirstTxt;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context ctxt, Intent intent)
        {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            volt = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            tempr = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            isc = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            if (!(isc == 0)) ischarging = true;
            else ischarging = false;


            batteryCurrent = level;

            if (batteryAtFirst == 0)
                batteryAtFirst = level;

            batteryCurrentTxt.setText("Şuan: " + batteryCurrent + "%");
            batteryAtFirstTxt.setText("İlk: " + batteryAtFirst + "%");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataflow_activity);
        findViewById(R.id.dataFlowTable).invalidate();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Extra Information:
        voltage = (TextView) findViewById(R.id.voltage);
        temperature = (TextView) findViewById(R.id.Temperature);

        // Essential Information:
        refreshButton = (Button) findViewById(R.id.refreshButton);
        energy = (TextView) findViewById(R.id.energyText);
        distance = (TextView) findViewById(R.id.distanceText);
        calorie = (TextView) findViewById(R.id.calorieText);
        gas = (TextView) findViewById(R.id.gasText);
        discharge = (TextView) findViewById(R.id.discharge);

        batteryAtFirstTxt = (TextView) this.findViewById(R.id.batteryAtFirstTxt);
        batteryCurrentTxt = (TextView) this.findViewById(R.id.batteryTxt);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    } // ONCREATE

    @Override
    protected void onResume()
    {
        super.onResume();

        progressBar.setVisibility(View.GONE);

        batteryDifference = batteryCurrent - batteryAtFirst;

        if (batteryDifference >= 1)
        {
            cycle = batteryDifference * 16;
            energy.setText("" + batteryDifference);
            distance.setText("" + cycle);
            // Calorie Calculation:
            kJoule = ((double) cycle / 1000) * 308;
            calorie.setText(kJoule + "kj");
            // Saved Gas Calculation:
            gasSaved = ((double) cycle / 1000) * 0.07;
            gas.setText("" + gasSaved);
            // Discharge Calculation:
            dischargeTl = ((double) batteryDifference / 100) * 0.1;
            discharge.setText(dischargeTl + "");
        }

        refreshButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(getIntent());
            }
        });

        final Thread tProgressBar = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    while (!isInterrupted())
                    {
                        if (i == 100) i = 0;
                        Thread.sleep(200);
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (ischarging)
                                {
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.setProgress(i);
                                    i += 10;
                                    refreshButton.setText("Şarj Oluyor...");
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    refreshButton.setText("Yenile");
                                }
                            }
                        });
                    }
                } catch (InterruptedException e)
                {
                }
            }
        };


        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    while (!isInterrupted())
                    {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if(ischarging)
                                {
                                    voltage.setText("Anlık Voltaj: " + volt);
                                    temperature.setText("Anlık Sıcaklık: " + tempr);
                                }
                                else
                                {
                                    voltage.setText("Anlık Voltaj: -");
                                    temperature.setText("Anlık Sıcaklık: -");
                                }
                            }
                        });
                    }
                } catch (InterruptedException e)
                {
                }
            }
        };


        tProgressBar.start();
        t.start();

    } // OnResume


} // ACTIVITY


// This Commented code for presentations. In situations like no bicycle available:
        /*Thread t = new Thread()
        {

            @Override
            public void run()
            {
                try
                {
                    while (!isInterrupted())
                    {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                batteryDifference += 1;
                                cycle = batteryDifference * 16;
                                energy.setText("" + batteryDifference);
                                distance.setText("" + cycle);
                                // Calorie Calculation:
                                kJoule = ((double) cycle / 1000) * 308;
                                calorie.setText(kJoule + "kj");
                                // Saved Gas Calculation:
                                gasSaved = ((double) cycle / 1000) * 0.07;
                                gas.setText("" + gasSaved);
                                // Discharge Calculation:
                                dischargeTl = ((double) batteryDifference / 100) * 0.1;
                                discharge.setText(dischargeTl + "");
                            }
                        });
                    }
                } catch (InterruptedException e)
                {
                }
            }
        };
        t.start();*/
