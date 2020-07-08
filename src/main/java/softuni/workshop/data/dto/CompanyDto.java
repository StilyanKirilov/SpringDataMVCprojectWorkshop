package softuni.workshop.data.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyDto {

    @XmlAttribute
    private String name;

    public CompanyDto() {
    }

    @NotNull(message = "Name can not be null.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
