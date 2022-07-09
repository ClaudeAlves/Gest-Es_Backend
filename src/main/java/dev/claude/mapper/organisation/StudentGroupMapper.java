package dev.claude.mapper.organisation;

import dev.claude.domain.organisation.StudentGroup;
import dev.claude.dto.ClassDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class StudentGroupMapper extends Mapper<StudentGroup, ClassDTO, ClassDTO> {
    @Override
    public StudentGroup toModel(ClassDTO dtoObject) {
        return StudentGroup.builder()
                .name(dtoObject.getName())
                .comment(dtoObject.getComment())
                .build();
    }

    @Override
    public ClassDTO toDto(StudentGroup modelObject) {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setName(modelObject.getName());
        classDTO.setComment(modelObject.getComment());
        return classDTO;
    }

    @Override
    public StudentGroup toModelFromCreation(ClassDTO creationObject) {
        return null;
    }

}
