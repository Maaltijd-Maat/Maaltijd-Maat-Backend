package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.model.Invite;
import com.hva.MaaltijdMaat.model.User;
import com.hva.MaaltijdMaat.service.EmailService;
import com.hva.MaaltijdMaat.service.GroupService;
import com.hva.MaaltijdMaat.service.InviteService;
import com.hva.MaaltijdMaat.service.UserService;
import com.hva.MaaltijdMaat.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/invite")
public class InviteController {
    private final InviteService inviteService;
    private final GroupService groupService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    @Autowired
    public InviteController(InviteService inviteService,
                            UserService userService,
                            GroupService groupService,
                            EmailService emailService,
                            JwtTokenUtil jwtTokenUtil) {
        this.inviteService = inviteService;
        this.groupService = groupService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Invite> createInvite(@RequestHeader(name = "Authorization") String token,
                                               @RequestParam String groupId,
                                               @RequestParam String inviteeMail) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User inviter = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));
            User invitee = userService.getUserInformation(inviteeMail);

            // Retrieve group and check if inviter is member of the group
            Group group = groupService.findGroup(groupId, inviter.getId());

            // Check if invitee is not already a member of the group
            if (groupService.isUserMemberOfGroup(group.getId(), invitee.getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Invite invite = Invite.builder()
                    .group(group)
                    .invitee(invitee)
                    .inviter(inviter)
                    .expireDate(new Date())
                    .build();

            invite = inviteService.createNewInvite(invite);
            emailService.constructGroupInvitationEmail(invite);

            return new ResponseEntity<>(invite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invite> getInvite(@RequestHeader(name = "Authorization") String token,
                                            @PathVariable("id") String inviteId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User invitee = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Invite invite = inviteService.findInvite(inviteId, invitee.getId());
            return new ResponseEntity<>(invite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Invite> acceptInvite(@RequestHeader(name = "Authorization") String token,
                                               @PathVariable("id") String inviteId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User invitee = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Invite invite = inviteService.findInvite(inviteId, invitee.getId());

            // Check if the accepter is the invitee
            if (invitee.equals(invite.getInvitee())) {
                groupService.addMember(invite.getGroup().getId(), invitee);

                // Remove invite
                inviteService.deleteInvite(inviteId);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/decline")
    public ResponseEntity<Invite> declineInvite(@RequestHeader(name = "Authorization") String token,
                                               @PathVariable("id") String inviteId) {
        try {
            String jwtToken = jwtTokenUtil.refactorToken(token);
            User invitee = userService.getUserInformation(jwtTokenUtil.getUsernameFromToken(jwtToken));

            Invite invite = inviteService.findInvite(inviteId, invitee.getId());

            // Check if the decliner is the invitee
            if (invitee.equals(invite.getInvitee())) {
                inviteService.deleteInvite(inviteId);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
