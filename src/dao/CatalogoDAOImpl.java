package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Corso;
import exceptions.ConnessioneException;

public class CatalogoDAOImpl implements CatalogoDAO {

	private Connection conn;

	public CatalogoDAOImpl() throws ConnessioneException {
		conn = SingletonConnection.getInstance();
	}

	/*
	 * registrazione di un nuovo corso nel catalogo dei corsi
	 */
	@Override
	public void insert(Corso corso) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO catalogo(id_corso,titolo,id_categoria,numeromaxpartecipanti,costo,descizione) VALUES (?,?,?,?,?,?)");
		ps.setInt(1, corso.getCodice());
		ps.setString(2, corso.getTitolo());
		ps.setInt(3, corso.getIdCategoria());
		ps.setInt(4, corso.getMaxPartecipanti());
		ps.setDouble(5, corso.getCosto());
		ps.setString(6, corso.getDescrizione());

		ps.executeUpdate();
	}

	/*
	 * modifica di tutti i dati di un corso nel catalogo dei corsi il corso viene
	 * individuato in base al idCorso se il corso non esiste si solleva un eccezione
	 */
	@Override
	public void update(Corso corso) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"UPDATE catalogo SET titolo=?, id_categoria=?, numeromaxpartecipanti=?, costo=?, descrizione=?, where id_corso=?");
		ps.setString(1, corso.getTitolo());
		ps.setInt(2, corso.getIdCategoria());
		ps.setInt(3, corso.getMaxPartecipanti());
		ps.setDouble(5, corso.getCosto());
		ps.setString(6, corso.getDescrizione());
		ps.setInt(7, corso.getCodice());
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("corso: " + corso.getCodice() + " non presente");

	}

	/*
	 * cancellazione di un nuovo corso nel catalogo dei corsi questo potr� essere
	 * cancellato solo se non vi sono edizioni di quel corso o qualsiasi altro
	 * legame con gli altri dati Se il corso non esiste si solleva una eccezione Se
	 * non � cancellabile si solleva una eccezione
	 */
	@Override
	public void delete(int idCorso) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM catalogo WHERE id_corso=?");
		ps.setInt(1, idCorso);
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("corso " + idCorso + " non presente o non cancellabile");
		// ... eccezione gestita dal DB per il principio dell'integrità referenziale
	}

	/*
	 * lettura di tutti i corsi dal catalogo se non ci sono corsi nel catalogo il
	 * metodo torna una lista vuota
	 */
	@Override
	public ArrayList<Corso> select() throws SQLException {
		ArrayList<Corso> catalogo = new ArrayList<Corso>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM catalogo");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idCodice = rs.getInt("id_corso");
			String titolo = rs.getString("titolo");
			int idCategoria = rs.getInt("id_categoria");
			int maxPartecipanti = rs.getInt("numeromaxpartecipanti");
			double costo = rs.getDouble("costo");
			String descrizione = rs.getString("descrizione");

			Corso result = new Corso(titolo, idCategoria, maxPartecipanti, costo, descrizione);
			result.setCodice(idCodice);
			catalogo.add(result);
		}
		if (catalogo.size() == 0) {
			throw new SQLException("non ci sono corsi nel catalogo");
		} else {
			return catalogo;
		}
	}

	/*
	 * lettura di un singolo corso dal catalogo dei corsi se il corso non � presente
	 * si solleva una eccezione// bisogna vedere riga 113( al posto dello 0 avevo
	 * inserito null);
	 */
	@Override
	public Corso select(int idCorso) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM catalogo where id_corso =?");

		ps.setInt(1, idCorso);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int idCodice = rs.getInt("id_corso");
			String titolo = rs.getString("titolo");
			int idCategoria = rs.getInt("id_categoria");
			int maxPartecipanti = rs.getInt("numeromaxpartecipanti");
			double costo = rs.getDouble("costo");
			String descrizione = rs.getString("descrizione");

			Corso corso = new Corso(titolo, idCategoria, maxPartecipanti, costo, descrizione);
			corso.setCodice(idCodice);
			return corso;
		} else {
			throw new SQLException("corso: " + idCorso + " non presente");
		}

	}

}