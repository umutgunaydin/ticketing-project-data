package com.company.converter;

import com.company.dto.ProjectDTO;
import com.company.service.ProjectService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationPropertiesBinding
public class ProjectDTOConverter implements Converter<String, ProjectDTO> {

    private final ProjectService projectService;

    public ProjectDTOConverter( ProjectService projectService) {
        this.projectService = projectService;
    }


    @Override
    public ProjectDTO convert(String source) {
        if (source==null ||source.equals("")){
            return null;
        }
        return projectService.getByProjectCode(source);
    }
}
