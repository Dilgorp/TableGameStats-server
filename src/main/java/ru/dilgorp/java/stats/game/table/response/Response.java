package ru.dilgorp.java.stats.game.table.response;

import lombok.EqualsAndHashCode;

/**
 * Класс-обертка, используется как возвращаемое значение для rest-запросов
 *
 * @param <T> тип данных ответа
 */
@EqualsAndHashCode
public class Response<T> {
    private final ResponseType type;
    private final String message;
    private final T data;

    public Response(ResponseType type, String message, T data) {
        this.type = type;
        this.message = message == null ? "" : message;
        this.data = data;
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
