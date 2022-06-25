package dev.claude.mapper;

public abstract class Mapper<M, D, C> {
    public abstract M toModel(D dtoObject);
    public abstract D toDto(M modelObject);
    public abstract M toModelFromCreation(C creationObject);
}

