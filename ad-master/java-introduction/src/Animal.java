public class Animal {
    private String name;
    private String species;
    private int numberOfEyes;

    public Animal(String name, String species, int numberOfEyes) {
        this.name = name;
        this.species = species;
        this.numberOfEyes = numberOfEyes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getNumberOfEyes() {
        return numberOfEyes;
    }

    public void setNumberOfEyes(int numberOfEyes) {
        this.numberOfEyes = numberOfEyes;
    }
}
