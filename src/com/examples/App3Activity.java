package com.examples;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
/**
 * Created by Amit Thapar on 25/04/2015.
 */
public class App3Activity extends Activity implements View.OnClickListener {

    EditText edit_name = null;
    EditText edit_mobile = null;
    EditText edit_email = null;
    String Error = null;
    Button rtn = null;
    TextView view_date = null;
    TextView view_slot = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getIntent().getExtras().getString("selected value");
        String selectedDate = getIntent().getExtras().getString("selected date");

        setContentView(R.layout.customer_form);

        TextView dateView = (TextView) findViewById(R.id.textViewDate);
        dateView.setText(selectedDate);
        TextView slotView = (TextView) findViewById(R.id.textViewSlot);
        slotView.setText(data);
    }

    @Override
    public void onClick(View view) {

        //To get the jason printed on the screen with radio buttons
        String serverURL = "http://192.168.43.124:9080/appointment-web/rest/reservation/add";

        rtn = (Button) findViewById(R.id.btnSubmit);
        edit_name = (EditText) findViewById(R.id.txtName);
        edit_mobile = (EditText) findViewById(R.id.txtMobile);
        edit_email = (EditText) findViewById(R.id.txtEmail);
        view_date = (TextView) findViewById(R.id.textViewDate);
        view_slot = (TextView) findViewById(R.id.textViewSlot);

        String slot = view_slot.getText().toString();
        String name = edit_name.getText().toString();
        String mobile = edit_mobile.getText().toString();
        String email = edit_email.getText().toString();
        String date = view_date.getText().toString();

        String jsonDate = Util.covertDatetoJSONDate(view_date.getText().toString());

        //System.out.println("json Date---------------------" + jsonDate);

        JSONObject toSend = new JSONObject();
        JSONObject childJSON = new JSONObject();
        try {
            toSend.put("slot", slot);
            childJSON.put("name", name);
            long mob = Long.valueOf(mobile).longValue();
            childJSON.put("mobile", mob);
            childJSON.put("email", email);
            toSend.put("customer", childJSON);
            toSend.put("reservationDate", jsonDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //System.out.println("JSON Object---------------------" + toSend.toString());
        GetJsonTask client = new GetJsonTask(serverURL, toSend);
        client.execute();
        }


    public class GetJsonTask extends AsyncTask<Void, Void, JSONObject> {
        private String URL;
        private JSONObject jsonObjSend;
        private ProgressDialog Dialog = new ProgressDialog(App3Activity.this);

        public GetJsonTask(String URL, JSONObject jsonObjSend) {
            this.URL = URL;
            this.jsonObjSend = jsonObjSend;
        }

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            //Start Progress Dialog (Message)
            Dialog.setMessage("Please wait..");
            Dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObjRecv = null;
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPostRequest = new HttpPost(URL);

                StringEntity se;
                se = new StringEntity(jsonObjSend.toString());

                // Set HTTP parameters
                httpPostRequest.setEntity(se);
                httpPostRequest.setHeader("Accept", "application/json");
                httpPostRequest.setHeader("Content-type", "application/json");

                long t = System.currentTimeMillis();
                HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    // Read the content stream
                    InputStream stream = entity.getContent();
                    // convert content stream to a String
                    String resultString= Util.convertStreamToString(stream);
                    stream.close();
                    resultString = resultString.substring(1,resultString.length()-1); // remove wrapping "[" and "]"

                    if(resultString != null) {
                        jsonObjRecv = new JSONObject(resultString);
                    }
                    else
                    {
                        jsonObjRecv = new JSONObject("");
                    }

                    // Raw DEBUG output of our received JSON object:
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjRecv;
        }

        protected void onPostExecute(JSONObject result) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (result != null) {

                sendErrorText();

            } else {

                view_date.setText("DATA  SAVED");
                view_slot.setText("");
                edit_name.setText("");
                edit_mobile.setText("");
                edit_email.setText("");

            }

            }

        void sendErrorText()
        {
            edit_name.setText("Output : "+Error);
            edit_mobile.setText("Output : "+Error);
            edit_email.setText("Output : "+Error);

        }




    }

}
/*  ###Sample JSON format ###
{
        "slot": "9:00 - 10:00",
        "customer": {
                "name": "devendra",
                "mobile": 94124444444,
                "email": "to.devendra@gmail.com"
        },
        "reservationDate": "2015-05-01T00:00:00Z"
}
*/