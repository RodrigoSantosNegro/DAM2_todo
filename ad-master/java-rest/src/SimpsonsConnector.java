import java.sql.*;
import java.util.ArrayList;

public class SimpsonsConnector {
    private Connection connection;

    public SimpsonsConnector() {
        String stringConnection = "jdbc:sqlite:db/sqlite3/simpsons.db";
        try{
            this.connection = DriverManager.getConnection(stringConnection);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Character> identifySimpsons(int milliseconds, int clipId){
        ArrayList<Character> simpsons = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            String sql = ("SELECT name, surname, description, imageUrl FROM TCharacters INNER JOIN TAppearances ON TCharacters.id = TAppearances.characterId WHERE TAppearances.startMilliseconds <= "+milliseconds+" AND TAppearances.endMilliseconds >= "+milliseconds+" AND TAppearances.clipId = "+clipId);
            ResultSet finalResult = statement.executeQuery(sql);
            while(finalResult.next()){
                String name = finalResult.getString(1);
                String surname = finalResult.getString(2);
                String description = finalResult.getString(3);
                String imageUrl = finalResult.getString(4);
                Character c = new Character(name, surname, description, imageUrl);
                simpsons.add(c);

            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return simpsons;

    }

    public void closeConnection(){
        try{
            this.connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
