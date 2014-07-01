package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.models.entities.Blog;
import tutorial.core.services.util.BlogList;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.BlogListResource;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Chris on 7/1/14.
 */
public class BlogListResourceAsm extends ResourceAssemblerSupport<BlogList, BlogListResource> {

    public BlogListResourceAsm()
    {
        super(BlogController.class, BlogListResource.class);
    }

    @Override
    public BlogListResource toResource(BlogList blogList) {
        BlogListResource res = new BlogListResource();
        res.setBlogs(new BlogResourceAsm().toResources(blogList.getBlogs()));
        return res;
    }
}
