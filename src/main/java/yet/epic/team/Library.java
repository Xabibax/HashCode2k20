package yet.epic.team;


import java.util.Arrays;

public class Library {
    private int id;
    private int nbBooks;
    private int nbDaysToSignup;
    private int nbShipBooks;

    private Book[] books;

    public Library() {
        this.nbBooks = 0;
        this.nbDaysToSignup = 0;
        this.nbShipBooks = 0;
        this.books = new Book[]{};
    }
    public Library(int id, int nbBooks, int nbDaysToSignup, int nbShipBooks, Book[] books) {
        this.nbBooks = nbBooks;
        this.nbDaysToSignup = nbDaysToSignup;
        this.nbShipBooks = nbShipBooks;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbBooks() {
        return nbBooks;
    }

    public void setNbBooks(int nbBooks) {
        this.nbBooks = nbBooks;
    }

    public int getNbDaysToSignup() {
        return nbDaysToSignup;
    }

    public void setNbDaysToSignup(int nbDaysToSignup) {
        this.nbDaysToSignup = nbDaysToSignup;
    }

    public int getNbShipBooks() {
        return nbShipBooks;
    }

    public void setNbShipBooks(int nbShipBooks) {
        this.nbShipBooks = nbShipBooks;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public void setBooks(String libSecondLine, int[] bookScore) {
        String[] booksScore = libSecondLine.split(" ");
        this.setBooks(new Book[booksScore.length]);
        for (int i = 0; i < booksScore.length; i++) {
            this.books[i] = new Book();
            this.books[i].setId(Integer.parseInt(booksScore[i]));
            this.books[i].setScore(bookScore[this.getId()]);
        }
    }

    @Override
    public String toString() {
        String result =  this.getNbBooks() + " " + this.getNbDaysToSignup() + " " + this.getNbShipBooks() +
                System.lineSeparator();
        for (int i = 0; i < this.getBooks().length - 1; i++) {
            result += this.getBooks()[i] + " ";
        }
        result += this.getBooks()[this.getBooks().length - 1];
        return result;
    }
}
