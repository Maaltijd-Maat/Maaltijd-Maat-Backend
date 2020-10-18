package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.GroupService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/group")
@CrossOrigin
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public GroupController(GroupService groupService,
                           UserService userService,
                           JwtTokenUtil jwtTokenUtil) {
        this.groupService = groupService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable("id") String groupId) {
        try {
            Optional<Group> _group = this.groupService.findGroup(groupId);

            return _group.map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Group>> getGroups(@RequestHeader(name = "Authorization") String token) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<Group> groups = groupService.findGroupsByUser(user.getId());
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<HttpStatus> createGroup(@RequestHeader(name = "Authorization") String token, @RequestBody String groupName) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User creator = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<User> members = new ArrayList<>();
            members.add(creator);

            Group _group = Group.builder()
                    .name(groupName)
                    .owner(creator)
                    .members(members)
                    .build();

            groupService.createGroup(_group);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}