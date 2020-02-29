package yet.epic.team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Books {
    private List<Book> books;

    public Books(String inputData) {
        String[] books = inputData.split(" ");
        this.books = new ArrayList<>();
        for (int i = 0; i < books.length; i++) {
            this.books.add(new Book(i, books[i]));
        }
    }

    public void addABook(Book book) {
        this.books.add(book);
    }

    public int size() {
        return this.books.size();
    }

    public Book removeABook(Book book) throws Exception {
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).getId() ==book.getId())
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

    public void addLibToBook(Book book, Library library) {
        this.books.get(book.getId()).addALibrary(library);
    }

}
