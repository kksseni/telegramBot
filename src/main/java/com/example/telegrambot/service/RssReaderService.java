package com.example.telegrambot.service;

import com.example.telegrambot.config.RssReaderConfig;
import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.HttpClientFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.telegrambot.config.RssReaderConfig.FEED_URL;

@Service
public class RssReaderService {

    private final RssReaderConfig rssReaderConfig;

    @Autowired
    public RssReaderService(RssReaderConfig rssReaderConfig) {
        this.rssReaderConfig = rssReaderConfig;
    }

    public List<String> readRssFeed() {
        List<String> htmlTextList = new ArrayList<>();

        return htmlTextList;
    }
}
