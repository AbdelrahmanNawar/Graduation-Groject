package com.example.nawar.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signup_button(View view) {
        startActivity(new Intent(MainActivity.this, Main3Activity.class));

    }

    public void Login_button(View view) {
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }
}
