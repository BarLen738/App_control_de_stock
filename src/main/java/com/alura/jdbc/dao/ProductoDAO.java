package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {

	private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Producto producto) {

		try {

			 PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTO " + "(NOMBRE, DESCRIPCION, CANTIDAD, CATEGORIA_ID)" + " VALUES(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

//	    	BLOQUE TRY CATCH PARA TRATAR EXCEPTION
			try (statement) {
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getCategoriaId());

				statement.execute();
    
                final ResultSet resultSet = statement.getGeneratedKeys();
    
                try (resultSet) {
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));
                        
                        System.out.println(String.format("Fue insertado el producto: %s", producto));
                    }
                }
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}

	}

	public List<Producto> listar() {

		List<Producto> resultado = new ArrayList<>();

		try {
//		CREAR UN STATEMENT
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			try (statement) {
//		EJECUTAR EN EL STATEMENT
				statement.execute();

//		MOSTRAR EL RESULTADO DEL STATEMENT, ABRE ITERACIÓN DE ELEMENTOS
				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {

//		MOSTRAR EL SIGUIENTE ELEMENTO, CONSTRUCCIÓN DE REGISTROS
					while (resultSet.next()) {
						resultado.add(new Producto(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
								resultSet.getString("DESCRIPCION"), resultSet.getInt("CANTIDAD")));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	public int eliminar(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE PRODUCTO SET " + " NOMBRE = ?, " + " DESCRIPCION = ?," + " CANTIDAD = ?" + " WHERE ID = ?");

			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

//	BÚSQUEDA DE PRODUCTOS POR ID DE CATEGORÍA
	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<>();

		try {
//		CREAR UN STATEMENT
			var querySelect = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD "
					+ " FROM PRODUCTO "
					+ " WHERE CATEGORIA_ID = ?";
			System.out.println(querySelect);
			final PreparedStatement statement = con
					.prepareStatement(querySelect);
			try (statement) {
//		EJECUTAR EN EL STATEMENT
				statement.setInt(1, categoriaId);
				statement.execute();

//		MOSTRAR EL RESULTADO DEL STATEMENT, ABRE ITERACIÓN DE ELEMENTOS
				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {

//		MOSTRAR EL SIGUIENTE ELEMENTO, CONSTRUCCIÓN DE REGISTROS
					while (resultSet.next()) {
						resultado.add(new Producto(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
								resultSet.getString("DESCRIPCION"), resultSet.getInt("CANTIDAD")));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
}
