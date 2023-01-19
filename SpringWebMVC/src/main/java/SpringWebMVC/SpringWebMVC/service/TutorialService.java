package SpringWebMVC.SpringWebMVC.service;

import SpringWebMVC.SpringWebMVC.model.Tutorial;
import SpringWebMVC.SpringWebMVC.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    public List<Tutorial> findAll() {
        return tutorialRepository.findAll();
    }

    public List<Tutorial> findByTitleContainingIgnoreCase(String title) {
        return tutorialRepository.findByTitleContainingIgnoreCase(title);
    }

    public Tutorial findById(long id){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        return tutorialData.orElse(null);
    }

    public Tutorial save(Tutorial tutorial){

        return tutorialRepository
                .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
    }

    public Tutorial updateTutorial(long id, Tutorial tutorial){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return tutorialRepository.save(_tutorial);
        } else {
            return null;
        }
    }

    public List<Tutorial> findByPublished(boolean b){
        return tutorialRepository.findByPublished(b);
    }

    public void deleteById(long id){
        tutorialRepository.deleteById(id);
    }

    public void deleteAll() {
        tutorialRepository.deleteAll();
    }
}
