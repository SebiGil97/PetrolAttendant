package at.fhooe.mc.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import at.fhooe.mc.android.Activity.MainActivity;
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

        ImageView iv = null;
        iv = (ImageView)_convertView.findViewById(R.id.activity_main_list_car_imageView);

        //---------- car brands for carList ----------
        if(data.getmCar().length() >= 4 && data.getmCar().substring(0,4).toLowerCase().equals("audi")) {
            iv.setImageResource(R.drawable.icon_audi_50);
        }else if(data.getmCar().length() >= 10 && data.getmCar().substring(0,10).toLowerCase().equals("alfa romeo")) {
            iv.setImageResource(R.drawable.icon_black_alfa_romeo_50);
        }else if(data.getmCar().length() >= 3 && data.getmCar().substring(0,3).toLowerCase().equals("bmw")) {
            iv.setImageResource(R.drawable.icon_black_bmw_50);
        }else if(data.getmCar().length() >= 9 && data.getmCar().substring(0,9).toLowerCase().equals("chevrolet")){
            iv.setImageResource(R.drawable.icon_chevrolet_48);
        }else if(data.getmCar().length() >= 7 && data.getmCar().substring(0,7).toLowerCase().equals("ferrari")) {
            iv.setImageResource(R.drawable.icon_black_ferrari_50);
        }else if(data.getmCar().length() >= 11 && data.getmCar().substring(0,11).toLowerCase().equals("lamborghini")){
            iv.setImageResource(R.drawable.icon_black_lamborghini_50);
        }else if(data.getmCar().length() >= 8 && data.getmCar().substring(0,8).toLowerCase().equals("mercedes")){
            iv.setImageResource(R.drawable.icon_mercedes_48);
        }else if(data.getmCar().length() >= 2 && data.getmCar().substring(0,2).toLowerCase().equals("mg")){
            iv.setImageResource(R.drawable.icon_mg_48);
        }else if(data.getmCar().length() >= 7 && data.getmCar().substring(0,7).toLowerCase().equals("porsche")){
            iv.setImageResource(R.drawable.icon_porsche_48);
        }else if(data.getmCar().length() >= 7 && data.getmCar().substring(0,7).toLowerCase().equals("renault")){
            iv.setImageResource(R.drawable.icon_renault_225);
        }else if(data.getmCar().length() >= 6 && data.getmCar().substring(0,6).toLowerCase().equals("suzuki")) {
            iv.setImageResource(R.drawable.icon_black_suzuki_50);
        }else if(data.getmCar().length() >= 5 && data.getmCar().substring(0,5).toLowerCase().equals("tesla")){
            iv.setImageResource(R.drawable.icon_black_tesla_50);
        }else if(data.getmCar().length() >= 2 && data.getmCar().substring(0,2).toLowerCase().equals("vw")){
            iv.setImageResource(R.drawable.icon_volkswagen_50);
        }else if(data.getmCar().length() >= 4 && data.getmCar().substring(0,4).toLowerCase().equals("opel")){
            iv.setImageResource(R.drawable.icon_black_traffic_accident_50);
        }else {
            iv.setImageResource(R.drawable.petrolattendent_icon_car_with_people_black_dp50);
        }


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
