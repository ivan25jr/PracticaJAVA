package Modelos;

import java.sql.SQLException;

import mySQL.MysqlBBDD;
import mySQL.MysqlEquipo;
import mySQL.MysqlJugadores;
import mySQL.MysqlLiga;
import mySQL.MysqlManager;

public class test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Equipo equipo = new Equipo("FC", 1);
		Jugadores j= new Jugadores("ian", 9, 10);
		Liga liga= new Liga("espa�ola");
		MysqlManager mg= new MysqlManager();
		mg.getBBDD().EliminarBBDD("bd_equipos");
		

		
}

}
