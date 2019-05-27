package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ItemDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ItemService {
    @Transactional
    void saveItem(ItemDTO itemDTO);

    @Transactional
    ItemDTO getItemById(Long id);

    @Transactional
    List<ItemDTO> getAll();

    void deleteItemById(Long id);

    void deleteItems(Long[] ids);

}
