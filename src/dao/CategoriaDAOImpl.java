package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Categoria;
import exceptions.ConnessioneException;

public class CategoriaDAOImpl implements CategoriaDAO {

	private Connection conn;

	public CategoriaDAOImpl() throws ConnessioneException {
		conn = SingletonConnection.getInstance();
	}

	/*
	 * inserimento di una nuova categoria
	 * 
	 */
	@Override
	public void insert(Categoria descrizione) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO categoria(id_categoria,descrizione) VALUES (?,?");
		ps.setInt(1, descrizione.getIdCategoria());
		ps.setString(2, descrizione.getDescrizione());
		ps.executeUpdate();
	}

	/*
	 * modifica del nome di una categoria. la categoria viene individuata in base al
	 * idCategoria se la categoria non esiste si solleva una eccezione
	 */
	@Override
	public void update(Categoria c) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE categoria SET categoria=? where id_categoria=?");
		ps.setString(1, c.getDescrizione());
		ps.setInt(2, c.getIdCategoria());
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("Categoria: " + c.getIdCategoria() + " non presente");

	}

	/*
	 * cancellazione di una singola categoria. Una categoria si può cancellare solo
	 * se non ci sono dati correlati. Se la categoria non esiste o non è
	 * cancellabile si solleva una eccezione
	 */
	@Override
	public void delete(int idCategoria) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM categoria WHERE id_categoria=?");
		ps.setInt(1, idCategoria);
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("Categoria " + idCategoria + " non presente");

	}

	/*
	 * lettura di una singola categoria in base al suo id se la categoria non esiste
	 * si solleva una eccezione
	 */
	@Override
	public Categoria select(int idCategoria) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria where id_categoria =?");

		ps.setInt(1, idCategoria);

		ResultSet rs = ps.executeQuery();
		Categoria categoria = null;
		if (rs.next()) {
			int IdCategoria = rs.getInt("id_categoria");
			String descrizione = rs.getString("descrizione");

			categoria = new Categoria(IdCategoria, descrizione);
			return categoria;
		} else {
			throw new SQLException("Categoria: " + idCategoria + " non presente");
		}
	}

	/*
	 * lettura di tutte le categorie. Se non vi sono categorie il metodo ritorna una
	 * lista vuota
	 */
	@Override
	public ArrayList<Categoria> select() throws SQLException {
		ArrayList<Categoria> categorie = new ArrayList<Categoria>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idCategoria = rs.getInt("id_categoria");
			String descrizione = rs.getString("descrizione");

			Categoria categoria = new Categoria(idCategoria, descrizione);
			categorie.add(categoria);
		}
		return categorie;

	}
}
