package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constants.GlobalConstants;
import softuni.workshop.data.dto.CompanyRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static softuni.workshop.constants.GlobalConstants.*;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public void importCompanies() throws JAXBException, FileNotFoundException {
        CompanyRootDto rootCompany = xmlParser.importFromXML(CompanyRootDto.class, COMPANIES_FILE_PATH);
        rootCompany
                .getCompanies()
                .forEach(c -> {
                    Company company = this.modelMapper.map(c, Company.class);
                    this.companyRepository.save(company);
                });
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return Files.readString(Paths.get(COMPANIES_FILE_PATH));
    }
}
