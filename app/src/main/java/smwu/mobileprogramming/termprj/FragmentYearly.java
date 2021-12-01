package smwu.mobileprogramming.termprj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentYearly extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yearly, container, false);
        //initUI(rootView);

        return rootView;
    }

    //private void initUI(ViewGroup rootView){}
}
