package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void createGroup(Group group) {
        this.groupRepository.insert(group);
    }

    public Optional<Group> findGroup(String groupId) { return this.groupRepository.findById(groupId); }

    public boolean belongsToGroup(String userMail, String groupId) {
        // TODO: this.groupRepository.findOne()
        return false;
    }
}
