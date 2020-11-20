package ru.dilgorp.java.stats.game.table.dao;

import java.util.List;

/**
 * Обеспечивает работу с данными базы (у которых есть владелец)
 *
 * @param <M>  Объект модели
 * @param <ID> Идентификатор объекта
 * @param <O>  Владелец объекта
 */
public interface DaoWithOwner<M, ID, O> extends Dao<M, ID> {

    /**
     * Возвращает список объектов модели по владельцу
     *
     * @param owner Владелец объектов модели
     * @return Список объектов модели
     */
    List<M> findByOwner(O owner);

    /**
     * Сохраняет список объектов модели по владельцу
     *
     * @param models Список объектов модели
     * @param owner  Владелец объектов модели
     * @return Список сохраненных объектов модели
     */
    List<M> saveByOwner(List<M> models, O owner);

    /**
     * Неподдерживаемый метод.
     * Необходимо использовать {@link #save(M, O)} вместо
     *
     * @param model Объект модели
     * @return Ничего не возвращает
     * @throws UnsupportedOperationException операция не поддерживается
     */
    @Override
    @Deprecated
    default M save(M model) {
        throw new UnsupportedOperationException("Unsupported operation. Use save(M model, O owner)");
    }

    /**
     * Сохраняет объект модели по владельцу.
     *
     * @param model Объект модели
     * @param owner Владелец объекта
     * @return Сохраненный объект модели
     */
    M save(M model, O owner);
}
