package com.example.target_heart_ratecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.text.NumberFormat; // for currency formatting
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.TextView; // for displaying text

public class MainActivity extends Activity {

	private static final NumberFormat intFormat = 
			NumberFormat.getIntegerInstance();
	
	private int age = 0;
	private TextView ageAmountView;
	private TextView maximumHeartRateAmountView;
	private TextView targetHeartRateAmountView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ageAmountView = 
				(TextView) findViewById(R.id.ageAmountView);
		maximumHeartRateAmountView =
				(TextView) findViewById(R.id.maximumHeartRateAmountView);
		targetHeartRateAmountView =
				(TextView) findViewById(R.id.targetHeartRateAmountView);
		
		ageAmountView.setText(intFormat.format(age));
		updateInfo();
		
		EditText ageEdit =
				(EditText) findViewById(R.id.ageEdit);
		ageEdit.addTextChangedListener(ageEditWatcher);
	}
	private void updateInfo(){
		int maximumHeartRate = 220 - age;
		int lowerTargetHeartRate = (int) (maximumHeartRate * 0.5);
		int upperTargetHeartRate = (int) (maximumHeartRate * 0.85);
		
		maximumHeartRateAmountView.setText(intFormat.format(maximumHeartRate));
		targetHeartRateAmountView.setText(intFormat.format(lowerTargetHeartRate)+
				" - "+intFormat.format(upperTargetHeartRate));
		
	}
	private TextWatcher ageEditWatcher = new TextWatcher(){
		public void onTextChanged(CharSequence s, int start, int before,
				int count){
			try{
				age = (int) Double.parseDouble(s.toString());
			}
			catch (NumberFormatException e){
				age = 0;
			}
			ageAmountView.setText(intFormat.format(age));
			updateInfo();
		}
		@Override
		public void afterTextChanged(Editable s){
			
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after){
			
		}
	};
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
