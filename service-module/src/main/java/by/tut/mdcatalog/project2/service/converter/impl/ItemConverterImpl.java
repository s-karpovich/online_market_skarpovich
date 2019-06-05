package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Item;
import by.tut.mdcatalog.project2.service.converter.ItemConverter;
import by.tut.mdcatalog.project2.service.model.ItemDTO;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public ItemDTO toDTO(Item item) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setUniqueNumber(item.getUniqueNumber());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setText(item.getText());
            return itemDTO;
    }

    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setPrice((itemDTO.getPrice()));
        item.setText(itemDTO.getText());
        return item;
    }
}
