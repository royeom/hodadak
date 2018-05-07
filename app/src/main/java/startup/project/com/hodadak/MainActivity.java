package startup.project.com.hodadak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView m_oListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] strDate = {"월요일 08:00", "화요일 09:00", "수요일 10:00", "목요일 09:00", "금요일 10:00"};

        ArrayList<ItemData> oData = new ArrayList<>();

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
    }
}
