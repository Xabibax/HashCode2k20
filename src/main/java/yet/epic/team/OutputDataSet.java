package yet.epic.team;

import java.util.ArrayList;
import java.util.List;

public class OutputDataSet {

    private List<Library> libraries;
    private Books books;

    public OutputDataSet(Books books) {
        this.books = new Books(books);
        this.libraries = new ArrayList<>();
    }

    public boolean containLib(Library library) {
        for (int i = 0; i < this.libraries.size(); i++) {
            if (this.libraries.get(i).getId() == library.getId())
                return true;
        }
        return false;
    }
    public boolean containBook(Book book) {
        for (int i = 0; i < this.libraries.size(); i++) {
            for (int j = 0; j < this.libraries.get(i).getNbBooks(); j++) {
                if (this.libraries.get(i).getBooks().get(j) == book.getId())
                    return true;
            }
        }
        return false;
    }

    public void addALibrary(Library library) throws Exception {
        if (!this.containLib(library)) {
            this.libraries.add(library);
        }
    }

    public void addABook(Library library, Book book) throws Exception {
        if (!this.containLib(library))
            this.addALibrary(library);
        for (int i = 0; i < this.libraries.size(); i++) {
            if (this.libraries.get(i).getId() == library.getId()) {
                this.libraries.get(i).addABook(book);
                this.libraries.get(i).scanABook();
            }
        }
    }

    public boolean asBeenScanned(Book book) {
        for (int i = 0; i < this.libraries.size(); i++) {
            for (int j = 0; j < this.libraries.get(i).getNbBooks(); j++) {
                if (this.libraries.get(i).getBooks().get(j) == book.getId())
                    return true;
            }
        }
        return false;
    }

    public List<Library> getLibraries() {
        return this.libraries;
    }

    public int getScore() throws Exception {
        int result = 0;
        for (int i = 0; i < this.libraries.size(); i++) {
            for (int j = 0; j < this.libraries.get(i).getScannedBooks().size(); j++) {
                result += this.books.getBookById(this.libraries.get(i).getScannedBooks().get(j)).getScore();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        // First Line //
        result +=  this.libraries.size() + System.lineSeparator();
        // Libraries lines //
        for (int i = 0; i < this.libraries.size(); i++) {
            // First library line
            result += this.libraries.get(i).getId() +  " " + this.libraries.get(i).getNbBooks() + System.lineSeparator();
            // Second library line
            result += this.libraries.get(i).toDataSetBooks() + System.lineSeparator();
        }
        return result;
    }
    public String toDataSet() {
        String result = "";
        // First Line //
        result +=  this.libraries.size() + System.lineSeparator();
        // Libraries lines //
        for (int i = 0; i < this.libraries.size(); i++) {
            // First library line
            result += this.libraries.get(i).getId() +  " " + this.libraries.get(i).getScannedBooks().size() + System.lineSeparator();
            // Second library line
            result += this.libraries.get(i).toDataSetBooksBis() + System.lineSeparator();
        }
        return result;
    }

    public String toDebug() throws Exception {
        String result = "";
        // First Line //
        result += "Number of Libraries : " + this.libraries.size() + System.lineSeparator();
        // Libraries lines //
        for (int i = 0; i < this.libraries.size(); i++) {

            // First library line
            result += this.libraries.get(i).toDebug();
        }
        return result;

    }
}
