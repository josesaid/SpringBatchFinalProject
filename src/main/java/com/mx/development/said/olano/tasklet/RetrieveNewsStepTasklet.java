package com.mx.development.said.olano.tasklet;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import com.mx.development.said.olano.entity.News;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Data
public class RetrieveNewsStepTasklet implements Tasklet {

	public static List<News> newsList = new ArrayList<>();

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException, FeedException {
		log.info("Esta dentro del step: RetrieveNewsStepTasklet");

		SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL("https://expansion.mx/rss/tecnologia")));

		String author = feed.getAuthor();
		String type = feed.getFeedType();
		String feedTitle = feed.getTitle();
		System.out.println(feed.getEntries().size());

		List<SyndEntryImpl> entries = feed.getEntries();
		Iterator<SyndEntryImpl> feedIterator = entries.iterator();
		int counter = 1;
		while(feedIterator.hasNext()){
			SyndEntryImpl entry = feedIterator.next();
			String entryTitle = entry.getTitle();
			this.newsList.add(new News(null, author, type, null, feedTitle, null, entryTitle));
		}
		return RepeatStatus.FINISHED;
	}

}