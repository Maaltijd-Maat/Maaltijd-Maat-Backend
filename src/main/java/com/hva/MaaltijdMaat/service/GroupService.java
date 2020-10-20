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

    /**
     * Find group by id and user id
     * @param groupId id of the group
     * @param userId id of the user
     * @return a group if there's a group found and the user is a member of the group;
     *         null if the group is not found or/and if the user is not a member of the group
     */
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
     * Creates a new group.
     * @param group group to be added to the database
     * @return The created group
     */
    public Group createGroup(Group group) {
        return this.groupRepository.insert(group);
    }

    /**
     * Find groups that the specified user belongs to.
     * @param userId id of the user
     * @return A list of groups of which the specified user is a member
     */
    public List<Group> findGroups(String userId) {
        Query query = new Query(Criteria.where("members.$id").is(new ObjectId(userId)));
        return mongoTemplate.find(query, Group.class);
    }

    /**
     * Updates a group specified by id by the specified updated group.
     * @param id id of group to be updated
     * @param updatedGroup group with updated fields
     * @return The updated group
     */
    public Optional<Group> updateGroup(String id, Group updatedGroup) {
        return this.groupRepository.findById(id).map(
                group -> {
                    group.setName(updatedGroup.getName());
                    return groupRepository.save(group);
                });
    }

    /**
     * Delete by id specified group.
     * @param id id of the group to be deleted
     */
    public void deleteGroup(String id) {
        this.groupRepository.deleteById(id);
    }

    /**
     * Checks if the by id specified user is a member of the by id specified group.
     * @param groupId group id
     * @param userId user id
     * @return true if the user is a member of the group; false if the user is not a member of the group
     */
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
