package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateMeal {
    final String groupId;
    final String title;
    final LocalDateTime start;
    final LocalDateTime end;
    final String description;
}
