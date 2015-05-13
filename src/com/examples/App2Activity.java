package com.examples;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class App2Activity extends Activity  {
    String data = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getIntent().getExtras().getString("calendarValue");
        setContentView(R.layout.rest_ful_webservice);
        TextView textView=(TextView)findViewById(R.id.textView1);
        textView.setText(data);
        //To get the jason printed on the screen with radio buttons

        String serverURL = "http://192.168.43.124:9080/appointment-web/rest/schedules/UN";

        new LongOperation().execute(serverURL);
    }

    // Parse the JSON from THE url

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        BufferedReader reader = null;

        try {

            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                inputStream.close();

            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        finally
        {
            try
            {
                reader.close();
            }

            catch(Exception ex) {}
        }

        return stringBuilder.toString();
    }

    // Class with extends AsyncTask class

    private class LongOperation  extends AsyncTask<String, Void, Void> {

        RadioButton radioBtn0 = (RadioButton) findViewById(R.id.radio0);
        RadioButton radioBtn1 = (RadioButton) findViewById(R.id.radio1);
        RadioButton radioBtn2 = (RadioButton) findViewById(R.id.radio2);
        RadioButton radioBtn3 = (RadioButton) findViewById(R.id.radio3);
        RadioButton radioBtn4 = (RadioButton) findViewById(R.id.radio4);
        RadioButton radioBtn5 = (RadioButton) findViewById(R.id.radio5);
        RadioButton radioBtn6 = (RadioButton) findViewById(R.id.radio6);
        RadioButton radioBtn7 = (RadioButton) findViewById(R.id.radio7);

        // Required initialization
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(App2Activity.this);

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            //Start Progress Dialog (Message)
            Dialog.setMessage("Please wait..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Content =    readJSONFeed(urls[0]);
            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

                sendErrorText();

            } else {

                // Show Response Json On Screen (activity)
                /****************** Start Parse Response JSON Data *************/
                String OutputData = "";

                JSONArray jsonArray;

                try {
                    /*
                    /****** Creates a new JSON Array. ********/
                     jsonArray = new JSONArray(Content);

                    /*********** Process each JSON Node ************/
                    int lengthJsonArr = jsonArray.length();
                    ArrayList myList = new ArrayList();
                    System.out.println("Length of Array---------------"+lengthJsonArr);
                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        HashMap<String, String> map = new HashMap<String, String>();
                        /****** Get Object for each JSON node.***********/
                        System.out.println("----"+i+"------"+jsonArray.getString(i));
                        map.put("slot_"+i,  jsonArray.getString(i));
                        myList.add(map);

                    }
                    /****************** End Parse Response JSON Data *************/
                    //Show Parsed Output on screen (activity)
                    String value = "";
                    if(myList.size()>0) {
                        for(int i=0; i < myList.size(); i++) {
                            Map schedule = (Map) myList.get(i);
                            {
                                if (schedule.containsKey("slot_0")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn0.setText(value);
                                        radioBtn0.setContentDescription(data);

                                    }
                                }
                                if (schedule.containsKey("slot_1")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn1.setText(value);
                                        radioBtn1.setContentDescription(data);
                                    }
                                }
                                if (schedule.containsKey("slot_2")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn2.setText(value);
                                        radioBtn2.setContentDescription(data);
                                    }
                                }
                                if (schedule.containsKey("slot_3")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn3.setText(value);
                                        radioBtn3.setContentDescription(data);
                                    }
                                }
                                if (schedule.containsKey("slot_4")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn4.setText(value);
                                        radioBtn4.setContentDescription(data);
                                    }
                                }
                                if (schedule.containsKey("slot_5")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn5.setText(value);
                                        radioBtn5.setContentDescription(data);
                                    }
                                }
                                if (schedule.containsKey("slot_6")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn6.setText(value);
                                        radioBtn6.setContentDescription(data);
                                    }
                                }
                                if (schedule.containsKey("slot_7")) {
                                    if ((schedule.get("slot_" + i) != null) || ("slot_" + i != "")) {
                                        value = (String) schedule.get("slot_" + i);
                                        radioBtn7.setText(value);
                                        radioBtn7.setContentDescription(data);
                                    }
                                }
                             }
                        }


                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

        void sendErrorText()
        {
            radioBtn0.setText("Output : "+Error);
            radioBtn1.setText("Output : "+Error);
            radioBtn2.setText("Output : "+Error);
            radioBtn3.setText("Output : "+Error);
            radioBtn4.setText("Output : "+Error);
            radioBtn5.setText("Output : "+Error);
            radioBtn6.setText("Output : "+Error);
            radioBtn7.setText("Output : "+Error);
         }

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Intent intent = null;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio0:
                if (checked)
                intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
               startActivity(intent);
                break;
            case R.id.radio1:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
                startActivity(intent);
                break;
            case R.id.radio2:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
                startActivity(intent);
                break;
            case R.id.radio3:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
                startActivity(intent);
                break;
            case R.id.radio4:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
                startActivity(intent);
                break;
            case R.id.radio5:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
                startActivity(intent);
                break;
            case R.id.radio6:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());;
                startActivity(intent);
                break;
            case R.id.radio7:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                intent.putExtra("selected value", ((RadioButton) view).getText());
                intent.putExtra("selected date", ((RadioButton) view).getContentDescription());
                startActivity(intent);
                break;
            }
    }
}