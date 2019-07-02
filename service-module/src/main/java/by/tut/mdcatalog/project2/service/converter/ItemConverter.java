package by.tut.mdcatalog.project2.service.converter;


import by.tut.mdcatalog.project2.repository.model.Item;
import by.tut.mdcatalog.project2.service.model.ItemDTO;

public interface ItemConverter {

    ItemDTO toDTO(Item item);

    Item fromDTO(ItemDTO itemDTO);
}
