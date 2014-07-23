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
    public List<Blog> findAllBlogs() {
        Query query = em.createQuery("SELECT b from Blog b");
        return query.getResultList();
    }

    @Override
    public Blog findBlog(Long id) {
        return em.find(Blog.class, id);
    }

    @Override
    public Blog findBlogByTitle(String title) {
        Query query = em.createQuery("SELECT b from Blog b where b.title=?1");
        query.setParameter(1, title);
        List<Blog> blogs = query.getResultList();
        if(blogs.isEmpty()) {
            return null;
        } else {
            return blogs.get(0);
        }
    }

    @Override
    public List<Blog> findBlogsByAccount(Long accountId) {
        Query query = em.createQuery("SELECT b from Blog b where b.owner.id=?1");
        query.setParameter(1, accountId);
        return query.getResultList();
    }
}
