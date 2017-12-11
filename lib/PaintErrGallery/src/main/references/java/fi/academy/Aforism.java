package fi.academy;

public class Aforism {

    int id;
    String text, author;

    public Aforism(int id, String text, String author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    public String print() {
        return this.text + " - " + this.author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
