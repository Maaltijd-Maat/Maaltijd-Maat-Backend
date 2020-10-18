package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.repository.GroupRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final MongoTemplate mongoTemplate;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, MongoTemplate mongoTemplate) {
        this.groupRepository = groupRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void createGroup(Group group) {
        this.groupRepository.insert(group);
    }

    public Optional<Group> findGroup(String groupId) { return this.groupRepository.findById(groupId); }

    public List<Group> findGroupsByUser(String userId) {
        Query query = new Query(Criteria.where("members.$id").is(new ObjectId(userId)));
        return mongoTemplate.find(query, Group.class);
    }
}
