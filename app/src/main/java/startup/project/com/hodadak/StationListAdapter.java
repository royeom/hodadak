package startup.project.com.hodadak;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class StationListAdapter extends BaseAdapter {
    private ArrayList<StationItemData> s_oData = null;
    private int nListCnt = 0;
    public static int selectCount = 0;

    public StationListAdapter(ArrayList<StationItemData> _oData) {
        s_oData = _oData;
        nListCnt = s_oData.size();
    }

    @Override
    public int getCount() {
        return nListCnt;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();



        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_stationitem, parent, false);
        }
        final Button station = (Button) convertView.findViewById(R.id.list_stationBtn);
        station.setText(s_oData.get(pos).stationName);

        station.setTag(pos);

        station.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationName = s_oData.get(pos).stationName;


                if(BusStation.map.containsKey(pos)){
                    station.setBackgroundColor(Color.parseColor("#dee4e4"));
                    BusStation.map.remove(pos);
                }else{
                    if(BusStation.map.size() == 0){
                        Toast toast = Toast.makeText(v.getContext(), "출발지 선택되었습니다.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }else if(BusStation.map.size() == 1){
                        Toast toast = Toast.makeText(v.getContext(), "도착지 선택되었습니다.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    station.setBackgroundColor(Color.parseColor("#000000"));
                    BusStation.map.put(pos, stationName);
                }


                if(BusStation.map.size() == 2){
                    selectCount = BusStation.map.size();
                }
            }
        });
        return convertView;
    }
}