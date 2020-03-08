package yet.epic.team;

import java.util.ArrayList;
import java.util.List;

public class Libraries {
    private int totalDayToScanBooks;
    private List<Library> libraries;

    public Libraries(int totalDayToScanBooks, Books books, List<String> inputData) throws Exception {
        this.totalDayToScanBooks    = totalDayToScanBooks;
        this.libraries = new ArrayList<>();
        for (int i = 0; i < inputData.size() - 1; i += 2) {
            this.addALibrary(
                new Library(i/2, totalDayToScanBooks, books, inputData.subList(i, i + 2))
            );
        }
    }
    public Libraries(Libraries libraries) {
        this.totalDayToScanBooks = libraries.getTotalDayToScanBooks();
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

    public Library getMostRecentSignupLib() {
        Library result = this.libraries.get(0);
        for (int i = 0; i < this.libraries.size(); i++) {
            if (result.getDayOfSignUp() > this.libraries.get(i).getDayOfSignUp())
                result = this.libraries.get(i);
        }
        return result;
    }

    public void removeALibrary(Library library) {
        for (int i = 0; i < this.libraries.size(); i++) {
            if (library.getId() == this.libraries.get(i).getId())
                this.libraries.remove(i);
        }
    }

    public Library getMostValuableLib() throws Exception {
        Library result =  this.libraries.get(0);
        for (int i = 1; i <  this.libraries.size(); i++) {
            if (result.getScore() < ( this.libraries.get(i).getScore()))
                result =  this.libraries.get(i);
        }
        return result;
    }

    public Library getLastSignedLibrary() {
        int lastDayOfSigneUp = Integer.MIN_VALUE;
        Library result = null;

        for (int i = 0; i < this.libraries.size(); i++) {
            if (lastDayOfSigneUp < this.libraries.get(i).getDayOfSignUp() && this.libraries.get(i).isSignedUp()) {
                result = this.libraries.get(i);
                lastDayOfSigneUp = result.getDayOfSignUp();
            }
        }
        return result;
    }

    public void updateDayOfSignup() {
        Library lastSignedupLib = this .getLastSignedLibrary();
        for (int i = 0; i < this.libraries.size(); i++) {
            if (!this.libraries.get(i).isSignedUp())
                this.libraries.get(i).setDayOfSignUp(lastSignedupLib.getDayOfSignUp() + lastSignedupLib.getNbDaysToSignup());
        }
    }
}
