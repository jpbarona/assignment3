import java.util.ArrayList;
import java.util.Arrays;

public class MotorVehicle extends Vehicle{

    public static final int DEFAULT_VEHICLE_ID = -100000;

    public static final String COLOR_STRING = "color";
    public static final String COST_STRING = "cost";
    public static final String ENGINE_STRING = "engine";
    public static final String MANUFACTURER_STRING = "manufacturer";
    public static final String TYPE_STRING = "type";
    public static final String VALID_STRING = "valid";
    public static final String WHEELS_STRING = "wheels";


    public int vehicleId = DEFAULT_VEHICLE_ID;
    public String[] color;
    public String[] engine;
    public String[] manufacturer;
    public String[] type;
    public String[] wheels;

    private boolean valid = false;
    private int cost = 0;


    public MotorVehicle(ArrayList<String[]> keyValuesList) {
        for (String[] keyValueTuple : keyValuesList) {
            String key = keyValueTuple[0].strip();
            String valueString = keyValueTuple[1].strip();

            switch(key) {
                case VEHICLE_ID_STRING:
                    if (this.vehicleId == DEFAULT_VEHICLE_ID) {this.vehicleId = Integer.parseInt(valueString);}
                    break;
                case COLOR_STRING:
                    String[] colorList = parseValue(valueString);
                    if (this.color == null) {this.color = colorList;}
                    break;
                case ENGINE_STRING:
                    String[] engineList = parseValue(valueString);
                    if (this.engine == null) {this.engine = engineList;}
                    break;
                case MANUFACTURER_STRING:
                    String[] manufacturerList = parseValue(valueString);
                    if (this.manufacturer == null) {this.manufacturer = manufacturerList;}
                    break;
                case TYPE_STRING:
                    String[] typeList = parseValue(valueString);
                    if (this.type == null) {this.type = typeList;}
                    break;
                case WHEELS_STRING:
                    String[] wheelsListRaw = parseValue(valueString);
                    String[] wheelsList = parseWheelsList(wheelsListRaw);
                    if (this.wheels == null) {this.wheels = wheelsList;}
                    break;
            }
        }
        this.valid = MotorVehicleValidator.isValid(this);
        this.cost  = MotorVehicleCost.computeVehicleCost(this);
    }

    public MotorVehicle() {}

    private static String[] parseWheelsList(String[] wheelsList) {
        String numberOfWheelsStringRaw = wheelsList[2].strip().toLowerCase();
        int numberOfWheels = Integer.parseInt(numberOfWheelsStringRaw);
        String numberOfWheelsStringParsed = String.format("%d", numberOfWheels);
        wheelsList[2] = numberOfWheelsStringParsed;
        return wheelsList;
    }

    @Override
    public void printVehicle() {
        String idString = String.valueOf(vehicleId);
        String vehicleIdLineString = VEHICLE_ID_STRING + KEY_VALUE_SEPARATOR + SPACE + idString;
        System.out.println(vehicleIdLineString);
        String[] validList = { String.valueOf(valid)};
        String[] costList = { String.valueOf(cost)};

        printLineString(COLOR_STRING, color);
        printLineString(COST_STRING, costList);
        printLineString(ENGINE_STRING, engine);
        printLineString(MANUFACTURER_STRING, manufacturer);
        printLineString(TYPE_STRING, type);
        printLineString(VALID_STRING,validList);
        printLineString(WHEELS_STRING, wheels);
    }


    @Override
    public int getVehicleId() {
        return this.vehicleId;
    }



    public String[] getColor() {
        return color;
    }

    public String[] getEngine() {
        return engine;
    }

    public String[] getManufacturer() {
        return manufacturer;
    }

    public String[] getType() {
        return type;
    }

    public String[] getWheels() {
        return wheels;
    }

    public static void main(String[] args) {}
}
