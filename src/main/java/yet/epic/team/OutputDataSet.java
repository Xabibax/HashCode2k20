package yet.epic.team;

import java.util.ArrayList;
import java.util.List;

public class OutputDataSet {

    private List<Library> libraries;

    public OutputDataSet() {
        this.libraries = new ArrayList<>();
    }

    public boolean containLib(Library library) {
        for (int i = 0; i < this.libraries.size(); i++) {
            if (this.libraries.get(i).getId() == library.getId())
                return true;
        }
        return false;
    }

    public void addALibrary(Library library) {
        this.libraries.add(new Library());
        this.libraries.get(this.libraries.size() - 1).setId(library.getId());
    }

    public void addABook(Library library, Book book) {
        if (!this.containLib(library))
            this.addALibrary(library);
        for (int i = 0; i < this.libraries.size(); i++) {
            if (this.libraries.get(i).getId() == library.getId())
                this.libraries.get(i).addABook(book);
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

    public int getFinalScore(Books books) {
        int result = 0;
        for (int i = 0; i < this.libraries.size(); i++) {
            for (int j = 0; j < this.libraries.get(i).getNbBooks(); j++) {
                result += books.getBook(this.libraries.get(i).getBooks().get(j)).getScore();
            }
        }
        return result;
    }
}
