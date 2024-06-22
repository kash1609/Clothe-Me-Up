package persistence;

import model.Closet;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Closet closet = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCloset() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCloset.json");
        try {
            Closet closet = reader.read();
            assertEquals("My closet", closet.getName());
            assertEquals(0, closet.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCloset() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCloset.json");
        try {
            Closet closet = reader.read();
            assertEquals("My closet", closet.getName());
            assertEquals(1, closet.size());
            assertEquals("ID:0\nName:Gray T-shirt\n Category:Tops" + "\n Color:Grayscale\n Occasion: Lounge"
                            + "\n Weather: Sunny\n Wear & Tear: 100\n Suited for 15 degrees\n",
                    closet.toString());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}