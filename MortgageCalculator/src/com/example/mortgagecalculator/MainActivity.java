package com.example.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.NumberFormat; // for currency formatting

import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

public class MainActivity extends Activity {

	private static final NumberFormat currencyFormat = 
			NumberFormat.getCurrencyInstance();
	private static final NumberFormat percentFormat =
			NumberFormat.getPercentInstance();
	private static final NumberFormat intFormat =
			NumberFormat.getIntegerInstance();
	private double purchasePriceAmount = 0.0;
	private double downPaymentAmount = 0.0;
	private double interestRateAmount = 0.0;
	private int customYear = 15;
	private TextView purchasePriceAmountView;
	private TextView downPaymentAmountView;
	private TextView interestRateAmountView;
	private TextView customMonthlyView;
	private TextView year10MonthlyAmountView;
	private TextView year20MonthlyAmountView;
	private TextView year30MonthlyAmountView;
	private TextView customMonthlyAmountView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		purchasePriceAmountView = 
				(TextView) findViewById(R.id.purchasePriceAmountView);
		downPaymentAmountView =
				(TextView) findViewById(R.id.downPaymentAmountView);
		interestRateAmountView =
				(TextView) findViewById(R.id.interestRateAmountView);
		customMonthlyView = 
				(TextView) findViewById(R.id.customMonthlyView);
		year10MonthlyAmountView =
				(TextView) findViewById(R.id.year10MonthlyAmountView);
		year20MonthlyAmountView =
				(TextView) findViewById(R.id.year20MonthlyAmountView);
		year30MonthlyAmountView =
				(TextView) findViewById(R.id.year30MonthlyAmountView);
		customMonthlyAmountView = 
				(TextView) findViewById(R.id.customMonthlyAmountView);
		
		purchasePriceAmountView.setText(
				currencyFormat.format(purchasePriceAmount));
		downPaymentAmountView.setText(
				currencyFormat.format(downPaymentAmount));
		interestRateAmountView.setText(
				percentFormat.format(interestRateAmount));
		updateStandard();
		updateCustom();
		
		EditText purchasePriceEditAmount =
				(EditText) findViewById(R.id.purchasePriceEditAmount);
		purchasePriceEditAmount.addTextChangedListener(purchasePriceEditAmountWatcher);
		EditText downPaymentEditAmount =
				(EditText) findViewById(R.id.downPaymentEditAmount);
		downPaymentEditAmount.addTextChangedListener(downPaymentEditAmountWatcher);
		EditText interestRateEditAmount =
				(EditText) findViewById(R.id.interestRateEditAmount);
		interestRateEditAmount.addTextChangedListener(interestRateEditAmountWatcher);
		SeekBar periodAmountSeekBar =
				(SeekBar) findViewById(R.id.periodAmountSeekBar);
		periodAmountSeekBar.setOnSeekBarChangeListener(periodAmountSeekBarListener);
	}
	private void updateStandard(){
		double year10MonthlyAmount = ((purchasePriceAmount-downPaymentAmount)
				* (interestRateAmount*Math.pow((1+interestRateAmount),120))
				/(Math.pow((1+interestRateAmount),120)-1));
		double year20MonthlyAmount = ((purchasePriceAmount-downPaymentAmount)
				* (interestRateAmount*Math.pow((1+interestRateAmount),240))
				/(Math.pow((1+interestRateAmount),240)-1));
		double year30MonthlyAmount = ((purchasePriceAmount-downPaymentAmount)
				* (interestRateAmount*Math.pow((1+interestRateAmount),360))
				/(Math.pow((1+interestRateAmount),360)-1));
		
		year10MonthlyAmountView.setText(currencyFormat.format(year10MonthlyAmount));
		year20MonthlyAmountView.setText(currencyFormat.format(year20MonthlyAmount));
		year30MonthlyAmountView.setText(currencyFormat.format(year30MonthlyAmount));
		
	}
	private void updateCustom(){
		customMonthlyView.setText(intFormat.format(customYear)+"years");
		
		double customMonthlyAmount = ((purchasePriceAmount-downPaymentAmount)
				* (interestRateAmount*Math.pow((1+interestRateAmount),customYear*12))
				/(Math.pow((1+interestRateAmount),customYear*12)-1));
		
		customMonthlyAmountView.setText(currencyFormat.format(customMonthlyAmount));
	}
	@Override
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
	private TextWatcher purchasePriceEditAmountWatcher = new TextWatcher() 
	{
	     // called when the user enters a number
	     @Override
	     public void onTextChanged(CharSequence s, int start, 
	         int before, int count) 
	     {         
	         // convert amountEditText's text to a double
	    	 try
	         {
	            purchasePriceAmount = Double.parseDouble(s.toString());
	         } // end try
	         catch (NumberFormatException e)
	         {
	            purchasePriceAmount = 0.0; // default if an exception occurs
	         } // end catch 

	         // display currency formatted bill amount
	         purchasePriceAmountView.setText(currencyFormat.format(purchasePriceAmount));
	         updateStandard(); // update the 15% tip TextViews
	         updateCustom(); // update the custom tip TextViews
	      } // end method onTextChanged

	      @Override
	      public void afterTextChanged(Editable s) 
	      {
	      } // end method afterTextChanged

	      @Override
	      public void beforeTextChanged(CharSequence s, int start, int count,
	         int after) 
	      {
	      } // end method beforeTextChanged
	};// end purchasePriceEditTextWatcher
	
	private TextWatcher downPaymentEditAmountWatcher = new TextWatcher() 
	{
	     // called when the user enters a number
	     @Override
	     public void onTextChanged(CharSequence s, int start, 
	         int before, int count) 
	     {         
	         // convert amountEditText's text to a double
	    	 try
	         {
	            downPaymentAmount = Double.parseDouble(s.toString());
	         } // end try
	         catch (NumberFormatException e)
	         {
	            downPaymentAmount = 0.0; // default if an exception occurs
	         } // end catch 

	         // display currency formatted bill amount
	         downPaymentAmountView.setText(currencyFormat.format(downPaymentAmount));
	         updateStandard(); // update the 15% tip TextViews
	         updateCustom(); // update the custom tip TextViews
	      } // end method onTextChanged

	      @Override
	      public void afterTextChanged(Editable s) 
	      {
	      } // end method afterTextChanged

	      @Override
	      public void beforeTextChanged(CharSequence s, int start, int count,
	         int after) 
	      {
	      } // end method beforeTextChanged
	};
	private TextWatcher interestRateEditAmountWatcher = new TextWatcher() 
	{
	     // called when the user enters a number
	     @Override
	     public void onTextChanged(CharSequence s, int start, 
	         int before, int count) 
	     {         
	         // convert amountEditText's text to a double
	    	 try
	         {
	            interestRateAmount = (Double.parseDouble(s.toString()) / 100.0)/12;
	         } // end try
	         catch (NumberFormatException e)
	         {
	            interestRateAmount = 0.0; // default if an exception occurs
	         } // end catch 

	         // display currency formatted bill amount
	         interestRateAmountView.setText(percentFormat.format(interestRateAmount*12));
	         updateStandard(); // update the 15% tip TextViews
	         updateCustom(); // update the custom tip TextViews
	      } // end method onTextChanged

	      @Override
	      public void afterTextChanged(Editable s) 
	      {
	      } // end method afterTextChanged

	      @Override
	      public void beforeTextChanged(CharSequence s, int start, int count,
	         int after) 
	      {
	      } // end method beforeTextChanged
	}; 
	private OnSeekBarChangeListener periodAmountSeekBarListener = 
		      new OnSeekBarChangeListener() 
		   {
		      // update customPercent, then call updateCustom
		      @Override
		      public void onProgressChanged(SeekBar seekBar, int progress,
		         boolean fromUser) 
		      {
		         // sets customPercent to position of the SeekBar's thumb
		         customYear = progress;
		         updateCustom(); // update the custom tip TextViews
		      } // end method onProgressChanged

		      @Override
		      public void onStartTrackingTouch(SeekBar seekBar) 
		      {
		      } // end method onStartTrackingTouch

		      @Override
		      public void onStopTrackingTouch(SeekBar seekBar) 
		      {
		      } // end method onStopTrackingTouch
		   };
}
