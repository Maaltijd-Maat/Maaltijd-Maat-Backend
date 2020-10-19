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

@Service
public class GroupService {
    private final MongoTemplate mongoTemplate;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, MongoTemplate mongoTemplate) {
        this.groupRepository = groupRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Group createGroup(Group group) {
        return this.groupRepository.insert(group);
    }

    public Group findGroup(String groupId, String userId) {
        Query query = new Query(Criteria
                .where("_id")
                .is(new ObjectId(groupId))
                .and("members.$id")
                .is(new ObjectId(userId))
        );

        return mongoTemplate.findOne(query, Group.class);
    }

    /**
     * Find groups by specified user id.
     * @param userId id of the user
     * @return A list of groups of which the specified user is a member
     */
    public List<Group> findGroupsByUser(String userId) {
        Query query = new Query(Criteria.where("members.$id").is(new ObjectId(userId)));
        return mongoTemplate.find(query, Group.class);
    }

    public void updateGroup(String id, Group updatedGroup) {
        this.groupRepository.findById(id).map(
                group -> {
                    group.setName(updatedGroup.getName());
                    return groupRepository.save(group);
                });
    }

    public void deleteGroup(String id) {
        this.groupRepository.deleteById(id);
    }

    public boolean isUserMemberOfGroup(String groupId, String userId) {
        Query query = new Query(Criteria
                .where("_id")
                    .is(new ObjectId(groupId))
                .and("members.$id")
                    .is(new ObjectId(userId))
        );

        return mongoTemplate.exists(query, Group.class);
    }
}
