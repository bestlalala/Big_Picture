package smwu.mobileprogramming.termprj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TodayTodo extends AppCompatActivity {

    CalendarActivity calendarActivity;
    TextView date;

    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_todo);

        Bundle extras1 = getIntent().getExtras();
        date = findViewById(R.id.textViewTodayDate);
        date.setText(extras1.getString("Date"));

        saveBtn = (Button) findViewById(R.id.todoSaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodayMain.class);
                Bundle extras2 = new Bundle();
                extras2.putString("Date", extras1.getString("Date"));
                intent.putExtras(extras2);
                startActivity(intent);
            }
        });
    }
}