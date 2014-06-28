package tutorial.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.BlogService;
import tutorial.core.services.exceptions.BlogNotFoundException;
import tutorial.rest.mvc.BlogController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Chris on 6/28/14.
 */
public class BlogControllerTest {
    @InjectMocks
    private BlogController controller;

    @Mock
    private BlogService blogService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createBlogEntryExistingBlog() throws Exception {
        Blog blog = new Blog();
        blog.setId(1L);

        BlogEntry entry = new BlogEntry();
        entry.setTitle("Test Title");
        entry.setId(1L);

        when(blogService.find(1L)).thenReturn(blog);
        when(blogService.create(eq(1L), any(BlogEntry.class))).thenReturn(entry);

        mockMvc.perform(post("/rest/blogs/1/blog-entries")
                .content("{\"title\":\"Generic Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(entry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("rest/blog-entries/1"))))
                .andExpect(header().string("Location", endsWith("rest/blog-entries/1")))
                .andExpect(status().isCreated());
    }


    @Test
    public void createBlogEntryNonExistingBlog() throws Exception {
        when(blogService.create(eq(1L), any(BlogEntry.class))).thenThrow(new BlogNotFoundException());

        mockMvc.perform(post("/rest/blogs/1/blog-entries")
                .content("{\"title\":\"Generic Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listBlogEntriesForExistingBlog() throws Exception {

        BlogEntry entryA = new BlogEntry();
        entryA.setId(1L);
        entryA.setTitle("Test Title");

        BlogEntry entryB = new BlogEntry();
        entryB.setId(2L);
        entryB.setTitle("Test Title");

        List<BlogEntry> blogListings = new ArrayList();
        blogListings.add(entryA);
        blogListings.add(entryB);

        when(blogService.findAll(1L)).thenReturn(blogListings);

        mockMvc.perform(get("/rest/blogs/1/blog-entries"))
                .andDo(print())
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blogs/1/blog-entries"))))
                .andExpect(jsonPath("$.entries[*].title", hasItem(is("Test Title"))))
                .andExpect(status().isOk());
    }


    @Test
    public void listBlogEntriesForNonExistingBlog() throws Exception {
        when(blogService.findAll(1L)).thenThrow(new BlogNotFoundException());

        mockMvc.perform(get("/rest/blogs/1/blog-entries"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
