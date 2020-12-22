package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateMeal {
    final String groupId;
    final LocalDateTime startDate;
    final LocalDateTime endDate;
    final String description;
}
