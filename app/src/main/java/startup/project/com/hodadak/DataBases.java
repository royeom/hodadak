package startup.project.com.hodadak;

import android.provider.BaseColumns;

public final class DataBases {
    public static final class CreateDB implements BaseColumns{

        // DB에는 PATH 테이블, PATH의 시간에 대한 테이블 총 2개의 테이블이 존재함.

        public static final String PATHID = "pathid";
        public static final String TITLE = "title";
        public static final String DEPARTURE = "departure";
        public static final String ARRIVE = "arrive";
        public static final String _TABLENAME0 = "pathtable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +PATHID+" integer primary key autoincrement, "
                +TITLE+" text not null , "
                +DEPARTURE+" text not null , "
                +ARRIVE+" text not null );";

        public static final String DAY = "day";
        public static final String HOUR = "hour";
        public static final String MINUTE = "minute";
        public static final String _TABLENAME1 = "pathtimetable";
        public static final String _CREATE1 = "create table if not exists "+_TABLENAME1+"("
                +PATHID+" integer not null , "
                +DAY+" text not null , "
                +HOUR+" integer not null , "
                +MINUTE+" integer not null );";
    }
}
