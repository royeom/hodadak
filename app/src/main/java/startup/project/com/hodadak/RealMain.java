package startup.project.com.hodadak;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class RealMain extends AppCompatActivity {
    Button basicBtn, expandedBtn, customBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_main);

        findViewById(R.id.expandedBtn).setOnClickListener(btnClickListener);
        findViewById(R.id.customBtn).setOnClickListener(btnClickListener);
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.expandedBtn:
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.customBtn:
                    Intent intent3 = new Intent(getApplicationContext(), WeatherActivity.class);
                    startActivity(intent3);
                    break;
            }
        }

    };
}