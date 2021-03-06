package ru.dilgorp.java.stats.game.table.domain.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Определяет правила преобразования объектов модели в сущности базы данных
 *
 * @param <E> Сущность базы данных
 * @param <M> Объект модели
 */
public interface Mapper<E, M> {

    /**
     * Преобразовывает объект модели в сущность базы данных
     *
     * @param model Объект модели
     * @return Сущность базы данных
     */
    E toEntity(M model);

    /**
     * Преобразовывет сущность базы данных в объект модели
     *
     * @param entity Сущность базы данных
     * @return Объект модели
     */
    M toModel(E entity);

    /**
     * Преобразовывает коллекцию объектов модели в коллекцию сущностей базы данных.
     * По-умолчанию коллекция {@link java.util.List}
     *
     * @param models Коллекция объктов модели
     * @return Коллекция сущностей базы данных
     */
    default Collection<E> modelsToEntities(Collection<M> models) {
        return models.stream()
                .map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * Преобразовывает коллекцию сущностей базы данных в коллекцию объектов модели
     *
     * @param entities Коллекция сущностей базы данных
     * @return Коллекция объектов модели
     */
    default Collection<M> entitiesToModels(Collection<E> entities) {
        return entities.stream()
                .map(this::toModel).collect(Collectors.toList());
    }
}
