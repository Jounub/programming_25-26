package ru.arkhipov.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arkhipov.MySpringBoot2Dbase.entity.Discipline;
import ru.arkhipov.MySpringBoot2Dbase.service.DisciplineService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping("/disciplines")
    public ResponseEntity<Object> allDisciplines(){
        List<Discipline> allDisciplines = disciplineService.getAllDisciplines();
        if(allDisciplines.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The disciplines list is empty");
        }
        return ResponseEntity.ok(allDisciplines);
    }

    @GetMapping("/disciplines/{id}")
    public ResponseEntity<Object> getDiscipline(@PathVariable("id") int id){
        Discipline discipline = disciplineService.getDiscipline(id);
        if(discipline == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Discipline with ID " + id + " not found");
        }
        return ResponseEntity.ok(discipline);
    }

    @PostMapping("/disciplines")
    public ResponseEntity<Object> saveDiscipline(@RequestBody Discipline discipline){
        try {
            Discipline saveDiscipline = disciplineService.saveDiscipline(discipline);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Discipline " + saveDiscipline + " successfully added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during adding discipline: " + e.getMessage());
        }
    }

    @PutMapping("/disciplines")
    public ResponseEntity<Object> updateDiscipline (@RequestBody Discipline discipline){
        if((discipline.getId() == 0) || disciplineService.getDiscipline(discipline.getId()) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot be updated: discipline with ID " + discipline.getId() + " not found");
        }

        try{
            disciplineService.saveDiscipline(discipline);
            return ResponseEntity.ok("Discipline info successfully updated: " + discipline);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during updating discipline info: " + e.getMessage());
        }
    }

    @DeleteMapping("/disciplines/{id}")
    public ResponseEntity<Object> deleteDiscipline(@PathVariable("id") int id){
        Discipline discipline = disciplineService.getDiscipline(id);
        if(discipline == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Discipline with ID " + id + " not found");
        }
        try{
            disciplineService.deleteDiscipline(id);
            return ResponseEntity.ok("Discipline with ID " + id + " successfully deleted");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during deleting discipline: " + e.getMessage());
        }
    }
}
