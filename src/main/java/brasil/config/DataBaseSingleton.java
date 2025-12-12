package brasil.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseSingleton {
    private static DataBaseSingleton instance;
    private Connection connection;

    
    private final String URL = "jdbc:postgresql://ep-empty-paper-adt5nf3z-pooler.c-2.us-east-1.aws.neon.tech:5432/brasilBurger?sslmode=require";
    private final String USER = "neondb_owner"; 
    private final String PASS = "npg_JU1ZOSpfd7lj";

    private DataBaseSingleton() {
        try {
            Class.forName("org.postgresql.Driver"); 
            this.connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println(" Erreur de connexion SQL.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trouvé.");
        }
    }

    public static DataBaseSingleton getInstance() {
        if (instance == null) {
            instance = new DataBaseSingleton();
        } else {
            try {
                if (instance.connection == null || instance.connection.isClosed()) {
                    instance = new DataBaseSingleton();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion.");
                e.printStackTrace();
            }
        }
    }
}
