package com.kohei3110.javaonazurecosmosdemo.CosmosCRUD;

import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.CreateItemRepository;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.DeleteItemRepository;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.ReadAllItemsRepository;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.ReadItemRepository;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.repository.UpdateItemRepository;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.CreateItemService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.DeleteItemService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.ReadAllItemsService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.ReadItemService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.UpdateItemService;

public class Factory {
    
    private CreateItemRepository createItemRepository;
    private CreateItemService createItemService;
    private ReadAllItemsRepository readAllItemsRepository;
    private ReadAllItemsService readAllItemsService;
    private ReadItemRepository readItemRepository;
    private ReadItemService readItemService;
    private UpdateItemRepository updateItemRepository;
    private UpdateItemService updateItemService;
    private DeleteItemRepository deleteItemRepository;
    private DeleteItemService deleteItemService;

    public Factory(){
        this.createItemRepository = new CreateItemRepository();
        this.createItemService = new CreateItemService(this.createItemRepository);
        this.readAllItemsRepository = new ReadAllItemsRepository();
        this.readAllItemsService = new ReadAllItemsService(this.readAllItemsRepository);
        this.readItemRepository = new ReadItemRepository();
        this.readItemService = new ReadItemService(this.readItemRepository);
        this.updateItemRepository = new UpdateItemRepository();
        this.updateItemService = new UpdateItemService(this.updateItemRepository);
        this.deleteItemRepository = new DeleteItemRepository();
        this.deleteItemService = new DeleteItemService(this.deleteItemRepository);
    }

    public CreateItemService injectCreateItemService() {
        return this.createItemService;
    }

    public ReadAllItemsService injectReadAllItemService() {
        return this.readAllItemsService;
    }

    public ReadItemService injectReadItemService() {
        return this.readItemService;
    }

    public UpdateItemService injectUpdateItemService() {
        return this.updateItemService;
    }

    public DeleteItemService injDeleteItemService() {
        return this.deleteItemService;
    }
}
