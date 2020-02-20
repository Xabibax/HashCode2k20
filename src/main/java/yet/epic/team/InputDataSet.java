package yet.epic.team;

import java.util.Arrays;

public class InputDataSet {
    private int nbBooks;
    private int nbLibrairies;
    private int nbOfDays;

    private int[] bookScore;

    private Library[] libraries;

    public InputDataSet() {
        this.nbBooks = 0;
        this.nbLibrairies = 0;
        this.nbOfDays = 0;
        this.bookScore = new int[]{};
        this.libraries = new Library[]{};
    }
    public InputDataSet(int nbBooks, int nbLibrairies, int nbOfDays, int[] bookScore, Library[] libraries) {
        this.nbBooks = nbBooks;
        this.nbLibrairies = nbLibrairies;
        this.nbOfDays = nbOfDays;
        this.bookScore = bookScore;
        this.libraries = libraries;
    }

    public int getNbBooks() {
        return nbBooks;
    }

    public void setNbBooks(int nbBooks) {
        this.nbBooks = nbBooks;
    }

    public int getNbLibrairies() {
        return nbLibrairies;
    }

    public void setNbLibrairies(int nbLibrairies) {
        this.nbLibrairies = nbLibrairies;
    }

    public int getNbOfDays() {
        return nbOfDays;
    }

    public void setNbOfDays(int nbOfDays) {
        this.nbOfDays = nbOfDays;
    }

    public int[] getBookScore() {
        return bookScore;
    }

    public void setBookScore(int[] bookScore) {
        this.bookScore = bookScore;
    }
    public void setBookScore(String[] bookScore) {
        this.setBookScore(new int[bookScore.length]);
        for (int i = 0; i < bookScore.length; i++) {
            this.bookScore[i] = Integer.parseInt(bookScore[i]);
        }
    }

    public Library[] getLibraries() {
        return libraries;
    }

    public void setLibraries(Library[] libraries) {
        this.libraries = libraries;
    }
    public void setLibraries(String[] libFirstLine, String[] libSecondLine) {
        this.setLibraries(new Library[libFirstLine.length]);
        for (int i = 0; i < libFirstLine.length; i++) {
            this.libraries[i] = new Library();
            this.libraries[i].setId(i);
            this.libraries[i].setNbBooks(Integer.parseInt(libFirstLine[i].split(" ")[0]));
            this.libraries[i].setNbDaysToSignup(Integer.parseInt(libFirstLine[i].split(" ")[1]));
            this.libraries[i].setNbShipBooks(Integer.parseInt(libFirstLine[i].split(" ")[2]));

            this.libraries[i].setBooks(libSecondLine[i], this.getBookScore());
        }
    }

    @Override
    public String toString() {
        String result =  this.getNbBooks() + " " + this.getNbLibrairies() + " " + this.getNbOfDays() +
                System.lineSeparator();
        for (int i = 0; i < this.getBookScore().length - 1; i++) {
            result += this.getBookScore()[i] + " ";
        }
        result += this.getBookScore()[this.getBookScore().length - 1] + System.lineSeparator();
        for (int i = 0; i < this.getLibraries().length - 1; i++) {
            result += this.getLibraries()[i] + System.lineSeparator();
        }
        result += this.getLibraries()[this.getLibraries().length - 1] + System.lineSeparator();
        return result;
    }
}
