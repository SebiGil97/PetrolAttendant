package at.fhooe.mc.android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Calendar;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.Objects.Refuel;

import static java.lang.Float.parseFloat;
import static java.lang.String.valueOf;

public class RefuelAdapter extends ArrayAdapter<Refuel> {

    public RefuelAdapter(Context _c){
        super(_c,-1);
    }

    public View getView(int _position, View _convertView, ViewGroup _parent){

        final Refuel data = getItem(_position);

        if(_convertView == null){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = inflater.inflate(R.layout.list_refuel, null);

        }

        String date = data.getmDate().toString();
        date = date.substring(0,10);


        TextView tv = null;
        tv = (TextView)_convertView.findViewById(R.id.list_refuel_textview_date);
        tv.setText(date);
        tv = (TextView)_convertView.findViewById(R.id.list_refuel_textView_Liter);
        tv.setText("Liter: " + String.format("%.2f",data.getmLiter())+ "L");//
        tv = (TextView)_convertView.findViewById(R.id.list_refuel_textView_price);
        tv.setText("Price: " + String.format("%.2f",data.getmPrice()) + "€");

        //for delete

        final CheckBox cb=(CheckBox) _convertView.findViewById(R.id.adapter_refuel_checkbox_delete);
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

        View v1 = _convertView.findViewById(R.id.adapter_refuel_checkbox_delete);
        if(data.isDelete()){
            v1.setVisibility(View.VISIBLE);
            cb.setChecked(false);
        }else{
            v1.setVisibility(View.GONE);
        }

        return _convertView;
    }

}
