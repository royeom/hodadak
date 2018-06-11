package startup.project.com.hodadak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class AddPath extends AppCompatActivity{

    EditText eT_name, eT_start, eT_end;
    Button btnAddTime, addDetailBtn;
    Spinner check_day, check_hour, check_minute;
    private ListView db_time_list = null;

    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        btnAddTime = (Button)findViewById(R.id.btnAddTime);
        eT_name = (EditText)findViewById(R.id.eT_name);
        eT_start = (EditText)findViewById(R.id.eT_start);
        eT_end = (EditText)findViewById(R.id.eT_end);
        addDetailBtn = (Button)findViewById(R.id.addDetailBtn);
        check_day = (Spinner)findViewById(R.id.check_day);
        check_hour = (Spinner)findViewById(R.id.check_hour);
        check_minute = (Spinner)findViewById(R.id.check_minute);
        final ArrayList<TimeItemData> myData = new ArrayList<>();

        db_time_list = (ListView)findViewById(R.id.db_time_list);


        btnAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeItemData timeItemData = new TimeItemData();
                timeItemData.day = check_day.getSelectedItem().toString();
                timeItemData.hour = check_hour.getSelectedItem().toString();
                timeItemData.minute = check_minute.getSelectedItem().toString();
                myData.add(timeItemData);
                TimeListAdapter oAdapter = new TimeListAdapter(myData);
                db_time_list.setAdapter(oAdapter);
            }
        });


        addDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailRouting.class);
                startActivity(intent);
            }
        });
    }

}