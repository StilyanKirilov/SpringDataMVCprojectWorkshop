package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.workshop.constants.GlobalConstants;
import softuni.workshop.data.dto.EmployeeRootDto;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static softuni.workshop.constants.GlobalConstants.EMPLOYEES_FILE_PATH;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importEmployees() throws JAXBException, FileNotFoundException {

        EmployeeRootDto rootEmployees = this.xmlParser.importFromXML(EmployeeRootDto.class, EMPLOYEES_FILE_PATH);
        rootEmployees
                .getEmployees()
                .forEach(e -> {
                    Employee employee = this.modelMapper.map(e, Employee.class);
                    employee.setProject(this.projectRepository.findByNameAndStartDate(e.getProjectDto().getName(), e.getProjectDto().getStartDate()));
                    this.employeeRepository.saveAndFlush(employee);
                });

    }

    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Paths.get(EMPLOYEES_FILE_PATH));
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        List<Employee> employees = this.employeeRepository.findAllByAgeGreaterThan(25);

        StringBuilder sb = new StringBuilder();

        employees
                .forEach(e -> sb.append(String.format("Name: %s %s%n   Age: %d%n   Project name: %s%n",
                        e.getFirstName(), e.getLastName(), e.getAge(), e.getProject().getName())));

        return sb.toString().trim();
    }
}
