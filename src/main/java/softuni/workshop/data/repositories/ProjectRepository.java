package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByNameAndStartDate(String name, String startDate);

    @Query("SELECT p FROM Project p WHERE p.finished = TRUE")
    List<Project> findAllByIsFinishedIsTrue();

}
