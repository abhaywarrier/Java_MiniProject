package InSys.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraderService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Trader> getAllTraders() {
        return mongoTemplate.findAll(Trader.class);
    }

    public List<Trader> getTradersByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Trader.class);
    }

    public List<Trader> getTradersByItemId(String itemId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("itemId").is(itemId));
        return mongoTemplate.find(query, Trader.class);
    }
}
