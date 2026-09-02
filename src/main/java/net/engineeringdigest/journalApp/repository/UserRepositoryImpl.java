package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUserForSA(){

        Query query = new Query();
//        query.addCriteria(Criteria.where("userName").is("vipul"));

        //regex = regular expression
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$" ));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        //blacklist these users
//        query.addCriteria(Criteria.where("userName").nin("Rajat","Shanu"));
//        // bring users have these roles
//        query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));


//        // users with sentimentAnalysis having type boolean   : queryn on field type
//        query.addCriteria(Criteria.where("sentimentAnanlysis").type(JsonSchemaObject.Type.BsonType.BOOLEAN));

        List<User> users=mongoTemplate.find(query, User.class);
        return users;
    }
}
