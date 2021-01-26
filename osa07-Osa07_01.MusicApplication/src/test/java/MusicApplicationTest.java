
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@Points("07-01")
public class MusicApplicationTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void statsListed() throws SQLException {
        goTo("http://localhost:" + server.getPort() + "/stats/artists");

        assertTrue("Oletettiin, että artistien albumitilastoja sisältävässä taulussa on yli 10 riviä. Nyt rivejä oli " + $("table").find("tr").size(), $("table").find("tr").size() > 10);

        Map<String, String> expected = new TreeMap<>();

        expected.put("Led Zeppelin", "14");
        expected.put("Metallica", "10");
        expected.put("Pearl Jam", "5");

        for (String text : $("table").find("tr").getTexts()) {

            for (String artist : new TreeSet<>(expected.keySet())) {
                if (text.contains(artist) && text.contains(expected.get(artist))) {
                    expected.remove(artist);
                }
            }
        }

        for (String artist : expected.keySet()) {
            fail("Listasta ei löytynyt artistia " + artist + ", jolla pitäisi olla " + expected.get(artist) + " kappaletta.");
        }
    }
}
