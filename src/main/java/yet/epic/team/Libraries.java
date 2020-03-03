package yet.epic.team;

import java.util.ArrayList;
import java.util.List;

public class Libraries {
    private int totalDayToScanBooks;
    private int dayOfLastSignedLibrary;
    private List<Library> libraries;

    public Libraries(int totalDayToScanBooks, List<String> inputData) {
        this.totalDayToScanBooks    = totalDayToScanBooks;
        this.dayOfLastSignedLibrary = 0;
        this.libraries = new ArrayList<>();
        for (int i = 0; i < inputData.size() - 1; i += 2) {
            this.addALibrary(
                    new Library(i/2, totalDayToScanBooks, inputData.subList(i, i + 2))
            );
        }
    }
    public Libraries(Libraries libraries) {
        this.totalDayToScanBooks = libraries.getTotalDayToScanBooks();
        this.dayOfLastSignedLibrary = libraries.getDayOfLastSignedLibrary();
        this.libraries = new ArrayList<>(libraries.getLibraries());
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void addALibrary(Library library) {
        this.libraries.add(library);
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public int getTotalDayToScanBooks() {
        return totalDayToScanBooks;
    }

    public void setTotalDayToScanBooks(int totalDayToScanBooks) {
        this.totalDayToScanBooks = totalDayToScanBooks;
    }

    public int getDayOfLastSignedLibrary() {
        return dayOfLastSignedLibrary;
    }

    public void setDayOfLastSignedLibrary(int dayOfLastSignedLibrary) {
        this.dayOfLastSignedLibrary = dayOfLastSignedLibrary;
    }

    public Library getLibrary(Integer i) {
        return this.libraries.get(i);
    }

    public int size() {
        return this.libraries.size();
    }

    public void removeABookFromLibs(Book book) {
        for (int i = 0; i < this.libraries.size(); i++) {
            this.libraries.get(i).removeABook(book);
        }
    }

    public void addBookToLib(Library library, Book book) {
        this.libraries.get(library.getId()).addABook(book);
    }
    public Books updateBooksLocation(Books books) {
        for (int i = 0; i < libraries.size(); i++) {
            for (int j = 0; j < libraries.get(i).getNbBooks(); j++) {
                Library library = libraries.get(i);
                Book book       = books.getBook(library.getBooks().get(j));
                books.addLibToBook(book, library);
            }
        }
        return books;
    }

    public Library getBestCapaLib(List<Integer> libId) {
        Library result = this.libraries.get(libId.get(0));
        for (int i = 1; i < libId.size(); i++) {
            if (result.getScanCapacity() < this.libraries.get(libId.get(i)).getScanCapacity())
                result = this.libraries.get(libId.get(i));
        }
        return result;
    }

    public Library getMostRestDaylib(List<Integer> libId) {
        Library result = this.libraries.get(libId.get(0));
        for (int i = 1; i < libId.size(); i++) {
            if (result.getRestingDays() < this.libraries.get(libId.get(i)).getRestingDays())
                result = this.libraries.get(libId.get(i));
        }
        return result;
    }

    public Library getBestLib(List<Integer> libId) {
        return getMostRestDaylib(libId);
        // return getBestCapaLib(libId);
    }

    public OutputDataSet scanABook(Book book, OutputDataSet outputDataSet) {
        if (book.getLibraries().size() > 0) {
            Library bestCapaLib = getBestLib(book.getLibraries());
            if (!this.libraries.get(bestCapaLib.getId()).isSignedUp())
                this.dayOfLastSignedLibrary += this.libraries.get(bestCapaLib.getId()).getDayOfSignUp();
            outputDataSet = this.libraries.get(bestCapaLib.getId()).scanABook(book, outputDataSet);
            for (int i = 0; i < this.libraries.size(); i++) {
                if (!this.libraries.get(i).isSignedUp())
                    this.libraries.get(i).setDayOfSignUp(bestCapaLib.getDayOfSignUp() + bestCapaLib.getNbDaysToSignup());
            }
        }

        return outputDataSet;
    }


}
