package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntry> getUserForSA() {
        Query query = new Query();
//        Here we can apply orOperator, andOperator and less than, etc.
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<UserEntry> users = mongoTemplate.find(query, UserEntry.class);
        return users;
    }
}