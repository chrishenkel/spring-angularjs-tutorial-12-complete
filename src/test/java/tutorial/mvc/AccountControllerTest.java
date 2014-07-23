package tutorial.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tutorial.core.models.entities.Account;
import tutorial.core.models.entities.Blog;
import tutorial.core.services.AccountService;
import tutorial.core.services.exceptions.AccountDoesNotExistException;
import tutorial.core.services.exceptions.AccountExistsException;
import tutorial.core.services.exceptions.BlogExistsException;
import tutorial.core.services.util.BlogList;
import tutorial.rest.mvc.AccountController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
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
public class AccountControllerTest {
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<Account> accountCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        accountCaptor = ArgumentCaptor.forClass(Account.class);
    }

    @Test
    public void findAllBlogsForAccount() throws Exception {
        List<Blog> list = new ArrayList<Blog>();

        Blog blogA = new Blog();
        blogA.setId(1L);
        blogA.setTitle("Title A");
        list.add(blogA);

        Blog blogB = new Blog();
        blogB.setId(2L);
        blogB.setTitle("Title B");
        list.add(blogB);

        BlogList blogList = new BlogList(list);

        when(service.findBlogsByAccount(1L)).thenReturn(blogList);

        mockMvc.perform(get("/rest/accounts/1/blogs"))
                .andExpect(jsonPath("$.blogs[*].title",
                        hasItems(endsWith("Title A"), endsWith("Title B"))))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllBlogsForNonExistingAccount() throws Exception {
        List<Blog> list = new ArrayList<Blog>();

        Blog blogA = new Blog();
        blogA.setId(1L);
        blogA.setTitle("Title A");
        list.add(blogA);

        Blog blogB = new Blog();
        blogB.setId(2L);
        blogB.setTitle("Title B");
        list.add(blogB);

        BlogList blogList = new BlogList(list);

        when(service.findBlogsByAccount(1L)).thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(get("/rest/accounts/1/blogs"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void createBlogExistingAccount() throws Exception {
        Blog createdBlog = new Blog();
        createdBlog.setId(1L);
        createdBlog.setTitle("Test Title");

        when(service.createBlog(eq(1L), any(Blog.class))).thenReturn(createdBlog);

        mockMvc.perform(post("/rest/accounts/1/blogs")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blogs/1"))))
                .andExpect(header().string("Location", endsWith("/blogs/1")))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBlogNonExistingAccount() throws Exception {
        when(service.createBlog(eq(1L), any(Blog.class))).thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(post("/rest/accounts/1/blogs")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createBlogExistingBlogName() throws Exception {
        when(service.createBlog(eq(1L), any(Blog.class))).thenThrow(new BlogExistsException());

        mockMvc.perform(post("/rest/accounts/1/blogs")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void createAccountNonExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setPassword("test");
        createdAccount.setName("test");

        when(service.createAccount(any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/rest/accounts")
                .content("{\"name\":\"test\",\"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/accounts/1")))
                .andExpect(jsonPath("$.name", is(createdAccount.getName())))
                .andExpect(status().isCreated());

        verify(service).createAccount(accountCaptor.capture());

        String password = accountCaptor.getValue().getPassword();
        assertEquals("test", password);
    }

    @Test
    public void createAccountExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setPassword("test");
        createdAccount.setName("test");

        when(service.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

        mockMvc.perform(post("/rest/accounts")
                .content("{\"name\":\"test\",\"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getExistingAccount() throws Exception {
        Account foundAccount = new Account();
        foundAccount.setId(1L);
        foundAccount.setPassword("test");
        foundAccount.setName("test");

        when(service.findAccount(1L)).thenReturn(foundAccount);

        mockMvc.perform(get("/rest/accounts/1"))
                .andDo(print())
                .andExpect(jsonPath("$.password", is(nullValue())))
                .andExpect(jsonPath("$.name", is(foundAccount.getName())))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistingAccount() throws Exception {
        when(service.findAccount(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/accounts/1"))
                .andExpect(status().isNotFound());
    }
}
