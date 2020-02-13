package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Edizione;
import entity.Utente;
import exceptions.ConnessioneException;

public class IscrizioneUtenteDAOImpl implements IscrizioneUtenteDAO {

	private Connection conn;

	public IscrizioneUtenteDAOImpl() throws ConnessioneException {
		conn = SingletonConnection.getInstance();
	}

	/*
	 * iscrizione di un certo utente ad una certa edizione di un corso. sia l'utente
	 * che l'edizione devono già essere stati registrati in precedenza se l'utente
	 * e/o l'edizione non esistono o l'utente é già iscritto a quella edizione si
	 * solleva una eccezione
	 */
	@Override
	public void iscriviUtente(int idEdizione, String idUtente) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO iscritti(id_utente, id_edizione) VALUES (?,?)");
		ps.setString(1, idUtente);
		ps.setInt(2, idEdizione);
		ps.executeUpdate();

		// ... eccezione gestita dal DB per il principio dell'integrità referenziale
	}

	/*
	 * cancellazione di una iscrizione ad una edizione nota: quando si cancella
	 * l'iscrizione, sia l'utente che l'edizione non devono essere cancellati se
	 * l'utente e/o l'edizione non esistono si solleva una eccezione
	 */
	@Override
	public void cancellaIscrizioneUtente(int idEdizione, String idUtente) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM iscritti WHERE id_utente=?, id_edizione = ?");
		ps.setString(1, idUtente);
		ps.setInt(2, idEdizione);
		int n = ps.executeUpdate();
		if (n == 0) {
			throw new SQLException("Utente o edizione errati");
		}
	}

	/*
	 * lettura di tutte le edizioni a cui è iscritto un utente se l'utente non
	 * esiste o non è iscritto a nessuna edizione si torna una lista vuota
	 */
	@Override
	public ArrayList<Edizione> selectIscrizioniUtente(String idUtente) throws SQLException {
		ArrayList<Edizione> edizioni = new ArrayList<Edizione>();

		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM iscritti join calendario using (id_edizione) WHERE id_utente = ?");
		ps.setString(1, idUtente);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idEdizione = rs.getInt("id_edizione");
			int idCorso = rs.getInt("id_corso");
			Date dataInizio = rs.getDate("dataInizio");
			int durata = rs.getInt("durata");
			String aula = rs.getString("aula");
			String docente = rs.getString("docente");

			Edizione result = new Edizione(idEdizione, idCorso, dataInizio, durata, aula, docente);
			edizioni.add(result);
		}
		if (edizioni.size() == 0) {
			throw new SQLException("Utente non iscritto ad alcun corso");
		} else {
			return edizioni;
		}
	}

	/*
	 * lettura di tutti gli utenti iscritti ad una certa edizione se l'edizione non
	 * esiste o non vi sono utenti iscritti si torna una lista vuota
	 */
	@Override
	public ArrayList<Utente> selectUtentiPerEdizione(int idEdizione) throws SQLException {
		ArrayList<Utente> utenti = new ArrayList<Utente>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM iscritti join registrati using (id_utente) WHERE id_edizione = ?");
		ps.setInt(1, idEdizione);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String idUtente = rs.getString("id_utente");
			String password = rs.getString("password");
			String nome = rs.getString("nome");
			String cognome = rs.getString("cognome");
			Date dataNascita = rs.getDate("dataNascita");
			String email = rs.getString("email");
			String telefono = rs.getString("telefono");

			Utente result = new Utente(idUtente, password, nome, cognome, dataNascita, email, telefono, false);
			utenti.add(result);
		}
		if (utenti.size() == 0) {
			throw new SQLException("Edizione non esistente o nessun utente iscritto a questa edizione"); // due casi diversi o gestiti insieme?
		} else {
			return utenti;
		}
	}

	/*
	 * ritorna il numero di utenti iscritti ad una certa edizione
	 */
	@Override
	public int getNumeroIscritti(int idEdizione) throws SQLException {
		int count = 0;
		PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM iscritti WHERE id_edizione = ?");
		ps.setInt(1, idEdizione);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			count = rs.getInt("count(*)");
		}
		return count;
	}
}