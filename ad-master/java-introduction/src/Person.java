public class Person {
    private String name;
    public Person(String name) {
        this.name = name;
        System.out.println(name + " acaba de ser instanciado");
    }

    public String getName() {
        return this.name;
    }

    public String greeting(){
        return "Muy buenas, " + this.name;
    }
}
