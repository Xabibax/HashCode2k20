package yet.epic.team;


import java.util.ArrayList;
import java.util.List;

public class Library {
    private int id;
    private int nbDaysToSignup;
    private int nbShipBooks;
    private int totalDayToScanBooks;

    private boolean hasSignedUp;
    private int dayOfSignUp;
    private int scanCapacity;

    private List<Integer> books;


    public Library() {
        this.nbDaysToSignup = 0;
        this.nbShipBooks = 0;
        this.books = new ArrayList<Integer>(){};
    }

    public Library(int libId, int totalDayToScanBooks, List<String> inputData) {
        // First line //
        this.id = libId;
        this.nbDaysToSignup = Integer.parseInt(inputData.get(0).split(" ")[1]);
        this.nbShipBooks    = Integer.parseInt(inputData.get(0).split(" ")[2]);
        this.scanCapacity = (totalDayToScanBooks - (0 + this.nbDaysToSignup)) * this.nbShipBooks;
        // Second line //
        this.books = new ArrayList<>();
        for (int i = 0; i < inputData.get(1).split(" ").length; i++) {
            this.books.add( Integer.parseInt(inputData.get(1).split(" ")[i]));
        }
        this.totalDayToScanBooks = totalDayToScanBooks;
        this.dayOfSignUp = 0;
        this.scanCapacity = (this.totalDayToScanBooks - (this.dayOfSignUp + this.nbDaysToSignup)) * this.nbShipBooks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNbBooks() {
        return this.books.size();
    }

    public int getNbDaysToSignup() {
        return nbDaysToSignup;
    }

    public int getNbShipBooks() {
        return nbShipBooks;
    }

    public List<Integer> getBooks() {
        return books;
    }

    public void addABook(Book book) {
        this.books.add(book.getId());
    }

    public boolean containBook(Book book) {
        return this.books.contains(book.getId());
    }

    public boolean isSignedUp() {
        return this.hasSignedUp;
    }

    public void signUp() {
        this.hasSignedUp = true;
        this.scanCapacity = (this.totalDayToScanBooks - (this.dayOfSignUp + this.nbDaysToSignup)) * this.nbShipBooks;
    }

    public void removeABook(Book book) {
        if (this.books.contains(book.getId()))
            this.books.remove((Integer) book.getId());
    }

    public int getDayOfSignUp() {
        return dayOfSignUp;
    }

    public void setDayOfSignUp(int i) {
        this.dayOfSignUp = i;
    }

    public int getScanCapacity() {
        return scanCapacity;
    }

    public OutputDataSet scanABook(Book book, OutputDataSet outputDataSet) {
        if (!this.isSignedUp())
            this.signUp();
        if (this.scanCapacity > 0) {
            this.scanCapacity--;
            outputDataSet.addABook(this, book);
        }
        return outputDataSet;
    }


    @Override
    public String toString() {
        String result = "Library id : " +  this.getId() + System.lineSeparator() +
                "Contain : ";
        for (int i = 0; i < this.getBooks().size() - 1; i++) {
            result += this.getBooks().get(i) + System.lineSeparator();
        }
        result += this.getBooks().get(this.getBooks().size() - 1);
        return result;
    }

    public String toDataSetBooks() {
        String result = "";
        for (int i = 0; i < this.books.size() - 1; i++) {
            result += this.books.get(i) + " ";
        }
        result += this.books.get(this.books.size() - 1);
        return result;
    }

}
