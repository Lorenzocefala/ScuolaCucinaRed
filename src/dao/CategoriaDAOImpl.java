package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Categoria;
import entity.Utente;
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
	public void insert(String descrizione) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO categoria(id_categoria,descrizione) VALUES (?,?");
		// ps.setInt(1, descrizione.getIdCategoria());
		// ps.setString(2, descrizione.getDescrizione());
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
		ps.setInt(5, c.getIdCategoria());
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("Categoria: " + c.getIdCategoria() + " non presente");

	}

	// controllo;

	/*
	 * cancellazione di una singola categoria una categoria si pu� cancellare solo
	 * se non ci sono dati correlati se la categoria non esiste o non � cancellabile
	 * si solleva una eccezione
	 */
	@Override
	public void delete(int idCategoria) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM categoria WHERE id_categoria=?");
		ps.setInt(1, idCategoria);
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("Categoria " + idCategoria + " non presente");

	}

// CONTROLLO;

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
			String Categoria = rs.getString("categoria");

			categoria = new Categoria(IdCategoria, Categoria);
			return categoria;
		} else {
			throw new SQLException("Categoria: " + idCategoria + " non presente");
		}
// controllo;
	}

	/*
	 * lettura di tutte le categorie se non vi sono categoria il metodo ritorna una
	 * lista vuota
	 */
	@Override
	public ArrayList<Categoria> select() throws SQLException {
		ArrayList<Categoria> c = new ArrayList<Categoria>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String idCategoria = rs.getString("id_categoria");
			String categoria = rs.getString("categoria");
			

			//Utente categorie = new Utente(idCategoria, categoria);
			// c.add(categorie);
		}
		return c;

	}  
// Condice purtroppo scritto con errori, errori che non riesco a risolvere.
}
