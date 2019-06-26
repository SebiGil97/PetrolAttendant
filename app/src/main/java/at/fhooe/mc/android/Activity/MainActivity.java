package at.fhooe.mc.android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import at.fhooe.mc.android.Adapter.CarAdapter;
import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.R;

public class MainActivity extends Activity implements View.OnClickListener {


    private static final String TAG = "TANK";
    List<Car> carList;
    CarAdapter adapter;
    boolean deleteON = false;

    //firebase

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CarList");
    DatabaseReference refuelRef;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);
        carList=new LinkedList<Car>();

        ImageButton ib = null;
        ib = (ImageButton) findViewById(R.id.activity_main_imageButton_addCar);
        ib.setOnClickListener(this);
        ib = (ImageButton) findViewById(R.id.activity_main_imageButton_delete);
        ib.setOnClickListener(this);


        // Read from the database

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Car> carListRestore = dataSnapshot.getValue(new GenericTypeIndicator<List<Car>>() {});
                if(carListRestore!=null){
                    carList=carListRestore;
                    for(int i=0;i<carList.size();i++){
                        adapter.add(carList.get(i));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //-----------DynamicList------------
        final ListView lv = (ListView) findViewById(R.id.activity_main_list);

        adapter = new CarAdapter(this); // which Context and how to use the selfmade adapter //carlist

        lv.setAdapter(adapter);

        lv.setClickable(true);
        lv.setLongClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int _position, long id) {
                Car car = (Car) lv.getItemAtPosition(_position);
                Toast.makeText(MainActivity.this, "clicked " + car.getmCar(), Toast.LENGTH_SHORT).show();


                Intent i = new Intent(MainActivity.this, ActivityRefuelList.class);
                i.putExtra("myCarRef", car);
                startActivity(i);

            }
        });



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int _pos, long id) {
                View v = findViewById(R.id.activity_main_imageButton_delete);
                v.setVisibility(View.VISIBLE);  //makes Delete Button Visible

                //make checkbox visible
                for(int i=0; i<carList.size(); i++){
                    carList.get(i).setDelete(true);
                }
                deleteON=true;
                adapter.notifyDataSetChanged();
                return true;
            }
        });




    }

    @Override
    public void onBackPressed() {

        //remove delete Buttons
        if(deleteON){
            closeDeleteMode();
        }else{
            super.onBackPressed();
        }

        Log.i(TAG,"back");
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()){
            case R.id.activity_main_imageButton_addCar:
                Log.i(TAG,"Add car pressed");
                Intent intent=new Intent(this, ActivityAddCar.class);
                intent.putExtra("carList",(Serializable)carList);
                startActivityForResult(intent,1);
                break;
            case R.id.activity_main_imageButton_delete:
                Log.i(TAG,"delete pressed");

                //removes checked items from list
                for(int i=0; i<carList.size();i++){
                    if(carList.get(i).isReadyDelete()){
                        //remove refuelList from firebase
                        refuelRef = database.getReference(carList.get(i).getmCar());
                        refuelRef.removeValue();
                        //remove car
                        adapter.remove(carList.get(i));
                        carList.remove(i);
                        i--;
                    }
                }
                adapter.notifyDataSetChanged();
                closeDeleteMode();
                myRef.setValue(carList);
                break;
            default:
                Log.e(TAG, "unexpected ID encountered");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //load car from ActivityAddCar
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Car newCar = (Car)data.getExtras().getSerializable("newCarResult");
                Toast.makeText(this, "added " + newCar.getmCar(), Toast.LENGTH_SHORT).show();
                carList.add(newCar);
                myRef.setValue(carList);
                adapter.add(newCar);
                adapter.notifyDataSetChanged();
            }

        }

    }//onActivityResult

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Destroy");
        myRef.setValue(carList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(deleteON){
            closeDeleteMode();
        }
        //remove delete Buttons

    }

    public void closeDeleteMode(){
        for(int i=0; i<carList.size();i++){
            carList.get(i).setDelete(false);
            carList.get(i).setReadyDelete(false);
        }
        deleteON=false;
        View v = findViewById(R.id.activity_main_imageButton_delete);
        v.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }
}

