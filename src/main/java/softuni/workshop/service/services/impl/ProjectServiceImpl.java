package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.workshop.data.dto.ProjectRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static softuni.workshop.constants.GlobalConstants.PROJECTS_FILE_PATH;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importProjects() throws JAXBException, FileNotFoundException {
        ProjectRootDto rootDto = xmlParser.importFromXML(ProjectRootDto.class, PROJECTS_FILE_PATH);
        rootDto
                .getProjects()
                .forEach(p -> {
                    Project project = modelMapper.map(p, Project.class);
                    project.setCompany(this.companyRepository.findByName(p.getCompany().getName()));
                    this.projectRepository.saveAndFlush(project);
                });

    }

    @Override
    public boolean areImported() {

        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return Files.readString(Paths.get(PROJECTS_FILE_PATH));
    }

    @Override
    public String exportFinishedProjects() {
        List<Project> projects = this.projectRepository.findAllByIsFinishedIsTrue();

        StringBuilder sb = new StringBuilder();

        projects
                .forEach(p -> sb.append(String.format("Project Name: %s%n   Description: %s%n   %s%n",
                        p.getName(), p.getDescription(), p.getPayment())));

        return sb.toString().trim();
    }
}
