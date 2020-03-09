package yet.epic.team;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class App {
    @NotNull
    private static String[] inputDataSetLocation = {
            "/a_example.txt",
            "/b_read_on.txt",
            "/c_incunabula.txt",
            "/d_tough_choices.txt",
            "/e_so_many_books.txt",
            "/f_libraries_of_the_world.txt",
    };

    private static Pair<Integer, Integer> interval = new Pair(-1,1);
    private static List<Integer> intervalValues = new ArrayList<>();

    public static int noise;

    public static Iterator<Integer> noiseIterator;

    private static List<OutputDataSet> outputdataSets = new ArrayList<>();

    @NotNull
    private static InputDataSet getInputDataSet(@NotNull String filePath) throws Exception {

        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);

        List<String> inputData = new ArrayList<>();

        while (myReader.hasNextLine())
            inputData.add(myReader.nextLine());

        myReader.close();

        InputDataSet result = new InputDataSet(inputData);

        return result;
    }

    private static void writeOutputDataSet(@NotNull String outputDataSet, @NotNull String outputPath) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(outputPath));
            os.write(outputDataSet.getBytes(), 0, outputDataSet.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static OutputDataSet getBestOutput() throws Exception {
        if (outputdataSets.size() > 0) {
            OutputDataSet result = outputdataSets.get(0);
            for (int i = 1; i < outputdataSets.size(); i++) {
                if (result.getScore() < outputdataSets.get(i).getScore())
                    result = outputdataSets.get(i);
            }
            return result;
        }
        throw new Exception("No output to evaluate");
    }

    public static void initialiseNoise() {
        if (intervalValues.size() == 0) {
            for (int i = interval.getKey(); i <= interval.getValue(); i++) {
                intervalValues.add(i);
            }
        }
        noiseIterator = new Iterator<Integer>() {
            public int cursor = -1;

            @Override
            public boolean hasNext() {
                return cursor < intervalValues.size() - 1 ;
            }

            @Override
            public Integer next() {
                noise = intervalValues.get(++cursor);
                return noise;
            }
        };
        noise = intervalValues.get(0);
    }

    public static void main(String[] args) throws Exception  {
        for (int i = 0; i < inputDataSetLocation.length; i++) {
            initialiseNoise();
            OutputDataSet outputDataSet;
            do {
                InputDataSet inputDataSet = getInputDataSet(System.getProperty("user.dir") +
                        inputDataSetLocation[i]);

                noiseIterator.next();
                Libraries libraries = new Libraries(inputDataSet.getLibraries());
                outputDataSet = new OutputDataSet();
                while (libraries.size() > 0) {
                    Library library = libraries.getWorthyLib();
                    while (library.getScanCapacity() > 0 && library.getBooks().size() > 0) {
                        Book book = library.getMostValuableBook();
                        library.scanABook(book);
                        libraries.removeABookFromLibs(book);
                    }
                    libraries.updateDayOfSignup();
                    if (library.getScannedBooks().size() > 0)
                        outputDataSet.addALibrary(library);
                    libraries.removeALibrary(library);
                }

                outputdataSets.add(new OutputDataSet(outputDataSet));

            } while (noiseIterator.hasNext());

            outputDataSet = getBestOutput();
            outputdataSets = new ArrayList<>();

            System.out.println("The predicted score for " + inputDataSetLocation[i] + " is : \t\t\t\t" +
                    NumberFormat.getIntegerInstance().format(outputDataSet.getScore()));

            writeOutputDataSet(outputDataSet.toDataSet(), System.getProperty("user.dir") +
                    inputDataSetLocation[i] + ".out");
            writeOutputDataSet(outputDataSet.toDebug(), System.getProperty("user.dir") +
                    inputDataSetLocation[i] + ".debug");
        }
    }
}
