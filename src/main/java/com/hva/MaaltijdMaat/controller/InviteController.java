package com.hva.MaaltijdMaat.controller;

import com.hva.MaaltijdMaat.model.Invite;
import com.hva.MaaltijdMaat.service.GroupService;
import com.hva.MaaltijdMaat.service.InviteService;
import com.hva.MaaltijdMaat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public class InviteController {
    private final GroupService groupService;
    private final InviteService inviteService;
    private final UserService userService;

    @Autowired
    public InviteController(GroupService groupService, InviteService inviteService, UserService userService) {
        this.groupService = groupService;
        this.inviteService = inviteService;
        this.userService = userService;
    }

    /**
     * Invites someone by e-mail to specified group.
     * @param invite
     * @param token
     * @return
     */
    public ResponseEntity<HttpStatus> inviteMember(Invite invite, @RequestHeader(name = "Authorization") String token) {
        /*
        TODO:
        1.  Check if inviter is a member of provided group.
        2.  Check if invitee is not already in group.
        3.  Check if invitee is not already invited.
        4.  Invite for group
         */

        // User inviter = userService.getUserInformation(inviterMail);



        Invite _invite = Invite.builder()
                            .groupId(invite.getGroupId())
                            .inviteeEmail(invite.getInviteeEmail())
                            // .inviter(inviter)
                            .build();

        return null;
    }

    public ResponseEntity<HttpStatus> acceptInvite(String inviteId, String userMail) {
        /*
        TODO: Transactional
        1.  Check if invite exist and invitee mail belongs to invite
        2.  Add member to group
         */
        return null;
    }

    public ResponseEntity<HttpStatus> declineInvite(String inviteId, String userMail) {
        /*
        TODO: Transactional
        1.  Check if invite exists and user's mail belongs to invite
        2.  Remove invite from database
         */
        return null;
    }

    public ResponseEntity<HttpStatus> withdrawInvite(String inviteId, String memberMail) {
        /*
        TODO: Transactional
        1.  Check if invite exists and member's mail belongs to group of invite
        2.  Remove invite from database
         */
        return null;
    }

    public ResponseEntity<Invite> getInvite(String inviteId, String inviteeEmail) {
        /*
        TODO
        1.  Check if invite exists
        2.  Check if provided mail belongs to the user who is logged in
        3.  Return invite
         */

        return null;
    }
}
