package DataGenerator;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.Files.*;

public class Generator {

    String genericPath = "json/";

    public String generateFemaleName() throws IOException {
        Gson gson = new Gson();
        String path = genericPath + "fnames.json";
//        NamingGeneric femaleNames = gson.fromJson(Files.readString(Paths.get(genericPath)), NamingGeneric.class);
//        return femaleNames.pickRandomName();
        FemaleNames femaleNames = gson.fromJson(Files.readString(Paths.get(path)), FemaleNames.class);  // Gets the object from file path
        return femaleNames.pickRandomName();
    }

    public String generateMaleName() throws IOException {
        Gson gson = new Gson();
        String path =  genericPath + "mnames.json";
        MaleNames maleNames = gson.fromJson(Files.readString(Paths.get(path)), MaleNames.class);
        return maleNames.pickRandomName();
    }

    public String generateSirName() throws IOException {
        Gson gson = new Gson();
        String path =  genericPath + "snames.json";
        Surnames sirNames = gson.fromJson(Files.readString(Paths.get(path)), Surnames.class);
        return sirNames.pickRandomName();
    }

    public Location generateLocation() throws IOException {
        Gson gson = new Gson();
        String path =  genericPath + "Locations.json";
        AllLocations allLocations = gson.fromJson(Files.readString(Paths.get(path)), AllLocations.class);
        Location location = allLocations.getRandomLocation();
        return location;
    }
}
