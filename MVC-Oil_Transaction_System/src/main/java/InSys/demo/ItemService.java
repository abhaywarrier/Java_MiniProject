package InSys.demo;
import org.springframework.stereotype.Repository;
import org.vaadin.crudui.crud.CrudListener;
import java.util.Collection;

@Repository
public class ItemService implements CrudListener<Item> {

    private final ItemRepo repo;

    public ItemService(ItemRepo repo) {
        this.repo = repo;
    }

    @Override
    public Collection<Item> findAll() {
        return repo.findAll();
    }

    @Override
    public Item add(Item Item) {
        return repo.insert(Item);
    }

    @Override
    public Item update(Item Item) {
        return repo.save(Item);
    }

    @Override
    public void delete(Item Item) {
        repo.delete(Item);
    }

}