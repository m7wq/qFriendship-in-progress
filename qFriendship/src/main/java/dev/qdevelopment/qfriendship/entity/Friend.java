package dev.qdevelopment.qfriendship.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter@Setter
public class Friend {
    UUID uuid;
    String name;
}
