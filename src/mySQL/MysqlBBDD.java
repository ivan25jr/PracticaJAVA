package mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import Conexion.ConexionBBDD;

public class MysqlBBDD {
	Connection con;
	ConexionBBDD bbdd;
	public MysqlBBDD() throws ClassNotFoundException, SQLException {
		con= bbdd.getConexion();
	}
	public void CrearBBDD2(String nombre) throws SQLException {
		EliminarBBDD(nombre);
		String sql="CREATE DATABASE "+nombre;
		String sqluse="use "+nombre;
		
	      Statement st= con.createStatement();
	      st.executeUpdate(sql);
	       
	     Statement st1= con.createStatement();
	     st.executeUpdate(sqluse);
	      CrearTabla();
	      establecerRelaciones();
		
	}

	public void EliminarBBDD(String nombre) throws SQLException {
		PreparedStatement ps = con.prepareStatement("DROP DATABASE IF EXISTS "+ nombre);
        ps.executeUpdate();
        ps.close();
		
	}


	public void CrearTabla() throws SQLException {
	PreparedStatement ps= con.prepareStatement("CREATE TABLE `jugador` (`idJugador` int(10) NOT NULL AUTO_INCREMENT,`nombreJugador` varchar(25) NOT NULL,`dorsal` int(25) NOT NULL,  `idEquipo` int(25) NOT NULL, PRIMARY KEY (idJugador)) ENGINE=InnoDB;");
		ps.executeUpdate();
		PreparedStatement ps1= con.prepareStatement("CREATE TABLE `equipo` (`idEquipo` int(10) NOT NULL AUTO_INCREMENT,`nombre` varchar(25) NOT NULL, `idLiga` int(10) NOT NULL,   PRIMARY KEY (idEquipo)	) ENGINE=InnoDB;");
		ps1.executeUpdate();
		PreparedStatement ps2= con.prepareStatement("CREATE TABLE `liga` ( `idLiga` int(10) NOT NULL AUTO_INCREMENT,`nombre` varchar(25) NOT NULL,  PRIMARY KEY (idLiga)) ENGINE=InnoDB;");

		ps2.executeUpdate();
		ps1.close();
		ps.close();
		ps2.close();
		
	}
	public void establecerRelaciones() throws SQLException {
		Statement st= con.createStatement();
		st.executeUpdate("ALTER TABLE JUGADOR ADD FOREIGN KEY (idEquipo) REFERENCES EQUIPO (idEQuipo) ON DELETE  CASCADE ON UPDATE  CASCADE;");
	}
}