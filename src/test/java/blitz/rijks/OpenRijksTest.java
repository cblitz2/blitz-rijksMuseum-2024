package blitz.rijks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpenRijksTest {
    @Test
    public void getPage() {
        // given
        OpenRijks service = new OpenRijksFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 3;

        // when
        ArtObjects art = service.getPage(apiKey.toString(), pageNum).blockingGet();

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
        OpenRijks service = new OpenRijksFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 3;
        String q = "a";

        // when
        ArtObjects art = service.query(apiKey.toString(), pageNum, q).blockingGet();

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
        OpenRijks service = new OpenRijksFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 3;
        String artist = "Rembrandt van Rijn";

        // when
        ArtObjects art = service.artist(apiKey.toString(), pageNum, artist).blockingGet();

        // then
        ArtObject artObject = art.artObjects[0];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }
}
