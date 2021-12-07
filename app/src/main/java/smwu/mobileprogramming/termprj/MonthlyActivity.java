package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.YearlyActivity.cal;
import static smwu.mobileprogramming.termprj.YearlyActivity.currentTime;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MonthlyActivity extends AppCompatActivity implements OnDatabaseCallback {

    FragmentMonthly fragmentMonthly;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MonthlyPlanAdapter adapter;

    TextView thismonthText;
    Button thismonthBtn, backBtn, nextBtn;

    Handler handler = new Handler();

    // 이번 달 구하기
    public static DecimalFormat df = new DecimalFormat("00");
    public static String thisMonth = df.format(cal.get(Calendar.MONTH)+1);

    GoalDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly);

        // 목표 입력하는 Fragment 띄우기
        thismonthText = findViewById(R.id.thisMonth);
        fragmentMonthly = new FragmentMonthly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentMonthly).commit();

        GetDataThread getDataThread = new GetDataThread();
        getDataThread.start();

        thismonthBtn = findViewById(R.id.thisMonthGoal);
        thismonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMonthly.thisMonthText.setText(thisMonth);
                getDataThread.run();
            }
        });

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MonthlyPlanAdapter();

        adapter.addItem(new MonthlyPlan(1, 1, ""));
        adapter.addItem(new MonthlyPlan(2, 2, ""));

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onYearlyItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
            }

            @Override
            public void onMonthlyItemClick(MonthlyPlanAdapter.ViewHolder holder, View view, int position) {
                MonthlyPlan item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨: " + item.getMonth(), Toast.LENGTH_LONG).show();
                executeQuery(item);
                fragmentMonthly.resetGoalText(item);
            }
        });
        backBtn = findViewById(R.id.button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), YearlyActivity.class);
                startActivity(intent);
            }
        });

        nextBtn = findViewById(R.id.button_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeeklyActivity.class);
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

    protected void onDestroy() {
        // close database
        if (database != null) {
            database.close();
            database = null;
        }
        super.onDestroy();
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

    @Override
    public void insert(String month, String goalText) {
        database.insertRecordMonthly(month, goalText);
        Toast.makeText(getApplicationContext(), "월별 목표를 추가했습니다.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void update(String month, String goalText) {
        database.updateRecordMonthly(month, goalText);
        Toast.makeText(getApplicationContext(), "웝별 목표를 수정했습니다.", Toast.LENGTH_LONG).show();
    }

    public void executeQueryForThisMonth() {
        Cursor cursor = database.rawQuery("select * from MONTHLY_GOAL WHERE MONTH="+thisMonth);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int month = cursor.getInt(1);
            String goalText = cursor.getString(2);
            fragmentMonthly.goalText.setText(goalText);

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

}