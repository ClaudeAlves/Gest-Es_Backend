package dev.claude.mapper.organisation;

import dev.claude.dto.ModuleDTO;
import dev.claude.mapper.Mapper;
import dev.claude.domain.organisation.Module;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper extends Mapper<Module, ModuleDTO, ModuleDTO> {
    @Override
    public Module toModel(ModuleDTO dtoObject) {
        return Module.builder()
                .name(dtoObject.getName())
                .build();
    }

    @Override
    public ModuleDTO toDto(Module modelObject) {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setName(modelObject.getName());
        return moduleDTO;
    }

    @Override
    public Module toModelFromCreation(ModuleDTO creationObject) {
        return null;
    }
}
