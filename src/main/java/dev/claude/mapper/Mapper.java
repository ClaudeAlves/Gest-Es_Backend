package dev.claude.mapper;

/**
 * Base mapper to be extended by other mappers
 * @param <M>
 * @param <D>
 * @param <C>
 */
public abstract class Mapper<M, D, C> {
    public abstract M toModel(D dtoObject);
    public abstract D toDto(M modelObject);
    public abstract M toModelFromCreation(C creationObject);
}

