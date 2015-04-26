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
import android.view.View.OnClickListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



public class App2Activity extends Activity  {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getIntent().getExtras().getString("calendarValue");
        setContentView(R.layout.rest_ful_webservice);
        TextView textView=(TextView)findViewById(R.id.textView1);
        textView.setText(data);
        //To get the jason printed on the screen with radio buttons
        String serverURL = "http://www.javascriptkit.com/dhtmltutors/javascriptkit.json";
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
/*
            try{
                // Set Request parameter
                // This is required in case data needs to be passed as a request parameter.
                //data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
*/

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
                String titleChild = "";
                String linkChild = "";
                String descriptionChild = "";

                JSONObject json;

                try {
                    /*
                    /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                    json = new JSONObject(Content);

                    String title = (String)json.get("title");
                    String link = (String)json.get("link");
                    String description = (String)json.get("description");
                    String language = (String)json.get("language");

                    /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                    /*******  Returns null otherwise.  *******/
                    JSONArray jsonMainNode = json.optJSONArray("items");

                    /*********** Process each JSON Node ************/
                    int lengthJsonArr = jsonMainNode.length();

                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        /****** Get Object for each JSON node.***********/
                       JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        /******* Fetch node values **********/
                        titleChild       = jsonChildNode.optString("title").toString();
                        linkChild     = jsonChildNode.optString("link").toString();
                        descriptionChild = jsonChildNode.optString("description").toString();

                   }

                    /****************** End Parse Response JSON Data *************/
                    OutputData += " Title           : "+ titleChild +"    "+ "Link      : "+ linkChild+ "   Description      :"+descriptionChild;
                    //Show Parsed Output on screen (activity)
                    radioBtn0.setText(titleChild);
                    radioBtn1.setText(linkChild);
                    radioBtn2.setText(descriptionChild);
                    radioBtn3.setText(language);

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
                    startActivity(intent);
                break;
            case R.id.radio1:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                    intent.putExtra("selected value", ((RadioButton) view).getText());
                    startActivity(intent);
                    break;
            case R.id.radio2:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                    intent.putExtra("selected value", ((RadioButton) view).getText());
                    startActivity(intent);
                    break;
            case R.id.radio3:
                if (checked)
                    intent = new Intent(this, App3Activity.class);
                    intent.putExtra("selected value", ((RadioButton) view).getText());
                    startActivity(intent);
                    break;
            }
    }
}