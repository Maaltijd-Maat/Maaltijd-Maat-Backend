package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class InviteService {
    private final MongoTemplate mongoTemplate;
    private final GroupRepository groupRepository;

    @Autowired
    public InviteService(MongoTemplate mongoTemplate, GroupRepository groupRepository) {
        this.mongoTemplate = mongoTemplate;
        this.groupRepository = groupRepository;
    }
}
