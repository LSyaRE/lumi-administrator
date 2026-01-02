package com.luminesway.concursoadminstrator.shared.interfaces;

import com.luminesway.concursoadminstrator.shared.utils.SpringResult;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CrudService<T> {
    /**
     * Creates a new resource using the provided payload.
     *
     * @param payload the input data for creating the resource, represented as an object of type T
     * @return a {@link SpringResult} object containing the outcome of the operation,
     *         including the created resource, status code, and any associated message or errors
     */
    SpringResult<?> create(T payload);
    /**
     * Retrieves a paginated list of all entities.
     *
     * @param pageable the pagination and sorting information
     * @return a {@link SpringResult} containing the paginated list of entities,
     * or error details if the operation was unsuccessful
     */
    SpringResult<?> findAll(Pageable pageable);
    /**
     * Updates an existing entity identified by its unique identifier with the provided payload.
     *
     * @param id      the unique identifier of the entity to update
     * @param payload the data transfer object containing the updated details of the entity
     * @return a {@link SpringResult} object containing the outcome of the update operation, which may include
     *         status code, updated entity details, success message, or any associated errors
     */
    SpringResult<?> update(UUID id, T payload);
    /**
     * Deletes an entity identified by the provided UUID.
     *
     * @param id the unique identifier of the entity to be deleted
     * @return a Result containing the outcome of the delete operation
     */
    SpringResult<?> delete(UUID id);
}
