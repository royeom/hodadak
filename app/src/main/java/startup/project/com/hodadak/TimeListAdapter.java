package startup.project.com.hodadak;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeListAdapter extends BaseAdapter{

    LayoutInflater inflater = null;
    private ArrayList<TimeItemData> m_oData = null;
    private int nListCnt = 0;

    public TimeListAdapter(ArrayList<TimeItemData> _oData){
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            final Context context = parent.getContext();
            if(inflater == null){
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.time_listview_item, parent, false);
        }

        TextView oTextDay = (TextView) convertView.findViewById(R.id.list_day);
        TextView oTextHour = (TextView) convertView.findViewById(R.id.list_hour);
        TextView oTextMinute = (TextView) convertView.findViewById(R.id.list_minute);

        oTextDay.setText(m_oData.get(position).day);
        oTextHour.setText(m_oData.get(position).hour);
        oTextMinute.setText(m_oData.get(position).minute);

        return convertView;
    }
}
