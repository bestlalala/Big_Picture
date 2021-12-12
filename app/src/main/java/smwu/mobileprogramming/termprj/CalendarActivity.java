package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.YearlyActivity.cal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    Button btn;
    TextView month, plan;

    // 이번 달 구하기
    public static Calendar cal = Calendar.getInstance();
    public static DecimalFormat df = new DecimalFormat("00");
    public static String thisMonth = df.format(cal.get(Calendar.MONTH)+1);

    GoalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        //이번 달 표시
        month = (TextView) findViewById(R.id.textViewMonth);
        month.setText("" + (thisMonth) + "월");

        //이번 달 목표 표시
        plan = (TextView) findViewById(R.id.textViewMonthPlan);

        btn = (Button)findViewById(R.id.calendarButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodayMain.class);
                startActivity(intent);
            }
        });
    }

    //이번달 목표 불러서 나타내기
    public void executeQueryForThisMonth() {
        Cursor cursor = database.rawQuery("select * from MONTHLY_GOAL WHERE MONTH="+thisMonth);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            int month = cursor.getInt(1);
            String goalText = cursor.getString(2);
            plan.setText(goalText);

            // Test code
            Log.d(TAG, "레코드#" + i + " : " + id + ", " + month + ", " + goalText);
        }

        cursor.close();
    }
}
