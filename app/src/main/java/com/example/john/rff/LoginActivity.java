package com.example.john.rff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by John on 02/12/2015.
 */

public class LoginActivity extends Activity
{
    //EditText userName;
    //private TextView name = (TextView) findViewById(R.id.userName);
    //private String userName = name.getText()+"";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //userName = (EditText) findViewById(R.id.userName);
        TextView hyperLink = (TextView) findViewById(R.id.hyperLink);
        hyperLink.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void loginButtonClicked(View view)
    {
        //if(userName.equals("Dogu"))
        Toast.makeText(getApplicationContext(), "Kullanıcı adı veya şifresi hatalı.", Toast.LENGTH_LONG).show();
        // else
        // Toast.makeText(getApplicationContext(), "Kullanıcı adı veya şifresi hatalı.", Toast.LENGTH_LONG).show();
    }

    public void skipClicked(View view)
    {
        //CharSequence charSequence = userName.getText();
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        //intent.putExtra("key", charSequence);
        startActivity(intent);
    }
}
