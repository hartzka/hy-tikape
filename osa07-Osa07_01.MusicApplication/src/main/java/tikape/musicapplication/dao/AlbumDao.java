package tikape.musicapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.musicapplication.database.Database;
import tikape.musicapplication.domain.Album;

public class AlbumDao implements Dao<Album, Integer> {

    private Database database;

    public AlbumDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Album> findAll() throws SQLException {
        List<Album> albums = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT albumId, title FROM Album").executeQuery()) {

            while (result.next()) {
                albums.add(new Album(result.getInt("albumId"), result.getString("title")));
            }
        }

        return albums;
    }

    public List<Album> findAllByArtistId(int artistId) throws SQLException {
        List<Album> albums = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT albumId, title FROM Album WHERE artistId = ?");
            stmt.setInt(1, artistId);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                albums.add(new Album(result.getInt("albumId"), result.getString("title")));
            }
        }

        return albums;
    }

    @Override
    public Album findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT albumId, title FROM Album WHERE albumId = ?");
            stmt.setInt(1, key);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return new Album(result.getInt("albumId"), result.getString("title"));
            }
        }

        return null;
    }

    @Override
    public void save(Album object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Album object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
