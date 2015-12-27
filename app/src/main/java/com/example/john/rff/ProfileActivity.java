package com.example.john.rff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by John on 02/12/2015.
 */
public class ProfileActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        Button dataButton = (Button) findViewById(R.id.dataButton);
    }

    public void dataClicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), DataFlowActivity.class);
        startActivity(intent);
    }

}
