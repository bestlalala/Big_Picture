package smwu.mobileprogramming.termprj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button setGoalBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setGoalBtn = findViewById(R.id.setGoalBtn);
        setGoalBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), YearlyActivity.class);
                startActivity(intent);
            }
        });

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);  // 캘린더 페이지 액티비티로 수정해야 함.
                startActivity(intent);
            }
        });

    }
}