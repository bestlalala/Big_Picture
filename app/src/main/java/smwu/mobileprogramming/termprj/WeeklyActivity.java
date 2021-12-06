package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.YearlyActivity.cal;
import static smwu.mobileprogramming.termprj.YearlyActivity.currentTime;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeeklyActivity extends AppCompatActivity {

    FragmentWeekly fragmentWeekly;

    TextView thisweekText;
    Button backBtn, nextBtn;
    Button categoryBtn;

    // 이번 달 구하기
    public static SimpleDateFormat weekFormat = new SimpleDateFormat("mm", Locale.KOREA);
    public static String thisWeek = weekFormat.format(cal.get(Calendar.WEEK_OF_MONTH));

    GoalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly);

        // 목표 입력하는 Fragment 띄우기
        thisweekText = findViewById(R.id.thisMonth);
        fragmentWeekly = new FragmentWeekly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentWeekly).commit();

        thisweekText = findViewById(R.id.thisMonthGoal);
        thisweekText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentWeekly.thisWeekText.setText(thisWeek);
                executeQueryForThisWeek();
            }
        });

        categoryBtn = findViewById(R.id.add_categoryBtn);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카테고리 만들기
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        backBtn = findViewById(R.id.button_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MonthlyActivity.class);
                startActivity(intent);
            }
        });

        nextBtn = findViewById(R.id.button_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void executeQueryForThisWeek() {
        Cursor cursor = database.rawQuery("select _id, week, category_title, category_color, category_content from WEEKLY_GOAL");
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int week = cursor.getInt(1);
            String category_title = cursor.getString(2);
            int color = cursor.getInt(3);
            String content = cursor.getString(4);
            if (week == Integer.valueOf(thisWeek)) {
                fragmentWeekly.category.setText(category_title);
                fragmentWeekly.category.setBackgroundColor(color);
                fragmentWeekly.todoList.setText(content);
                // Test code
                Log.d(TAG, "레코드#" + i + " : " + id + ", " + week + ", " + category_title + ", " + content);
            }
            cursor.close();
        }
    }
}