package startup.project.com.hodadak;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Sample extends Activity {

    EditText edit;
    TextView text;

    XmlPullParser xpp;
    String key="5jn5rp4ZBmYpQvkstlnVQP0DfD%2BIKzeDoiAbrRht11ZDP3%2FrEGA7xfYLuhTZlUIAyB7fTW0wbS5g0OpUZeC22w%3D%3D";

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.result);
    }

    //Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v){

        switch( v.getId() ){
            case R.id.button:

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        data= getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                text.setText(data); //TextView에 문자열  data 출력
                            }
                        });

                    }
                }).start();

                break;
        }

    }//mOnClick method..


    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    String getXmlData(){

        StringBuffer buffer=new StringBuffer();

        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        //String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        //String query = "%EC%A0%84%EB%A0%A5%EB%A1%9C";

        String queryUrl="http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?"
                    +"ServiceKey="+key+"&busRouteId=100100118";

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals(("stId"))) {
                            buffer.append("정류소 ID : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }else if(tag.equals(("stNm"))) {
                            buffer.append("정류소명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }else if(tag.equals(("firstTm"))) {
                            buffer.append("첫차시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }else if(tag.equals(("lastTm"))) {
                            buffer.append("막차시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }

                        /*
                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("addr")){
                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("chargeTp")){
                            buffer.append("충전소타입 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("cpId")){
                            buffer.append("충전소ID :");
                            xpp.next();
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("cpNm")){
                            buffer.append("충전기 명칭 :");
                            xpp.next();
                            buffer.append(xpp.getText());//telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("cpStat")){
                            buffer.append("충전기 상태 코드 :");
                            xpp.next();
                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("cpTp")){
                            buffer.append("충전 방식 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("  ,  "); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("csId")){
                            buffer.append("충전소 ID :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("cpNm")){
                            buffer.append("충전소 명칭 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("lat")){
                            buffer.append("위도 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("longi")){
                            buffer.append("경도 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("statUpdateDatetime")){
                            buffer.append("충전기상태갱신시각 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        */
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");

        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....

}
/*
package startup.project.com.hodadak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusStation extends Activity {
    EditText edit;
    TextView text;
    String data;
    String busID;

    List<String> busStation = new ArrayList<>();
    busStation.add("aa");

    ArrayAdapter<String> itemsAdapter =
            new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, busStation);

    ListView listView = (ListView) findViewById(R.id.lvItems);
listView.setAdapter(itemsAdapter);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_bus_station);

        final ArrayList<StationItemData> stationData = new ArrayList<>();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                busID = getBusRouteId();
                data = getXmlData(busID, stationData);
                text = (TextView) findViewById(R.id.SBS_stationT);



                for(int i=0; i<5; ++i){
                    ItemData oItem = new ItemData();
                    oItem.strTitle = "경로" + (i+1);
                    oItem.strPath = "길음 -> 국민대";
                    oItem.strTime = strDate[i];
                    oData.add(oItem);

                }

                m_oListView = (ListView)findViewById(R.id.listView);
                ListAdapter oAdapter = new ListAdapter(oData);
                m_oListView.setAdapter(oAdapter);


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        text.setText(data); //TextView에 문자열  data 출력
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

    String getXmlData(String busID, ArrayList<StationItemData> stationData) {
        StringBuffer buffer = new StringBuffer();

        try {
            String serviceUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll";
            String serviceKey = "q5NHvC3mbJzf8B3sC8WNTnARBf5XEyINcsJXV93BMObehiiqgqEdn5zTWuv6dUFijFr64bcm8VFYexP5nI7NXg%3D%3D";
            String strURL = serviceUrl + "?ServiceKey=" + serviceKey + "&busRouteId=" + busID;
            URL url = new URL(strURL);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), "utf-8");

            StationItemData sItem = new StationItemData();

            int eventType = parser.getEventType();
            boolean bSet = false;
            buffer.append(busID+"\n");

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
                            String content = parser.getText();
                            sItem.stationName = content;
                            stationData.add(sItem);
                            buffer.append(content+"\n");
                            bSet = false;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        tag = parser.getName();
                        if (tag.equals("item")) buffer.append("\n");
                        break;

                }
                eventType = parser.next();

            }
        } catch (Exception e) {

        }
        return buffer.toString();
    }
}

 */



