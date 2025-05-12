import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class EVEMain {
    private static final String SPACE = " ";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final String VALUES_SEPARATOR = ",";

    private static final int KV_LIST_DEFAULT_SIZE = 5;
    public static final String TYPE = "type";

    public static final String[] MOTOR_VEHICLE_TYPES = {"car", "truck", "motorcycle"};
    public static final String[] SEA_VEHICLE_TYPES = {"sailing vessel", "powered vessel"};


    private static void processLineFeed() {
            Scanner reader = new Scanner(System.in);
            boolean justReceivedEntry = false;
            ArrayList<String[]> keyValuesList = new ArrayList<>(KV_LIST_DEFAULT_SIZE);
            ArrayList<Vehicle> vehiclesList = new ArrayList<>();

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isBlank()) {
                    if (justReceivedEntry)
                    {
                        addVehicle(keyValuesList, vehiclesList);
                        justReceivedEntry = false;
                    }
                    continue;
                }
                if(!lineIsValid(line)) {continue;}
                addKeyValueToList(line, keyValuesList);
                justReceivedEntry = true;
        }
        if (justReceivedEntry) {
            addVehicle(keyValuesList, vehiclesList);
        }
        vehiclesList.sort(Comparator.comparingInt(Vehicle::getVehicleId));
        printAllVehicles(vehiclesList);
    }

    private static void printAllVehicles(ArrayList<Vehicle> vehiclesList) {
        int lastIndex = vehiclesList.size()-1;
        for (int i = 0; i < vehiclesList.size(); i++) {
            Vehicle motorVehicle = vehiclesList.get(i);
            motorVehicle.printVehicle();
            if (i != lastIndex) {System.out.println();}
        }
    }

    private static void addVehicle(ArrayList<String[]> keyValuesList, ArrayList<Vehicle> vehiclesList) {
        for (String[] keyValueTuple : keyValuesList) {
            String key = keyValueTuple[0].strip().toLowerCase();
            String valueStringRaw = keyValueTuple[1];
            if (key.equals(TYPE)) {
                String type = valueStringRaw.split(VALUES_SEPARATOR)[0].strip().toLowerCase();
                if (arrayContains(type, MOTOR_VEHICLE_TYPES)) {
                    Vehicle vehicle = new MotorVehicle(keyValuesList);
                    vehiclesList.add(vehicle);
                    keyValuesList.clear();
                    return;
                }
                if (arrayContains(type, SEA_VEHICLE_TYPES)) {
                    Vehicle vehicle = new SeaVehicle(keyValuesList);
                    vehiclesList.add(vehicle);
                    keyValuesList.clear();
                    return;
                }
            }
        }
    }

    private static boolean arrayContains(Object target, Object[] array) {
        boolean contains = false;
        for (Object x : array) {
            if (x.equals(target)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    private static void addKeyValueToList(String line, ArrayList<String[]> keyValuesList) {
        String[] splitLine = line.split(KEY_VALUE_SEPARATOR);
        String key = splitLine[0];
        String keyString = key.strip();

        String value = splitLine[1];
        String valuesString = createValuesString(value);

        String[] keyValueTuple = {keyString, valuesString};
        keyValuesList.add(keyValueTuple);
    }

    private static boolean lineIsValid(String line) {
        boolean valid = true;
        valid &= line.contains(KEY_VALUE_SEPARATOR);
        valid &= line.split(KEY_VALUE_SEPARATOR).length == 2;
        return valid;
    }

    private static String createValuesString(String value) {
        String valuesString;
        if (value.length() > 1) {
            String[] parsedValuesList = getParsedValuesList(value);
            valuesString = getParsedValuesString(parsedValuesList);
        }
        else {
            valuesString = value.trim();
        }
        return valuesString;
    }

    private static String getParsedValuesString(String[] parsedValuesList) {
            String parsedValuesString = "";
            for (int i = 0; i < parsedValuesList.length; i++) {
                String value = parsedValuesList[i];
                if (i == parsedValuesList.length-1) {
                    parsedValuesString+= SPACE + value;
                }
                else {
                    parsedValuesString += SPACE + value + VALUES_SEPARATOR;
                }
            }
            return parsedValuesString;
    }

    private static String[] getParsedValuesList(String value) {
        String[] valueFields = value.split(VALUES_SEPARATOR);
        String [] parsedValues = new String[valueFields.length];
        for (int i = 0; i < valueFields.length; i++) {
            parsedValues[i] = valueFields[i].trim();
        }
        return parsedValues;
    }

    public static void main(String[] args) {
            processLineFeed();
    }
}