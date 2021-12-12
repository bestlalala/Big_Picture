package smwu.mobileprogramming.termprj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodayMain extends AppCompatActivity {
    Button cateBtn;
    CalendarActivity calendarActivity;

    TextView date;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TodayMain_PlanAdapter planAdapter;

    String cate_name, todo1, todo2;
    int color;

     Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_main);
        date = findViewById(R.id.textViewTodayDate);

        Bundle extras = getIntent().getExtras();
        date.setText(extras.getString("Date"));
        cate_name = extras.getString("Category");
        color = extras.getInt("Color");
        todo1 = extras.getString("Todo1");
        todo2 = extras.getString("Todo2");

        cateBtn = (Button) findViewById(R.id.todayCategoryButton);

        cateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), TodayCategory.class);

                Bundle extras1 = new Bundle();
                extras1.putString("Date", extras.getString("Date"));
                intent1.putExtras(extras1);

                startActivity(intent1);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        planAdapter = new TodayMain_PlanAdapter();

        //planAdapter.addItem(new TodayMain_Plan("예시 : 카테고리 선택", "할 일 1", "할 일2", 0));
        planAdapter.addItem(new TodayMain_Plan(cate_name, todo1, todo2, color));


        recyclerView.setAdapter(planAdapter);

        planAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onYearlyItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
            }

            @Override
            public void onMonthlyItemClick(MonthlyPlanAdapter.ViewHolder holder, View view, int position) {
            }

            @Override
            public void onTodayPlanItemClick(TodayMain_PlanAdapter.ViewHolder holder, View view, int position) {
                TodayMain_Plan item = planAdapter.getItem(position);

//                Intent intent2 = new Intent(getApplicationContext(), TodayTodo.class);
//                Bundle extras2 = new Bundle();
//                extras2.putString("Date", extras.getString("Date"));
//                intent2.putExtras(extras2);
//                startActivity(intent2);
            }

            @Override
            public void onTodayCategoryItemClick(TodayCategoryItemAdapter.ViewHolder holder, View view, int position) {
            }
        });

    }
}