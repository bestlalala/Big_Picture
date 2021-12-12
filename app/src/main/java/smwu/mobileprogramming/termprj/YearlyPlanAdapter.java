package smwu.mobileprogramming.termprj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class YearlyPlanAdapter extends RecyclerView.Adapter<YearlyPlanAdapter.ViewHolder> implements OnItemClickListener {
    ArrayList<YearlyPlan> items = new ArrayList<YearlyPlan>();
    OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.plan_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YearlyPlan item =items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(YearlyPlan item) {
        items.add(item);
    }

    public void setItems(ArrayList<YearlyPlan> items) {
        this.items = items;
    }

    public YearlyPlan getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, YearlyPlan item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onYearlyItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onYearlyItemClick(holder, view, position);
        }
    }

    @Override
    public void onMonthlyItemClick(MonthlyPlanAdapter.ViewHolder holder, View view, int position) {    }

    @Override
    public void onTodayPlanItemClick(TodayMain_PlanAdapter.ViewHolder holder, View view, int position) {
    }

    @Override
    public void onTodayCategoryItemClick(TodayCategoryItemAdapter.ViewHolder holder, View view, int position) {
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView, textView2;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.title);
            textView2 = itemView.findViewById(R.id.goalText);

            itemView.setOnClickListener(new View.OnClickListener(){ // 아이템 뷰에 OnClickListener 설정하기
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) { // 아이템 뷰 클릭 시 미리 정의한 다른 리스너의 메서드 호출하기
                        listener.onYearlyItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }

        public void setItem(YearlyPlan item) {
            textView.setText(String.valueOf(item.getYear()));
            textView2.setText(item.getGoalText());
        }

    }


}
