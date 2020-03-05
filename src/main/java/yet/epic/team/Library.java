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
        this.id = libId;
        // First line //
        String[] firstLine = inputData.get(0).split(" ");
        this.nbDaysToSignup = Integer.parseInt(firstLine[1]);
        this.nbShipBooks    = Integer.parseInt(firstLine[2]);

        // Second line //
        this.books = new ArrayList<>();
        String[] bookContained = inputData.get(1).split(" ");
        for (int i = 0; i < bookContained.length; i++) {
            this.books.add( Integer.parseInt(bookContained[i]));
        }

        this.totalDayToScanBooks = totalDayToScanBooks;
        this.dayOfSignUp = 0;
        this.updateScanCapacity();
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
        this.updateScanCapacity();
    }

    public void updateScanCapacity() {
        this.scanCapacity = (this.totalDayToScanBooks - (this.dayOfSignUp + this.nbDaysToSignup)) * this.getNbShipBooks();
    }

    public void removeABook(Book book) {
        if (this.books.contains(book.getId()))
            this.books.remove((Integer) book.getId());
    }

    public int getScore(Books books) throws Exception {
        int result = 0;
        for (int i = 0; i < this.books.size(); i++) {
            result += books.getBookById(this.books.get(i)).getScore();
        }
        return result;
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

    public int getRestingDays() {
        return scanCapacity/this.getNbShipBooks();
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

    public void setNbDaysToSignup(int nbDaysToSignup) {
        this.nbDaysToSignup = nbDaysToSignup;
    }

    public void setNbShipBooks(int nbShipBooks) {
        this.nbShipBooks = nbShipBooks;
    }

    public void setTotalDayToScanBooks(int totalDayToScanBooks) {
        this.totalDayToScanBooks = totalDayToScanBooks;
    }

    public void setHasSignedUp(boolean hasSignedUp) {
        this.hasSignedUp = hasSignedUp;
    }

    public void setScanCapacity(int scanCapacity) {
        this.scanCapacity = scanCapacity;
    }

    @Override
    public String toString() {
        String result = "Library id : " +  this.getId() + System.lineSeparator() +
                "Contain : ";
        for (int i = 0; i < this.getBooks().size() - 1; i++) {
            result += this.getBooks().get(i) + System.lineSeparator();
        }
        if (this.books.size() > 0)
            result += this.books.get(this.books.size() - 1);
        return result;
    }

    public String toDataSetBooks() {
        String result = "";
        for (int i = 0; i < this.books.size() - 1; i++) {
            result += this.books.get(i) + " ";
        }
        if (this.books.size() > 0)
            result += this.books.get(this.books.size() - 1);
        return result;
    }

}
