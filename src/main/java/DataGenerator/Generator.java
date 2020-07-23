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
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        FemaleNames femaleNames = gson.fromJson(content, FemaleNames.class);
        return femaleNames.pickRandomName();
    }

    public String generateMaleName() throws IOException {
        Gson gson = new Gson();
        String path =  genericPath + "mnames.json";
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        MaleNames maleNames = gson.fromJson(content, MaleNames.class);
        return maleNames.pickRandomName();
    }

    public String generateSurname() throws IOException {
        Gson gson = new Gson();
        String path =  genericPath + "snames.json";
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Surnames sirNames = gson.fromJson(content, Surnames.class);
        return sirNames.pickRandomName();
    }

    public Location generateLocation() throws IOException {
        Gson gson = new Gson();
        String path =  genericPath + "locations.json";
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        AllLocations allLocations = gson.fromJson(content, AllLocations.class);
        Location location = allLocations.getRandomLocation();
        return location;
    }
}
