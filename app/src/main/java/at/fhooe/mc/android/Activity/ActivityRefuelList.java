package at.fhooe.mc.android.Activity;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.sql.Ref;
import java.util.LinkedList;
import java.util.List;

import at.fhooe.mc.android.Fragments.FragementStatistic;
import at.fhooe.mc.android.Fragments.FragmentRefuelList;
import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.R;
import at.fhooe.mc.android.Objects.Refuel;
import at.fhooe.mc.android.Adapter.RefuelAdapter;

public class ActivityRefuelList extends Activity implements OnBackPressedListener, View.OnClickListener  {

    private static final String TAG = "TANK";
    private static final String SP_KEY = "PatrolAttendent";
    List<Refuel> refuelList;
    Car car;
    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference carRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel_list);

        Button b = null;
        b = findViewById(R.id.activity_refuel_list_button_addRefuel);
        b.setOnClickListener(this);

        ImageButton ib = null;
        ib = (ImageButton) findViewById(R.id.activity_refuel_list_imageButton_showRefuelList);
        ib.setOnClickListener(this);
        ib = (ImageButton) findViewById(R.id.activity_refuel_List_imageButton_statistic);
        ib.setOnClickListener(this);

        //Intalize List
        refuelList=new LinkedList<Refuel>();



        car = (Car) getIntent().getExtras().getSerializable("myCarRef");


        //List Fragment as Default
        final FragmentManager fMgr = getFragmentManager();
        FragmentTransaction fT = fMgr.beginTransaction();
        fT.replace(R.id.activity_refuel_list_fragmentcontainer, new FragmentRefuelList());
        fT.commit();


        //Set String
        carRef = database.getReference(car.getmCar());

        //firebase Listener loads Refuel List
        carRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Refuel> refuelListRestore = dataSnapshot.getValue(new GenericTypeIndicator<List<Refuel>>() {});
                if(refuelListRestore!=null){
                    refuelList=refuelListRestore;

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }

    @Override
    public void onClick(View _v) {
        final FragmentManager fMgr = getFragmentManager();
        switch(_v.getId()){
            case R.id.activity_refuel_list_button_addRefuel:{
                Log.i(TAG,"addRefuel pressed");
                Intent intent=new Intent(this, ActivityAddRefuel.class);
                if(refuelList.size()!=0){
                    intent.putExtra("LastMileage", refuelList.get(refuelList.size()-1).getmMileage());
                }else{
                    intent.putExtra("LastMileage", car.getmMileage());
                }
                startActivityForResult(intent,2);}
                break;
            case R.id.activity_refuel_list_imageButton_showRefuelList:{
                Log.i(TAG,"RefuelList pressed");
                FragmentTransaction fT = fMgr.beginTransaction();
                fT.replace(R.id.activity_refuel_list_fragmentcontainer, new FragmentRefuelList());
                fT.commit();}
                break;
            case R.id.activity_refuel_List_imageButton_statistic:{
                Log.i(TAG,"Statistic");
                //Send him List view
                getIntent().putExtra("RefuelList", (Serializable) refuelList);
                //send CarMileAge
                getIntent().putExtra("CarMileage", car.getmMileage());
                FragmentTransaction fT = fMgr.beginTransaction();
                fT.replace(R.id.activity_refuel_list_fragmentcontainer, new FragementStatistic());
                fT.commit();}
                break;
            default:
                Log.e(TAG,"unexpected ID encountered");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            Refuel newRefuel = (Refuel)data.getExtras().getSerializable("myRefuel");
            Toast.makeText(this, "added " , Toast.LENGTH_SHORT).show();


            refuelList.add(newRefuel);
            carRef.setValue(refuelList); //update Database

            //refresh Fragement
            final FragmentManager fMgr = getFragmentManager();
            FragmentTransaction fT = fMgr.beginTransaction();
            fT.replace(R.id.activity_refuel_list_fragmentcontainer, new FragmentRefuelList());
            fT.commit();
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences(SP_KEY, MODE_PRIVATE);

        List<Fragment> fragmentList = getFragmentManager().getFragments();
        if (fragmentList != null) {

            for(Fragment fragment : fragmentList){

                if(fragment instanceof OnBackPressedListener){
                    ((OnBackPressedListener)fragment).onBackPressed();
                    Boolean deleteON = sp.getBoolean("FragmentDeleteBoolean", true); // holt sich String

                    if(deleteON == false){
                        super.onBackPressed();
                    }
                }

            }
        }
    }
}
