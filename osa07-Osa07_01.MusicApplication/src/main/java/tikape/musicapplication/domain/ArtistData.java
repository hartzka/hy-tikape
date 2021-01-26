package tikape.musicapplication.domain;

public class ArtistData {

    private int id;
    private String name;
    private int albumCount;

    public ArtistData(int id, String name, int albumCount) {
        this.id = id;
        this.name = name;
        this.albumCount = albumCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAlbumCount() {
        return albumCount;
    }

}
