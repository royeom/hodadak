package startup.project.com.hodadak;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
    // DB에서 SELECT로 특정 name을 가진 것을 찾아오는 코드.
        DbOpenHelper mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Cursor iCursor = mDbOpenHelper.selectColumns();
        while(iCursor.moveToNext()){
        String tempID = iCursor.getString(iCursor.getColumnIndex("userid"));
        String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
        String tempAge = iCursor.getString(iCursor.getColumnIndex("age"));
        String tempGender = iCursor.getString(iCursor.getColumnIndex("gender"));
        if(tempName.equals("John"){
        String Result = tempID + "," +tempName + "," + tempAge + "," + tempGender;
        }
        }
*/
public class ListAdapter extends BaseAdapter{

    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData){
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
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.list_title);
        TextView oTextPath = (TextView) convertView.findViewById(R.id.list_path);
        TextView oTextTime = (TextView) convertView.findViewById(R.id.list_time);

        oTextTitle.setText(m_oData.get(position).strTitle);
        oTextPath.setText(m_oData.get(position).strPath);
        oTextTime.setText(m_oData.get(position).strTime);

        return convertView;
    }
}
