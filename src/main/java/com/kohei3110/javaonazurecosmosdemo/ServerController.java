package com.kohei3110.javaonazurecosmosdemo;

import java.util.List;
import java.util.logging.Logger;

import com.azure.cosmos.models.CosmosItemResponse;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.Factory;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.model.Item;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.CreateItemService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.DeleteItemService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.ReadAllItemsService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.ReadItemService;
import com.kohei3110.javaonazurecosmosdemo.CosmosCRUD.service.UpdateItemService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@ComponentScan("{com.kohei3110.javaonazureblobdemo.CosmosCRUD}")
public class ServerController {

	Logger logger = Logger.getLogger(ServerController.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(ServerController.class, args);
	}

	Factory factory = new Factory();

	@PostMapping("items/sync")
	public CosmosItemResponse<Item> createItemSync(@RequestBody Item item) throws Exception {
		CreateItemService createItemService = factory.injectCreateItemService();
		try {
			return createItemService.requestCosmosDBSync(item);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("create item operation has failed");
		}
	}

	@PostMapping("/items/async")
	public Mono<CosmosItemResponse<Item>> createItemAsync(@RequestBody Item item) throws Exception {
		CreateItemService createItemService = factory.injectCreateItemService();
		try {
			return createItemService.requestCosmosDBAsync(item);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("create item operation has failed");
		}
	}

	@GetMapping("/items/sync")
	public List<Item> getItemSync() throws Exception {
		ReadAllItemsService readAllItemsService = factory.injectReadAllItemService();
		try {
			return readAllItemsService.requestCosmosDB();
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("get all item operation has failed");
		}
	}

	@GetMapping("/items/async")
	public List<Item> getItemAsync() throws Exception {
		ReadAllItemsService readAllItemsService = factory.injectReadAllItemService();
		try {
			return readAllItemsService.requestCosmosDBAsync();
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("get all item operation has failed");
		}
	}

	@GetMapping("/items/sync/{id}")
	public Item getItemByIdSync(@PathVariable("id") String id) throws Exception {
		ReadItemService readItemService = factory.injectReadItemService();
		try {
			return readItemService.requestCosmosDBSync(id);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("get item operation has failed");
		}
	}

	@GetMapping("/items/async/{id}")
	public Mono<CosmosItemResponse<Item>> getItemByIdAsync(@PathVariable("id") String id) throws Exception {
		ReadItemService readItemService = factory.injectReadItemService();
		try {
			return readItemService.requestCosmosDBAsync(id);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("get item operation has failed");
		}
	}

	@PutMapping("/items/sync")
	public Item updateItemSync(@RequestBody Item item) throws Exception {
		UpdateItemService updateItemService = factory.injectUpdateItemService();
		try {
			return updateItemService.requestCosmosDBSync(item);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("update item operation has failed");
		}
	}

	@PutMapping("/items/async")
	public Item updateItemAsync(@RequestBody Item item) throws Exception {
		UpdateItemService updateItemService = factory.injectUpdateItemService();
		try {
			return updateItemService.requestCosmosDBAsync(item);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("update item operation has failed");
		}
	}

	@DeleteMapping("/items/sync/{id}")
	public ResponseEntity<String> deleteItemSync(@PathVariable("id") String id) throws Exception {
		DeleteItemService deleteItemService = factory.injDeleteItemService();
		try {
			CosmosItemResponse<Object> response = deleteItemService.requestCosmosDBSync(id);
			if (Integer.valueOf(response.getStatusCode()) == 204) {
				return new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
			throw new Exception("delete item operation has failed");
		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new Exception("delete item operation has failed");
		}
	}

	@DeleteMapping("/items/async/{id}")
	public ResponseEntity<String> deleteItemAsync(@PathVariable("id") String id) throws Exception {
		DeleteItemService deleteItemService = factory.injDeleteItemService();
		CosmosItemResponse<Object> response = deleteItemService.requestCosmosDBAsync(id);
		if (Integer.valueOf(response.getStatusCode()) == 204) {
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}