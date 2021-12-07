package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.WeeklyActivity.thisWeek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class CategoryActivity extends AppCompatActivity implements OndbCategoryCallback {
    TextView thisWeekText;
    Button cancelBtn, colorPickBtn, saveBtn;
    EditText categoryName, categoryTodo;

    GoalDatabase database;
    Handler handler1 = new Handler();
    Handler handler2 = new Handler();

    int category_color=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        thisWeekText = findViewById(R.id.thisWeek);
        thisWeekText.setText(thisWeek+"주차");

        categoryName = findViewById(R.id.categoryName);
        categoryTodo = findViewById(R.id.category_todo);

        colorPickBtn = findViewById(R.id.button3);
        colorPickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickThread colorPickThread = new ColorPickThread();
                colorPickThread.start();
            }
        });

        cancelBtn = findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeeklyActivity.class);
                startActivity(intent);
            }
        });

        saveBtn = findViewById(R.id.saveBtn_category);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveThread saveThread = new SaveThread();
                saveThread.start();
            }
        });

        database = GoalDatabase.getInstance(this);
    }

    private class ColorPickThread extends Thread {

        public void run() {
            super.run();
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    openColorPicker();
                }
            });
        }
    }
    private class SaveThread extends Thread {
        public void run() {
            super.run();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    Category category = new Category(categoryName.getText().toString(), category_color, categoryTodo.getText().toString());
                    insert(thisWeek, category);
                    Intent intent = new Intent(getApplicationContext(), WeeklyActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        colors.add("#ffab91");
        colors.add("#F48FB1");
        colors.add("#ce93d8");
        colors.add("#b39ddb");
        colors.add("#9fa8da");
        colors.add("#90caf9");
        colors.add("#81d4fa");
        colors.add("#80deea");
        colors.add("#80cbc4");
        colors.add("#c5e1a5");
        colors.add("#e6ee9c");
        colors.add("#fff59d");
        colors.add("#ffe082");
        colors.add("#ffcc80");
        colors.add("#bcaaa4");

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        categoryName.setBackgroundColor(color);  // OK 버튼 클릭 시 이벤트
                        category_color = color;
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
    }

    @Override
    public void insert(String week, Category category) {
        database = GoalDatabase.getInstance(this);
        try {
            synchronized (database) {
                database.insertRecordWeekly(week, category);
                Toast.makeText(getApplicationContext(), "이번주 목표를 추가했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        finally {
            boolean isOpen = database.open();
            if (isOpen) {
                Log.d(TAG, "Goal database is open.");
            } else {
                Log.d(TAG, "Goal database is not open.");
            }
            if (database != null && isOpen){
                database.close();
            }
        }
    }

}