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

    LayoutInflater inflater = null;
    private ArrayList<StationItemData> s_oData = null;
    private int nListCnt = 0;


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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_stationitem, parent, false);
        }


        final Button station = (Button) convertView.findViewById(R.id.list_stationBtn);

        station.setTag(position);
        station.setOnClickListener(mOnClickListener);

        /*
        station.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                boolean isClick = true;
                if (isClick) {
                    station.setBackgroundColor(Color.parseColor("#AF0000"));
                    station.getText();
                    isClick = false;
                } else {
                    station.setBackgroundColor(Color.parseColor("#FF0000"));
                    isClick = true;
                }
                Toast toast = Toast.makeText(v.getContext(), "선택되었습니다", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });
        */

        station.setText(s_oData.get(position).stationName);

        return convertView;
    }
    Button.OnClickListener mOnClickListener = new  Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            int position = Integer.parseInt( (v.getTag().toString()) );





        }
    };



}