package yet.epic.team;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


public class App 
{
    private static String[] inputDataSetLocation = {
            "/a_example.txt",
            "/b_read_on.txt",
            "/c_incunabula.txt",
            "/d_tough_choices.txt",
            "/e_so_many_books.txt",
            "/f_libraries_of_the_world.txt",
    };
    private static String inputDataSet;



    private static InputDataSet getInputDataSet(String filePath) throws Exception {

        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);

        List<String> inputData = new ArrayList<>();

        while (myReader.hasNextLine())
            inputData.add(myReader.nextLine());

        myReader.close();

        InputDataSet result = new InputDataSet(inputData);

        return result;
    }

    private static void writeOutputDataSet(String outputDataSet, String outputPath) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(outputPath));
            os.write(outputDataSet.getBytes(), 0, outputDataSet.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) throws Exception {
        for (int i = 0; i < inputDataSetLocation.length; i++) {
            System.out.println("Parsing " + inputDataSetLocation[i]);
            InputDataSet inputDataSet = getInputDataSet(System.getProperty("user.dir") + inputDataSetLocation[i]);

            Books books                 = inputDataSet.getBooks();
            Libraries libraries         = inputDataSet.getLibraries();
            OutputDataSet outputDataSet = new OutputDataSet();

            List<Book> bookNotScanned = new ArrayList<>();
            List<Book> bookScanned = new ArrayList<>();
            while (books.size() > 0) {
                Book mostValuableBook = books.getMostValuableBook();
                outputDataSet = libraries.scanABook(mostValuableBook, outputDataSet);
                if (!outputDataSet.asBeenScanned(mostValuableBook)) {
                    // System.out.println("Couldn't scan this book :" + System.lineSeparator() + mostValuableBook);
                    bookNotScanned.add(mostValuableBook);
                }
                else
                    bookScanned.add(mostValuableBook);
                books.removeABook(mostValuableBook);
                libraries.removeABookFromLibs(mostValuableBook);
            }
            int libWithScanCapacity = 0;
            for (int j = 0; j < libraries.size(); j++) {
                if (libraries.getLibrary(j).getScanCapacity() > 0)
                    libWithScanCapacity++;
            }
            int finalScore = 0;
            for (int j = 0; j < bookScanned.size(); j++) {
                finalScore += bookScanned.get(j).getScore();
            }
            int missedScore = 0;
            for (int j = 0; j < bookNotScanned.size(); j++) {
                missedScore += bookNotScanned.get(j).getScore();
            }
            System.out.println("There is " + libWithScanCapacity + " libraries still capable to scan books" + System.lineSeparator() +
                                "And there is " + bookNotScanned.size() + " books that were not scanned" + System.lineSeparator() +
                                "The predicted score for " + inputDataSetLocation[i] + " is : " + finalScore + System.lineSeparator() +
                                "This solution missed " + missedScore + " points");

            writeOutputDataSet(outputDataSet.toString(), System.getProperty("user.dir") + inputDataSetLocation[i] + ".out");

        }
    }
}
