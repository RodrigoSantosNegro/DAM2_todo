import java.sql.*;
import java.util.ArrayList;

public class MoviesDataProvider {
    public MoviesDataProvider(){
        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            System.out.println("I have connected to the database");

            System.out.println();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT title FROM TMovies");
            while (result.next()) {
                System.out.println(result.getString(1));
            }
            System.out.println();

            conn.close();
            System.out.println("The connection has been stopped");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getTwoColumns() {
        ArrayList<String> finalResult = new ArrayList<>();

        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT title, genre FROM TMovies");
            while(result.next()) {
                String firstColumnValue = result.getString(1);
                String secondColumValue = result.getString(2);
                String concatenated = firstColumnValue + ", " + secondColumValue;
                finalResult.add(concatenated);
            }

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return finalResult;
    }

    public ArrayList<String> getColumnUsingWhere() {
        ArrayList<String> finalResult = new ArrayList<>();

        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT title FROM TMovies WHERE year > 2010");
            while(result.next()) {
                String firstColumnValue = result.getString(1);
                finalResult.add(firstColumnValue);
            }

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return finalResult;
    }

    public ArrayList<String> getResultsIssue6(){
        ArrayList<String> finalResult = new ArrayList<>();

        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT id, title FROM TMovies WHERE year > 1990 AND duration < 149");
            while(result.next()){
                String id = result.getString(1);
                String title = result.getString(2);
                String concatenated = id + "," + title;
                finalResult.add(concatenated);
            }
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return finalResult;
    }

    public ArrayList<String> getResultsIssue7(){
        ArrayList<String> finalResult = new ArrayList<>();

        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT title, year, duration FROM TMovies WHERE countryIso3166 = 'US'");
            while(result.next()){
                String title = result.getString(1);
                String year = result.getString(2);
                String duration = result.getString(3);
                String concatenated = title + "," + year + "," + duration;
                finalResult.add(concatenated);
            }
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return finalResult;
    }

    public String getResultIssue8(){
        String genre = null;
        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT genre FROM TMovies WHERE id = 6");
            while(result.next()){
                genre = result.getString(1);
            }
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return genre;
    }

    public String getResultIssue9(){
        String title = null;
        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT title FROM TMovies WHERE id = 6");
            while(result.next()){
                title = result.getString(1);
            }
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return title;
    }

    public ArrayList<String> getResultsIssue10(){
        ArrayList<String> finalResult = new ArrayList<>();

        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            Connection conn = DriverManager.getConnection(connectionStr);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT id, title FROM TMovies WHERE year > 1990 OR duration < 149");
            while(result.next()){
                String id = result.getString(1);
                String title = result.getString(2);
                String concatenated = id + "," + title;
                finalResult.add(concatenated);
            }
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return finalResult;
    }

}
