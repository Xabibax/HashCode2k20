package yet.epic.team;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Books {
    private List<Book> books;

    public Books(@NotNull String inputData) {
        String[] books = inputData.split(" ");
        this.books = new ArrayList<>();
        for (int i = 0; i < books.length; i++) {
            this.books.add(new Book(i, books[i]));
        }
    }

    public Books(@NotNull Books books) {
        this.books = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            this.books.add(books.getBook(i));
        }
    }

    public void addABook(Book book) {
        this.books.add(book);
    }

    public int size() {
        return this.books.size();
    }

    public Book removeABook(@NotNull Book book) throws Exception {
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).getId() == book.getId())
                return this.books.remove(i);
        }
        throw new Exception("ERROR : Try to remove an Uncontained book.");
    }

    public Book getMostValuableBook() {
        Book result = this.books.get(0);
        for (int i = 1; i < this.books.size(); i++) {
            if (result.getScore() < this.books.get(i).getScore())
                result = this.books.get(i);
        }
        return result;
    }

    public Book getBook(Integer i) {
        return this.books.get(i);
    }

    public Book getBookById(Integer i) throws Exception {
        for (int j = 0; j < this.books.size(); j++) {
            if (this.books.get(j).getId() == i)
                return this.books.get(j);
        }
        throw new Exception("This book id is unknown");
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void addLibToBook(@NotNull Book book, Library library) {
        this.books.get(book.getId()).addALibrary(library);
    }

}
