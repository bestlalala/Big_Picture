package smwu.mobileprogramming.termprj;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentWeekly extends Fragment {
    OnDatabaseCallback in;
    TextView thisweekText;
    EditText goalText;
    Button categoryBtn, saveBtn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        in = (OnDatabaseCallback) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weekly, container, false);

        thisweekText = rootView.findViewById(R.id.thisYear);
        goalText = rootView.findViewById(R.id.editText_yearly_goal);

        categoryBtn = rootView.findViewById(R.id.add_categoryBtn);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카테고리 버튼 눌렀을 때
            }
        });

        // 입력한 목표를 데이터베이스에 저장하기
        saveBtn = rootView.findViewById(R.id.saveBtn_yearly_goal);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goalText.getText().toString() == "")
                    // 원래 없으면 새로 생성해서 삽입
                    in.insert(thisweekText.getText().toString(), goalText.getText().toString());
                else
                    // 원래 있으면 업데이트
                    in.update(thisweekText.getText().toString(), goalText.getText().toString());
            }
        });

        return rootView;
    }

    public void resetGoalText(YearlyPlan item) {
        thisweekText.setText(String.valueOf(item.getYear()));
        goalText.setText(item.getGoalText());
    }
}