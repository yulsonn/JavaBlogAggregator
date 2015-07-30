package com.jscomp.jba.service;

import com.jscomp.jba.entity.Item;
import com.jscomp.jba.exception.RssException;
import com.jscomp.jba.rss.ObjectFactory;
import com.jscomp.jba.rss.TRss;
import com.jscomp.jba.rss.TRssChannel;
import com.jscomp.jba.rss.TRssItem;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class RssService {

    public List<Item> getItems(File file) throws RssException {
        return getItems(new StreamSource(file));
    }

    public List<Item> getItems(String url) throws RssException {
        return getItems(new StreamSource(url));
    }

    private List<Item> getItems(Source source) throws RssException{
        ArrayList<Item> list = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
            TRss rss = jaxbElement.getValue();

            List<TRssChannel> channels = rss.getChannel();
            for (TRssChannel channel : channels){
                List<TRssItem> items = channel.getItem();
                for(TRssItem rssItem : items) {
                    Item item = new Item();
                    item.setTitle(rssItem.getTitle());
                    item.setDescription(rssItem.getDescription());
                    item.setLink(rssItem.getLink());
                    Date pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(rssItem.getPubDate());
                    item.setPublishedDate(pubDate);
                    list.add(item);
                }
            }

        } catch (JAXBException e) {
            throw new RssException(e);
        } catch (ParseException e) {
            throw new RssException(e);
        }

        return list;
    }
}
