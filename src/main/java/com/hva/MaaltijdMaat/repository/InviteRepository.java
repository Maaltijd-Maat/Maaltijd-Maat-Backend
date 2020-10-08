package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Invite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InviteRepository extends MongoRepository<Invite, String> {}
