package yet.epic.team;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int id;
    private int score;

    private List<Library> libraries;

    public Book() {
        this.id = 0;
        this.score = 0;
        this.libraries = new ArrayList<>();
    }
    public Book(int id, int score) {
        this.id = id;
        this.score = score;
        this.libraries = new ArrayList<>();
    }
    public Book(int id, String score) {
        this(id, Integer.parseInt(score));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addALibrary(Library library) {
        this.libraries.add(library);
    }

    public boolean isContainedBy(Library library) {
        return this.libraries.contains(library);
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public Library getMostRecentSignupLib() {
        Library result = this.libraries.get(0);
        for (int i = 1; i < this.libraries.size(); i++) {
            if (result.getDayOfSignUp() > this.libraries.get(i).getDayOfSignUp())
                result = this.libraries.get(i);
        }
        return result;
    }

    public Library getMostRestDaylib() throws Exception {
        Library result = this.libraries.get(0);
        for (int i = 1; i <  this.libraries.size(); i++) {
            if (result.getRestingDays() <  this.libraries.get(i).getRestingDays())
                result =  this.libraries.get(i);
        }
        return result;
    }

    public Library getMostRestDayAndMostShiplib() throws Exception {
        Library result = this.libraries.get(0);
        for (int i = 1; i <  this.libraries.size(); i++) {
            if (result.getRestingDays() <  this.libraries.get(i).getRestingDays())
                if (result.getNbShipBooks() < this.libraries.get(i).getNbShipBooks())
                    result =  this.libraries.get(i);
        }
        return result;
    }

    public Library getMostScanCapaLib() throws Exception {
        Library result = this.libraries.get(0);
        long scanCapa = result.getScanCapacity();
        for (int i = 1; i <  this.libraries.size(); i++) {
            long currentLibScanCapa = this.libraries.get(i).getScanCapacity();
            if (scanCapa <  currentLibScanCapa) {
                result =  this.libraries.get(i);
                scanCapa = currentLibScanCapa;
            }
        }
        return result;
    }

    public Library getFewerSignupDays() {
        Library result =  this.libraries.get(0);
        for (int i = 1; i <  this.libraries.size(); i++) {
            if (result.getNbDaysToSignup() >  this.libraries.get(i).getNbDaysToSignup())
                result =  this.libraries.get(i);
        }
        return result;
    }

    public Library getMostValuableLib() throws Exception {
        Library result =  this.libraries.get(0);
        for (int i = 1; i <  this.libraries.size(); i++) {
            if (result.getScore() < ( this.libraries.get(i).getScore()))
                result =  this.libraries.get(i);
        }
        return result;
    }

    public Library getBestLib() throws Exception {
        //return getMostValuableLib();
        //return getFewerSignupDays();
        //return getMostRestDaylib();
        //return getMostRestDayAndMostShiplib();
        return getMostScanCapaLib();
    }

    public void scanBook() throws Exception {
        if (this.libraries.size() > 0) {
            Library bestCapaLib = getBestLib();
            bestCapaLib.scanABook(this);
        }
    }

    @Override
    public String toString() {
        String containedBy = "";
        for (Library library : this.libraries) {
            containedBy += library.getId() + " ";
        }
        return  "Book id : " + this.getId()  + System.lineSeparator() +
                "Score : " + this.getScore() + System.lineSeparator() +
                "Contained by : " + containedBy;
    }
    public String toDataSet() {
        return String.valueOf(this.getId());
    }
}
