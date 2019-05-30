package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Item;
import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {

    void persist(Item item);

   // void delete(Item item);

    //List<Item> getAll();
   List<Item> getAllWithOrder();

    Item getById(Long id);
}
