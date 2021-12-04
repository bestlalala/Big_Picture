package smwu.mobileprogramming.termprj;

import java.util.ArrayList;

public interface OnDatabaseCallback {
    public void insert(String year, String goalText);
    public ArrayList<YearlyPlan> selectAll();
}
