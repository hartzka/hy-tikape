package tikape.musicapplication.domain;

public class Track {

    private Integer id;
    private String name;

    public Track(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
