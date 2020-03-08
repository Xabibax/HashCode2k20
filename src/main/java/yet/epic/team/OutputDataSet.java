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
    public boolean containBook(Book book) {
        for (int i = 0; i < this.libraries.size(); i++) {
            for (int j = 0; j < this.libraries.get(i).getBooks().size(); j++) {
                if (this.libraries.get(i).getBooks().get(j).getId() == book.getId())
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

    public List<Library> getLibraries() {
        return this.libraries;
    }

    public int getScore() throws Exception {
        int result = 0;
        for (Library library : this.libraries) {
            result += library.getScanScore();
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
            result += this.libraries.get(i).getId() +  " " + this.libraries.get(i).getBooks().size() + System.lineSeparator();
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
        for (Library library : this.libraries) {

            // First library line
            result += library.toDebug();
        }
        return result;

    }
}
