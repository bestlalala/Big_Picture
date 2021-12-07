package smwu.mobileprogramming.termprj;

import static smwu.mobileprogramming.termprj.GoalDatabase.TAG;
import static smwu.mobileprogramming.termprj.WeeklyActivity.thisWeek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentWeekly extends Fragment {
    LinearLayout linearLayout;
    TextView thisWeekText;
    Button categoryBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weekly, container, false);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);

        thisWeekText = rootView.findViewById(R.id.thisWeek);
        thisWeekText.setText(thisWeek+"주차");


        categoryBtn = rootView.findViewById(R.id.add_categoryBtn);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }


    public void addCategory(TextView textView) {
        linearLayout.addView(textView);
    }
}