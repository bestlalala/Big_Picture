package smwu.mobileprogramming.termprj;

public class WeeklyPlan {
    int _id;
    int week;
    Category category;

    public WeeklyPlan(int _id, int week, Category category) {
        this._id = _id;
        this.week = week;
        this.category = category;
    }

    public WeeklyPlan(int week, Category category) {
        this.week = week;
        this.category = category;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "WeeklyPlan{" +
                "week=" + week +
                ", category=" + category +
                '}';
    }
}
