package tutorial.tutorial.core.lists;

import tutorial.core.entities.BlogEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 6/28/14.
 */
public class BlogEntryList {
    private List<BlogEntry> entries = new ArrayList<BlogEntry>();

    public List<BlogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }
}
