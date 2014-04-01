package t02;

/**
 * Created by arsenykogan on 30/03/14.
 */
public class CarFuel {

    private final String name;
    private final String fuelType;
    private final double consumption;

    public CarFuel(final String name, final String fuelType, final double consumption) {
        this.name = name;
        this.fuelType = fuelType;
        this.consumption = consumption;
    }

    public String getName() {
        return name;
    }

    public String getFuelType() {
        return fuelType;
    }

    public double getConsumption() {
        return consumption;
    }

    @Override
    public String toString() {
        return "CarFuel{" +
                "name='" + name + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", consumption=" + consumption +
                '}';
    }
}
