package tutorial.core.services;

import tutorial.core.entities.BlogEntry;

/**
 * Created by Chris on 6/27/14.
 */
public interface BlogEntryService {
    public BlogEntry find(Long id);
}
