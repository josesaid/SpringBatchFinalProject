package com.mx.development.said.olano.listener.job;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;

@Slf4j
@Component
public class RetrieveNewsListener implements JobExecutionListener {
    @SneakyThrows
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(RetrieveNewsListener.class.getSimpleName()+" has been executed at: " + LocalDateTime.now() );
        if (jobExecution.getStatus() == BatchStatus.STARTED) {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL("https://expansion.mx/rss/tecnologia")));
            if(feed!=null)
                log.info("Comenzará la recolección remota de noticias");
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(RetrieveNewsListener.class.getSimpleName()+" has been executed at: " + LocalDateTime.now() );
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("La recolección de noticias terminó de manera exitosa.");
        }
    }

}
