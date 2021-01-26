
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

@Points("07-02")
public class MusicApplicationOsa2Test extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @ClassRule
    public static ServerRule server = new ServerRule();

    @Test
    public void statsListed() throws SQLException {
        goTo("http://localhost:" + server.getPort() + "/stats/invoices");

        assertTrue("Oletettiin, että laskutilastoja sisältävässä taulussa on yli 10 riviä. Nyt rivejä oli " + $("table").find("tr").size(), $("table").find("tr").size() > 10);

        Map<String, String> expectedOrders = new TreeMap<>();

        expectedOrders.put("Daan Peeters", "7");
        expectedOrders.put("Dan Miller", "7");
        expectedOrders.put("Puja Srivastava", "6");
        
        Map<String, String> expectedOrderSums = new TreeMap<>();

        expectedOrderSums.put("Daan Peeters", "37.62");
        expectedOrderSums.put("Dan Miller", "39.62");
        expectedOrderSums.put("Puja Srivastava", "36.64");

        for (String text : $("table").find("tr").getTexts()) {

            for (String customer : new TreeSet<>(expectedOrders.keySet())) {
                if (text.contains(customer) && text.contains(expectedOrders.get(customer))) {
                    expectedOrders.remove(customer);
                }
            }
            
            for (String customer : new TreeSet<>(expectedOrderSums.keySet())) {
                if (text.contains(customer) && text.contains(expectedOrderSums.get(customer))) {
                    expectedOrderSums.remove(customer);
                }
            }
        }

        for (String customer : expectedOrders.keySet()) {
            fail("Taulukosta ei löytynyt asiakasta " + customer + ", jolla pitäisi olla " + expectedOrders.get(customer) + " tilausta.");
        }
        
        for (String artist : expectedOrderSums.keySet()) {
            fail("Taulukosta ei löytynyt asiakasta " + artist + ", jonka tilausten summan pitäisi olla " + expectedOrderSums.get(artist) + " ");
        }
    }
}
