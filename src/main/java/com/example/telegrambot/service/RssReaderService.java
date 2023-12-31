package com.example.telegrambot.service;

import com.example.telegrambot.config.RssReaderConfig;
import com.example.telegrambot.model.Feed;
import com.example.telegrambot.model.FeedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.example.telegrambot.config.RssReaderConfig.*;

@Service
@RequiredArgsConstructor
public class RssReaderService {

    private final RssReaderConfig rssReaderConfig;

    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;
            // Set header values intial to the empty string
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    switch (localPart) {
                        case ITEM -> {
                            if (isFeedHeader) {
                                isFeedHeader = false;
                                feed = new Feed(title, link, description, language,
                                        copyright, pubdate);
                            }
                            event = eventReader.nextEvent();
                        }
                        case TITLE -> title = getCharacterData(event, eventReader);
                        case DESCRIPTION -> description = getCharacterData(event, eventReader);
                        case LINK -> link = getCharacterData(event, eventReader);
                        case GUID -> guid = getCharacterData(event, eventReader);
                        case LANGUAGE -> language = getCharacterData(event, eventReader);
                        case AUTHOR -> author = getCharacterData(event, eventReader);
                        case PUB_DATE -> pubdate = getCharacterData(event, eventReader);
                        case COPYRIGHT -> copyright = getCharacterData(event, eventReader);
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        FeedMessage message = new FeedMessage();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        assert feed != null;
                        feed.getMessages().add(message);
                        event = eventReader.nextEvent();
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            URL url = new URL(FEED_URL);
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
