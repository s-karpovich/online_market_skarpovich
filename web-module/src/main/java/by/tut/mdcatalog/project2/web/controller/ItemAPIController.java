package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ItemService;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemAPIController {
    private static final Logger logger = LoggerFactory.getLogger(ItemAPIController.class);
    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping
    public ResponseEntity addItem(@RequestBody ItemDTO itemDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(result.toString(), HttpStatus.BAD_REQUEST);
        }
        itemService.saveItem(itemDTO);
        logger.info("Item added via REST API (ID): {}", itemDTO.getId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItemById(id);
        logger.info("Item deleted via REST API (ID): {}");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getItems() {
        List<ItemDTO> itemsDTO = itemService.getAll();
        logger.info("Requested all Items via REST API");
        return new ResponseEntity(itemsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getItemById(@PathVariable("id") Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        if (itemDTO != null) {
            logger.info("Item requested via REST API (ID): {}", id);
            return new ResponseEntity(itemDTO, HttpStatus.OK);
        } else {
            logger.info("Item was not found via REST API (ID): {}", id);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
