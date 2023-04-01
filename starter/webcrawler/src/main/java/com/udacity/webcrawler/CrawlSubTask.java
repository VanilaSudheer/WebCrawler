package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;
//TODO
/*
public class CrawlSubTask extends RecursiveAction {
    private final Clock clock;
    private final String url;
    private final Instant deadline;
    private final int maxDepth;
    private final Map<String, Integer> counts;
    private final Set<String> visitedUrls;
    private final PageParserFactory parserFactory;
    private final List<Pattern> ignoredUrls;


    CrawlSubTask(
            Clock clock, String url,
            Instant deadline,
            int maxDepth,
            Map<String, Integer> counts,
            Set<String> visitedUrls,
            PageParserFactory parserFactory,
            List<Pattern> ignoredUrls) {
        this.clock = clock;
        this.url = url;
        this.deadline = deadline;
        this.maxDepth = maxDepth;
        this.counts = counts;
        this.visitedUrls = visitedUrls;
        this.parserFactory = parserFactory;
        this.ignoredUrls = ignoredUrls;
    }

    @Override
    protected void compute() {

        List<RecursiveAction> subTasks = new ArrayList<>();

        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return;
        }
        for (Pattern pattern : ignoredUrls) {
            if (pattern.matcher(url).matches()) {
                return;
            }
        }
        if (visitedUrls.contains(url)) {
            return;
        }
        synchronized (this){
            if (visitedUrls.contains(url)) {
                return;
            }
            visitedUrls.add(url);
        }

        PageParser.Result result = parserFactory.get(url).parse();
        for (Map.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            if (counts.containsKey(e.getKey())) {
                counts.put(e.getKey(), e.getValue() + counts.get(e.getKey()));
            } else {
                counts.put(e.getKey(), e.getValue());
            }
        }
        for (String link : result.getLinks()) {
            CrawlSubTask task = new CrawlSubTask(clock,link, deadline, maxDepth - 1,
                    counts, visitedUrls,parserFactory,ignoredUrls);
            subTasks.add(task);

        }
        invokeAll(subTasks);

    }


}

 */
