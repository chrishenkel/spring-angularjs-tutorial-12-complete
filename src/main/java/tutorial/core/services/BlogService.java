package tutorial.core.services;

import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;

import java.util.List;

/**
 * Created by Chris on 6/28/14.
 */
public interface BlogService {
    public Blog find(Long id); // Returns the Blog with the id, or null if it can't be found


    /**
     * @param blogId the id of the blog to add this BlogEntry to
     * @param data the BlogEntry containing the data to be used for creating the new entity
     * @return the created BlogEntry with a generated ID
     * @throws tutorial.core.services.exceptions.BlogNotFoundException if the blog to add to cannot be found
     */
    public BlogEntry create(Long blogId, BlogEntry data);

    public List<BlogEntry> findAll(Long blogId); // find all associated blog entries
}
