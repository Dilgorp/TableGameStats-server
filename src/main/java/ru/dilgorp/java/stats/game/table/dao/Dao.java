package ru.dilgorp.java.stats.game.table.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Обеспечивает работы с данными базы
 *
 * @param <M>  Модель объекта
 * @param <ID> Идентификатор
 */
@Transactional
public interface Dao<M, ID> {

    /**
     * Возвращает все объекты модели
     *
     * @return Список всех объектов модели
     */
    List<M> findAll();

    /**
     * Сохраняет объект в базу
     *
     * @param model Объект модели
     * @return Объект модели
     */
    M save(M model);

    /**
     * Возващает объект модели по идентификатору
     *
     * @param id Идентификатор объекта
     * @return Объект модели
     */
    Optional<M> findById(ID id);

    /**
     * Удаляет объект модели по идентификатору
     *
     * @param id идентификатор объекта модели
     */
    void delete(ID id);
}
