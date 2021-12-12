package smwu.mobileprogramming.termprj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodayMain extends AppCompatActivity {

    Button cateBtn;

    RecyclerView recyclerView;
    TodayMain_PlanAdapter planAdapter;

     Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_main);

        cateBtn = (Button) findViewById(R.id.todayCategoryButton);

        cateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), TodayCategory.class);
                startActivity(intent1);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        planAdapter = new TodayMain_PlanAdapter();

        planAdapter.addItem(new TodayMain_Plan("카테고리 선택", "할 일 1", "할 일2"));

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

                Intent intent2 = new Intent(getApplicationContext(), TodayTodo.class);
                startActivity(intent2);
            }

            @Override
            public void onTodayCategoryItemClick(TodayCategoryItemAdapter.ViewHolder holder, View view, int position) {
            }
        });

    }
}