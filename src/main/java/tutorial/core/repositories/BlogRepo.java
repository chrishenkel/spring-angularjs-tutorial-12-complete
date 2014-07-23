package tutorial.core.repositories;

import tutorial.core.models.entities.Blog;
import tutorial.core.models.entities.BlogEntry;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;

/**
 * Created by Chris on 7/10/14.
 */
public interface BlogRepo {
    public Blog createBlog(Blog data);
    public BlogList findAllBlogs();
    public Blog findBlog(Long id);
    public Blog findBlogByTitle(String title);
    public BlogList findBlogsByAccount(Long accountId);
}
