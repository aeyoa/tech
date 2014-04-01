package t02;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arsenykogan on 31/03/14.
 */
public class FuelUI {

    private static FuelCalculator fuelCalculator = new FuelCalculator();

    public FuelUI() {
        fuelCalculator = new FuelCalculator();
    }


    public static void main(String[] args) throws FileNotFoundException {

        /* Parse the list of the cars and fuels here.
        * Create the list of known CarFuels. */
        final Parser parser = new Parser("/Users/arsenykogan/Documents/github/tech/src/java/t02/data.txt");
        final List<CarFuel> carFuelList = parser.parseData();

        /* Create string list with all car names. */
        final List<String> carNamesList = new ArrayList<String>();
        for (CarFuel carFuel : carFuelList) {
            carNamesList.add(carFuel.getName());
        }
        final String[] carNames = carNamesList.toArray(new String[]{});

        /* Initialize FuelCalculator with first CarFuel in the list. */
        fuelCalculator.setCarFuel(carFuelList.get(0));

        /* Create simple window here */
        final JFrame window = new JFrame("Converter"); // Set title to converter
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close button closes app
        window.setLocationRelativeTo(null); // Set position of the window to the center of screen
        window.setResizable(false); // Make window not resizable


        final GridBagLayout gridBagLayout = new GridBagLayout();

//        final JPanel contentPanel = new JPanel(new GridBagLayout());
        JPanel contentPanel = new JPanel(new GridLayout(4, 3));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5)); // Add borders
        window.setContentPane(contentPanel);



        /* LABEL: CAR NAME */
        window.add(new JLabel("Марка автомобиля: ") {
            {
                setHorizontalAlignment(RIGHT);
            }
        });

        /* COMBO BOX: SELECTOR OF CAR NAME*/
        final JComboBox<String> carNamesComboBox = new JComboBox<String>(carNames);
        window.add(carNamesComboBox);

        /* EMPTY LABEL */
        window.add(new JLabel());

        /* LABEL: NAME OF FUEL*/
        final JLabel fuelTypeLable = new JLabel("Цена на " + commonFormatOfFuel(fuelCalculator.getCarFuel()));
        fuelTypeLable.setHorizontalAlignment(JLabel.RIGHT);
        window.add(fuelTypeLable);

        /* TEXT FIELD: FUEL PRICE */
        final JTextField fuelPriceField = new JTextField();
        window.add(fuelPriceField);

        /* LABEL: FUEL PRICE UNITS */
        window.add(new JLabel("руб/л"));

        /* LABEL: ANNUAL RUN */
        window.add(new JLabel("Годовой пробег") {
            {
                setHorizontalAlignment(RIGHT);
            }
        });

        /* TEXT FIELD: ANNUAL RUN */
        final JTextField annualRunField = new JTextField();
        window.add(annualRunField);

        /* LABEL: UNITS */
        window.add(new JLabel("км"));

        /* BUTTON: CALCULATE */
        final JButton calculateButton = new JButton("Рассчитать стоимость");
        window.add(calculateButton);

        /* LABEL: FINAL PRICE */
        final JLabel finalPriceLabel = new JLabel("0 руб.");
        window.add(finalPriceLabel);

        /* EMPTY LABEL */
        window.add(new JLabel(""));


        /* Add action listeners. */
        carNamesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                for (CarFuel carFuel : carFuelList) {
                    if (carFuel.getName().equals(carNamesComboBox.getSelectedItem())) {
                        fuelCalculator.setCarFuel(carFuel);
                    }
                }
                fuelTypeLable.setText("Цена на " + commonFormatOfFuel(fuelCalculator.getCarFuel()));
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                fuelCalculator.setAnnualRun(annualRunField.getText());
                fuelCalculator.setCost(fuelPriceField.getText());
                finalPriceLabel.setText(fuelCalculator.calculate() + "");
            }
        });


        /* Fit window size to it's content. */
        window.pack();
        /* Show! */
        window.setVisible(true);

    }

    /* Returns the name of the fuel in the common form. */
    private static String commonFormatOfFuel(final CarFuel carFuel) {
        /* HashMap with know types of fuel and their names. */
        final HashMap<String, String> fuelNames = new HashMap<String, String>() {
            {
                /* Add some fuel types here. */
                put("ДТ", "дизельное топливо");
                put("95", "95-ый бензин");
                put("98", "98-ый бензин");
            }
        };

        return fuelNames.get(carFuel.getFuelType());
    }
}
