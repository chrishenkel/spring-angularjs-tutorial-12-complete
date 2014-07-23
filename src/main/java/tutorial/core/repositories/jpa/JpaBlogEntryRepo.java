package tutorial.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import tutorial.core.models.entities.Account;
import tutorial.core.models.entities.BlogEntry;
import tutorial.core.repositories.BlogEntryRepo;
import tutorial.core.services.util.BlogEntryList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Chris on 7/10/14.
 */
@Repository
public class JpaBlogEntryRepo implements BlogEntryRepo {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BlogEntry findBlogEntry(Long id) {
        return em.find(BlogEntry.class, id);
    }

    @Override
    public BlogEntry deleteBlogEntry(Long id) {
        BlogEntry entry = em.find(BlogEntry.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public BlogEntry updateBlogEntry(Long id, BlogEntry data) {
        BlogEntry entry = em.find(BlogEntry.class, id);
        entry.setTitle(data.getTitle());
        entry.setContent(data.getContent());
        return entry;
    }

    @Override
    public BlogEntry createBlogEntry(BlogEntry data) {
        em.persist(data);
        return data;
    }

    @Override
    public List<BlogEntry> findByBlogId(Long blogId) {
        Query query = em.createQuery("SELECT b FROM BlogEntry b WHERE b.blog.id=?1");
        query.setParameter(1, blogId);
        return query.getResultList();
    }
}
