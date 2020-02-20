package yet.epic.team;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


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

        for (int i = 0; myReader.hasNextLine(); i++) {
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

    public static void main( String[] args ) throws Exception {
        for (int i = 0; i < inputDataSetLocation.length; i++) {
            InputDataSet inputDataSet = getInputDataSet(System.getProperty("user.dir") + inputDataSetLocation);
            int daysTotal = inputDataSet.getNbOfDays();
            int daysToSignUp = 0;
            int[] dayStartScan = new int[inputDataSet.getLibraries().length];
            int[] bookDone = new int[inputDataSet.getNbBooks()];
            Arrays.fill(bookDone, 0);

            for (int j = 0; j < inputDataSet.getLibraries().length; j++) {
                Library library = inputDataSet.getLibraries()[i];
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
                            }
                        } else
                            break;
                    }
                    if (! library.hasNextBook())
                        break;
                }
            }
        }
    }
}
