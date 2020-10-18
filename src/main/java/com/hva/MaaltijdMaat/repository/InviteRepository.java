package com.hva.MaaltijdMaat.repository;

import com.hva.MaaltijdMaat.model.Group;
import com.hva.MaaltijdMaat.model.Invite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InviteRepository extends MongoRepository<Invite, String> {
}
