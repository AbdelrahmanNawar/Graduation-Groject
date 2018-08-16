package com.example.nawar.mobile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

    EditText FirstName,LastName,Username,Password,Email,PhoneNumber;
    Button reg_button;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        FirstName = (EditText)findViewById(R.id.FirstName);
        LastName = (EditText)findViewById(R.id.LastName);
        Username = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        Email = (EditText)findViewById(R.id.Email);
        PhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        reg_button = (Button)findViewById(R.id.sign_up);
        reg_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(FirstName.getText().toString().equals("")||LastName.getText().toString().equals("")||Username.getText().toString().equals("")||Password.getText().toString().equals("")||Email.getText().toString().equals("")||PhoneNumber.getText().toString().equals(""))
                {
                    builder = new AlertDialog.Builder(Main3Activity.this);
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please fill all the fields");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                }
                else
                {
                    BackgroundTask backgroundTask = new BackgroundTask(Main3Activity.this);
                    backgroundTask.execute("register",FirstName.getText().toString(),LastName.getText().toString(),Username.getText().toString(),Password.getText().toString(),Email.getText().toString(),PhoneNumber.getText().toString());
                }

            }
        });
    }
}
