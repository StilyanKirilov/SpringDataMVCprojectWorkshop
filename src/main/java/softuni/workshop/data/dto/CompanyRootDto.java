package softuni.workshop.data.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyRootDto {

    @XmlElement(name = "company")
    private List<CompanyDto> companies;

    public CompanyRootDto() {
    }


    public List<CompanyDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDto> companies) {
        this.companies = companies;
    }
}
