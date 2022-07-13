package dev.claude.mapper.organisation;

import dev.claude.domain.organisation.Subject;
import dev.claude.dto.SubjectDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper extends Mapper<Subject, SubjectDTO, SubjectDTO> {
    @Override
    public Subject toModel(SubjectDTO dtoObject) {
        return Subject.builder()
                .name(dtoObject.getName())
                .abbreviation(dtoObject.getAbbreviation())
                .build();
    }

    @Override
    public SubjectDTO toDto(Subject modelObject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.name(modelObject.getName());
        subjectDTO.abbreviation(modelObject.getAbbreviation());
        return subjectDTO;
    }

    @Override
    public Subject toModelFromCreation(SubjectDTO creationObject) {
        return null;
    }
}
