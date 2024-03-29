package org.churk.telegrambot.handler.fact;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "facts")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Fact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID factId;
    @Column(length = 3500)
    private final Long chatId;
    private final String comment;
}
