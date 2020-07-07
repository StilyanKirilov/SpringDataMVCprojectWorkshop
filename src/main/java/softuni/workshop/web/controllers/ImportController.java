package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

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
                this.employeeService.areImported(),
                this.projectService.areImported()
        };
        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() {
        return this.view("xml/import-companies");
    }

    @GetMapping("/projects")
    public ModelAndView projects() {
        return this.view("xml/import-projects");
    }

    @GetMapping("/employees")
    public ModelAndView employees() {
        return this.view("xml/import-employees");
    }

}
