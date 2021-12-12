package smwu.mobileprogramming.termprj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodayCategoryItemAdapter extends RecyclerView.Adapter<TodayCategoryItemAdapter.ViewHolder> implements OnItemClickListener {
    ArrayList<TodayCategoryItem> items = new ArrayList<>();
    OnItemClickListener listener;

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.today_category_fragmant, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TodayCategoryItem item = items.get(position);
        viewHolder.setItem(item);
    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(TodayCategoryItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<TodayCategoryItem> items) {
        this.items = items;
    }

    public TodayCategoryItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, TodayCategoryItem item) {
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

    @Override
    public void onTodayPlanItemClick(TodayMain_PlanAdapter.ViewHolder holder, View view, int position) {
    }

    public void onTodayCategoryItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onTodayCategoryItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            btn = itemView.findViewById(R.id.categoryButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onTodayCategoryItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(TodayCategoryItem item) {
            btn.setText(item.getCategory_name());
            btn.setBackgroundColor(item.getColor());
        }
    }
}
