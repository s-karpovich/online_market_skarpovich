package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ItemDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ItemService {

    void saveItem(ItemDTO itemDTO);

    ItemDTO getItemById(Long id);

    List<ItemDTO> getItems();

    void deleteItemById(Long id);

    void deleteItems(Long[] ids);

}
