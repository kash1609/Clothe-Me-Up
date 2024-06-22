package ui;

import model.Closet;
import model.Clothing;

import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class ClotheMeUpGUI {

    private static int WIDTH = 1100;
    private static int HEIGHT = 750;
    private static final String JSON_STORE = "./data/closet.json";

    private Closet closet;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame mainFrame;

    // panels
    private JPanel inputPanel;
    private JPanel functionPanel;
    private JPanel listDisplayPanel;
    private JScrollPane tablePanel;

    // inputPanel
    private JLabel enterClothing;
    private JTextField inputClothingName;
    private JTextField inputCategory;
    private JTextField inputColor;
    private JTextField inputOccasion;
    private JTextField inputWeather;
    private JTextField inputWear;
    private JTextField inputTemperature;
    private JButton addClothing;
    private JTextField closetName;
    private JButton save;
    private JButton load;
    private ImageIcon infoImage;
    private JLabel infoImageLabel;

    // functionPanel
    private JLabel removeClothingText;
    private JTextField removeID;
    private JTextArea outfitOptionResult;
    private JTextField inputOccasionForOutfit;
    private JTextField inputWeatherForOutfit;
    private JTextField inputTempForOutfit;
    private JButton removeClothing;
    private JButton outfitOption;
    private ImageIcon decorativeImage;
    private JLabel decoImageLabel;

    // closetDisplayPanel
    private JLabel closetLabel;
    private JTable closetTable;
    private String[] columnNames;
    private String[][] data;
    private JButton displayFullCloset;

    // to run ClotheMeUp GUI
    ClotheMeUpGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        try {
            closet = jsonReader.read();
        } catch (IOException ioe) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

        createInputPanel();
        createFunctionPanel();
        createClosetDisplayPanel();
        createMainFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates the closet display in GUI
    private void createClosetDisplayPanel() {
        listDisplayPanel = new JPanel();
        listDisplayPanel.setBounds(0, 0, 500, HEIGHT);
        listDisplayPanel.setLayout(null);
        int inputWidth = 480;
        int inputHeight = 25;
        createDisplayClosetTable();

        closetLabel = new JLabel("Press the button to use closet");
        closetLabel.setBounds(20, 30, inputWidth, inputHeight);
        createClosetDisplayButton(inputWidth, inputHeight);

        listDisplayPanel.add(closetLabel);
        listDisplayPanel.add(displayFullCloset);
        listDisplayPanel.add(tablePanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the button for listDisplayPanel
    private void createClosetDisplayButton(int inputWidth, int inputHeight) {
        displayFullCloset = new JButton("Show Full Closet");
        displayFullCloset.setBounds(20, 60, inputWidth, inputHeight);
        displayFullCloset.addActionListener(new DisplayFullClosetButton());
    }

    // MODIFIES: this
    // EFFECTS: creates the displayClosetTable
    private void createDisplayClosetTable() {
        columnNames = new String[]{"ID", "Name", "Category", "Color", "Occasion",
                "Weather", "Wear", "Temperature"};
        collectClosetItems();
        closetTable = new JTable(data, columnNames);
        tablePanel = new JScrollPane(closetTable);
        tablePanel.setBounds(20, 100, 470, HEIGHT);
        closetTable.getTableHeader().setBackground(Color.WHITE);
        closetTable.setBounds(20, 100, 470, HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: collects the clothing items in clothing to display
    private void collectClosetItems() {
        int size = closet.size();
        Clothing item;
        String[][] clothingArray = new String[size][8];
        for (int i = 0; i < size; i++) {
            item = closet.getIDinCloset(i);
            clothingArray[i][0] = Integer.toString(i);
            clothingArray[i][1] = item.getName();
            clothingArray[i][2] = item.getCategory().name();
            clothingArray[i][3] = item.getColorPalette().name();
            clothingArray[i][4] = item.getOccasion().name();
            clothingArray[i][5] = item.getWeather().name();
            clothingArray[i][6] = Integer.toString(item.getWear());
            clothingArray[i][7] = Integer.toString(item.getTemperature());
        }
        data = clothingArray;
    }

    // ActionListener implementation for the displayFullCloset button
    private class DisplayFullClosetButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            columnNames = new String[]{"ID", "Name", "Category", "Color", "Occasion",
                    "Weather", "Wear", "Temperature"};
            collectClosetItems();
            closetTable = new JTable(data, columnNames);
            closetTable.getTableHeader().setBackground(Color.WHITE);
            closetTable.setBounds(20, 100, 470, HEIGHT);
            tablePanel.setViewportView(closetTable);
            mainFrame.revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates FunctionPanel in GUI
    private void createFunctionPanel() {
        functionPanel = new JPanel(null);
        functionPanel.setBounds(800, 0, 300, HEIGHT);
        int inputWidth = 260;
        int inputHeight = 25;

        removeClothingText = new JLabel("Enter Clothing ID to remove");
        removeClothingText.setBounds(20, 10, inputWidth, inputHeight);

        createDecorativeImage(inputWidth);
        createFunctionPanelRemoveClothingSection(inputWidth, inputHeight);
        createFunctionPanelOutfitOptionSection(inputWidth, inputHeight);

        functionPanel.add(removeClothingText);
        functionPanel.add(removeID);
        functionPanel.add(removeClothing);
        functionPanel.add(inputWeatherForOutfit);
        functionPanel.add(inputOccasionForOutfit);
        functionPanel.add(inputTempForOutfit);
        functionPanel.add(outfitOption);
        functionPanel.add(outfitOptionResult);
        functionPanel.add(decoImageLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates the decorative image in the Function Panel
    private void createDecorativeImage(int inputWidth) {
        decorativeImage = new ImageIcon(new ImageIcon(
                "data/clothemeup.jpeg").getImage().getScaledInstance(inputWidth,
                250, Image.SCALE_DEFAULT));
        decoImageLabel = new JLabel();
        decoImageLabel.setIcon(decorativeImage);
        decoImageLabel.setBounds(20,300, inputWidth, 250);
    }

    // MODIFIES: this
    // EFFECTS: creates the Function Panel Remove Clothing Section
    private void createFunctionPanelRemoveClothingSection(int inputWidth, int inputHeight) {
        removeClothing = new JButton("Remove Clothing");
        removeClothing.setBounds(20,70, inputWidth, inputHeight);
        removeClothing.addActionListener(new RemoveClothingButton());
        removeID = new JTextField();
        removeID.setBounds(20, 40, inputWidth, inputHeight);
    }

    // MODIFIES: this
    // EFFECTS: creates the Remove Clothing button
    private class RemoveClothingButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idText = removeID.getText();
            int id = parseInt(idText);
            closet.removeClothing(id);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the outfit option section in the Function Panel
    private void createFunctionPanelOutfitOptionSection(int inputWidth, int inputHeight) {
        outfitOption = new JButton("Show Outfit Option");
        outfitOption.setBounds(20,190, inputWidth, inputHeight);
        outfitOption.addActionListener(new OutfitOptionButton());
        inputOccasionForOutfit = new JTextField();
        inputOccasionForOutfit.setBounds(20,100, inputWidth, inputHeight);
        inputOccasionForOutfit.setText("Input Occasion for Outfit");
        inputWeatherForOutfit = new JTextField();
        inputWeatherForOutfit.setBounds(20,130, inputWidth, inputHeight);
        inputWeatherForOutfit.setText("Input Weather for Outfit");
        inputTempForOutfit = new JTextField();
        inputTempForOutfit.setBounds(20,160, inputWidth, inputHeight);
        inputTempForOutfit.setText("Input Temperature for Outfit");
        outfitOptionResult = new JTextArea();
        outfitOptionResult.setBounds(20,220, inputWidth, 50);
        outfitOptionResult.setEditable(false);
    }

    // ActionListener implementation for the OutfitOption button
    public class OutfitOptionButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Clothing.Occasion occasion = selectOccasion(inputOccasionForOutfit.getText());
            Clothing.Weather weather = selectWeather(inputWeatherForOutfit.getText());
            int temp = parseInt(inputTempForOutfit.getText());
            String result = closet.selectOutfit(occasion, weather, temp);
            outfitOptionResult.setText(result);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the Main Frame of the closet application
    private void createMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setLayout(null);
        mainFrame.setTitle("Clothe Me Up");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().setBackground(new Color(0, 100, 100));
        mainFrame.add(listDisplayPanel);
        mainFrame.add(inputPanel);
        mainFrame.add(functionPanel);
        mainFrame.addWindowListener(new PrintLog());
    }

    // MODIFIES: this
    // EFFECTS: creates the InputPanel in the closet application
    private void createInputPanel() {
        inputPanel = new JPanel(null);
        inputPanel.setBounds(500, 0, 300, HEIGHT);
        int inputWidth = 260;
        int inputHeight = 25;

        createInfoImage(inputWidth);
        createInputPanelText(inputWidth, inputHeight);
        createInputPanelButton(inputWidth, inputHeight);

        inputPanel.add(infoImageLabel);
        inputPanel.add(enterClothing);
        inputPanel.add(inputClothingName);
        inputPanel.add(inputCategory);
        inputPanel.add(inputColor);
        inputPanel.add(inputWeather);
        inputPanel.add(inputOccasion);
        inputPanel.add(inputTemperature);
        inputPanel.add(inputWear);
        inputPanel.add(addClothing);
        inputPanel.add(closetName);
        inputPanel.add(save);
        inputPanel.add(load);
    }

    // MODIFIES: this
    // EFFECTS: creates the informational image in the Input Panel
    private void createInfoImage(int inputWidth) {
        infoImage = new ImageIcon(new ImageIcon(
                "data/info.png").getImage().getScaledInstance(inputWidth,
                250, Image.SCALE_DEFAULT));
        infoImageLabel = new JLabel();
        infoImageLabel.setIcon(infoImage);
        infoImageLabel.setBounds(20,40, inputWidth, 250);
    }

    // MODIFIES: this
    // EFFECTS: creates the Input Panel Buttons
    private void createInputPanelButton(int inputWidth, int inputHeight) {
        addClothing = new JButton("Add Clothing Item");
        addClothing.setBounds(20, 560, inputWidth, inputHeight);
        addClothing.addActionListener(new AddClothingButton());
        closetName = new JTextField();
        if (closet != null) {
            closetName.setText("Loaded " + closet.getName() + " from " + JSON_STORE);
        } else {
            closetName.setText("Failed to load closet from " + JSON_STORE);
        }
        closetName.setBounds(20, 590, inputWidth, inputHeight);
        closetName.setEditable(false);

        save = new JButton("Save Closet");
        save.setBounds(20, 620, inputWidth, inputHeight);
        save.addActionListener(new SaveButton());

        load = new JButton("Load Closet");
        load.setBounds(20, 650, inputWidth, inputHeight);
        load.addActionListener(new LoadButton());
    }

    // MODIFIES: this
    // EFFECTS: creates the text fields of the Input Panel
    private void createInputPanelText(int inputWidth, int inputHeight) {
        enterClothing = new JLabel("Enter Clothing Item Details below using image");
        enterClothing.setBounds(20, 320, inputWidth, inputHeight);
        inputClothingName = new JTextField();
        inputClothingName.setText("Enter Name of Clothing item");
        inputClothingName.setBounds(20, 350, inputWidth, inputHeight);
        inputCategory = new JTextField();
        inputCategory.setText("Enter Category of Clothing");
        inputCategory.setBounds(20, 380, inputWidth, inputHeight);
        inputColor = new JTextField();
        inputColor.setText("Enter Color of Clothing");
        inputColor.setBounds(20, 410, inputWidth, inputHeight);
        inputWeather = new JTextField();
        inputWeather.setText("Enter Weather for Clothing");
        inputWeather.setBounds(20, 440, inputWidth, inputHeight);
        inputOccasion = new JTextField();
        inputOccasion.setText("Enter Occasion for Clothing");
        inputOccasion.setBounds(20, 470, inputWidth, inputHeight);
        inputTemperature = new JTextField();
        inputTemperature.setText("Enter suitable temperature");
        inputTemperature.setBounds(20, 500, inputWidth, inputHeight);
        inputWear = new JTextField();
        inputWear.setText("Enter wear times");
        inputWear.setBounds(20, 530, inputWidth, inputHeight);
    }

    // ActionListener implementation for the Add Clothing Button
    private class AddClothingButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clothingNameText = inputClothingName.getText();
            String categoryText = inputCategory.getText();
            Clothing.Category category = selectCategory(categoryText);
            String colorText = inputColor.getText();
            Clothing.ColorPalette colorPalette = selectColor(colorText);
            String occasionText = inputOccasion.getText();
            Clothing.Occasion occasion = selectOccasion(occasionText);
            String weatherText = inputWeather.getText();
            Clothing.Weather weather = selectWeather(weatherText);
            String tempText = inputTemperature.getText();
            int tempNum = parseInt(tempText);
            String wearText = inputWear.getText();
            int wearNum = parseInt(wearText);
            Clothing item = new Clothing(clothingNameText, category, colorPalette, occasion, weather,
                    wearNum, tempNum);
            inputClothingName.setText("Enter Name of Clothing item");
            inputCategory.setText("Enter Category of Clothing");
            inputColor.setText("Enter Color of Clothing");
            inputWeather.setText("Enter Weather for Clothing");
            inputOccasion.setText("Enter Occasion for Clothing");
            inputTemperature.setText("Enter suitable temperature");
            inputWear.setText("Enter wear times");
            closet.addClothing(item);
        }
    }

    // MODIFIES: this
    // EFFECTS: selects weather option for input
    private Clothing.Weather selectWeather(String occasion) {
        if (occasion.equals("s")) {
            return Clothing.Weather.Sunny;
        } else if (occasion.equals("r")) {
            return Clothing.Weather.Rainy;
        } else if (occasion.equals("w")) {
            return Clothing.Weather.Windy;
        } else if (occasion.equals("n")) {
            return Clothing.Weather.Snow;
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: selects category option for input
    private Clothing.Category selectCategory(String category) {
        if (category.equals("t")) {
            return Clothing.Category.Tops;
        } else if (category.equals("b")) {
            return Clothing.Category.Bottoms;
        } else if (category.equals("j")) {
            return Clothing.Category.Jackets;
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: selects color option for input
    private Clothing.ColorPalette selectColor(String color) {
        if (color.equals("g")) {
            return Clothing.ColorPalette.Grayscale;
        } else if (color.equals("c")) {
            return Clothing.ColorPalette.ColorBlock;
        } else if (color.equals("p")) {
            return Clothing.ColorPalette.Print;
        } else if (color.equals("d")) {
            return Clothing.ColorPalette.Denim;
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: selects occasion option for input
    private Clothing.Occasion selectOccasion(String occasion) {
        if (occasion.equals("s")) {
            return Clothing.Occasion.School;
        } else if (occasion.equals("f")) {
            return Clothing.Occasion.Formal;
        } else if (occasion.equals("p")) {
            return Clothing.Occasion.Party;
        } else if (occasion.equals("l")) {
            return Clothing.Occasion.Lounge;
        } else {
            return null;
        }
    }

    // ActionListener implementation for Save button
    private class SaveButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(closet);
                jsonWriter.close();
                closetName.setText("Saved " + closet.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException fnf) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // ActionListener implementation for Load button
    private class LoadButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                closet = jsonReader.read();
                closetName.setText("Loaded " + closet.getName() + " from " + JSON_STORE);
            } catch (IOException ioe) {
                closetName.setText("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // WindowClosing implementation for Printing Log
    private class PrintLog extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent w) {
            for (Event next : EventLog.getInstance()) {
                System.out.println(next.toString() + "\n\n");
            }
        }
    }
}
