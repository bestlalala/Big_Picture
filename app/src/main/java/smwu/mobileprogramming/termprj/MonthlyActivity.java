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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MonthlyActivity extends AppCompatActivity implements OnDatabaseCallback {

    FragmentMonthly fragmentMonthly;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MonthlyPlanAdapter adapter;

    TextView thismonthText;
    Button thismonthBtn, backBtn, nextBtn;

    // 이번 달 구하기
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("mm", Locale.KOREA);
    public static String thisMonth = monthFormat.format(currentTime);
    public Calendar cal = Calendar.getInstance();


    GoalDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly);

        // 목표 입력하는 Fragment 띄우기
        thismonthText = findViewById(R.id.thisMonth);
        fragmentMonthly = new FragmentMonthly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentMonthly).commit();

        thismonthBtn = findViewById(R.id.thisMonthGoal);
        thismonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMonthly.thisMonthText.setText(thisMonth);
                executeQueryForThisMonth();
            }
        });

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MonthlyPlanAdapter();
//        cal.add(Calendar.MONTH, + 1);
//        String nextMonth = monthFormat.format(cal.getTime());
//        cal.add(Calendar.MONTH, + 1);
//        String nnextMonth = monthFormat.format(cal.getTime());

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
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class); // MonthlyActivity로 수정해야 함.
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
        Cursor cursor = database.rawQuery("select _id, month, goal from MONTHLY_GOAL");
        for (int i = 0; i< cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int month = cursor.getInt(1);
            String goalText = cursor.getString(2);
            if (month == Integer.valueOf(thisMonth))
                fragmentMonthly.goalText.setText(goalText);

            // Test code
            Log.d(TAG, "레코드#" + i + " : " + id + ", " + month + ", " + goalText);
        }
        cursor.close();
    }

    public void executeQuery(MonthlyPlan item) {
        Cursor cursor = database.rawQuery("select _id, month, goal from MONTHLY_GOAL");
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