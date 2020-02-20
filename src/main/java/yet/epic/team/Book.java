package yet.epic.team;

import java.util.Arrays;

public class Book {
    private int id;
    private int score;

    public Book() {
        this.id = 0;
        this.score = 0;
    }
    public Book(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
