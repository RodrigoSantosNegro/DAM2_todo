public class SimpleMathDemo {
    private int firstNumber(){
        return 1198;
    }

    private int secondNumber(){
        return 3959;
    }

    private int thirdNumber(){
        return 1638;
    }

    public int doCalculations(){
        return firstNumber()+secondNumber()+thirdNumber();
    }

    public void printMultiplication() {
        System.out.println("Los múltiplos de 7 menores que 70 son:");
        for (int i = 0; i < 70; i+=7) {
            System.out.println(i);
        }
    }

    public void loop50Times() {
        for (int i = 0; i < 50; i++) {
            System.out.println("Imprimiendo... " + i);
            if (i == 30) {
                return; // Se puede hacer return si el método devuelve
                // void. Tiene el mismo efecto...
                // ¡Pero no devuelve nada!
            }
        }
    }

    public void printNumber(int nombreArbitrario) {
        System.out.println(nombreArbitrario);
    }

    public void printProduct(int n1, int n2){
        System.out.println(n1*n2);
    }

    public void printProduct(String var, int num1, int num2) {
        System.out.println("Un saludo," + " " + var);
        System.out.println("Este es el producto de tus dos valores:");
        System.out.println(num1 * num2);
    }

    public int getTheBiggerNumber(int n1, int n2, int n3){
        int mayor = n1;
        if(n2>mayor) mayor = n2;
        if(n3>mayor) mayor = n3;
        return mayor;
    }

    public int getRandomNumberInRange(int start, int length){
        if(length<0) return 0;
        else return (int) Math.floor((Math.random() * (length)) + start);
    }

    public int getTheSmallerValue(int n1, int n2, int n3, int n4){
        int menor = n1;
        if(n2<menor) menor =n2;
        if(n3<menor) menor =n3;
        if(n4<menor) menor =n4;
        return menor;
    }
}
