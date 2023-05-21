package by.bsuir.vladiyss.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(final String entityName, final Long id) {
        super(entityName + " with id " + id + " was not found");
    }
}
