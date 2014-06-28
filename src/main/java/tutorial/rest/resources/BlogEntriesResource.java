package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 6/28/14.
 */
public class BlogEntriesResource extends ResourceSupport {
    private String title;

    private List<BlogEntryResource> entries;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BlogEntryResource> getEntries() {
        return entries;
    }

    public void setEntries(List<BlogEntryResource> entries) {
        this.entries = entries;
    }
}
