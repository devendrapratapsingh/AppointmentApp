package com.examples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class App2Activity extends Activity {

	Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String data = getIntent().getExtras().getString("calendarValue");
		//calendarValue.setText(data);
		setContentView(R.layout.main2);
		TextView textView=(TextView)findViewById(R.id.textView1);
		textView.setText(data);
		
		
	}

}