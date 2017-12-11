package fi.academy;

public class Insult {

    long id;
    String content;

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {

        return id;
    }

    public String getContent() {
        return content;
    }

    public Insult(long id, String content) {

        this.id = id;
        this.content = content;
    }
}
