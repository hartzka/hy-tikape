package tikape.musicapplication.domain;

public class Album {

    private Integer id;
    private String title;

    public Album(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    
    

}
