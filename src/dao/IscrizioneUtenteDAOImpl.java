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

		// ...
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

		PreparedStatement ps = conn.prepareStatement("SELECT id_edizione FROM iscritti WHERE id_utente = ?");
		ps.setString(1, idUtente);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String idEdizione = rs.getString("id_edizione");

			Edizione result = new Edizione();
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * ritorna il numero di utenti iscritti ad una certa edizione
	 */
	@Override
	public int getNumeroIscritti(int idEdizione) throws SQLException {
		ArrayList<Utente> utenti = new ArrayList<Utente>();
		
		PreparedStatement ps = conn.prepareStatement("SELECT id_utente FROM iscritti WHERE id_edizione = ?");
		ps.setInt(1, idEdizione);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
		//	Utente result = rs.getString("id_utente");
		//	utenti.add(result);
		
		
	}
		return 0;
	}
	
}
