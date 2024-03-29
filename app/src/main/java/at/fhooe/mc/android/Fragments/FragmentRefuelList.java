package at.fhooe.mc.android.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import at.fhooe.mc.android.Activity.OnBackPressedListener;
import at.fhooe.mc.android.Adapter.RefuelAdapter;
import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.Objects.Refuel;
import at.fhooe.mc.android.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRefuelList extends Fragment implements OnBackPressedListener {

    private static final String SP_KEY = "PatrolAttendent";
    private static final String VALUE_KEY = "FragmentDeleteBoolean";
    List<Refuel> refuelList;
    private Car car;

    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference carRef;

    //Listview
    RefuelAdapter adapter;
    boolean deleteON;

    private static final String TAG = "TANK";

    public FragmentRefuelList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Fragment on Create");

        //get Car Intent
        Intent i = getActivity().getIntent();
        car = (Car) i.getSerializableExtra("myCarRef");

        //Intalize List
        refuelList=new LinkedList<Refuel>();

        //------------Firebase-------------------------------

        //Referenze for firebase
        carRef = database.getReference(car.getmCar());
        //load RefuelList from Database
        carRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Refuel> refuelListRestore = dataSnapshot.getValue(new GenericTypeIndicator<List<Refuel>>() {});
                if(refuelListRestore!=null){
                    refuelList=refuelListRestore;
                    for(int i=0;i<refuelList.size();i++){
                        adapter.add(refuelList.get(i));
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"Fragment on Create View");

        final View view = inflater.inflate(R.layout.fragment_fragment_refuel_list, container, false);

        TextView tv = (TextView) view.findViewById(R.id.fragment_refuel_textView_mileage);
        tv.setText("first mileage " + Integer.valueOf(car.getmMileage()) + " km");

        //--------Dynamic List--------
        final ListView lv=(ListView) view.findViewById(R.id.fragment_refuel_list_listview);

        adapter = new RefuelAdapter(getActivity());

        lv.setAdapter(adapter);

        //delete Button
        ImageButton ib = null;
        ib = (ImageButton) view.findViewById(R.id.fragment_refuel_list_imageButton_delete);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"delete pressed!");

                //removes checked items from list
                for(int i=0; i<refuelList.size();i++){
                    if(refuelList.get(i).isReadyDelete()){
                        adapter.remove(refuelList.get(i));
                        refuelList.remove(i);
                        i--;
                    }
                }
                adapter.notifyDataSetChanged();
                closeDeleteMode();
                carRef.setValue(refuelList);
            }
        });


        //for delete
        lv.setClickable(true);
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                                  int _pos, long id) {
                View v = (View) view.findViewById(R.id.fragment_refuel_list_imageButton_delete);
                v.setVisibility(View.VISIBLE);  //makes Delete Button Visible
                //make checkbox visible
                for(int i=0; i<refuelList.size(); i++){
                    refuelList.get(i).setDelete(true);
                }
                deleteON=true;
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;
    }

    public void closeDeleteMode(){
        for(int i=0; i<refuelList.size();i++){
            refuelList.get(i).setDelete(false);
            refuelList.get(i).setReadyDelete(false);
        }
        deleteON = false;
        View v1 = (View) getView().findViewById(R.id.fragment_refuel_list_imageButton_delete);
        v1.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();

        closeDeleteMode();
    }

    @Override
    public void onBackPressed() {
        //remove delete Buttons

        SharedPreferences sp = getActivity().getSharedPreferences(SP_KEY, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(VALUE_KEY, deleteON);

        if(deleteON){
            closeDeleteMode();
        }

        edit.commit();

        Log.i(TAG,"back");
    }



}
