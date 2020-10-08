package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InviteService {
    private final InviteRepository inviteRepository;

    @Autowired
    public InviteService(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }
}
