package softuni.workshop.service.services;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface EmployeeService {

    void importEmployees() throws JAXBException, FileNotFoundException;

    boolean areImported();

    String readEmployeesXmlFile() throws IOException;

    String exportEmployeesWithAgeAbove();
}
