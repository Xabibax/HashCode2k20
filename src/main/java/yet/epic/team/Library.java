package yet.epic.team;


import java.util.ArrayList;
import java.util.List;

public class Library {
    private int id;
    private long nbDaysToSignup;
    private long nbShipBooks;


    private int totalDayToScanBooks;

    private boolean hasSignedUp;
    private long dayOfSignUp;
    private long restingDays;
    private int restingScanForToday;

    private List<Integer> books;


    public Library() {
        this.nbDaysToSignup = 0;
        this.nbShipBooks = 0;
        this.books = new ArrayList<Integer>(){};
    }

    public Library(Library lib) {
        this.id = lib.getId();
        this.nbDaysToSignup = lib.getNbDaysToSignup();
        this.nbShipBooks = lib.getNbShipBooks();

        this.totalDayToScanBooks = lib.getTotalDayToScanBooks();

        this.hasSignedUp = lib.getHasSignedUp();
        this.dayOfSignUp = lib.getDayOfSignUp();
        this.restingDays = lib.getRestingDays();
        this.restingScanForToday = lib.getRestingScanForToday();

        this.books = new ArrayList<Integer>(){};
        for (int i = 0; i < lib.getNbBooks(); i++) {
            this.books.add(lib.getBooks().get(i));
        }
    }

    public Library(int libId, int totalDayToScanBooks, List<String> inputData) throws Exception {
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
        this.updateRestingDays();
    }

    public long getRestingDays() {
        return restingDays;
    }

    public int getTotalDayToScanBooks() {
        return totalDayToScanBooks;
    }

    public void setBooks(List<Integer> books) {
        this.books = books;
    }

    public boolean isHasSignedUp() {
        return hasSignedUp;
    }

    public int getRestingScanForToday() {
        return restingScanForToday;
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

    public int getNbBooks() {
        return this.books.size();
    }

    public long getNbDaysToSignup() {
        return nbDaysToSignup;
    }

    public long getNbShipBooks() {
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

    public void signUp() throws Exception {
        this.hasSignedUp = true;
        this.updateRestingDays();
    }

    public void updateRestingDays() throws Exception {
        if (this.totalDayToScanBooks > this.dayOfSignUp) {
            this.restingDays = (this.totalDayToScanBooks - (this.dayOfSignUp + this.nbDaysToSignup));
        }
        else
            this.restingDays = 0;
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

    public void setRestingDays(long restingDays) {
        this.restingDays = restingDays;
    }

    public void setRestingScanForToday(int restingScanForToday) {
        this.restingScanForToday = restingScanForToday;
    }

    public long getDayOfSignUp() {
        return dayOfSignUp;
    }

    public void setDayOfSignUp(long i) {
        this.dayOfSignUp = i;
    }

    public void scanABook() {
        if (this.restingScanForToday >= this.getNbShipBooks()) {
            this.restingScanForToday = 0;
            this.restingDays --;
        }
        else
            this.restingScanForToday++;
    }

    public OutputDataSet scanABook(Book book, OutputDataSet outputDataSet) throws Exception {
        if (!this.isSignedUp())
            this.signUp();
        if (this.restingDays > 0) {
            scanABook();
            outputDataSet.addABook(this, book);
        }
        return outputDataSet;
    }

    public void setNbDaysToSignup(long nbDaysToSignup) {
        this.nbDaysToSignup = nbDaysToSignup;
    }

    public void setNbShipBooks(long nbShipBooks) {
        this.nbShipBooks = nbShipBooks;
    }

    public void setTotalDayToScanBooks(int totalDayToScanBooks) {
        this.totalDayToScanBooks = totalDayToScanBooks;
    }

    public void setHasSignedUp(boolean hasSignedUp) {
        this.hasSignedUp = hasSignedUp;
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

    public String toDebug() {
        String result = "";
        result += "Library : " + this.getId() + System.lineSeparator();
        result += "SignUp day : " + this.getDayOfSignUp() + " and need " + this.getNbDaysToSignup() + " days to signup" +  System.lineSeparator();
        result += "Resting days to scan : " + (this.getTotalDayToScanBooks() - (this.getDayOfSignUp() + this.getNbDaysToSignup())) + System.lineSeparator();
        result += "Number of books scan a day : " + this.getNbShipBooks() + System.lineSeparator();

        result += "Calculated scanned days : " + (this.getNbBooks() / this.getNbShipBooks() ) + System.lineSeparator();
        result += "Resting days : " + this.getRestingDays() + System.lineSeparator();

        result += this.getId() +  " " + this.getNbBooks() + System.lineSeparator();
        // Second library line
        result += this.toDataSetBooks() + System.lineSeparator();
        return result;
    }

}
