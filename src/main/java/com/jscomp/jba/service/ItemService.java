package com.jscomp.jba.service;

import com.jscomp.jba.entity.Item;
import com.jscomp.jba.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems(){
        return itemRepository.findAll(new PageRequest(0, 20, Sort.Direction.DESC, "publishedDate")).getContent();
    }
}
