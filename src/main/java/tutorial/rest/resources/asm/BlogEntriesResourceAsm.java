package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.entities.BlogEntry;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.BlogEntriesResource;
import tutorial.rest.resources.BlogEntryResource;

import java.util.List;

/**
 * Created by Chris on 6/28/14.
 */
public class BlogEntriesResourceAsm extends ResourceAssemblerSupport<List<BlogEntry>, BlogEntriesResource> {
    public BlogEntriesResourceAsm() {
        super(BlogController.class, BlogEntriesResource.class);
    }

    @Override
    public BlogEntriesResource toResource(List<BlogEntry> blogEntries) {
        List<BlogEntryResource> resources = new BlogEntryResourceAsm().toResources(blogEntries);
        BlogEntriesResource listResource = new BlogEntriesResource();
        listResource.setEntries(resources);
        return listResource;
    }
}
