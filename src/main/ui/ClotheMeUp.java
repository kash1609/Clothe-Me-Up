package ui;

import model.Closet;
import model.Clothing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;


//Closet Application
public class ClotheMeUp {
    private static final String JSON_STORE = "./data/closet.json";
    private Closet closet;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the Closet Application
    public ClotheMeUp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runClotheMeUp();
    }

    // MODIFIES: this
    // EFFECTS: processes the user input
    private void runClotheMeUp() {
        if (init()) {
            boolean keepRunning = true;
            String closetCommand;
            while (keepRunning) {
                displayClosetOptions();
                closetCommand = input.next();
                closetCommand = closetCommand.toLowerCase();
                if (closetCommand.equals("q")) {
                    keepRunning = false;
                } else {
                    processClosetCommand(closetCommand);
                }
            }
        }
        System.out.println("\nHope you like your outfit!");
    }

    // MODIFIES: this
    // EFFECTS: processes the user command
    private void processClosetCommand(String closetCommand) {
        if (closetCommand.equals("a")) {
            addClothing();
        } else if (closetCommand.equals("r")) {
            removeClothing();
        } else if (closetCommand.equals("o")) {
            outfitOption();
        } else if (closetCommand.equals("p")) {
            printClothing();
        } else if (closetCommand.equals("s")) {
            saveCloset();
        } else if (closetCommand.equals("l")) {
            loadCloset();
        } else {
            System.out.println("Selection is not valid!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the closet with 6 items inputted
    private boolean init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        String closetCommand;
        System.out.println("\nWELCOME TO CLOTHE ME UP, your fashion advisor!");
        while (true) {
            displayStartOptions();
            closetCommand = input.next();
            closetCommand = closetCommand.toLowerCase();
            if (closetCommand.equals("q")) {
                return false;
            } else if (closetCommand.equals("n")) {
                System.out.println("Enter the name of your closet");
                String name = input.next();
                closet = new Closet(name);
                return true;
            } else if (closetCommand.equals("l")) {
                loadCloset();
                return true;
            } else {
                System.out.println("Selection is not valid!");
            }
        }
    }

    // EFFECTS: displays Closet's start options to user
    private void displayStartOptions() {
        System.out.println("\nn -> to create new closet");
        System.out.println("\tl -> to load old closet");
        System.out.println("\tq -> to quit the application");
    }

    // EFFECTS: displays Closet's options to user
    private void displayClosetOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add clothing item");
        System.out.println("\tr -> remove clothing item");
        System.out.println("\to -> ask for an outfit option");
        System.out.println("\tp -> print clothes in closet");
        System.out.println("\ts -> save closet to file");
        System.out.println("\tl -> load closet from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds clothing item to closet
    private void addClothing() {
        Clothing item = createClothing();
        closet.addClothing(item);
    }

    // EFFECTS: prints all the clothes in the closet
    private void printClothing() {
        System.out.println("Here are the clothes in your closet");
        System.out.println(closet.toString());
    }

    // MODIFIES: this
    // EFFECTS: removes clothing item from closet
    private void removeClothing() {
        System.out.println("Here are the clothes in your closet");
        System.out.println(closet.toString());
        System.out.println("Enter ID of clothing to be removed");
        int itemID = input.nextInt();
        closet.removeClothing(itemID);
    }

    // EFFECTS: provides clothing options based on user input
    private void outfitOption() {
        System.out.println("BEEP, BOP, BEEP!");
        System.out.println("Answer these questions for your RANDOM outfit of the day ;)");
        System.out.println("What is the occasion?");
        Clothing.Occasion occasion = selectOccasion();
        System.out.println("What is the weather outside today?");
        Clothing.Weather weather = selectWeather();
        System.out.println("What is the temperature outside today?");
        int temp = input.nextInt();
        System.out.println("The following are your clothing options:");
        System.out.println(closet.selectOutfit(occasion, weather, temp));
    }

    // EFFECTS: prompts user to input fields required to create clothing item
    private Clothing createClothing() {
        System.out.println("\nName of clothing item?");
        String name = input.next();
        Clothing.Category category = selectCategory();
        Clothing.ColorPalette colorPalette = selectColorPalette();
        Clothing.Occasion occasion = selectOccasion();
        Clothing.Weather weather = selectWeather();
        System.out.println("\nMaximum wear times of clothing item?");
        int wear = input.nextInt();
        System.out.println("\nSuitable temperature for clothing item");
        int temp = input.nextInt();
        return new Clothing(name, category, colorPalette,
                occasion, weather, wear, temp);
    }

    // EFFECTS: prompts user to input a weather based on options and returns it
    private Clothing.Weather selectWeather() {
        String occasion;
        while (true) {
            System.out.println("\nSelect weather from:");
            System.out.println("\ts -> sunny");
            System.out.println("\tr -> rainy");
            System.out.println("\tw -> windy");
            System.out.println("\tn -> snow");
            occasion = input.next().toLowerCase();
            if (occasion.equals("s")) {
                return Clothing.Weather.Sunny;
            } else if (occasion.equals("r")) {
                return Clothing.Weather.Rainy;
            } else if (occasion.equals("w")) {
                return Clothing.Weather.Windy;
            } else if (occasion.equals("n")) {
                return Clothing.Weather.Snow;
            }
        }
    }

    // EFFECTS: prompts user to input an occasion based on options and returns it
    private Clothing.Occasion selectOccasion() {
        String occasion;
        while (true) {
            System.out.println("\nSelect occasion from:");
            System.out.println("\ts -> school");
            System.out.println("\tf -> formal");
            System.out.println("\tp -> party");
            System.out.println("\tl -> lounge");
            occasion = input.next().toLowerCase();
            if (occasion.equals("s")) {
                return Clothing.Occasion.School;
            } else if (occasion.equals("f")) {
                return Clothing.Occasion.Formal;
            } else if (occasion.equals("p")) {
                return Clothing.Occasion.Party;
            } else if (occasion.equals("l")) {
                return Clothing.Occasion.Lounge;
            }
        }
    }

    // EFFECTS: prompts user to input a category of clothing based on options and returns it
    private Clothing.Category selectCategory() {
        String category;
        while (true) {
            System.out.println("\nSelect category from:");
            System.out.println("\tt -> tops");
            System.out.println("\tb -> bottoms");
            System.out.println("\tj -> jackets");
            category = input.next().toLowerCase();
            if (category.equals("t")) {
                return Clothing.Category.Tops;
            } else if (category.equals("b")) {
                return Clothing.Category.Bottoms;
            } else if (category.equals("j")) {
                return Clothing.Category.Jackets;
            }
        }
    }

    // EFFECTS: prompts user to input a color palette for the clothing based on options and returns it
    private Clothing.ColorPalette selectColorPalette() {
        String category;
        while (true) {
            System.out.println("\nSelect color palette for item:");
            System.out.println("\tg -> grayscale");
            System.out.println("\tc -> color block");
            System.out.println("\tp -> print");
            System.out.println("\td -> denim");
            category = input.next().toLowerCase();
            if (category.equals("g")) {
                return Clothing.ColorPalette.Grayscale;
            } else if (category.equals("c")) {
                return Clothing.ColorPalette.ColorBlock;
            } else if (category.equals("p")) {
                return Clothing.ColorPalette.Print;
            } else if (category.equals("d")) {
                return Clothing.ColorPalette.Denim;
            }
        }
    }

    // EFFECTS: saves the closet to file
    private void saveCloset() {
        try {
            jsonWriter.open();
            jsonWriter.write(closet);
            jsonWriter.close();
            System.out.println("Saved " + closet.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads closet from file
    private void loadCloset() {
        try {
            closet = jsonReader.read();
            System.out.println("Loaded" + closet.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

