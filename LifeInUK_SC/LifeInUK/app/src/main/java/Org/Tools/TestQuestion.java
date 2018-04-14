package Org.Tools;

/**
 * Created by Omer Habib on 12/7/2016.
 */

public class TestQuestion {

    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String category1;
    String category2;
    String answer;
    String explain;
    Boolean pass;
    int test_id;


    public TestQuestion() {
        question="Question # __";
        option1=null;
        option2=null;
        option3=null;
        option4=null;
        category1=null;
        category2=null;
        answer=null;
        explain=null;
        pass=false;
        test_id=0;
    }

    public TestQuestion(String question, String option1, String option2, String option3, String option4, String category1, String category2, String answer, String explain, Boolean pass) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.category1 = category1;
        this.category2 = category2;
        this.answer = answer;
        this.explain = explain;
        this.pass=pass;
    }

    public TestQuestion(String question, String option1, String option2, String option3, String option4, String category1, String category2, String answer, String explain, Boolean pass, int test_id) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.category1 = category1;
        this.category2 = category2;
        this.answer = answer;
        this.explain = explain;
        this.pass = pass;
        this.test_id = test_id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return this.option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return this.option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getCategory1() {
        return this.category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return this.category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Boolean getPass() {
        return this.pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public int getTest_id() {
        return this.test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }
}
