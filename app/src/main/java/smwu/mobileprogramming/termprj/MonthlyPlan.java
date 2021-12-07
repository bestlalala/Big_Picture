package smwu.mobileprogramming.termprj;

public class MonthlyPlan {
    int _id;
    int month;
    String goalText;

    public MonthlyPlan(int _id, int month, String goalText) {
        this._id = _id;
        this.month = month;
        this.goalText = goalText;
    }

    public MonthlyPlan(int month, String goalText) {
        this.month = month;
        this.goalText = goalText;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getGoalText() {
        return goalText;
    }

    public void setGoalText(String goalText) {
        this.goalText = goalText;
    }

    @Override
    public String toString() {
        return "MonthlyPlan{" +
                "month=" + month +
                ", goalText='" + goalText + '\'' +
                '}';
    }
}
