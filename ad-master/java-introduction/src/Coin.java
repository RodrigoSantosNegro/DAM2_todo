public class Coin {
    private int recompensa;

    public Coin(int recompensa) {
        if(recompensa<=0) throw new RuntimeException("Recompensa no válida");
        this.recompensa = recompensa;
    }

    public String flip(){
        String cruz = "Has tirado al aire la moneda y ha salido cruz. Has perdido";
        String cara = "Has tirado al aire la moneda y ha salido cara. Has ganado "+ recompensa +" euros";
        if(Math.random()<0.5) return cruz;
        else return cara;
    }
}