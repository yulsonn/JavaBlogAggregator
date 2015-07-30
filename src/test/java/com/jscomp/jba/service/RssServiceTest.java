package com.jscomp.jba.service;


import com.jscomp.jba.entity.Item;
import com.jscomp.jba.exception.RssException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class RssServiceTest {

    private RssService rssService;

    @Before
    public void setUp() throws Exception{
        rssService = new RssService();

    }

    @Test
    public void testGetItemsFile() throws RssException {
        List<Item> items = rssService.getItems(new File("test-rss/javavids.xml"));
        assertEquals(10, items.size());
        Item firstItem = items.get(0);
        assertEquals("How to solve Source not found error during debug in Eclipse", firstItem.getTitle());
        assertEquals("23 06 2014 00:35:49", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
    }
}
