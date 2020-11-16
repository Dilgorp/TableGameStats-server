package ru.dilgorp.java.stats.game.table.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {
    private String name;
    private String path;
    private String image;
    private String description;
}
