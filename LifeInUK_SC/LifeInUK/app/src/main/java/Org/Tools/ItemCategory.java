package Org.Tools;

public class ItemCategory {

    String title;
    int progress;
    String answered;
    String correct;

    public ItemCategory() {
        title = "All Categories";
        progress = 45;
        answered = "1234";
        correct = "1122";
    }

    public ItemCategory(String title, int progress, String answered, String correct) {
        this.title = title;
        this.progress = progress;
        this.answered = answered;
        this.correct = correct;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getAnswered() {
        return this.answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public String getCorrect() {
        return this.correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
