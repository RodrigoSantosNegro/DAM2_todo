public class Greeter {
    public void sayHello() {
        System.out.println("Hello world!");
    }

    public String byeWorld(){
        return "Bye!";
    }

    private int firstNumber() {
        return 36;
    }

    private int secondNumber() {
        return 42;
    }

    public int sumTwoNumbers() {
        return firstNumber() + secondNumber();
    }

}