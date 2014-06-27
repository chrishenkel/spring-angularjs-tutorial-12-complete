package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Chris on 6/27/14.
 */
public class BlogEntryResource extends ResourceSupport {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
