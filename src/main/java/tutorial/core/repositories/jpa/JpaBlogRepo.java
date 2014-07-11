package tutorial.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import tutorial.core.models.entities.Blog;
import tutorial.core.repositories.BlogRepo;
import tutorial.core.services.util.BlogList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Chris on 7/10/14.
 */
@Repository
public class JpaBlogRepo implements BlogRepo {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Blog createBlog(Blog data) {
        em.persist(data);
        return data;
    }

    @Override
    public BlogList findAllBlogs() {
        Query query = em.createQuery("SELECT blog from Blog blog");
        return new BlogList(query.getResultList());
    }

    @Override
    public Blog findBlog(Long id) {
        return em.find(Blog.class, id);
    }
}
