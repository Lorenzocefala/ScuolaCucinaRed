package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Feedback;
import entity.Utente;
import exceptions.ConnessioneException;

public class FeedBackDAOImpl implements FeedbackDAO {

	private Connection conn;

	public FeedBackDAOImpl() throws ConnessioneException {
		conn = SingletonConnection.getInstance();
	}

	/*
	 * inserimento di un singolo feedbak relativo ad una edizione di un corso da
	 * aprte di un utente se un utente ha gi� inserito un feedback per una certa
	 * edizione si solleva una eccezione
	 */
	@Override
	public void insert(Feedback feedback) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO feedback(id_edizione,id_utente,voto) VALUES (?,?,?)");
		ps.setInt(1, feedback.getIdEdizione());
		ps.setString(2, feedback.getIdUtente());
		ps.setInt(3, feedback.getVoto());

		ps.executeUpdate();
	}

	// codice da controllare;

	/*
	 * modifica di tutti i dati di un singolo feedback un feedback viene individuato
	 * attraverso l'idFeedback se il feedback non esiste si solleva una eccezione
	 */
	@Override
	public void update(Feedback feedback) throws SQLException {

		PreparedStatement ps = conn.prepareStatement(
				"UPDATE feedback SET id_feedback=?, id_edizione=?, id_utente=?, descrizione=?, voto=?");
		ps.setInt(1, feedback.getIdFeedback());
		ps.setInt(2, feedback.getIdEdizione());
		ps.setString(3, feedback.getIdUtente());
		ps.setString(4, feedback.getDescrizione());
		ps.setInt(5, feedback.getVoto());
		int n = ps.executeUpdate();
		if (n == 0)
			throw new SQLException("feedback: " + feedback.getIdFeedback() + " non presente");

	}

	// da controllare;

	/*
	 * cancellazione di un feedback se il feedback non esiste si solleva una
	 * eccezione
	 */
	@Override
	public void delete(int idFeedback) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM feedback WHERE id_feedback=?");
		ps.setInt(1, idFeedback);
		int n = ps.executeUpdate();
		if (n == 0) {
			throw new SQLException("feedback " + idFeedback + " non presente");
		}

	}

	// da controllare;

	/*
	 * lettura di un singolo feedback scritto da un utente per una certa edizione se
	 * il feedback non esiste si solleva una eccezione
	 */
	@Override
	public Feedback selectSingoloFeedback(String idUtente, int idEdizione, int idFeedback) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM feedback where id_feedback =?");

		ps.setInt(1, idEdizione);

		ResultSet rs = ps.executeQuery();
		Feedback result = null;
		if (rs.next()) {
			idEdizione = rs.getInt("id_edizione");
			idUtente = rs.getString("id_utente");
			idFeedback = rs.getInt("id_feedback");

			// result = new Feedback (idEdizione, idUtente,idFeedback, true);
			return result;
		} else {
			throw new SQLException("Feedback: " + idFeedback + " non presente");
		}
		// quest' ultima parte è da controllare molto attentamente!!!!!!!;
	}

	/*
	 * lettura di tutti i feedback di una certa edizione se non ci sono feedback o
	 * l'edizione non esiste si torna una lista vuota
	 */
	@Override
	public ArrayList<Feedback> selectPerEdizione(int idEdizione) throws SQLException {
		ArrayList<Feedback> feedback = new ArrayList<Feedback>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM feedback");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String IdFeedback = rs.getString("id_feedback");
			String IdEdizione = rs.getString("id_edizione");

			Feedback result = new Feedback(IdFeedback, IdEdizione, idEdizione);// controllare perchè ci sono 3
																				// parametri;
			feedback.add(result);
		}
		if (feedback.size() == 0) {
			throw new SQLException("non sono presenti utenti registrati");
		} else {
			return feedback;
		}
		// controllo;
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
			String IdFeedback = rs.getString("id_feedback");
			String IdUtente = rs.getString("id_Utente");

			Feedback result = new Feedback(IdFeedback, IdUtente, 0);
			feedback.add(result);
		}
		if (feedback.size() == 0) {
			throw new SQLException("non sono presenti feedback");
		} else {
			return feedback;
		}
		// da controllare;
	}

	/*
	 * lettura di tutti i feedback scritti per un certo corso (nota: non edizione ma
	 * corso) se non ci sono feedback o il corso non esiste si torna una lista vuota
	 */
	@Override
	public ArrayList<Feedback> selectFeedbackPerCorso(int idCorso) throws SQLException {
		ArrayList<Feedback> feedback = new ArrayList<Feedback>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM catalogo where = id_corso=?");

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String IdFeedback = rs.getString("id_feedback");
			String IdCorso = rs.getString("id_corso");

			Feedback result = new Feedback(IdFeedback, IdCorso, 0);
			feedback.add(result);
		}
		if (feedback.size() == 0) {
			throw new SQLException("non sono presenti feedback");
		} else {
			return feedback;
		}
//controllo;
	}

}
