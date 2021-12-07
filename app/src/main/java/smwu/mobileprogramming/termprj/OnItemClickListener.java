package smwu.mobileprogramming.termprj;

import android.view.View;

public interface OnItemClickListener {
    public void onYearlyItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position);
    public void onMonthlyItemClick(MonthlyPlanAdapter.ViewHolder holder, View view, int position);
}
