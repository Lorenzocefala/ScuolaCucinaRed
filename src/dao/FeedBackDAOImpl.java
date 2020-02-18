package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Feedback;
import exceptions.ConnessioneException;

public class FeedBackDAOImpl implements FeedbackDAO {

	private Connection conn;

	public FeedBackDAOImpl() throws ConnessioneException {
		conn = SingletonConnection.getInstance();
	}

	/*
	 * inserimento di un singolo feedbak relativo ad una edizione di un corso da
	 * parte di un utente se un utente ha già inserito un feedback per una certa
	 * edizione si solleva una eccezione
	 */
	@Override
	public void insert(Feedback feedback) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO feedback (id_feedback, id_edizione, id_utente, descrizione, voto) VALUES (?,?,?,?)");
		ps.setInt(1, feedback.getIdFeedback());
		ps.setInt(2, feedback.getIdEdizione());
		ps.setString(3, feedback.getIdUtente());
		ps.setString(4, feedback.getDescrizione());
		ps.setInt(5, feedback.getVoto());

		ps.executeUpdate();
	}

	/*
	 * modifica di tutti i dati di un singolo feedback un feedback viene individuato
	 * attraverso l'idFeedback se il feedback non esiste si solleva una eccezione
	 */
	@Override
	public void update(Feedback feedback) throws SQLException {

		PreparedStatement ps = conn.prepareStatement(
				"UPDATE feedback SET id_feedback=?, id_edizione=?, id_utente=?, descrizione=?, voto=?"); // bisogna aggiungere il controllo con la data
		ps.setInt(1, feedback.getIdFeedback());
		ps.setInt(2, feedback.getIdEdizione());
		ps.setString(3, feedback.getIdUtente());
		ps.setString(4, feedback.getDescrizione());
		ps.setInt(5, feedback.getVoto());
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("feedback: " + feedback.getIdFeedback() + " non presente");

	}

	/*
	 * cancellazione di un feedback se il feedback non esiste si solleva una
	 * eccezione
	 */
	@Override
	public void delete(int idFeedback) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM feedback WHERE id_feedback=?"); // bisogna aggiungere il controllo con la data
		ps.setInt(1, idFeedback);
		int n = ps.executeUpdate();
		if (n == 0) {
			throw new SQLException("feedback " + idFeedback + " non presente");
		}
	}

	/*
	 * lettura di un singolo feedback scritto da un utente per una certa edizione se
	 * il feedback non esiste si solleva una eccezione
	 */
	@Override
	public Feedback selectSingoloFeedback(String idUtente, int idEdizione, int idFeedback) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM feedback where id_edizione = ? and id_utente = ?");

		ps.setInt(1, idEdizione);
		ps.setString(2, idUtente);

		ResultSet rs = ps.executeQuery();
		Feedback result = null;
		while (rs.next()) {
			idFeedback = rs.getInt("id_feedback");
			idEdizione = rs.getInt("id_edizione");
			idUtente = rs.getString("id_utente");
			idFeedback = rs.getInt("id_feedback");
			String descrizione = rs.getString("descrizione");
			int voto = rs.getInt("voto");

			result = new Feedback(idFeedback, idEdizione, idUtente, descrizione, voto);
		}
		if (result != null) {
			return result;
		} else {
			throw new SQLException("Feedback: " + idFeedback + " non presente");
		}
	}

	/*
	 * lettura di tutti i feedback di una certa edizione se non ci sono feedback o
	 * l'edizione non esiste si torna una lista vuota
	 */
	@Override
	public ArrayList<Feedback> selectPerEdizione(int idEdizione) throws SQLException {
		ArrayList<Feedback> feedback = new ArrayList<Feedback>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM feedback where id_edizione =?");

		ps.setInt(1, idEdizione);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idFeedback = rs.getInt("id_feedback");
			String descrizione = rs.getString("descrizione");
			int voto = rs.getInt("voto");
			idEdizione = rs.getInt("id_edizione");
			String idUtente = rs.getString("id_utente");

			Feedback result = new Feedback(idFeedback, idEdizione, idUtente, descrizione, voto);
			feedback.add(result);
		}
		if (feedback.size() == 0) {
			throw new SQLException("non sono presenti feedback per questa edizione");
		} else {
			return feedback;
		}
	}

	/*
	 * lettura di tutti i feedback scritti da un certo utente se non ci sono
	 * feedback o l'utente non esiste si torna una lista vuota
	 */
	@Override
	public ArrayList<Feedback> selectPerUtente(String idUtente) throws SQLException {
		ArrayList<Feedback> feedback = new ArrayList<Feedback>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM feedback where id_utente=?");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idFeedback = rs.getInt("id_feedback");
			String descrizione = rs.getString("descrizione");
			int voto = rs.getInt("voto");
			int idEdizione = rs.getInt("id_edizione");
			idUtente = rs.getString("id_utente");

			Feedback result = new Feedback(idFeedback, idEdizione, idUtente, descrizione, voto);
			feedback.add(result);
		}
		if (feedback.size() == 0) {
			throw new SQLException("non sono presenti feedback di questo utente");
		} else {
			return feedback;
		}
	}

	/*
	 * lettura di tutti i feedback scritti per un certo corso (nota: non edizione ma
	 * corso) se non ci sono feedback o il corso non esiste si torna una lista vuota
	 */
	@Override
	public ArrayList<Feedback> selectFeedbackPerCorso(int idCorso) throws SQLException {
		ArrayList<Feedback> feedback = new ArrayList<Feedback>();

		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM feedback join calendario using (id_edizione) where id_corso = ?");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idFeedback = rs.getInt("id_feedback");
			idCorso = rs.getInt("id_corso");
			int idEdizione = rs.getInt("id_edizione");
			String idUtente = rs.getString("id_utente");
			String descrizione = rs.getString("descrizione");
			int voto = rs.getInt("voto");

			Feedback result = new Feedback(idFeedback, idEdizione, idUtente, descrizione, voto);
			feedback.add(result);
		}
		if (feedback.size() == 0) {
			throw new SQLException("non sono presenti feedback per questo corso");
		} else {
			return feedback;
		}
	}

}
