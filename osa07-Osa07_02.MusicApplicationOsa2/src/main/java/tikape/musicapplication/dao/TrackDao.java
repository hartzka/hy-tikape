package tikape.musicapplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.musicapplication.database.Database;
import tikape.musicapplication.domain.Track;

public class TrackDao implements Dao<Track, Integer> {

    private Database database;

    public TrackDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Track> findAll() throws SQLException {
        List<Track> tracks = new ArrayList<>();

        try ( Connection conn = database.getConnection();  ResultSet result = conn.prepareStatement("SELECT trackId, name FROM Track").executeQuery()) {

            while (result.next()) {
                tracks.add(new Track(result.getInt("trackId"), result.getString("name")));
            }
        }

        return tracks;
    }

    public List<Track> findAllForAlbum(int albumId) throws SQLException {
        List<Track> albums = new ArrayList<>();

        try ( Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT trackId, name FROM Track WHERE albumId = ?");
            stmt.setInt(1, albumId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                albums.add(new Track(result.getInt("trackId"), result.getString("name")));
            }
        }

        return albums;
    }

    @Override
    public Track findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Track object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Track object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
