package tutorial.core.services;

import tutorial.core.models.entities.Account;
import tutorial.core.models.entities.Blog;

/**
 * Created by Chris on 6/28/14.
 */
public interface AccountService {
    public Account find(Long id);
    public Account create(Account data);
    public Blog create(Long accountId, Blog data);
}
