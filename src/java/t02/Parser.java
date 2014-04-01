package t02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by arsenykogan on 30/03/14.
 */
public class Parser {

    private final String filename;
    private final Scanner scanner;
    private final List<CarFuel> carFuelList;

    public Parser(final String filename) throws FileNotFoundException {
        this.filename = filename;
        this.scanner = new Scanner(new File(filename));
        carFuelList = new ArrayList<CarFuel>();
    }

    public List<CarFuel> parseData() {

        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            final String parts[] = line.split("\\t");
            carFuelList.add(new CarFuel(parts[2], parts[0], Double.parseDouble(parts[1])));
        }

        return carFuelList;
    }

}
