package com.example.nawar.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask <String,Void,String>
{
    String register_url= "http:// 192.168.1.107/mobile/register_test.php";
    String login_url= "http:// 192.168.1.107/mobile/login.php";

    Context ctx;
    ProgressDialog progressDialog;

    Activity activity;
    AlertDialog.Builder builder;

    public BackgroundTask(Context ctx)
    {
        this.ctx = ctx;
        activity = (Activity)ctx;
    }

    @Override
    protected void onPreExecute(){
       builder = new AlertDialog.Builder(activity);
       progressDialog = new ProgressDialog(ctx);
       progressDialog.setTitle("Please wait...");
       progressDialog.setMessage("Connecting to server...");
       progressDialog.setIndeterminate(true);
       progressDialog.setCancelable(false);
       progressDialog.show();
    }
    @Override

    protected String doInBackground(String... params) {
        String method  = params [0];

        if (method.equals("register"))
        {
            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String FirstName = params [1];
                String LastName = params [2];
                String Username = params [3];
                String Password = params [4];
                String Email = params [5];
                String PhoneNumber = params [6];
                String data = URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(FirstName,"UTF-8")+"$"+
                        URLEncoder.encode("LastName","UTF-8")+"="+URLEncoder.encode(LastName,"UTF-8")+"$"+
                        URLEncoder.encode("Username","UTF-8")+"="+URLEncoder.encode(Username,"UTF-8")+"$"+
                        URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"$"+
                        URLEncoder.encode("Email","UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"$"+
                        URLEncoder.encode("PhoneNumber","UTF-8")+"="+URLEncoder.encode(PhoneNumber,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(line+"\n");
                }

                httpURLConnection.disconnect();
                Thread.sleep(5000);
                Log.d("Test","test 3 pass");
                return stringBuilder.toString().trim();

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("login"))
        {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String Username = params [1];
                String Password = params [2];
                String data = URLEncoder.encode("Username","UTF-8")+"="+URLEncoder.encode(Username,"UTF-8")+"$"+
                                URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(line+"\n");
                }

                httpURLConnection.disconnect();
                Thread.sleep(5000);
                Log.d("Test","test 3 pass");
                return stringBuilder.toString().trim();

            }catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String json) {

        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_Response");
            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("message");
            if(code.equals("reg_true"))
            {
                showDialog("Registration Success",message,code);
            }
            else if (code.equals("reg_false"))
            {
                showDialog("Registration Failed",message,code);

            }
            else if (code.equals("login_true"))
            {
                Intent intent = new Intent (activity,HomeActivity.class);
                intent.putExtra("message",message);
                activity.startActivity(intent);
            }
            else if (code.equals("login_false"))
            {
                showDialog("login error...",message,code);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void showDialog(String title, String message, String code)
    {
        builder.setTitle(title);
        if(code.equals("reg_true")||code.equals("reg_false"))
        {
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            });

        }
        else if (code.equals("login_false"))
        {
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText Username,Password;
                    Username = activity.findViewById(R.id.Username);
                    Password = activity.findViewById(R.id.Password);
                    Username.setText("");
                    Password.setText("");
                    dialog.dismiss();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
