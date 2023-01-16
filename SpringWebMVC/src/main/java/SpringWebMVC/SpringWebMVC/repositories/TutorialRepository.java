package SpringWebMVC.SpringWebMVC.repositories;

import SpringWebMVC.SpringWebMVC.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
}
