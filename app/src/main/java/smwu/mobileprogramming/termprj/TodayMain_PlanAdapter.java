package smwu.mobileprogramming.termprj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodayMain_PlanAdapter extends RecyclerView.Adapter<TodayMain_PlanAdapter.ViewHolder> implements OnItemClickListener {
    ArrayList<TodayMain_Plan> items = new ArrayList<TodayMain_Plan>();
    OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.today_main_fragment,viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TodayMain_Plan item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(TodayMain_Plan item) {
        items.add(item);
    }

    public void setItems(ArrayList<TodayMain_Plan> items) {
        this.items = items;
    }

    public TodayMain_Plan getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, TodayMain_Plan item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onYearlyItemClick(YearlyPlanAdapter.ViewHolder holder, View view, int position) {
    }
    @Override
    public void onMonthlyItemClick(MonthlyPlanAdapter.ViewHolder holder, View view, int position) {
    }

    public void onTodayPlanItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onTodayPlanItemClick(holder, view, position);
        }
    }

    @Override
    public void onTodayCategoryItemClick(TodayCategoryItemAdapter.ViewHolder holder, View view, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        CheckBox todo1;
        CheckBox todo2;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super (itemView);

            category = itemView.findViewById(R.id.textViewTodayCategory);
            todo1 = itemView.findViewById(R.id.checkboxTodo1);
            todo2 = itemView.findViewById(R.id.checkboxTodo2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onTodayPlanItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(TodayMain_Plan item) {
            category.setText(item.getCategory());
            todo1.setText(item.getTodo1());
            todo2.setText(item.getTodo2());
            category.setBackgroundColor(item.getColor());
        }
    }

}
