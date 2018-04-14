package Org.Tools;

public class ItemQuestionDetail{
    private int qid=0;
    private String question=null;
    private String answer=null;
    private String user_ans=null;
    private String category=null;
    private Boolean flag=false;
    private Boolean remarks=false;

    public ItemQuestionDetail() {
        qid=0;
        question=null;
        answer=null;
        user_ans=null;
        category=null;
        flag=false;
        remarks=false;
    }

    public ItemQuestionDetail(int qid, String question, String answer, String user_ans,String category,Boolean flag,Boolean remarks) {

        this.qid = qid;
        this.question = question;
        this.answer = answer;
        this.user_ans = user_ans;
        this.category = category;
        this.flag=flag;
        this.remarks=remarks;
    }

    public int getQid() {
        return this.qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUser_ans() {
        return this.user_ans;
    }

    public void setUser_ans(String user_ans) {
        this.user_ans = user_ans;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getRemarks() {
        return this.remarks;
    }

    public void setRemarks(Boolean remarks) {
        this.remarks = remarks;
    }
}
