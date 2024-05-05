package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.feed;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.News;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedCreator {
    public void createFeed(){
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_1.0");
        feed.setTitle("Test title");
        feed.setLink("http://www.somelink.com");
        feed.setDescription("Basic description");
    }
    public SyndEntry addEntry(SyndFeed syndFeed){
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle("Entry title");
        entry.setLink("http://www.somelink.com/entry1");
        syndFeed.setEntries(List.of(entry));
        return entry;
    }

    public SyndEntry addDescription(SyndEntry entry){
        SyndContent description = new SyndContentImpl();
        description.setType("text/html");
        description.setValue("First entry");
        entry.setDescription(description);
        return entry;
    }

    public SyndEntry addCategory(SyndEntry entry){
        List<SyndCategory> categories = new ArrayList<>();
        SyndCategory category = new SyndCategoryImpl();
        category.setName("Sophisticated category");
        categories.add(category);
        entry.setCategories(categories);
        return entry;
    }

    public void publishFeed(File file, SyndFeed feed) throws IOException, FeedException {
        Writer writer = new FileWriter(file);
        SyndFeedOutput syndFeedOutput = new SyndFeedOutput();
        syndFeedOutput.output(feed, writer);
        writer.close();
    }

    public List<News> readExternalFeed() throws IOException, FeedException {
        List<News> newsList = new ArrayList<>();
        URL feedSource = new URL("https://expansion.mx/rss/tecnologia");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        System.out.println("Author:" + feed.getAuthor());
        String author = feed.getAuthor();
        String type = feed.getFeedType();
        String feedTitle = feed.getTitle();
        String category = feed.getCategories()==null ? "" : feed.getCategories().get(0).toString();
        System.out.println(feed.getEntries().size());

        List<SyndEntryImpl> entries = feed.getEntries();
        Iterator<SyndEntryImpl> feedIterator = entries.iterator();
        int counter = 1;
        while(feedIterator.hasNext()){
            SyndEntryImpl entry = feedIterator.next();
            String entryTitle = entry.getTitle();
            //System.out.println("entryTitle: " + entryTitle);
            newsList.add(new News(counter++, author, type, null, feedTitle, category, entryTitle));
        }
        return newsList;
    }

}
