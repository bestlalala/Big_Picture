package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.YearlyActivity.thisYear;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class FragmentYearly extends Fragment {
    OnDatabaseCallback in;
    TextView thisyearText;
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_yearly, container, false);

        thisyearText = rootView.findViewById(R.id.thisYear);
        goalText = rootView.findViewById(R.id.editText_yearly_goal);

        // 입력한 목표를 데이터베이스에 저장하기
        saveBtn = rootView.findViewById(R.id.saveBtn_yearly_goal);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원래 없으면 새로 생성해서 삽입
                in.insert(thisyearText.getText().toString(), goalText.getText().toString());

                // 원래 있으면 업데이트
            }
        });

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView){
    }

    public void resetGoalText(YearlyPlan item) {
        thisyearText.setText(String.valueOf(item.getYear()));
        goalText.setText(item.getGoalText());
    }
}