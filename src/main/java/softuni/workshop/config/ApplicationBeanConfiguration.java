package softuni.workshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import softuni.workshop.util.XmlParser;
import softuni.workshop.util.XmlParserImpl;

import javax.xml.bind.JAXBException;

@Configuration
public class ApplicationBeanConfiguration {

    //TODO

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser() throws JAXBException {
        return new XmlParserImpl();
    }
}
