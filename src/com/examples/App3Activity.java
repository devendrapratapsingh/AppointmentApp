package com.examples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Amit Thapar on 25/04/2015.
 */
public class App3Activity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getIntent().getExtras().getString("selected value");
        setContentView(R.layout.main2);
        TextView textView=(TextView)findViewById(R.id.textView1);
        textView.setText(data);
     }
}
