package smwu.mobileprogramming.termprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class YearlyActivity extends AppCompatActivity {
    Button backBtn, nextBtn;
    FragmentYearly fragmentYearly;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    YearlyPlanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yearly);

        fragmentYearly = new FragmentYearly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentYearly).commit();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new YearlyPlanAdapter();

        adapter.addItem(new YearlyPlan(2022, "2022년 계획"));
        adapter.addItem(new YearlyPlan(2023, "2023년 계획"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnYearlyItemClickListener() {
            @Override
            public void onItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
                YearlyPlan item = adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "연도 선택됨"+item.getYear(), Toast.LENGTH_SHORT).show();
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
    }
}