package yet.epic.team;


import java.io.File;
import java.util.Scanner;


public class App 
{
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
        getInputDataSet(System.getProperty("user.dir") + "\\a_example.txt");
    }
}
