package smwu.mobileprogramming.termprj;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class GoalDatabase {

    public static final String TAG = "GoalDataBase";

    /**
     * singleton instance
     */
    public static GoalDatabase database;

    /**
     * database name
     */
    public static String DATABASE_NAME = "goal.db";

    /**
     * table name
     */
    public static String TABLE_YEARLY = "YEARLY_GOAL";
    public static String TABLE_MONTHLY = "MONTHLY_GOAL";
    public static String TABLE_WEEKLY = "WEEKLY_GOAL";

    // version
    public static int DATABASE_VERSION = 1;

    //Helper class defined
    private DatabaseHelper dbHelper;

    // Database object
    private SQLiteDatabase db;

    private Context context;

    /**
     * Constructor
     */
    public GoalDatabase(Context context) {
        this.context = context;
    }

    public static GoalDatabase getInstance(Context context) {
        if (database == null) {
            database = new GoalDatabase(context);
        }
        return database;
    }

    /**
     * open database
     *
     * @return
     */
    public boolean open() {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    /**
     * close database
     */
    public void close() {
        db.close();
        database = null;
    }

    /**
     * excute raw query using the input SQL
     * close the cursor after fetching any result
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }
        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }
        return true;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase _db) {
            // TABLE_YEARLY_GOAL
            println("creating table [" + TABLE_YEARLY + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_YEARLY;
            try {
                _db.execSQL(DROP_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_YEARLY + "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + " YEAR TEXT, "
                    + " GOAL TEXT, "
                    + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
                // insert records
                insertRecordYearly(_db, "2021", "모프 A+ 받기");
                insertRecordYearly(_db, "2022", "우수장학금 받기");

            // TABLE_MONTHLY_GOAL
            println("creating table [" + TABLE_YEARLY + "].");

            // drop existing table
            String DROP_SQL2 = "drop table if exists " + TABLE_MONTHLY;
            try {
                _db.execSQL(DROP_SQL2);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL2", ex);
            }

            // create table
            String CREATE_SQL2 = "create table " + TABLE_MONTHLY+ "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + " MONTH TEXT, "
                    + " GOAL TEXT, "
                    + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL2);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL2", ex);
            }
            // insert records
            insertRecordMonthly("12", "학기 마무리 잘하기");

            // TABLE_WEEKLY_GOAL
            println("creating table [" + TABLE_YEARLY + "].");

            // drop existing table
            String DROP_SQL3 = "drop table if exists " + TABLE_WEEKLY;
            try {
                _db.execSQL(DROP_SQL3);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL3", ex);
            }

            // create table
            String CREATE_SQL3 = "create table " + TABLE_WEEKLY+ "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + " WEEK TEXT, "
                    + " CATEGORY_TITLE TEXT, "
                    + " CATEGORY_COLOR TEXT, "
                    + " CATEGORY_CONTENT, "
                    + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL3);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL3", ex);
            }
            // insert records
            insertRecordMonthly("12", "학기 마무리 잘하기");

        }
        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {   // version 1

            }
        }

            private void insertRecordYearly(SQLiteDatabase _db, String year, String goalText) {
                try {
                    _db.execSQL( "insert into " + TABLE_YEARLY + "(YEAR, GOAL) values ('" + year + "', '" + goalText + "');" );
                } catch(Exception ex) {
                    Log.e(TAG, "Exception in executing insert SQL.", ex);
                }
            }
    }

    public void insertRecordYearly( String year, String goalText) {
        try {
            db.execSQL( "insert into " + TABLE_YEARLY + "(YEAR, GOAL) values ('" + year + "', '" + goalText + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public void insertRecordMonthly( String month, String goalText) {
        try {
            db.execSQL( "insert into " + TABLE_MONTHLY + "(MONTH, GOAL) values ('" + month + "', '" + goalText + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public void updateRecordYearly(String year, String goalText) {
        try {
            db.execSQL("UPDATE " + TABLE_YEARLY
                    + " SET "
                    + "GOAL=" + "('" + goalText + "')"
                + " WHERE " + "YEAR=" + year);
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executing update SAL.", ex);
        }
    }
    public void updateRecordMonthly(String month, String goalText) {
        try {
            db.execSQL("UPDATE " + TABLE_MONTHLY
                    + " SET "
                    + "GOAL=" + "('" + goalText + "')"
                + " WHERE " + "MONTH=" + month);
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executing update SAL.", ex);
        }
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }
}
