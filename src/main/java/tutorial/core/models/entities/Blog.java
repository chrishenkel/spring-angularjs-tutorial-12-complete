package tutorial.core.models.entities;

/**
 * Created by Chris on 6/28/14.
 */
public class Blog {
    private Long id;

    private String title;

    private Account owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
