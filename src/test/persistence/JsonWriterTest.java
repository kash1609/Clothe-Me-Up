package persistence;

import model.Clothing;
import model.Closet;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Closet closet = new Closet("My closet");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Closet closet = new Closet("My closet");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCloset.json");
            writer.open();
            writer.write(closet);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCloset.json");
            closet = reader.read();
            assertEquals("My closet", closet.getName());
            assertEquals(0, closet.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCloset() {
        try {
            Closet closet = new Closet("My closet");
            closet.addClothing(new Clothing("Gray T-shirt", Clothing.Category.Tops,
                    Clothing.ColorPalette.Grayscale,
                    Clothing.Occasion.Lounge, Clothing.Weather.Sunny, 100, 16));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCloset.json");
            writer.open();
            writer.write(closet);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCloset.json");
            closet = reader.read();
            assertEquals("My closet", closet.getName());
            assertEquals(1, closet.size());
            assertEquals("ID:0\nName:Gray T-shirt\n Category:Tops" + "\n Color:Grayscale\n Occasion: Lounge"
                            + "\n Weather: Sunny\n Wear & Tear: 100\n Suited for 15 degrees\n",
                    closet.toString());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}