package at.fhooe.mc.android.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.Objects.Refuel;
import at.fhooe.mc.android.R;

import static java.lang.String.valueOf;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragementStatistic extends Fragment {


    private static final String TAG = "TANK";
    int carMileage;
    float consumption=0;
    float averagePrice=0;
    float diffdistanz;
    String trend = " - " ;

    public FragementStatistic() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get Car Intent
        Intent i = getActivity().getIntent();
        List<Refuel> refuelList= (List<Refuel>) i.getSerializableExtra("RefuelList");
        carMileage = (Integer) i.getSerializableExtra("CarMileage");

        //calculat consumption and average price per 100km
        if(refuelList.size()==1){
           float distance =refuelList.get(0).getmMileage() - carMileage;
           distance = distance/100;
           consumption = refuelList.get(0).getmLiter()/distance;
           averagePrice = refuelList.get(0).getmPrice()/distance;
        }else if(refuelList.size()>1){
            float distance =refuelList.get(refuelList.size()-1).getmMileage() - carMileage;
            distance = distance/100;
            for(int y=0;y<refuelList.size();y++){
                consumption = consumption + refuelList.get(y).getmLiter();
                averagePrice = averagePrice + refuelList.get(y).getmPrice();
            }
            consumption = consumption / distance;
            averagePrice = averagePrice / distance;

            //calculate Trend
            diffdistanz=refuelList.get(refuelList.size()-1).getmMileage()-refuelList.get(refuelList.size()-2).getmMileage();
            diffdistanz=diffdistanz/100;

            Log.i(TAG,valueOf(refuelList.get(refuelList.size()-1).getmLiter()/diffdistanz));

            if(consumption>refuelList.get(refuelList.size()-1).getmLiter()/diffdistanz){

                trend="Keep it up!";
            }else{
                trend="You can do that better!";
            }

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragement_statistic, container, false);

        TextView tv = null;
        tv = (TextView) view.findViewById(R.id.activity_refuel_list_textview_consumption);
        tv.setText(String.format("%.2f",consumption) +" l / 100km");
        tv = (TextView) view.findViewById(R.id.activity_refuel_textview_cost);
        tv.setText(String.format("%.2f",averagePrice) + " € / 100km");
        tv = (TextView) view.findViewById(R.id.activity_refuel_textview_trend);
        tv.setText(trend);


        return view;
    }



}
