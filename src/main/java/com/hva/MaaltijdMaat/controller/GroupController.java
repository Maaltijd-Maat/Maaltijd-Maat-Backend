package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/group")
@CrossOrigin
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<Group> getGroup(@RequestParam String groupId) {
        try {
            Optional<Group> group = this.groupService.findGroup(groupId);

            return group.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HttpStatus> createGroup(/* TODO */ @RequestParam String creatorEmailAdress, @RequestBody Group group) {
        try {
            // User author = userService.findUser(creatorEmailAdress);
            Group _group = Group.builder()
                    .name(group.getName())
                    .build();

            groupService.createGroup(_group);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
