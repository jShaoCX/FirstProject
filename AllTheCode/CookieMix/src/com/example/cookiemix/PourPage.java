package com.example.cookiemix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//THIS CAN BE FOR BOBA TOO! FUTURE IDEA?
public class PourPage extends Activity implements SensorEventListener{

	Sensor accelerometer;
	SensorManager sm;
	TextView acceleration;
	ImageView ingredientIcon;
	ImageView pourGauge;
	
	double pourAmount;
	
	int eggsCracked;
	
	IngredientType currentIngredient = IngredientType.NONE;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pour_page);
        
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        sm.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        acceleration = (TextView)findViewById(R.id.textView_acceleration);
        
        pourGauge = (ImageView)findViewById(R.id.imageView_pourAmount);
        ingredientIcon = (ImageView)findViewById(R.id.imageView_icon);
        
        pourAmount = 1.0;
        eggsCracked = 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pour_page, menu);
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


	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		acceleration.setText("X: " + arg0.values[0] + 
				"\nY: " + arg0.values[1] + 
				"\nZ: " + arg0.values[2]);
		
		if(currentIngredient == IngredientType.EGG)
		{
			if(arg0.values[2] > 19.5 && eggsCracked < 4)
			{
				//doesnt work in increments, too many events caught 
				//and fill up gauge all the way too quick
				eggsCracked++;
				pourGauge.getLayoutParams().height = eggsCracked*200;
			}
		}
		else
		{
			pourAmount += arg0.values[1];
		
			if(pourAmount > 0 && pourAmount < 800)
			{
				int roundedAmount = (int) Math.round(pourAmount);
				pourGauge.getLayoutParams().height = roundedAmount;
			} 
			else if(pourAmount < 0)
			{
				pourAmount = 0;
			}
			else if(pourAmount > 800)
			{
				pourAmount = 800;
			}
		}
	}
	
    public void GoToFlour(View view)
    {
    	currentIngredient = IngredientType.FLOUR;
    	
    	Context context = ingredientIcon.getContext();
    	int id = context.getResources().getIdentifier("flour_icon", "drawable", context.getPackageName());
    	ingredientIcon.setImageResource(id);
    	pourAmount = 0;
    	pourGauge.getLayoutParams().height=0;
    	pourGauge.setBackgroundColor(Color.parseColor("#FFFFFF"));
    	
    }
    
    public void GoToBrownSugar(View view)
    {
    	currentIngredient = IngredientType.BROWN_SUGAR;
    	
    	Context context = ingredientIcon.getContext();
    	int id = context.getResources().getIdentifier("brownsugar_icon", "drawable", context.getPackageName());
    	ingredientIcon.setImageResource(id);
    	pourGauge.getLayoutParams().height=0;
    	pourAmount = 0;
    	pourGauge.setBackgroundColor(Color.parseColor("#B87333"));
    }
    
    public void GoToWhiteSugar(View view)
    {
    	currentIngredient = IngredientType.WHITE_SUGAR;
    	
    	Context context = ingredientIcon.getContext();
    	int id = context.getResources().getIdentifier("whitesugar_icon", "drawable", context.getPackageName());
    	ingredientIcon.setImageResource(id);
    	pourGauge.getLayoutParams().height=0;
    	pourAmount = 0;
    	pourGauge.setBackgroundColor(Color.parseColor("#EEEEEE"));
    }
    
    public void GoToButter(View view)
    {
    	currentIngredient = IngredientType.BUTTER;
    	
    	Context context = ingredientIcon.getContext();
    	int id = context.getResources().getIdentifier("butter_icon", "drawable", context.getPackageName());
    	ingredientIcon.setImageResource(id);
    	pourGauge.getLayoutParams().height=0;
    	pourAmount = 0;
    	pourGauge.setBackgroundColor(Color.parseColor("#FFEC8B"));
    }
    
    public void GoToEgg(View view)
    {
    	currentIngredient = IngredientType.EGG;
    	
    	Context context = ingredientIcon.getContext();
    	int id = context.getResources().getIdentifier("egg_icon", "drawable", context.getPackageName());
    	ingredientIcon.setImageResource(id);
    	
    	eggsCracked = 0;
    	pourAmount = 0;
    	pourGauge.getLayoutParams().height = 0;
    	pourGauge.setBackgroundColor(Color.parseColor("#FFE600"));
    }
    
    public void StartMixing(View view){
    	Intent intent = new Intent(PourPage.this, MixingPage.class);
    	startActivity(intent);
    }
}
