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

    public Tutorial save(Tutorial tutorial){
        Tutorial _tutorial = tutorialRepository
                .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));

        return _tutorial;
    }
}
