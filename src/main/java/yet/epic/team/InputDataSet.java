package yet.epic.team;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public class InputDataSet {

    private int nbOfDays;
    private Books books;
    private Libraries libraries;

    public InputDataSet(@NotNull List<String> inputData) throws Exception {
        String[] firstLine = inputData.get(0).split(" ");
        int nbBooks = Integer.parseInt(firstLine[0]);
        int nbLibraries = Integer.parseInt(firstLine[1]);
        this.nbOfDays = Integer.parseInt(firstLine[2]);
        this.books = new Books(inputData.get(1));
        this.libraries = new Libraries(this.nbOfDays, this.books, inputData.subList(2, inputData.size()));
        if (nbBooks != this.books.size() || nbLibraries != this.libraries.size())
            throw new Exception("There a difference between Input file and intern variables");
    }

    public int getNbBooks() {
        return this.books.size();
    }

    public int getNbLibrairies() {
        return this.libraries.size();
    }

    public int getNbOfDays() {
        return nbOfDays;
    }

    public void setNbOfDays(int nbOfDays) {
        this.nbOfDays = nbOfDays;
    }

    public Books getBooks() {
        return books;
    }

    public Libraries getLibraries() {
        return libraries;
    }

    @NotNull
    @Override
    public String toString() {
        String result = this.getNbBooks() + " " + this.getNbLibrairies() + " " + this.getNbOfDays() +
                System.lineSeparator();
        for (int i = 0; i < this.getBooks().size() - 1; i++) {
            result += this.getBooks().getBook(i).getScore() + " ";
        }
        result += this.getBooks().getBook(this.getNbBooks() - 1).getScore() + System.lineSeparator();
        for (int i = 0; i < this.getLibraries().size() - 1; i++) {
            result += this.getLibraries().getLibrary(i) + System.lineSeparator();
        }
        result += this.getLibraries().getLibrary(this.getNbLibrairies() - 1) + System.lineSeparator();
        return result;
    }
}
