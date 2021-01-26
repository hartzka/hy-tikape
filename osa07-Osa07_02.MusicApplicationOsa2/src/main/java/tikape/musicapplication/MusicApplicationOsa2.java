package tikape.musicapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.musicapplication.dao.AlbumDao;
import tikape.musicapplication.dao.ArtistDao;
import tikape.musicapplication.dao.TrackDao;
import tikape.musicapplication.database.Database;
import tikape.musicapplication.domain.InvoiceData;
public class MusicApplicationOsa2 {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:Chinook_Sqlite.db");
        AlbumDao albumDao = new AlbumDao(database);
        TrackDao trackDao = new TrackDao(database);
        ArtistDao artistDao = new ArtistDao(database);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artists", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artists", artistDao.findAll());

            return new ModelAndView(map, "artists");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artists/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artist", artistDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("albums", albumDao.findAllByArtistId(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "artist");
        }, new ThymeleafTemplateEngine());

        Spark.get("/albums", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("albums", albumDao.findAll());

            return new ModelAndView(map, "albums");
        }, new ThymeleafTemplateEngine());

        Spark.get("/albums/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("artist", artistDao.findOneByAlbumId(Integer.parseInt(req.params("id"))));
            map.put("album", albumDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("tracks", trackDao.findAllForAlbum(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "album");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/stats/invoices", (req, res) -> {
            HashMap map = new HashMap<>();
            List<InvoiceData> invoiceData = new ArrayList<>();
           
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Customer.FirstName AS first, Customer.LastName AS last, COUNT(Invoice.InvoiceId) AS invoices, SUM(Invoice.Total) AS total FROM Customer, Invoice WHERE Customer.CustomerId = Invoice.CustomerId GROUP BY Customer.LastName");

            ResultSet result = stmt.executeQuery();
            while (result.next()) {

                invoiceData.add(new InvoiceData(result.getString("first") + " " + result.getString("last"), result.getInt("invoices"), result.getDouble("total")));

            }
            conn.close();
            
            map.put("invoicedata", invoiceData);

            return new ModelAndView(map, "stats-invoices");
        }, new ThymeleafTemplateEngine());

    }
}
