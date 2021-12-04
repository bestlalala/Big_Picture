package smwu.mobileprogramming.termprj;

public class YearlyPlan {
    int _id;
    int year;
    String goalText;

    public YearlyPlan(int _id, int year, String goalText) {
        this._id = _id;
        this.year = year;
        this.goalText = goalText;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGoalText() {
        return goalText;
    }

    public void setGoalText(String goalText) {
        this.goalText = goalText;
    }
}