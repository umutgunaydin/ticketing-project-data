package com.company.converter;

import com.company.dto.ProjectDTO;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ProjectDTOConverter implements Converter<String, ProjectDTO> {

    private final ProjectService projectService;

    public ProjectDTOConverter(ProjectService projectService) {
        this.projectService = projectService;
    }


    @Override
    public ProjectDTO convert(String source) {
        return projectService.findById(source);
    }
}
