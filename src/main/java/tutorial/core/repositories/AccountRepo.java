package tutorial.core.repositories;

import tutorial.core.models.entities.Account;
import tutorial.core.models.entities.Blog;

/**
 * Created by Chris on 7/9/14.
 */
public interface AccountRepo {
    public Account findAccount(Long id);
    public Account createAccount(Account data);
}
