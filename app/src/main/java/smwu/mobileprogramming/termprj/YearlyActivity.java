package smwu.mobileprogramming.termprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class YearlyActivity extends AppCompatActivity {
    Button backBtn, nextBtn;
    FragmentYearly fragmentYearly;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    YearlyPlanAdapter adapter;
    TextView thisyearText;

    Date currentTime;
    SimpleDateFormat yearFormat;

    String thisYear, nextYear, nnextYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yearly);

        // 올해 연도 구하기
        currentTime = Calendar.getInstance().getTime();
        yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        thisYear = yearFormat.format(currentTime);

        thisyearText = findViewById(R.id.thisYear);
        fragmentYearly = new FragmentYearly();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentYearly).commit();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new YearlyPlanAdapter();

        adapter.addItem(new YearlyPlan(1, Integer.valueOf(thisYear)+1, Integer.valueOf(thisYear)+1+"년 계획"));
        adapter.addItem(new YearlyPlan(2, Integer.valueOf(thisYear)+2, Integer.valueOf(thisYear)+2+"년 계획"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnYearlyItemClickListener() {
            @Override
            public void onItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
                YearlyPlan item = adapter.getItem(position);
                // Fragment 해당 연도로 바꿔야 함.

                switch (item._id){
                    case 1:
                        nextYear = String.valueOf(Integer.valueOf(thisYear)+1);
                        thisyearText = findViewById(R.id.thisYear);
                        thisyearText.setText(nextYear);
                        break;
                    case 2:
                        nnextYear = String.valueOf(Integer.valueOf(thisYear)+2);
                        thisyearText = findViewById(R.id.thisYear);
                        thisyearText.setText(nnextYear);
                        break;
                }
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