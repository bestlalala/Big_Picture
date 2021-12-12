package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.YearlyActivity.cal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class TodayCategory extends AppCompatActivity {
    TodayMain todayMain;

    CalendarActivity calendarActivity;
    TextView date;

    FragmentWeekly fragmentWeekly;
    Button saveBtn;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TodayCategoryItemAdapter adapter;

    String cate, cate_;
    String todo1, todo1_, todo2, todo2_;
    int col,col_;

    GoalDatabase database;

    public static DecimalFormat df = new DecimalFormat("0");
    public static String thisWeek = df.format(cal.get(Calendar.WEEK_OF_MONTH)+1);
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_category);

        Bundle extras1 = getIntent().getExtras();
        date = findViewById(R.id.textViewTodayDate);
        date.setText(extras1.getString("Date"));

        GetDataThread thread = new GetDataThread();
        thread.start();

        //이번주 목표 카테고리 리사이클러뷰로 띄우기
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TodayCategoryItemAdapter();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onYearlyItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
            }

            @Override
            public void onMonthlyItemClick(MonthlyPlanAdapter.ViewHolder holder, View view, int position) {
            }

            @Override
            public void onTodayPlanItemClick(TodayMain_PlanAdapter.ViewHolder holder, View view, int position) {
            }

            @Override
            public void onTodayCategoryItemClick(TodayCategoryItemAdapter.ViewHolder holder, View view, int position) {
                TodayCategoryItem item = adapter.getItem(position);
                cate_ = cate;
                col_ = col;
                todo1_ = todo1;
                todo2_ = todo2;
            }
        });

        //저장 버튼
        saveBtn = (Button) findViewById(R.id.categorySaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodayMain.class);

                Bundle extras2 = new Bundle();
                extras2.putString("Date", extras1.getString("Date"));
                extras2.putString("Category", cate_);
                extras2.putInt("Color", col_);
                extras2.putString("Todo1", todo1_);
                extras2.putString("Todo2", todo2_);
                intent.putExtras(extras2);

                startActivity(intent);

//                SaveThread saveThread = new SaveThread();
//                saveThread.start();
            }
        });
    }

//    private class SaveThread extends Thread {
//        public void run() {
//            super.run();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    //데이터베이스 저장 함수 insert(thisWeek, category);
//
//                    Intent intent = new Intent(getApplicationContext(), TodayMain.class);
//
//                    Bundle extras = new Bundle();
//                    extras.putString("Date", extras.getString("Date"));
//                    intent.putExtras(extras);
//
//                    startActivity(intent);
//                }
//            });
//        }
//    }

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
        todo1 = category.content;
        //todo2 =

        adapter.addItem(new TodayCategoryItem(category.title, category.color));
        recyclerView.setAdapter(adapter);
        cate = category.title;
        col = category.color;
    }

}
