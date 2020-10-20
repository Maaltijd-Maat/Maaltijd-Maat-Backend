package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Invite;
import com.hva.MaaltijdMaat.repository.InviteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}
