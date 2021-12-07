package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.YearlyActivity.cal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Calendar;

public class WeeklyActivity extends AppCompatActivity {

    FragmentWeekly fragmentWeekly;
    Button backBtn, nextBtn;

    Handler handler = new Handler();

    public static DecimalFormat df = new DecimalFormat("0");
    public static String thisWeek = df.format(cal.get(Calendar.WEEK_OF_MONTH)+1);

    GoalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly);


        // 목표 입력하는 Fragment 띄우기
        fragmentWeekly = new FragmentWeekly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentWeekly).commit();

        GetDataThread thread = new GetDataThread();
        thread.start();

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

    public void onDestroy() {
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
                    executeQueryForThisWeek();
                }
            });

        }
    }
    private void executeQueryForThisWeek() {
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

        Cursor cursor = database.rawQuery("select * from WEEKLY_GOAL WHERE WEEK="+thisWeek);
        try {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                int week = cursor.getInt(1);
                String category_title = cursor.getString(2);
                int color = cursor.getInt(3);
                String content = cursor.getString(4);
                Log.d(TAG, "레코드" + " : " + id + ", " + week + ", " + category_title + ", " + color + ", " + content);
                createTextView(new Category(category_title, color, content));
            }
            while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            Log.d(TAG, "Exception in executeQueryForThisWeek", ex);
        }
    }

    public void createTextView(Category category) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(5 * dm.density);
        TextView category_title = new TextView(this);
        category_title.setText(category.title);
        category_title.setBackgroundColor(category.color);
        category_title.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
        category_title.setTextColor(Color.BLACK);
        category_title.setPadding(size, size, size, size);
        category_title.setBackground(getResources().getDrawable(R.drawable.date_bg));

        TextView todo = new TextView(this);
        todo.setText(category.content);
        todo.setTextColor(Color.BLACK);
        todo.setPadding(size, 0, size, 0);

        fragmentWeekly.addCategory(category_title);
        fragmentWeekly.addCategory(todo);
    }
}