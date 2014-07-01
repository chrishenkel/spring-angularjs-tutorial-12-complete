package tutorial.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tutorial.core.models.entities.Blog;
import tutorial.core.models.entities.BlogEntry;
import tutorial.core.services.BlogEntryService;
import tutorial.rest.mvc.BlogEntryController;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Chris on 6/19/14.
 */
public class BlogEntryControllerTest {
    @InjectMocks
    private BlogEntryController controller;

    @Mock
    private BlogEntryService service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getExistingBlogEntry() throws Exception {
        BlogEntry entry = new BlogEntry();
        entry.setId(1L);
        entry.setTitle("Test Title");

        Blog blog = new Blog();
        blog.setId(1L);

        entry.setBlog(blog);

        when(service.findBlogEntry(1L)).thenReturn(entry);

        mockMvc.perform(get("/rest/blog-entries/1"))
                .andExpect(jsonPath("$.title", is(entry.getTitle())))
                .andExpect(jsonPath("$.links[*].href",
                        hasItems(endsWith("/blogs/1"), endsWith("/blog-entries/1"))))
                .andExpect(jsonPath("$.links[*].rel",
                        hasItems(is("self"), is("blog"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistingBlogEntry() throws Exception {
        when(service.findBlogEntry(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/blog-entries/1"))
           .andExpect(status().isNotFound());
    }


    @Test
    public void deleteExistingBlogEntry() throws Exception {
        BlogEntry deletedBlogEntry = new BlogEntry();
        deletedBlogEntry.setId(1L);
        deletedBlogEntry.setTitle("Test Title");

        when(service.deleteBlogEntry(1L)).thenReturn(deletedBlogEntry);

        mockMvc.perform(delete("/rest/blog-entries/1"))
                .andExpect(jsonPath("$.title", is(deletedBlogEntry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blog-entries/1"))))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistingBlogEntry() throws Exception {
        when(service.deleteBlogEntry(1L)).thenReturn(null);

        mockMvc.perform(delete("/rest/blog-entries/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateExistingBlogEntry() throws Exception {
        BlogEntry updatedEntry = new BlogEntry();
        updatedEntry.setId(1L);
        updatedEntry.setTitle("Test Title");

        when(service.updateBlogEntry(eq(1L), any(BlogEntry.class)))
                .thenReturn(updatedEntry);

        mockMvc.perform(put("/rest/blog-entries/1")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(updatedEntry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blog-entries/1"))))
                .andExpect(status().isOk());
    }

    @Test
    public void updateNonExistingBlogEntry() throws Exception {
        when(service.updateBlogEntry(eq(1L), any(BlogEntry.class)))
                .thenReturn(null);

        mockMvc.perform(put("/rest/blog-entries/1")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
