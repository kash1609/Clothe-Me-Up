package persistence;

import model.Clothing;
import model.Closet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Event;
import model.EventLog;
import org.json.*;

// Represents a reader that reads closet from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads closet from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Closet read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded Closet from " + source));
        return parseCloset(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses closet from JSON object and returns it
    private Closet parseCloset(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Closet closet = new Closet(name);
        addClothes(closet, jsonObject);
        return closet;
    }

    // MODIFIES: closet
    // EFFECTS: parses clothes from JSON object and adds them to closet
    private void addClothes(Closet closet, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("clothes");
        List<Clothing> l = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextClothing = (JSONObject) json;
            l.add(convertToClothing(nextClothing));
        }
        closet.addClothes(l);
    }

    // MODIFIES: wr
    // EFFECTS: parses clothing from JSON object and adds it to closet
    private Clothing convertToClothing(JSONObject jsonObject) {
        Clothing.Category category = Clothing.Category.valueOf(jsonObject.getString("category"));
        Clothing.ColorPalette colorPalette = Clothing.ColorPalette.valueOf(jsonObject.getString("colorPalette"));
        Clothing.Occasion occasion = Clothing.Occasion.valueOf(jsonObject.getString("occasion"));
        Clothing.Weather weather = Clothing.Weather.valueOf(jsonObject.getString("weather"));
        int wear = jsonObject.getInt("wear");
        int temperature = jsonObject.getInt("temperature");
        String name = jsonObject.getString("name");

        return new Clothing(name, category, colorPalette, occasion, weather, wear, temperature);
    }
}

