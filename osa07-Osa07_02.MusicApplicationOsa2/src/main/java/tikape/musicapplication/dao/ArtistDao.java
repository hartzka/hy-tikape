package tikape.musicapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.musicapplication.database.Database;
import tikape.musicapplication.domain.Artist;

public class ArtistDao implements Dao<Artist, Integer> {

    private Database database;

    public ArtistDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Artist> findAll() throws SQLException {
        List<Artist> artists = new ArrayList<>();

        try ( Connection conn = database.getConnection();  ResultSet result = conn.prepareStatement("SELECT artistId, name FROM Artist").executeQuery()) {

            while (result.next()) {
                artists.add(new Artist(result.getInt("artistId"), result.getString("name")));
            }
        }

        return artists;
    }

    public Artist findOneByAlbumId(int albumId) throws SQLException {

        try ( Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT Artist.artistId, Artist.name FROM Artist, Album WHERE Artist.artistId = Album.artistId AND Album.albumId = ?");
            stmt.setInt(1, albumId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return new Artist(result.getInt("artistId"), result.getString("name"));
            }
        }

        return null;
    }

    @Override
    public Artist findOne(Integer key) throws SQLException {
        try ( Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT artistId, name FROM Artist where artistId = ?");
            stmt.setInt(1, key);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return new Artist(result.getInt("artistId"), result.getString("name"));
            }
        }

        return null;
    }

    @Override
    public void save(Artist object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Artist object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
