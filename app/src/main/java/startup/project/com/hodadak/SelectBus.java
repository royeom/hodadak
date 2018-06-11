package startup.project.com.hodadak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SelectBus extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bus);

        Button sb_selectBtn = (Button)findViewById(R.id.SB_selectBtn);
        Button sb_cancleBtn = (Button)findViewById(R.id.SB_cancleBtn);


        sb_selectBtn.setOnClickListener(new View.OnClickListener(){
            EditText sb_busED = (EditText)findViewById(R.id.SB_busED);

            @Override
            public void onClick(View v) {
                int busNumber = Integer.parseInt(sb_busED.getText().toString());
                Intent intent = new Intent(SelectBus.this, BusStation.class);
                intent.putExtra("Bus_Number", busNumber);
                startActivity(intent);
            }
        });

        sb_cancleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailRouting.class);
                startActivity(intent);

            }
        });
    }
}

