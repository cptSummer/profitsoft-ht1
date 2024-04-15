import com.profitsoft.entity.Photo;
import com.profitsoft.entity.User;
import com.profitsoft.utils.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

public class JsonParserTest {

    @Test
    public void testParseFileExistsAndCanBeRead() {
        JsonParser parser = new JsonParser();
        try {
            List<?> result = parser.parse("src/main/resources/assets/photos.json", Photo.class);
            assertNotNull(result);

        } catch (IOException e) {
            fail("Exception not expected for an existing file.");
        }
    }

    @Test
    public void testParseFileDoesNotExist() {
        JsonParser parser = new JsonParser();
        assertThrows(NoSuchFileException.class, () -> parser.parse("src/main/resources/assets/photos34.json", Photo.class));
    }

    @Test
    public void testParseEmptyFile() {
        JsonParser parser = new JsonParser();
        try {
            List<?> result = parser.parse("src/main/resources/assets/empty_photos.json", Photo.class);
            assertNull(result);

        } catch (IOException e) {
            fail("Exception not expected for an empty file.");
        }
    }

    @Test
    public void testParseValidClass() {
        JsonParser parser = new JsonParser();
        try {
            List<?> result = parser.parse("src/main/resources/assets/photos.json", Photo.class);
            assertNotNull(result);

        } catch (IOException e) {
            fail("Exception not expected for a valid class.");
        }
    }

    @Test
    public void testParseNullClass() {
        JsonParser parser = new JsonParser();
        assertThrows(NullPointerException.class, () -> parser.parse("src/main/resources/assets/photos.json", null));
    }

    @Test
    public void testParseNullPath() {
        JsonParser parser = new JsonParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("", Photo.class));
    }

}
