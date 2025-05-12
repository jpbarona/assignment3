import java.util.ArrayList;

public class SeaVehicle extends Vehicle{
    public static final int DEFAULT_SEA_VEHICLE_ID = -10001;
    public static final String SAILS_STRING = "sails";
    public int vehicleId = DEFAULT_SEA_VEHICLE_ID;

    public static final String COLOR_STRING = "color";
    public static final String COST_STRING = "cost";
    public static final String ENGINE_STRING = "engine";
    public static final String MANUFACTURER_STRING = "manufacturer";
    public static final String TYPE_STRING = "type";
    public static final String VALID_STRING = "valid";

    public String[] color;
    public String[] engine;
    public String[] manufacturer;
    public String[] type;
    public String[] sails;

    public boolean valid = false;
    public boolean repeatedField = false;
    public int cost = 0;


    public SeaVehicle(ArrayList<String[]> keyValuesList) {
        for (String[] keyValueTuple : keyValuesList) {
            String key = keyValueTuple[0].strip();
            String valueString = keyValueTuple[1].strip();

            switch(key) {
                case VEHICLE_ID_STRING:
                    if (this.vehicleId == DEFAULT_SEA_VEHICLE_ID) {this.vehicleId = Integer.parseInt(valueString);}
                    else {this.repeatedField = true;}
                    break;
                case COLOR_STRING:
                    if (this.color == null) {this.color = parseValue(valueString);}
                    else {this.repeatedField = true;}
                    break;
                case MANUFACTURER_STRING:
                    if (this.manufacturer == null) {this.manufacturer = parseValue(valueString);}
                    else {this.repeatedField = true;}
                    break;
                case TYPE_STRING:
                    if (this.type == null) {this.type = parseValue(valueString);}
                    else {this.repeatedField = true;}
                    break;
                case ENGINE_STRING:
                    if (this.engine == null) {this.engine = parseValue(valueString);}
                    else {this.repeatedField = true;}
                    break;
                case SAILS_STRING:
                    if (this.sails == null) {
                        String[] sailsListRaw = parseValue(valueString);
                        this.sails = parseSails(sailsListRaw);
                    }
                    else {this.repeatedField = true;}
                    break;
            }
        }
        this.valid = SeaVehicleValidator.isValid(this) && !this.repeatedField;
        this.cost = SeaVehicleCost.computeCost(this);
    }
    public static String[] parseSails(String[] sailsList) {
        if (sailsList.length < 2) {return sailsList;}
        String[] parsedSails = new String[sailsList.length];
        parsedSails = sailsList;
        String numberOfSailsString = sailsList[2];
        int numberOfSailsInt = Integer.parseInt(numberOfSailsString);
        String numberOfSailsParsed = String.valueOf(numberOfSailsInt);
        parsedSails[2] = numberOfSailsParsed;
        return parsedSails;
    }

    @Override
    public void printVehicle() {
        String idString = String.valueOf(vehicleId);
        String vehicleIdLineString = VEHICLE_ID_STRING + KEY_VALUE_SEPARATOR + SPACE + idString;
        System.out.println(vehicleIdLineString);
        String[] validList = { String.valueOf(valid)};
        String[] costList = { String.valueOf(cost)};
        printLineString(COLOR_STRING, this.color);
        printLineString(COST_STRING, costList);
        if (this.engine != null) {printLineString(ENGINE_STRING, this.engine);}
        printLineString(MANUFACTURER_STRING, this.manufacturer);
        if (this.sails != null) {printLineString(SAILS_STRING, sails);}
        printLineString(TYPE_STRING, this.type);
        printLineString(VALID_STRING, validList);
    }

    @Override
    public int getVehicleId() {
        return this.vehicleId;
    }
}
