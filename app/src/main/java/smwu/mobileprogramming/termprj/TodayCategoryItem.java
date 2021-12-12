package smwu.mobileprogramming.termprj;

import android.graphics.Color;

public class TodayCategoryItem {
    String category_name;
    int color;

    public TodayCategoryItem(String category_name, int color) {
        this.category_name = category_name;
        this.color = color;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
