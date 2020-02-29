package yet.epic.team;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int id;
    private int score;

    private List<Integer> libraries;

    public Book() {
        this.id = 0;
        this.score = 0;
        this.libraries = new ArrayList<>();
    }
    public Book(int id, int score) {
        this.id = id;
        this.score = score;
        this.libraries = new ArrayList<>();
    }
    public Book(int id, String score) {
        this(id, Integer.parseInt(score));
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

    public void addALibrary(Library library) {
        this.libraries.add(library.getId());
    }

    public boolean isContainedBy(Library library) {
        return this.libraries.contains(library.getId());
    }

    public List<Integer> getLibraries() {
        return libraries;
    }



    @Override
    public String toString() {
        return  "Book id : " + this.getId()  + System.lineSeparator() +
                "Score : " + this.getScore() + System.lineSeparator() +
                "Contained by : " + this.libraries;
    }
    public String toDataSet() {
        return String.valueOf(this.getId());
    }
}
