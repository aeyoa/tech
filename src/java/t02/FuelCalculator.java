package t02;

/**
 * Created by arsenykogan on 31/03/14.
 */
public class FuelCalculator {

    private CarFuel carFuel;
    private double cost;
    private double annualRun;

    public FuelCalculator() {

    }

    /* Setters */
    public void setCarFuel(final CarFuel carFuel) {
        this.carFuel = carFuel;
    }

    public void setCost(final String cost) {
        try {
            this.cost = Double.parseDouble(cost);
        } catch (NumberFormatException e) {
            new PopUp().showPopUp("Цена на топливо введена неправильно");
        }
    }

    public void setAnnualRun(final String annualRun) {
        try {
            this.annualRun = Double.parseDouble(annualRun);
        } catch (NumberFormatException e) {
            new PopUp().showPopUp("Годовой пробег введен неправильно");
        }
    }

    /* Getters */
    public CarFuel getCarFuel() {
        return carFuel;
    }

    public double getCost() {
        return cost;
    }

    public double getAnnualRun() {
        return annualRun;
    }

    /* Returns the final annual price of fuel */
    public double calculate() {
        return annualRun * cost * carFuel.getConsumption() / 100;
    }
}
