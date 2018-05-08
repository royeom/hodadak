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
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView m_oListView = null;

    public void bHandler(View v){
        switch(v.getId()){
            case R.id.basicBtn:
                showBasicNotification();
                break;
            case R.id.expandedBtn:
                showExpandedLayoutNotification();
                break;
            case R.id.customBtn:
                showCustomLayoutNotification();
                break;
        }
    }

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

    // 기본 알림
    public void showBasicNotification(){
        NotificationCompat.Builder mBuilder = createNotification();
        mBuilder.setContentIntent(createPendingIntent());

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    // 알림창을 보는 상태에서 자동적으로 확장하는 알림
    public void showExpandedLayoutNotification(){
        NotificationCompat.Builder mBuilder = createNotification();
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        events[0] = "Monday";
        events[1] = "Tuesday";
        events[2] = "Wedsnday";
        events[3] = "Thursday";
        events[4] = "Friday";
        events[5] = "Saturday";
        inboxStyle.setBigContentTitle("Event tracker details:");
        inboxStyle.setSummaryText("Events summary");
        NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher, "button1", createPendingIntent());
        for (String str : events) {
            inboxStyle.addLine(str);
        }
        //스타일 추가
        mBuilder.setStyle(inboxStyle);
        mBuilder.setContentIntent(createPendingIntent());

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    // XML로 직접만든 화면으로 보여주는 알림
    private void showCustomLayoutNotification(){
        NotificationCompat.Builder mBuilder = createNotification();

        //커스텀 화면 만들기
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setImageViewResource(R.id.img, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.title, "Title");
        remoteViews.setTextViewText(R.id.message, "message");

        //노티피케이션에 커스텀 뷰 장착
        mBuilder.setContent(remoteViews);
        mBuilder.setContentIntent(createPendingIntent());

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    // 알림을 누르면 실행되는 기능을 가져오는 알림 => 실제 기능
    private PendingIntent createPendingIntent(){
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        return stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    // 알림 Build
    private NotificationCompat.Builder createNotification(){
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle("StatusBar Title")
                .setContentText("StatusBar subTitle")
                .setSmallIcon(R.mipmap.ic_launcher/*스와이프 전 아이콘*/)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        return builder;

    }
}
