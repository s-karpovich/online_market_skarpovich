package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ItemRepository;
import by.tut.mdcatalog.project2.repository.model.Item;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.ItemService;
import by.tut.mdcatalog.project2.service.converter.ItemConverter;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public void saveItem(ItemDTO itemDTO) {
        itemDTO.setUniqueNumber(UUID.randomUUID().toString());
        itemRepository.persist(itemConverter.fromDTO(itemDTO));
    }

    @Override
    @Transactional
    public ItemDTO getItemById(Long id) {
        return itemConverter.toDTO(itemRepository.getById(id));
    }

    @Override
    @Transactional
    public List<ItemDTO> getItems() {
        List<ItemDTO> itemsDTO = new ArrayList<>();
        List<Item> itemList = itemRepository.getAll();
        for (Item item : itemList) {
            ItemDTO itemDTO = itemConverter.toDTO(item);
            itemsDTO.add(itemDTO);
        }
        return itemsDTO;
    }
    
    @Override
    @Transactional
    public void deleteItems(Long[] ids) {
        for (Long id : ids) {
            Item item = itemRepository.getById(id);
            if (item != null && !item.getDeleted()) {
                itemRepository.delete(item);
            }
        }
    }

    @Override
    @Transactional
    public void deleteItemById(Long id) {
        Item item = itemRepository.getById(id);
        if (item != null && !item.getDeleted()) {
            itemRepository.delete(item);
        }
    }
}

