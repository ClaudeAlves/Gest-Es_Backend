package dev.claude.mapper.evaluation;

import dev.claude.domain.evalutation.Mark;
import dev.claude.dto.MarkDTO;
import dev.claude.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class MarkMapper extends Mapper<Mark, MarkDTO, MarkDTO> {
    @Override
    public Mark toModel(MarkDTO dtoObject) {
        return Mark.builder()
                .value(dtoObject.getValue())
                .build();
    }

    @Override
    public MarkDTO toDto(Mark modelObject) {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setStudentId(modelObject.getStudent().getIdUser());
        markDTO.setTestId(modelObject.getIdMark());
        markDTO.setValue(modelObject.getValue());
        return markDTO;
    }

    @Override
    public Mark toModelFromCreation(MarkDTO creationObject) {
        return null;
    }
}
