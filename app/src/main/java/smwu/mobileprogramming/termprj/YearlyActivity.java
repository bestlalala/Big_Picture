package smwu.mobileprogramming.termprj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YearlyActivity extends AppCompatActivity {
    Button backBtn, nextBtn;
    Button nextYear, nnextYear;
    FragmentYearly fragmentYearly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yearly);

        fragmentYearly = new FragmentYearly();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentYearly).commit();

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

        nextYear = findViewById(R.id.nextYearBtn);
        nextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2022년 목표 설정하기
            }
        });

        nnextYear = findViewById(R.id.nnextYearBtn);
        nnextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2023년 목표 설정하기
            }
        });
    }
}