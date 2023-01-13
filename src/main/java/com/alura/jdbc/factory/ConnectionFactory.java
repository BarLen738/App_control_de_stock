package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//DESIGN PATTERN - FACTORY METHOD - ENCAPSULAR CÓDIGO ESPECÍFICO CENTRALIZANDO LA LÓGICA EN UN SOLO PUNTO
public class ConnectionFactory {
	
	private DataSource dataSource;
	
//	ABRIR POOL DE CONEXIONES
	public ConnectionFactory( ) {
		var comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true6serverTimeZone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("20230501");
		
//		SETEAR CANTIDAD MÁXIMA DE CONEXIONES
		comboPooledDataSource.setMaxPoolSize(10);
		
		this.dataSource = comboPooledDataSource;
	}
	
	public Connection recuperaConexion() {
		
//		ABRIR CONEXIÓN
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
		
	}
	
}
