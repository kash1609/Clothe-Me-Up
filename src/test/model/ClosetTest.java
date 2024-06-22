package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClosetTest {
    private Closet testCloset;
    private Clothing clothing1;
    private Clothing clothing2;
    private Clothing clothing3;
    private Clothing clothing4;


    @BeforeEach
    void runBefore() {
        testCloset = new Closet("Test Closet");
        clothing1 = new Clothing("Gray T-shirt", Clothing.Category.Tops, Clothing.ColorPalette.Grayscale,
                Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 100, 16);
        clothing2 = new Clothing("White Jeans", Clothing.Category.Bottoms, Clothing.ColorPalette.Grayscale,
                Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 100, 17);
        clothing3 = new Clothing("Black Hoodie", Clothing.Category.Jackets, Clothing.ColorPalette.Grayscale,
                Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 100, 15);
        clothing4 = new Clothing("Blue Hoodie", Clothing.Category.Jackets, Clothing.ColorPalette.ColorBlock,
                Clothing.Occasion.School, Clothing.Weather.Sunny, 100, 15);
        testCloset.addClothing(clothing1);
        testCloset.addClothing(clothing2);
        testCloset.addClothing(clothing3);
        testCloset.addClothing(clothing4);
    }

    @Test
    void testAddClothing() {
        assertEquals(4, testCloset.size());
        testCloset.addClothing(new Clothing("Blue T-shirt", Clothing.Category.Tops,
                Clothing.ColorPalette.ColorBlock,
                Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 100, 16));
        assertEquals(5, testCloset.size());
    }

    @Test
    void testRemoveClothing() {
        assertEquals(4, testCloset.size());
        testCloset.removeClothing(0);
        assertEquals(3, testCloset.size());
    }

    @Test
    void testGetOutfit() {
        assertEquals("Tops: Gray T-shirt" + "\nBottoms: White Jeans" + "\nJackets: Black Hoodie",
                testCloset.selectOutfit(Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 15));
        assertEquals("No clothing options available!",
                testCloset.selectOutfit(Clothing.Occasion.School, Clothing.Weather.Sunny, 15));
        assertEquals("No clothing options available!",
                testCloset.selectOutfit(Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 20));
        assertEquals("No clothing options available!",
                testCloset.selectOutfit(Clothing.Occasion.Lounge, Clothing.Weather.Snow, 20));
    }

    @Test
    void testGetOutfitWithoutTops() {
        testCloset.removeClothing(0);
        assertEquals("No clothing options available!",
                testCloset.selectOutfit(Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 15));
    }

    @Test
    void testGetOutfitWithoutBottoms() {
        testCloset.removeClothing(1);
        assertEquals("No clothing options available!",
                testCloset.selectOutfit(Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 15));
    }

    @Test
    void testGetOutfitWithoutJackets() {
        testCloset.removeClothing(2);
        testCloset.removeClothing(2);
        assertEquals("No clothing options available!",
                testCloset.selectOutfit(Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 15));
    }

    @Test
    void testPrintClothing() {
        testCloset.removeClothing(1);
        testCloset.removeClothing(1);
        testCloset.removeClothing(1);
        assertEquals("ID:0\nName:Gray T-shirt\n Category:Tops" +
                "\n Color:Grayscale\n Occasion: Lounge\n Weather: Sunny\n Wear & Tear: 100\n Suited for 15 degrees\n",
                testCloset.toString());
    }

    @Test
    void testName() {
        assertEquals("Test Closet", testCloset.getName());
    }

    @Test
    void testGetID() {
        assertEquals(clothing4, testCloset.getIDinCloset(3));
    }
}
