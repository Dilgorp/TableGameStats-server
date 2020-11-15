package ru.dilgorp.java.stats.game.table.domain.auth;

/**
 * Описывает права доступа в системе
 */
public enum  Authority {
    /**
     * Полные права
     */
    ALL,

    /**
     * Создавание пользователей
     */
    WRITE_USER,

    /**
     * Редактирование информации об играх
     */
    WRITE_GAME_INFO,

    /**
     * Просмотр информации об  играх
     */
    READ_GAME_INFO,

    /**
     * Редактирование информации об игроках
     */
    WRITE_PLAYER_INFO
}
