package at.fhooe.mc.android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.R;

public class ActivityAddCar extends Activity implements View.OnClickListener {

    private static final String TAG = "TANK";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        Button b=null;
        b=findViewById(R.id.activity_add_car_button_saveCar);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId()){
            case R.id.activity_add_car_button_saveCar:
                Log.i(TAG,"Save car pressed");

                EditText car=(EditText)findViewById(R.id.activity_add_car_text_carName);
                String carName = car.getText().toString();
                EditText mileage=(EditText)findViewById(R.id.activity_add_car_number_mileage);
                int carMilage = Integer.parseInt(mileage.getText().toString());
                Car myCar=new Car(carName,carMilage);

                /*
                Intent intent=new Intent(this,MainActivity.class);
                intent.putExtra("newCar",myCar);
                startActivity(intent);
                */

                /*--------- return intent ----------*/
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newCarResult", myCar);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

                break;
            default:
                Log.e(TAG, "unexpected ID encountered");

        }

    }
}
