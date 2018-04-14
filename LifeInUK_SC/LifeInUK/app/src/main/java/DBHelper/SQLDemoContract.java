package DBHelper;

import android.provider.BaseColumns;

/**
 * Created by Omer Habib on 11/9/2016.
 */

public abstract class SQLDemoContract {

    public static abstract class tables{
        public static abstract class StudentEntry implements BaseColumns {
            public static final String TABLE_NAME = "lifeintheuk";

            public static final String COLUMN_QUESTION = "question";
            public static final String COLUMN_OPTION1 = "option1";
            public static final String COLUMN_OPTION2 = "option2";
            public static final String COLUMN_OPTION3 = "option3";
            public static final String COLUMN_OPTION4 = "option4";
            public static final String COLUMN_CATEGORY1 = "category1";
            public static final String COLUMN_CATEGORY2 = "category2";
            public static final String COLUMN_ANSWER = "answer";
            public static final String COLUMN_EXPLAIN = "explanation";
        }
    }

    public static abstract class commands{
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public abstract class StudentEntry{
            public static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + tables.StudentEntry.TABLE_NAME + " ( " +
                            tables.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            tables.StudentEntry.COLUMN_QUESTION + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_OPTION1 + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_OPTION2 + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_OPTION3 + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_OPTION4 + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_CATEGORY1 + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_CATEGORY2 + TEXT_TYPE + COMMA_SEP +
                            tables.StudentEntry.COLUMN_ANSWER + TEXT_TYPE +COMMA_SEP +
                            tables.StudentEntry.COLUMN_EXPLAIN + TEXT_TYPE +
                            " )";
        }
    }
}