package at.fhooe.mc.android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.ListIterator;

import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.R;

public class ActivityAddCar extends Activity implements View.OnClickListener {

    private static final String TAG = "TANK";
    List<Car> carList;
    boolean sameCar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        Button b=null;
        b=findViewById(R.id.activity_add_car_button_saveCar);
        b.setOnClickListener(this);
        carList = (List<Car>)getIntent().getSerializableExtra("carList");
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId()){
            case R.id.activity_add_car_button_saveCar: {
                Log.i(TAG, "Save car pressed");

                EditText car = (EditText) findViewById(R.id.activity_add_car_text_carName);
                String carName = car.getText().toString();
                EditText mileage = (EditText) findViewById(R.id.activity_add_car_number_mileage);

                //check for same name
                for(int i=0;i<carList.size();i++){
                    if(carList.get(i).getmCar().equals(carName)){
                        sameCar=true;
                    }
                }

                if (carName.isEmpty()) {
                    Toast.makeText(this, "please enter a car", Toast.LENGTH_SHORT).show();
                } else if (mileage.getText().toString().equals("")) {
                    Toast.makeText(this, "please enter a mileage", Toast.LENGTH_SHORT).show();
                } else if(sameCar) {
                    Toast.makeText(this, carName + " already exists, please choose another name or extend it!", Toast.LENGTH_LONG).show();
                }else{
                        int carMileage = Integer.parseInt(mileage.getText().toString());
                        Car myCar = new Car(carName, carMileage);

                        /*--------- return intent ----------*/
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("newCarResult", myCar);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                }


                sameCar=false;
            } break;
            default:
                Log.e(TAG, "unexpected ID encountered");

        }

    }
}
