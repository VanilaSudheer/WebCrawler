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

public class ParallelRecursive extends RecursiveAction {
    private Clock clock;
    private List<Pattern> ignoredUrl;
    private int maxDepth;
    private PageParserFactory pageParserFactory;
    Instant deadline;
    Map<String, Integer> counts;
    Set<String> visitedUrls;
    private String url;

    public ParallelRecursive(Clock clock,List<Pattern> ignoredUrl, PageParserFactory pageParserFactory, String url, Instant deadline,int maxDepth, Map<String, Integer> counts, Set<String> visitedUrls) {
        this.clock = clock;
        this.ignoredUrl = ignoredUrl;
        this.pageParserFactory = pageParserFactory;
        this.deadline = deadline;
        this.visitedUrls = visitedUrls;
        this.url = url;
        this.maxDepth = maxDepth;
        this.counts = counts;
    }

    @Override
    protected void compute() {
        crawlInternal(url,deadline,maxDepth,counts,visitedUrls);
    }
    public void crawlInternal(
            String url,
            Instant deadline,
            int maxDepth,
            Map<String, Integer> counts,
            Set<String> visitedUrls) {
        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return;
        }
        for (Pattern pattern : ignoredUrl) {
            if (pattern.matcher(url).matches()) {
                return;
            }
        }
        /*
        if (visitedUrls.contains(url)) {
            return;
        }
        visitedUrls.add(url);

         */
        if (visitedUrls.contains(url)) {
            return;
        }
        synchronized (this){
            if (visitedUrls.contains(url)) {
                return;
            }
            visitedUrls.add(url);
        }

        PageParser.Result result = pageParserFactory.get(url).parse();
        for (Map.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            if (counts.containsKey(e.getKey())) {
                counts.put(e.getKey(), e.getValue() + counts.get(e.getKey()));
            } else {
                counts.put(e.getKey(), e.getValue());
            }
        }
        List<RecursiveAction> subtask =new ArrayList<>();
        for (String link : result.getLinks()) {
            ParallelRecursive parallelRecursive = new ParallelRecursive(clock,ignoredUrl,pageParserFactory,link,deadline,maxDepth-1,counts,visitedUrls);
            subtask.add(parallelRecursive);
        }
        invokeAll(subtask);
    }
}
