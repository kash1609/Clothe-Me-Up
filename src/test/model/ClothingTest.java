package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClothingTest {
    private Clothing testClothing;

    @BeforeEach
    void runBefore() {
        testClothing= new Clothing("Pink Sweater", Clothing.Category.Tops, Clothing.ColorPalette.ColorBlock,
                Clothing.Occasion.School, Clothing.Weather.Windy, 100, 13);
    }

    @Test
    void testConstructor(){
        assertEquals("Pink Sweater", testClothing.getName());
        assertEquals(Clothing.Category.Tops, testClothing.getCategory());
        assertEquals(Clothing.ColorPalette.ColorBlock, testClothing.getColorPalette());
        assertEquals(Clothing.Occasion.School, testClothing.getOccasion());
        assertEquals(Clothing.Weather.Windy, testClothing.getWeather());
        assertEquals(100, testClothing.getWear());
        assertEquals(15, testClothing.getTemperature());
    }

    @Test
    void testTemperatureRoundUp(){
        assertEquals(15, testClothing.getTemperature());
    }

    @Test
    void testToString(){
        assertEquals("Name:Pink Sweater\n Category:Tops\n Color:ColorBlock" +
                "\n Occasion: School\n Weather: Windy\n Wear & Tear: 100" +
                "\n Suited for 15 degrees", testClothing.toString());
    }
}