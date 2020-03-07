package yet.epic.team;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
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

    public static int getScore(List<Book> books) {
        int result = 0;
        for (int j = 0; j < books.size(); j++) {
            result += (books.get(j).getScore());
        }
        return result;
    }

    public static void main( String[] args ) throws Exception {
        for (int i = 0; i < inputDataSetLocation.length; i++) {

            System.out.println("Parsing " + inputDataSetLocation[i]);
            InputDataSet inputDataSet = getInputDataSet(System.getProperty("user.dir") + inputDataSetLocation[i]);

            int maxScore = getScore(inputDataSet.getBooks().getBooks());

            Books books                 = new Books(inputDataSet.getBooks());
            Libraries libraries         = inputDataSet.getLibraries();
            OutputDataSet outputDataSet = new OutputDataSet(inputDataSet.getBooks());

            List<Book> bookNotScanned = new ArrayList<>();
            List<Book> bookScanned = new ArrayList<>();
            System.out.print("Remaining books : ");

            while (books.size() > 0) {
                Book mostValuableBook = books.getMostValuableBook();
                System.out.print(books.size());
                outputDataSet = libraries.scanABook(mostValuableBook, books, outputDataSet);
                if (!outputDataSet.asBeenScanned(mostValuableBook)) {
                    // System.out.println("Couldn't scan this book :" + System.lineSeparator() + mostValuableBook);
                    bookNotScanned.add(mostValuableBook);
                }
                else
                    bookScanned.add(mostValuableBook);
                books.removeABook(mostValuableBook);
                libraries.removeABookFromLibs(mostValuableBook);
            }


            int libWithRestingDays = 0;
            for (int j = 0; j < libraries.size(); j++) {
                if (libraries.getLibrary(j).getRestingDays() > 0)
                    libWithRestingDays++;
            }
            System.out.println("There is " + libWithRestingDays + " libraries still capable to scan books" + System.lineSeparator() +
                                "And there is " + bookNotScanned.size() + " books that were not scanned" + System.lineSeparator() +
                                "The predicted score for " + inputDataSetLocation[i] + " is : " + NumberFormat.getIntegerInstance().format(outputDataSet.getScore()) + System.lineSeparator() +
                                "This solution missed " + NumberFormat.getIntegerInstance().format(getScore(bookNotScanned)) + " points" + System.lineSeparator() +
                                "If all books were scanned the solution would have scored " + NumberFormat.getIntegerInstance().format(maxScore));
//            System.out.println("There were " + (bookNotScanned.size() + bookScanned.size()) + " books to scan");
//            for (int j = 0; j < outputDataSet.getLibraries().size(); j++) {
//                for (int k = 0; k < libraries.size(); k++) {
//                    if (libraries.getLibrary(k).getId() == outputDataSet.getLibraries().get(j).getId()) {
//                        System.out.println("The library " + libraries.getLibrary(k).getId() + " has signed up on day " +
//                                libraries.getLibrary(k).getDayOfSignUp() + " and so had " +
//                                (libraries.getTotalDayToScanBooks() -
//                                        (libraries.getLibrary(k).getDayOfSignUp() + libraries.getLibrary(k).getNbDaysToSignup())) + " days to scan its books");
//                        System.out.println("The library can scan " + libraries.getLibrary(k).getNbShipBooks() + " books by days ");
//                        System.out.println("It begin with " +
//                                ((libraries.getTotalDayToScanBooks() -
//                                        (libraries.getLibrary(k).getDayOfSignUp() + libraries.getLibrary(k).getNbDaysToSignup())) *
//                                                libraries.getLibrary(k).getNbShipBooks())  +
//                                " scan capacity and end up with " +  libraries.getLibrary(k).getScanCapacity());
//                        System.out.println("The library scanned " + outputDataSet.getLibraries().get(j).getBooks().size() + " books");
//                    }
//                }
//            }

            writeOutputDataSet(outputDataSet.toString(), System.getProperty("user.dir") + inputDataSetLocation[i] + ".out");
            writeOutputDataSet(outputDataSet.toDebug(), System.getProperty("user.dir") + inputDataSetLocation[i] + ".debug");

        }
    }
}
