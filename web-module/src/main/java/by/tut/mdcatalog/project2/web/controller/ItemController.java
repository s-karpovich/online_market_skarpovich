package by.tut.mdcatalog.project2.web.controller;

import by.tut.mdcatalog.project2.service.ItemService;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        List<ItemDTO> itemsDTO = itemService.getItems();
        model.addAttribute("items", itemsDTO);
        return "items";
    }

    @GetMapping("/items/{id}")
    public String getItemById(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        ItemDTO itemDTO = itemService.getItemById(id);
        modelMap.addAttribute("item", itemDTO);
        return "item";
    }

    @PostMapping("/items/delete")
    public String deleteItems(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids == null) {
            logger.info("Delete incompleted: no users selected");
            return "/items";
        } else {
            itemService.deleteItems(ids);
            logger.info("Items deleted(IDs):{}", ids);
            return "redirect:/success";
        }
    }

    @PostMapping("/items/copy")
    public String copyItem(@RequestParam(value = "id", required = false) Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        itemDTO.setId(null);
        itemDTO.setName(itemDTO.getName()+"_copy");
        itemService.saveItem(itemDTO);
        logger.info("Item copied(IDs):{}", id);
        return "redirect:/success";
    }
}
