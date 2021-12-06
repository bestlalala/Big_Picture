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

import smwu.mobileprogramming.termprj.OnDatabaseCallback;


public class FragmentMonthly extends Fragment {
    OnDatabaseCallback in;
    TextView thisMonthText;
    EditText goalText;
    Button saveBtn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        in = (OnDatabaseCallback) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_monthly, container, false);

        thisMonthText = rootView.findViewById(R.id.thisYear);
        goalText = rootView.findViewById(R.id.editText_monthly_goal);

        // 입력한 목표를 데이터베이스에 저장하기
        saveBtn = rootView.findViewById(R.id.saveBtn_monthly_goal);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goalText.getText().toString() == "")
                    // 원래 없으면 새로 생성해서 삽입
                    in.insert(thisMonthText.getText().toString(), goalText.getText().toString());
                else
                    // 원래 있으면 업데이트
                    in.update(thisMonthText.getText().toString(), goalText.getText().toString());
            }
        });

        return rootView;
    }

    public void resetGoalText(MonthlyPlan item) {
        thisMonthText.setText(String.valueOf(item.getMonth()));
        goalText.setText(item.getGoalText());
    }
}