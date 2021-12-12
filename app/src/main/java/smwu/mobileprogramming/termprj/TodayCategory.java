package smwu.mobileprogramming.termprj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodayCategory extends AppCompatActivity {

    Button saveBtn;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_category);

        //이번주 목표 카테고리 리사이클러뷰로 띄우기
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        TodayCategoryItemAdapter adapter = new TodayCategoryItemAdapter();

        adapter.addItem(new TodayCategoryItem("이번주 목표 카테고리", 000000));

        recyclerView.setAdapter(adapter);

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
                //할일에 카테고리 뜨게 하기
            }
        });

        //저장 버튼
        saveBtn = (Button) findViewById(R.id.categorySaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveThread saveThread = new SaveThread();
                saveThread.start();
            }
        });
    }

    private class SaveThread extends Thread {
        public void run() {
            super.run();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    TodayMain_Plan plan = new TodayMain_Plan("카테고리 선택한거", "할 일을 작성해주세요", "할 일을 작성해주세요");
                    //데이터베이스 저장 함수 insert(thisWeek, category);

                    Intent intent = new Intent(getApplicationContext(), TodayMain.class);
                    startActivity(intent);
                }
            });
        }
    }
}
