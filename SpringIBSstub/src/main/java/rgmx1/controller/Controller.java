package rgmx1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rgmx1.logic.Pet;
import rgmx1.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController

public class Controller {

    private static final PetModel petModel = PetModel.getInstance();

    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value ="/createPet", consumes = "application/json")

    public ResponseEntity<String> createPet(@RequestBody Pet pet){
        petModel.add(pet, newId.getAndIncrement());
        return ResponseEntity.ok("Pet created successfully");
    }

    @GetMapping(value ="/getAll", produces = "application/json")

    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")

    public Pet getPet(@RequestBody Map<String, Integer> id){
        return petModel.getFromList(id.get("id"));

    }

    @PutMapping(value = "/updatePet/{id}", consumes = "application/json")
    public ResponseEntity<String> updatePet(@PathVariable("id") int id, @RequestBody Pet pet) {
        boolean updated = petModel.update(id, pet);
        if (updated) {
            return ResponseEntity.ok("Pet updated successfully");
        } else {
            return ResponseEntity.status(404).body("Pet not found");
        }
    }


    @DeleteMapping(value = "/deletePet/{id}")
    public ResponseEntity<String> deletePet(@PathVariable("id") int id) {
        boolean deleted = petModel.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Pet deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Pet not found");
        }
    }
}