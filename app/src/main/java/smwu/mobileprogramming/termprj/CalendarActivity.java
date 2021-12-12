package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.YearlyActivity.cal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    String Date;
    TextView calDate;

    Button btn;
    TextView month, plan;
    Handler handler = new Handler();

    // 이번 달 구하기
    public static Calendar cal = Calendar.getInstance();
    public static DecimalFormat df = new DecimalFormat("00");
    public static String thisMonth = df.format(cal.get(Calendar.MONTH)+1);

    GoalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //사이드 메뉴바
//        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//
//        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // start에 지정된 Drawer 열기
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//        NavigationView navigationView = findViewById(R.id.navigationView);
//        navigationView.setItemIconTintList(null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date = year + "/" + (month + 1) + "/" + dayOfMonth;
//                calDate = (TextView) findViewById(R.id.calendarDate);
//                calDate.setText(Date);
            }
        });


        //이번 달 표시
        month = (TextView) findViewById(R.id.textViewMonth);
        month.setText("" + (thisMonth) + "월");

        //이번 달 목표 표시
        plan = (TextView) findViewById(R.id.textViewMonthPlan);
        GetDataThread getDataThread = new GetDataThread();
        getDataThread.start();
        getDataThread.run();

        btn = (Button)findViewById(R.id.calendarButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodayMain.class);

                Bundle extras = new Bundle();
                extras.putString("Date", Date);
                intent.putExtras(extras);

                startActivity(intent);
            }
        });

        // open database
        if (database != null) {
            database.close();
            database = null;
        }

        database = GoalDatabase.getInstance(this);
        boolean isOpen = database.open();
        if (isOpen) {
            Log.d(TAG, "Goal database is open.");
        } else {
            Log.d(TAG, "Goal database is not open.");
        }
    }

    //이번달 목표 불러서 나타내기
    public void executeQueryForThisMonth() {
        Cursor cursor = database.rawQuery("select * from MONTHLY_GOAL WHERE MONTH="+thisMonth);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int month = cursor.getInt(1);
            String goalText = cursor.getString(2);
            plan.setText(goalText);

            // Test code
            Log.d(TAG, "레코드#" + i + " : " + id + ", " + month + ", " + goalText);
        }

        cursor.close();
    }

    public void executeQuery(MonthlyPlan item) {
        Cursor cursor = database.rawQuery("select * from MONTHLY_GOAL");
        for (int i = 0; i< cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int month = cursor.getInt(1);
            String goalText = cursor.getString(2);
            if (month == item.getMonth())
                item.setGoalText(goalText);

            // Test code
            Log.d(TAG, "레코드#" + i + " : " + id + ", " + month + ", " + goalText);
        }
        cursor.close();
    }

    class GetDataThread extends Thread {

        public void run() {
            super.run();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    executeQueryForThisMonth();
                }
            });
        }
    }
}
