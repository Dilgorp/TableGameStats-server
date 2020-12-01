package ru.dilgorp.java.stats.game.table.domain.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Определяет правила преобразования объектов модели в сущности базы данных с владельцами
 *
 * @param <E> Сущность базы данных
 * @param <M> Объект модели
 * @param <O> Владелец сущности
 */
public interface MapperWithOwner<E, M, O> {

    /**
     * Преобразовывает объект модели в сущность базы данных
     *
     * @param model Объект модели
     * @param owner Владелец сущности
     * @return Сущность базы данных
     */
    E toEntity(M model, O owner);

    /**
     * Преобразовывет сущность базы данных в объект модели
     *
     * @param entity Сущность базы данных
     * @return Объект модели
     */
    M toModel(E entity);

    /**
     * Преобразовывает коллекцию объектов модели в список сущностей базы данных.
     * По-умолчанию коллекция {@link java.util.List}
     *
     * @param models Коллекция объктов модели
     * @param owner  Владелец коллекции сущности
     * @return Список сущностей базы данных
     */
    default List<E> modelsToEntities(Collection<M> models, O owner) {
        if (models == null) {
            return null;
        }

        return models.stream()
                .map(m -> toEntity(m, owner)).collect(Collectors.toList());
    }

    /**
     * Преобразовывает коллекцию сущностей базы данных в список объектов модели
     *
     * @param entities Коллекция сущностей базы данных
     * @return Список объектов модели
     */
    default List<M> entitiesToModels(Collection<E> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toModel).collect(Collectors.toList());
    }
}
