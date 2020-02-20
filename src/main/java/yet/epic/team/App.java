package yet.epic.team;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


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
        InputDataSet result = new InputDataSet();

        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);

        inputDataSet = "";
        String firstLine = myReader.nextLine();
        inputDataSet += firstLine + System.lineSeparator();
        String secondLine = myReader.nextLine();
        inputDataSet += secondLine + System.lineSeparator();

        result.setNbBooks(Integer.parseInt(firstLine.split(" ")[0]));
        result.setNbLibrairies(Integer.parseInt(firstLine.split(" ")[1]));
        result.setNbOfDays(Integer.parseInt(firstLine.split(" ")[2]));

        String[] libFirstLine = new String[result.getNbLibrairies()];
        String[] libSecondLine = new String[result.getNbLibrairies()];

        for (int i = 0; myReader.hasNextLine() && i < result.getNbLibrairies(); i++) {
            libFirstLine[i] = myReader.nextLine();
            inputDataSet += libFirstLine[i] + System.lineSeparator();
            libSecondLine[i] = myReader.nextLine();
            inputDataSet += libSecondLine[i] + System.lineSeparator();
        }

        myReader.close();


        result.setBookScore(secondLine.split(" "));

        result.setLibraries(libFirstLine, libSecondLine);

        System.out.println(result);

        if (!result.toString().equals(inputDataSet))
            throw new Exception("Erreur InputDataSet creatyed different from the actual file");

        return result;
    }

    private static void writeOutputDataSet(String outputDataSet, String outputPath) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(outputPath));
            os.write(outputDataSet.getBytes(), 0, outputDataSet.length());
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
            InputDataSet inputDataSet = getInputDataSet(System.getProperty("user.dir") + inputDataSetLocation[i]);
            int daysTotal = inputDataSet.getNbOfDays();
            int daysToSignUp = 0;
            int[] dayStartScan = new int[inputDataSet.getLibraries().length];
            int[] bookDone = new int[inputDataSet.getNbBooks()];
            Arrays.fill(bookDone, 0);
            List<Library> resultLibraries = new ArrayList<>();
            List<Integer> signUpOrder = new ArrayList<>();

            for (int j = 0; j < inputDataSet.getLibraries().length; j++) {

                Library library = inputDataSet.getLibraries()[j];
                resultLibraries.add(new Library(library));
                resultLibraries.get(resultLibraries.size() - 1).setBooks(new Book[]{});
                daysToSignUp += library.getNbDaysToSignup();
                dayStartScan[library.getId()] = daysToSignUp;

                int restingDays = daysTotal - dayStartScan[library.getId()];
                while (restingDays  > 0) {
                    int bookScan = 0;
                    while (bookScan < library.getNbShipBooks()) {
                        if (library.hasNextBook()) {
                            Book nextBook = library.nextBook();
                            if (bookDone[nextBook.getId()] != 1) {
                                bookDone[nextBook.getId()] = 1;
                                bookScan++;
                                resultLibraries.get(resultLibraries.size() - 1).addABook(nextBook);
                            }
                        } else
                            break;
                    }
                    restingDays--;
                    if (!resultLibraries.get(resultLibraries.size() - 1).hasNextBook())
                        break;
                }
                if (resultLibraries.get(resultLibraries.size() - 1).getBooks().length == 0) {
                    daysToSignUp -= library.getNbDaysToSignup();
                    resultLibraries.remove(resultLibraries.size() - 1);
                }

            }
            StringBuilder finalResult = new StringBuilder();
            finalResult.append(resultLibraries.size()).append(System.lineSeparator());
            while (!resultLibraries.isEmpty()) {
                Library currentLibrary = resultLibraries.remove(0);
                finalResult.append(currentLibrary.getId()).append(" ").append(currentLibrary.getBooks().length).append(System.lineSeparator());
                currentLibrary.clearCursor();
                for (int j = 0; j < currentLibrary.getBooks().length - 1; j++) {
                    finalResult.append(currentLibrary.nextBook().getId()).append(" ");
                }
                finalResult.append(currentLibrary.nextBook().getId()).append(" ").append(System.lineSeparator());
            }
            writeOutputDataSet(finalResult.toString(), System.getProperty("user.dir") + inputDataSetLocation[i] + ".out");

        }
    }
}
