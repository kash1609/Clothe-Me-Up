package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

//Represents a closet having clothes categorised by tops, bottoms, jackets
public class Closet implements Writable {
    private final String name;
    private final List<Clothing> closetItems;

    /*
     * EFFECTS: A closet is constructed  with an empty list of clothes
     */
    public Closet(String name) {
        this.name = name;
        closetItems = new ArrayList<>();
    }

    /*
     * REQUIRES: closet that is a list of clothing
     * MODIFIES: this
     * EFFECTS: clothing item is added to the list of clothing
     */
    public void addClothing(Clothing item) {
        closetItems.add(item);
        EventLog.getInstance().logEvent(new Event("Added closet item " + item.name + " to closet"));
    }

    /*
     * REQUIRES: a list of clothing
     * MODIFIES: this
     * EFFECTS: adds a list of clothing items to closet
     */
    public void addClothes(List<Clothing> newClothes) {
        closetItems.addAll(newClothes);
    }

    /*
     * REQUIRES: closet that has a non-zero length
     * MODIFIES: this
     * EFFECTS: clothing item is removed from the list of clothing
     *          based on ID number inputted. The user can find
     *          the ID number by calling print clothing
     */
    public void removeClothing(int id) {
        EventLog.getInstance().logEvent(new Event("Removed closet item "
                + closetItems.get(id).name + " from closet"));
        closetItems.remove(id);
    }

    /*
     * EFFECTS: items in closet are returned with their fields and ID numbers
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < closetItems.size(); i++) {
            output.append("ID:").append(i).append("\n").append(closetItems.get(i)).append("\n");
        }
        return output.toString();
    }

    // EFFECTS: gets size of closet
    public int size() {
        return closetItems.size();
    }

    // EFFECTS: gets name of clothing item
    public String getName() {
        return name;
    }

    // EFFECTS: returns clothing item by ID given
    public Clothing getIDinCloset(int id) {
        return closetItems.get(id);
    }

    /*
     * REQUIRES: occasion, weather, and temperature
     * EFFECTS: returns a random top, random bottom, random jacket
     *          that match the occasion, weather, and temperature
     *          or returns no options if at least one of the three items
     *          are not found
     */
    public String selectOutfit(Clothing.Occasion occasion, Clothing.Weather weather, int temp) {
        List<Clothing> tops = new ArrayList<>();
        List<Clothing> bottoms = new ArrayList<>();
        List<Clothing> jackets = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Asked for clothing option"));
        temp = (temp + 2) / 5 * 5;
        for (Clothing item : closetItems) {
            if (item.occasion == occasion && item.weather == weather && item.temperature == temp) {
                if (item.category == Clothing.Category.Tops) {
                    tops.add(item);
                } else if (item.category == Clothing.Category.Bottoms) {
                    bottoms.add(item);
                } else {
                    jackets.add(item);
                }
            }
        }
        if (tops.size() == 0 || bottoms.size() == 0 || jackets.size() == 0) {
            return "No clothing options available!";
        }
        Random rand = new Random();
        return "Tops: " + tops.get(rand.nextInt(tops.size())).name
                + "\nBottoms: " + bottoms.get(rand.nextInt(bottoms.size())).name
                + "\nJackets: " + jackets.get(rand.nextInt(jackets.size())).name;
    }

    //EFFECTS: adds clothes to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("clothes", clothesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray clothesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c : closetItems) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
