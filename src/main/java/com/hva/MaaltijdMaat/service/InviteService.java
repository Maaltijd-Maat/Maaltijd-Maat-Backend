package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Invite;
import com.hva.MaaltijdMaat.repository.InviteRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class InviteService {
    private final MongoTemplate mongoTemplate;
    private final InviteRepository inviteRepository;

    @Autowired
    public InviteService(MongoTemplate mongoTemplate, InviteRepository inviteRepository) {
        this.mongoTemplate = mongoTemplate;
        this.inviteRepository = inviteRepository;
    }

    /**
     * Creates a new invite.
     * @param invite invite to be added to the database
     * @return The created invite including id
     */
    public Invite createNewInvite(Invite invite) {
        return inviteRepository.insert(invite);
    }

    public Invite findInvite(String inviteId, String inviteeId) {
        Query query = new Query(Criteria
                .where("_id")
                .is(new ObjectId(inviteId))
                .and("invitee.$id")
                .is(new ObjectId(inviteeId))
        );

        return mongoTemplate.findOne(query, Invite.class);
    }

    /**
     * Deletes specified invite from the database.
     * @param inviteId id of the invite
     */
    public void deleteInvite(String inviteId) {
        inviteRepository.deleteById(inviteId);
    }
}
