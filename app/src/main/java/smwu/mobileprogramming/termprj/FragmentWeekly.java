package smwu.mobileprogramming.termprj;

import android.app.Activity;
import android.content.Intent;
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
    TextView thisWeekText;
    TextView category, todoList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        in = (OnDatabaseCallback) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weekly, container, false);

        thisWeekText = rootView.findViewById(R.id.thisYear);
        category = rootView.findViewById(R.id.category_title);
        todoList = rootView.findViewById(R.id.category_todo);

        return rootView;
    }
}