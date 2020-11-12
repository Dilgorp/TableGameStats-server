package ru.dilgorp.java.stats.game.table.domain.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Определяет правила преобразования DTO в сущности системы
 *
 * @param <D>  Документ, сущность системы
 * @param <TO> DTO, объект передаваемый клиенту (получаемый от клиента)
 */
public interface Mapper<D, TO> {

    /**
     * Преобразовывает DTO в документ
     *
     * @param transferObject DTO, объект передаваемый клиенту (получаемый от клиента)
     * @return Документ, сущность системы
     */
    D toDocument(TO transferObject);

    /**
     * Преобразовывет документ в DTO
     *
     * @param document Документ, сущность системы
     * @return DTO, объект передаваемый клиенту (получаемый от клиента)
     */
    TO toTransferObject(D document);

    /**
     * Преобразовывает коллекцию DTO в коллекцию документов.
     * По-умолчанию коллекция {@link java.util.List}
     *
     * @param transferObjects Коллекция DTO
     * @return Коллекция документов
     */
    default Collection<D> transferObjectsToDocuments(Collection<TO> transferObjects) {
        return transferObjects.stream()
                .map(this::toDocument).collect(Collectors.toList());
    }

    /**
     * Преобразовывает коллекцию документов в коллекцию DTO
     *
     * @param documents Коллекция документов
     * @return Коллекция DTO
     */
    default Collection<TO> documentsToTransferObject(Collection<D> documents) {
        return documents.stream()
                .map(this::toTransferObject).collect(Collectors.toList());
    }
}
