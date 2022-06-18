package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service;

import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.ReadAllItemsRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReadAllItemsService {
    
    private ReadAllItemsRepository repository;

    public ReadAllItemsService(ReadAllItemsRepository repository) {
        this.repository = repository;
    }

    public List<Item> requestCosmosDB() {
        return repository.requestCosmosDBSync();
    }

    public List<Item> requestCosmosDBAsync() throws Exception {
        return repository.requesetCosmosDBAsync();
    }
}
