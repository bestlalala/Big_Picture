package smwu.mobileprogramming.termprj;

import static java.sql.DriverManager.println;
import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class YearlyActivity extends AppCompatActivity implements OnDatabaseCallback {

    FragmentYearly fragmentYearly;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    YearlyPlanAdapter adapter;

    TextView thisyearText;
    Button thisyearBtn, backBtn, nextBtn;

    // 올해 연도 구하기
    public static Date currentTime = Calendar.getInstance().getTime();
    public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    public static String thisYear = yearFormat.format(currentTime);

    GoalDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yearly);

        // 목표 입력하는 Fragment 띄우기
        thisyearText = findViewById(R.id.thisYear);
        fragmentYearly = new FragmentYearly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentYearly).commit();

        thisyearBtn = findViewById(R.id.thisYearGoal);
        thisyearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentYearly.thisyearText.setText(thisYear);
                executeQueryForThisYear();
            }
        });

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new YearlyPlanAdapter();

        adapter.addItem(new YearlyPlan(1, Integer.valueOf(thisYear)+1, ""));
        adapter.addItem(new YearlyPlan(2, Integer.valueOf(thisYear)+2, ""));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnYearlyItemClickListener() {
            @Override
            public void onItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
                YearlyPlan item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨: " + item.getYear(), Toast.LENGTH_LONG).show();
                executeQuery(item);
                fragmentYearly.resetGoalText(item);
            }
        });

        backBtn = findViewById(R.id.button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
    public void insert(String year, String goalText) {
        database.insertRecordYearly(year, goalText);
        Toast.makeText(getApplicationContext(), "연도별 목표를 추가했습니다.", Toast.LENGTH_LONG).show();
    }

    @Override
    public ArrayList<YearlyPlan> selectAll() {
        ArrayList<YearlyPlan> result = database.selectAll();
        Toast.makeText(getApplicationContext(), "연도별 목표를 조회했습니다.", Toast.LENGTH_LONG).show();

        return result;
    }
    public void executeQueryForThisYear() {
        Cursor cursor = database.rawQuery("select _id, year, goal from YEARLY_GOAL");
        for (int i = 0; i< cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int year = cursor.getInt(1);
            String goalText = cursor.getString(2);
            if (year == Integer.valueOf(thisYear))
                fragmentYearly.goalText.setText(goalText);

            // Test code
            Log.d(TAG, "레코드#" + i + " : " + id + ", " + year + ", " + goalText);
        }
        cursor.close();
    }

    public void executeQuery(YearlyPlan item) {
        Cursor cursor = database.rawQuery("select _id, year, goal from YEARLY_GOAL");
        for (int i = 0; i< cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int year = cursor.getInt(1);
            String goalText = cursor.getString(2);
            if (year == item.getYear())
                item.setGoalText(goalText);

            // Test code
            Log.d(TAG, "레코드#" + i + " : " + id + ", " + year + ", " + goalText);
        }
        cursor.close();
    }

}