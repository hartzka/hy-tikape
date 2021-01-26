package tikape.musicapplication.domain;

public class Artist {

    private Integer id;
    private String name;

    public Artist(Integer id, String name) {
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
