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
    private long scanCapacity;

    private List<Book> books        = new ArrayList<>();
    private List<Book> scannedBooks = new ArrayList<>();

    public Library(Library lib) throws Exception {
        this.id = lib.getId();
        this.nbDaysToSignup = lib.getNbDaysToSignup();
        this.nbShipBooks = lib.getNbShipBooks();

        this.totalDayToScanBooks = lib.getTotalDayToScanBooks();

        this.hasSignedUp = lib.getHasSignedUp();
        this.dayOfSignUp = lib.getDayOfSignUp();
        this.scanCapacity = lib.getScanCapacity();

        this.books = lib.getBooks();

        this.scannedBooks = lib.getScannedBooks();
    }

    public Library(int libId, int totalDayToScanBooks, Books books, List<String> inputData) throws Exception {
        this.id = libId;
        // First line //
        String[] firstLine = inputData.get(0).split(" ");
        this.nbDaysToSignup = Integer.parseInt(firstLine[1]);
        this.nbShipBooks    = Integer.parseInt(firstLine[2]);

        // Second line //
        this.books = new ArrayList<>();
        String[] bookContained = inputData.get(1).split(" ");
        for (int i = 0; i < bookContained.length; i++) {
            Book book = books.getBookById(Integer.parseInt(bookContained[i]));
            book.addALibrary(this);
            this.books.add(book);
        }

        this.totalDayToScanBooks = totalDayToScanBooks;
        this.dayOfSignUp = 0;

        this.updateScanCapacity();
    }

    public long getRestingDays() throws Exception {
        return this.getScanCapacity()/this.getNbShipBooks();
    }

    public int getTotalDayToScanBooks() {
        return totalDayToScanBooks;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean isHasSignedUp() {
        return hasSignedUp;
    }

    public boolean getHasSignedUp() {
        return hasSignedUp;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNbDaysToSignup() {
        return nbDaysToSignup;
    }

    public int getNbShipBooks() {
        return nbShipBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addABook(Book book) {
        this.books.add(book);
    }

    public boolean containBook(Book book) {
        return this.books.contains(book.getId());
    }

    public boolean isSignedUp() {
        return this.hasSignedUp;
    }

    public void signUp() throws Exception {
        this.hasSignedUp = true;
        this.updateScanCapacity();
    }

    public void updateScanCapacity() throws Exception {
        if ((this.totalDayToScanBooks - (this.dayOfSignUp + this.nbDaysToSignup)) > 0) {
            this.scanCapacity = (this.totalDayToScanBooks - (this.dayOfSignUp + this.nbDaysToSignup)) * this.getNbShipBooks();
        }
        else
            this.scanCapacity = 0;
    }

    public long getWorth() throws Exception {
        return this.getScore() / (this.getScanCapacity());
    }

    public long getScanCapacity() {
        return this.scanCapacity;
    }

    public void removeABook(Book book) {
        this.books.remove(book);
    }

    public int getScore() throws Exception {
        int result = 0;
        for (Book book : this.books) {
            result += book.getScore();
        }
        return result;
    }
    public int getScanScore() throws Exception {
        int result = 0;
        for (Book book : this.scannedBooks) {
            result += book.getScore();
        }
        return result;
    }

    public int getDayOfSignUp() {
        return dayOfSignUp;
    }

    public void setDayOfSignUp(int i) {
        this.dayOfSignUp = i;
    }

    public void scanABook() throws Exception {
        if (this.scanCapacity > 0)
            this.scanCapacity--;
        else if (this.scanCapacity < 0)
            throw new Exception("Scan capacity negative in " + this);
    }

    public List<Book> getScannedBooks() {
        return scannedBooks;
    }

    public void scanABook(Book book) throws Exception {
        if (!this.isSignedUp())
            this.signUp();
        if (this.getScanCapacity() > 0) {
            scanABook();
            this.scannedBooks.add(book);
        }
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


    public Book getMostValuableBook() {
        Book result = this.books.get(0);
        for (int i = 1; i < this.books.size(); i++) {
            if (result.getScore() < this.books.get(i).getScore())
                result = this.books.get(i);
        }
        return result;
    }

    /**
     *
     * @return
     */
    //**

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

    public String toDataSetBooksBis() {
        String result = "";
        for (int i = 0; i < this.scannedBooks.size() - 1; i++) {
            result += this.scannedBooks.get(i).toDataSet() + " ";
        }
        if (this.scannedBooks.size() > 0)
            result += this.scannedBooks.get(this.scannedBooks.size() - 1).toDataSet();
        return result;
    }

    public String toDebug() throws Exception {
        String result = "";
        result += "Library : " + this.getId() + System.lineSeparator();
        result += "SignUp day : " + this.getDayOfSignUp() + " and need " + this.getNbDaysToSignup() + " days to signup" +  System.lineSeparator();
        result += "Resting days to scan : " + (this.getTotalDayToScanBooks() - (this.getDayOfSignUp() + this.getNbDaysToSignup())) + System.lineSeparator();
        result += "Number of books scan a day : " + this.getNbShipBooks() + System.lineSeparator();

        result += "Calculated scanned days : " + (this.getScannedBooks().size() / this.getNbShipBooks() ) + System.lineSeparator();
        result += "Resting days : " + this.getRestingDays() + System.lineSeparator();

        result += this.getId() +  " " + this.getScannedBooks().size() + System.lineSeparator();
        return result;
    }

}
