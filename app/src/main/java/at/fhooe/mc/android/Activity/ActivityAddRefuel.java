package at.fhooe.mc.android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.Objects.Refuel;

import static java.lang.String.valueOf;

public class ActivityAddRefuel extends Activity implements View.OnClickListener {

    private static final String TAG = "TANK";
    int lastMileage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refuel);
        Button b=null;
        b=findViewById(R.id.activity_add_refuel_button_save);
        b.setOnClickListener(this);
        lastMileage = (Integer) getIntent().getIntExtra("LastMileage",0);
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId()){
            case R.id.activity_add_refuel_button_save:
                Log.i(TAG,"SaveRefuel pressed");

                EditText mileage=(EditText)findViewById(R.id.activity_add_refuel_mileage);
                int newMileage = Integer.parseInt(mileage.getText().toString());

                //safe for wrong
                if(newMileage>lastMileage) {

                    EditText price = (EditText) findViewById(R.id.activity_add_refuel_price);
                    float newPrice = Float.parseFloat(price.getText().toString());
                    EditText liter = (EditText) findViewById(R.id.activity_add_refuel_liter);
                    float newLiter = Float.parseFloat(liter.getText().toString());

                    Refuel myRefuel = new Refuel(newPrice, newLiter, Calendar.getInstance().getTime(), newMileage);

                    /*--------- return intent ----------*/
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("myRefuel", myRefuel);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else{
                    Toast.makeText(this,"mileage must be over: " + lastMileage ,Toast.LENGTH_LONG).show();
                }
                break;



            default: Log.e(TAG,"unexpected ID");
        }
    }
}
