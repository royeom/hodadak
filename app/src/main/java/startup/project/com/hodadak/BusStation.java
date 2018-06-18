package startup.project.com.hodadak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusStation extends Activity {
    EditText edit;
    TextView text;
    ArrayList<StationItemData> data;
    public static ArrayList<String> stationInfo;
    HashMap<Integer, String> map = new HashMap<Integer, String>();
    String busID;

    private ListView stationListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_bus_station);

        final ArrayList<StationItemData> stationData = new ArrayList<>();

        Button sbs_setBtn = (Button)findViewById(R.id.SBS_setBtn);
        sbs_setBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailRouting.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                busID = getBusRouteId();
                data = getXmlData(busID, stationData);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        stationListView = (ListView)findViewById(R.id.listView_station);
                        StationListAdapter sAdapter = new StationListAdapter(data);
                        stationListView.setAdapter(sAdapter);

                        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                if(map.containsKey(position)){
                                    map.remove(position);
                                    System.out.println("선택되어있습니다.");

                                }else{
                                    map.put(position, data.get(position).stationName);
                                    System.out.println("Station 이름  :   " + data.get(position).stationName);
                                }

                            }
                        });
                    }
                });

            }
        }).start();



    }



    String getBusRouteId() {
        StringBuffer buffer = new StringBuffer();
        Intent intent = getIntent();
        int busNumber = intent.getIntExtra("Bus_Number", 0);

        try {
            String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
            String serviceKey = "q5NHvC3mbJzf8B3sC8WNTnARBf5XEyINcsJXV93BMObehiiqgqEdn5zTWuv6dUFijFr64bcm8VFYexP5nI7NXg%3D%3D";
            String strSrch = Integer.toString(busNumber);
            String strURL = serviceUrl + "?ServiceKey=" + serviceKey + "&strSrch=" + strSrch;

            URL url = new URL(strURL);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), "utf-8");

            int eventType = parser.getEventType();
            boolean bSet = false;
            boolean isFirst = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals("busRouteId")) {
                            bSet = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (bSet) {
                            String content = parser.getText();
                            buffer.append(content+"\n");
                            bSet = false;
                            isFirst = true;
                        }
                        break;
                }
                eventType = parser.next();
                if(isFirst)
                    break;
            }
        } catch (Exception e) {

        }
        return buffer.toString();
    }

    ArrayList<StationItemData> getXmlData(String busID, ArrayList<StationItemData> stationData) {


        try {
            String serviceUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll";
            String serviceKey = "q5NHvC3mbJzf8B3sC8WNTnARBf5XEyINcsJXV93BMObehiiqgqEdn5zTWuv6dUFijFr64bcm8VFYexP5nI7NXg%3D%3D";
            String strURL = serviceUrl + "?ServiceKey=" + serviceKey + "&busRouteId=" + busID;
            URL url = new URL(strURL);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), "utf-8");

            int eventType = parser.getEventType();
            boolean bSet = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals("stNm")) {
                            bSet = true;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        if (bSet) {
                            StationItemData sItem = new StationItemData();
                            String content = parser.getText();
                            sItem.stationName = content;
                            stationData.add(sItem);
                            bSet = false;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;

                }
                eventType = parser.next();

            }
        } catch (Exception e) {

        }

        return stationData;
    }
}
