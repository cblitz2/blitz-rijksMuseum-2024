package blitz.rijks;

import com.andrewoid.ApiKey;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RijksServiceTest {
    @Test
    public void getPage() {
        // given
        RijksService service = new RijksServiceFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 3;

        // when
        ArtObjects art = service.getPage(apiKey.get(), pageNum).blockingGet();

        // then
        ArtObject artObject = art.artObjects[0];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }

    @Test
    public void query() {
        // given
        RijksService service = new RijksServiceFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 3;
        String q = "a";

        // when
        ArtObjects art = service.query(apiKey.get(), pageNum, q).blockingGet();

        // then
        ArtObject artObject = art.artObjects[0];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }

    @Test
    public void artist() {
        // given
        RijksService service = new RijksServiceFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 3;
        String artist = "Rembrandt van Rijn";

        // when
        ArtObjects art = service.artist(apiKey.get(), pageNum, artist).blockingGet();

        // then
        ArtObject artObject = art.artObjects[0];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }
}
