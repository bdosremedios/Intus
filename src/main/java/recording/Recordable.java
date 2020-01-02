package recording;

// Object that can be saved and loaded
public interface Recordable {

    // EFFECTS: saves the object in filename
    void save(String filename);

    // EFFECTS: loads the object from filename
    void load(String filename);

}
