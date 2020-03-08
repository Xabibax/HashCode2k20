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
        System.out.println(1/2);
        System.out.println("Int max value : " + Integer.MAX_VALUE + System.lineSeparator()
                + "Long max value : " + Long.MAX_VALUE);
        for (int i = 0; i < inputDataSetLocation.length; i++) {

            switch (i) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
            System.out.println("Parsing " + inputDataSetLocation[i]);
            InputDataSet inputDataSet = getInputDataSet(System.getProperty("user.dir") +
                    inputDataSetLocation[i]);

            int maxScore = getScore(inputDataSet.getBooks().getBooks());

            Books books                 = new Books(inputDataSet.getBooks());
            Libraries libraries         = inputDataSet.getLibraries();
            OutputDataSet outputDataSet = new OutputDataSet();

            //System.out.print("Remaining books : ");

            while (libraries.size() > 0) {
                Library library = libraries.getWorthyLib();
                while ( library.getScanCapacity() > 0 && library.getBooks().size() > 0) {
                    Book book = library.getMostValuableBook();
                    library.scanABook(book);
                    libraries.removeABookFromLibs(book);
                }
                libraries.updateDayOfSignup();
                if (library.getScannedBooks().size() > 0)
                    outputDataSet.addALibrary(library);
                libraries.removeALibrary(library);
            }

            System.out.println("The predicted score for " + inputDataSetLocation[i] + " is : " +
                    NumberFormat.getIntegerInstance().format(outputDataSet.getScore()) + System.lineSeparator());


            writeOutputDataSet(outputDataSet.toDataSet(), System.getProperty("user.dir") +
                    inputDataSetLocation[i] + ".out");
            writeOutputDataSet(outputDataSet.toDebug(), System.getProperty("user.dir") +
                    inputDataSetLocation[i] + ".debug");

        }
    }
}
