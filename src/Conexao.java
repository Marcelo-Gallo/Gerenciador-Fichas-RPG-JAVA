import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Conexao -> Singleton
public class Conexao {
	//instancia Conexao
	private static Conexao instance;
	
	private Connection connection;
	private String url = "jdbc:postgresql://localhost:5432/trabalho_final";
	private String username = "postgres";
	private String password = "postdba";
	
	private Conexao() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			this.connection = DriverManager.getConnection(url, username, password);
		} catch(ClassNotFoundException ex) {
			throw new SQLException(ex);
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static Conexao getInstance() throws SQLException {
		if(instance == null) {
			instance = new Conexao();
		}
		else if(instance.getConnection().isClosed()){
			instance = new Conexao();
		}
		return instance;
	}
	
}