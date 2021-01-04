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

import static com.hva.MaaltijdMaat.util.ColorUtil.intToARGB;

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
    public ResponseEntity<Group> getGroup(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable("id") String groupId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
            String userId = user.getId();

            Group group = this.groupService.findGroup(groupId, userId);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Group>> getGroups(@RequestHeader(name = "Authorization") String token) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<Group> groups = groupService.findGroups(user.getId());
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Group> createGroup(@RequestHeader(name = "Authorization") String token, @RequestBody String groupName) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User creator = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            List<User> members = new ArrayList<>();
            members.add(creator);

            String color = intToARGB(groupName.hashCode());

            Group group = Group.builder()
                    .name(groupName)
                    .color(color)
                    .owner(creator)
                    .members(members)
                    .build();

            return new ResponseEntity<>(groupService.createGroup(group), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<HttpStatus> updateGroup(@RequestHeader(name = "Authorization") String token,
                                                  @PathVariable String groupId,
                                                  @RequestBody Group group) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
            String userId = user.getId();

            if (groupService.isUserMemberOfGroup(groupId, userId)) {
                groupService.updateGroup(groupId, group);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<HttpStatus> deleteGroup(@RequestHeader(name = "Authorization") String token,
                                                  @PathVariable String groupId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User user = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
            String userId = user.getId();

            if (groupService.isUserMemberOfGroup(groupId, userId)) {
                groupService.deleteGroup(groupId);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
