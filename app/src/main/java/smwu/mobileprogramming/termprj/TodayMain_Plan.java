package smwu.mobileprogramming.termprj;

public class TodayMain_Plan {
    String category;
    String todo1;
    String todo2;
    int color;

    public TodayMain_Plan(String category, String todo1, String todo2, int color) {
        this.category = category;
        this.todo1 = todo1;
        this.todo2 = todo2;
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTodo1() {
        return todo1;
    }

    public void setTodo1(String todo1) {
        this.todo1 = todo1;
    }

    public String getTodo2() {
        return todo2;
    }

    public void setTodo2(String todo2) {
        this.todo2 = todo2;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
