package at.fhooe.mc.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import at.fhooe.mc.android.Objects.Car;
import at.fhooe.mc.android.R;

public class CarAdapter extends ArrayAdapter<Car> {

   // private final List<Car> mCars;

    public CarAdapter(Context _c){          //, List<Car> _cars
        super(_c,-1);
       // mCars = _cars;
    }

    public View getView(int _position, View _convertView, ViewGroup _parent){

        final Car data = getItem(_position);

        if(_convertView == null){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = inflater.inflate(R.layout.list_car, null);

        }

        TextView tv = null;
        tv = (TextView)_convertView.findViewById(R.id.activity_main_list_car_textview_carname);
        tv.setText(data.getmCar());

        Button b = (Button) _convertView.findViewById(R.id.activity_main_list_car_button_settings);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), data.getmCar() + " options", Toast.LENGTH_SHORT).show();
                /*   remove(data);
                mCars.remove(data);
                notifyDataSetChanged();*/
            }
        });


        //for delete

        final CheckBox cb=(CheckBox) _convertView.findViewById(R.id.activity_main_list_car_checkBox_delete);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        data.setReadyDelete(true);
                    }else{
                        data.setReadyDelete(false);
                    }
              }
          }

        );

        View v1 = _convertView.findViewById(R.id.activity_main_list_car_checkBox_delete);
        if(data.isDelete()){
            v1.setVisibility(View.VISIBLE);
            cb.setChecked(false);
        }else{
            v1.setVisibility(View.GONE);
        }

        return _convertView;
    }


}
