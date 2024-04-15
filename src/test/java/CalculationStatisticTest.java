import com.profitsoft.entity.Photo;
import com.profitsoft.service.PhotoService;
import com.profitsoft.utils.CalculationStatistics;
import com.profitsoft.utils.UtilService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CalculationStatisticTest {
    private CalculationStatistics calcStatistics;
    private ArrayList<Photo> photos;
    private ArrayList<Photo> emptyPhotos;

    @Before
    public void setUp() {
        calcStatistics = new CalculationStatistics();
        photos = new ArrayList<>(Arrays.asList(
                new Photo("photo1", "jpg", "user/photo1.jpg", "sea, sun", new Date(2020, 11, 1)),
                new Photo("photo2", "png", "user/photo2.jpg", "logo, square", new Date(2021, 1, 13)),
                new Photo("photo2", "png", "user/photo2.jpg", "logo, circle", new Date(2021, 1, 13))
        ));
        emptyPhotos = new ArrayList<>(Arrays.asList(
                new Photo(null, null, null, null, null),
                new Photo(null, null, null, null, null)
        ));
    }

    @Test
    public void testEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> calcStatistics.getAttributeCount(new ArrayList<>(), "photoName"));
    }

    @Test
    public void testEmptyAttribute() {
        assertThrows(IllegalArgumentException.class, () -> calcStatistics.getAttributeCount(photos, ""));
    }

    @Test
    public void testNullAttributeValues() {
        HashMap<String, Integer> result = calcStatistics.getAttributeCount(emptyPhotos, "photoName");
        assertEquals(0, result.size());
    }

    @Test
    public void testNonNullAttributeValues() {
        HashMap<String, Integer> result = calcStatistics.getAttributeCount(photos, "photoName");
        assertEquals(2, result.size());
        assertEquals(1, (int) result.get("photo1"));
        assertEquals(2, (int) result.get("photo2"));
    }

    @Test
    public void testDuplicateAttributeValues() {
        HashMap<String, Integer> result = calcStatistics.getAttributeCount(photos, "photoName");
        assertEquals(2, (int) result.get("photo2"));
    }

    @Test
    public void testDuplicateTags() {
        HashMap<String, Integer> result = calcStatistics.getTagAttributeCount(photos);
        assertEquals(2, (int) result.get("logo"));
        assertEquals(1, (int) result.get("sun"));
    }

    @Test
    public void testUniqueTags() {
        HashMap<String, Integer> result = calcStatistics.getTagAttributeCount(photos);
        assertEquals(1, (int) result.get("sun"));
        assertEquals(1, (int) result.get("sea"));
        assertEquals(1, (int) result.get("circle"));
        assertEquals(1, (int) result.get("square"));
    }


}
