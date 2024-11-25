package rgmx1.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PetModel implements Serializable {

    private static final PetModel instance = new PetModel();

    private final Map<Integer, Pet> model;

    public PetModel() {
        model = new HashMap<Integer, Pet>();

    }

    public static PetModel getInstance(){
        return instance;
    }

    public void add(Pet pet, int id) {
        model.put(id, pet);
    }

    public Pet getFromList(int id){

        return model.get(id);
    }

    public Map<Integer, Pet> getAll(){
        return model;
    }


    public boolean update(int id, Pet pet) {
        if (model.containsKey(id)) {
            model.put(id, pet);
            return true;
        }
        return false;
    }


    public boolean delete(int id) {
        if (model.containsKey(id)) {
            model.remove(id);
            return true;
        }
        return false;
    }

}
