package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ProjectService projectService;

    @Autowired
    public ImportController(EmployeeService employeeService, CompanyService companyService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.projectService = projectService;
    }


//    public ImportController() {
//    }

    @GetMapping("/xml")
    public ModelAndView xmls() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");

        boolean[] areImported = new boolean[]{
                this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()
        };
        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-companies");
        modelAndView.addObject("companies", this.companyService.readCompaniesXmlFile());

        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() throws JAXBException, FileNotFoundException {
        this.companyService.importCompanies();

        return this.redirect("/import/xml");
    }

    @GetMapping("/projects")
    public ModelAndView projects() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-projects");

        modelAndView.addObject("projects",this.projectService.readProjectsXmlFile());
        return modelAndView;
    }

    @PostMapping("/projects")
    public ModelAndView projectsConfirm() throws JAXBException, FileNotFoundException {
        this.projectService.importProjects();

        return this.redirect("/import/xml");
    }

    @GetMapping("/employees")
    public ModelAndView employees() throws IOException {

        ModelAndView modelAndView = new ModelAndView("xml/import-employees");

        modelAndView.addObject("employees",this.employeeService.readEmployeesXmlFile());

        return modelAndView;
    }
    @PostMapping("/employees")
    public ModelAndView employeesConfirm() throws JAXBException, FileNotFoundException {
        this.employeeService.importEmployees();

        return this.redirect("/import/xml");
    }

}
