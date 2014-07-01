package tutorial.core.services;

import tutorial.core.models.entities.BlogEntry;

/**
 * Created by Chris on 6/27/14.
 */
public interface BlogEntryService {
    public BlogEntry findBlogEntry(Long id); // Returns the BlogEntry or null if it can't be found
    public BlogEntry deleteBlogEntry(Long id); // Deletes the found BlogEntry or returns null if it can't be found

    /**
     * @param id the id of the BlogEntry to updateBlogEntry
     * @param data the BlogEntry containing the data to be used for the updateBlogEntry
     * @return the updated BlogEntry or null if the BlogEntry with the id cannot be found
     */
    public BlogEntry updateBlogEntry(Long id, BlogEntry data);

}
