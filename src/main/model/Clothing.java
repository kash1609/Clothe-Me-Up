package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a clothing item with the attributes: category, weather, occasion, color palette,
//                                                wear & tear, temperature and name
public class Clothing implements Writable {
    //Represents the categories of clothing
    public enum Category { Tops, Bottoms, Jackets }

    //Represents the weather options in Vancouver
    public enum Weather { Sunny, Rainy, Windy, Snow }

    //Represents the occasion options for a Student
    public enum Occasion { School, Formal, Party, Lounge }

    //Represents the color palettes of clothes
    public enum ColorPalette { Grayscale, ColorBlock, Print, Denim } //color palette of clothing

    protected Category category;             // category of clothes
    protected ColorPalette colorpalette;     // color palette of clothing
    protected Occasion occasion;             // occasion for it be worn
    protected Weather weather;               // weather appropriate
    protected int wear;                      // the wear and tear of the clothing
    protected int temperature;               // temperature appropriate for clothing
    protected String name;                   // name of the clothing

    /*
     * REQUIRES: All the attributes to make a clothing item
     * EFFECTS: make a clothing item with the attributes from input value
     */
    public Clothing(String name, Category category, ColorPalette colorpalette,
                    Occasion occasion, Weather weather,
                    int wear, int temperature) {
        this.name = name;
        this.category = category;
        this.colorpalette = colorpalette;
        this.occasion = occasion;
        this.weather = weather;
        this.wear = wear;
        this.temperature = (temperature + 2) / 5 * 5; // rounds to nearest 5 degree temperature
    }

    /*
     * EFFECTS: returns a string representation of clothing item
     */
    public String toString() {
        return "Name:" + name
                + "\n Category:" + category.name()
                + "\n Color:" + colorpalette.name()
                + "\n Occasion: " + occasion.name()
                + "\n Weather: " + weather.name()
                + "\n Wear & Tear: " + wear
                + "\n Suited for " + temperature + " degrees";
    }

    // EFFECTS: gets name of clothing
    public String getName() {
        return name;
    }

    // EFFECTS: gets category of clothing
    public Category getCategory() {
        return category;
    }

    // EFFECTS: gets occasion of clothing
    public Occasion getOccasion() {
        return occasion;
    }

    // EFFECTS: gets weather of clothing
    public Weather getWeather() {
        return weather;
    }

    // EFFECTS: gets ColorPalette of clothing
    public ColorPalette getColorPalette() {
        return colorpalette;
    }

    // EFFECTS: gets wear of clothing
    public int getWear() {
        return wear;
    }

    // EFFECTS: gets temperature of clothing
    public int getTemperature() {
        return temperature;
    }

    // EFFECTS: writes the clothing and element onto a JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("occasion", occasion);
        json.put("weather", weather);
        json.put("colorPalette", colorpalette);
        json.put("wear", wear);
        json.put("temperature", temperature);
        return json;
    }
}

