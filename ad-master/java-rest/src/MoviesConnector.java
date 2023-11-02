import java.sql.*;
import java.util.ArrayList;

public class MoviesConnector {
    private Connection connection;

    public MoviesConnector() {
        String connectionStr = "jdbc:sqlite:db/sqlite3/movies.db";
        try {
            this.connection = DriverManager.getConnection(connectionStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertExampleFilm() {
        try {
            Statement statement = this.connection.createStatement();
            int affectedRows = statement.executeUpdate("INSERT INTO TMovies (title, year, duration, countryIso3166, genre, synopsis) VALUES ('Inside Out', 2015, 94, 'us', 'animation', 'Riley es una chica que disfruta o padece toda clase de sentimientos. Aunque su vida ha estado marcada por la Alegría, también se ve afectada por otro tipo de emociones. Lo que Riley no entiende muy bien es por qué motivo tiene que existir la Tristeza en su vida. Una serie de acontecimientos hacen que Alegría y Tristeza se mezclen en una inesperada aventura que dará un vuelco al mundo de Riley.')");
            if (affectedRows == 1) {
                System.out.println("Se ha insertado satisfactoriamente");
            } else {
                System.out.println("Parece que ha habido un problema");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createDrama(String title, int year, int duration, String countryCode, String synopsis){
        try {
            Statement statement = this.connection.createStatement();
            int affectedRows = statement.executeUpdate("INSERT INTO TMovies (title, year, duration, countryIso3166, genre, synopsis) VALUES('"+title+"', '"+year+"', '"+duration+"', '"+countryCode+"', 'drama', '"+synopsis+"')");
            statement.close();
            return affectedRows == 1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public boolean modifyYearByFilmId(int newYear, int filmId){
        try {
            Statement statement = this.connection.createStatement();
            int affectedRows = statement.executeUpdate("UPDATE TMovies SET year = "+newYear+" WHERE id = "+filmId+"");
            statement.close();
            return affectedRows == 1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean wipeFilm(int filmId){
        try {
            Statement statement = this.connection.createStatement();
            int affectedRows = statement.executeUpdate("DELETE FROM TMovies WHERE id = "+filmId+"");
            statement.close();
            return affectedRows == 1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Movie> getAll(boolean orderByDurationAsc) {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            String sql = "SELECT * FROM TMovies";
            if (orderByDurationAsc){
                sql += " ORDER BY duration ASC";
            }
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                int id = result.getInt(1);
                String title = result.getString(2);
                int year = result.getInt(3);
                int duration = result.getInt(4);
                String countryIso3166 = result.getString(5);
                String genre = result.getString(6);
                String synopsis = result.getString(7);
                Movie aMovie = new Movie(id, title, year, duration, countryIso3166, genre, synopsis);
                allMovies.add(aMovie);
            }

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allMovies;
    }

    public Movie getByID(int movieID) {
        Movie aMovie = null;
        try {
            Statement statement = this.connection.createStatement();
            String sql = "SELECT * FROM TMovies WHERE id = " + movieID;
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                int id = result.getInt(1);
                String title = result.getString(2);
                int year = result.getInt(3);
                int duration = result.getInt(4);
                String countryIso3166 = result.getString(5);
                String genre = result.getString(6);
                String synopsis = result.getString(7);
                aMovie = new Movie(id, title, year, duration, countryIso3166, genre, synopsis);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aMovie;
    }




}
