package DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Omer Habib on 11/9/2016.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL(SQLDemoContract.commands.StudentEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  lifeintheuk");
        onCreate(sqLiteDatabase);

    }


}
