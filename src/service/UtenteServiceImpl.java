package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.FeedbackDAO;
import dao.RegistrazioneUtenteDAO;
import dao.RegistrazioneUtenteDAOImpl;
import entity.Feedback;
import entity.Utente;
import exceptions.ConnessioneException;
import exceptions.DAOException;

public class UtenteServiceImpl implements UtenteService {

	// dichiarare qui tutti i dao di cui si ha bisogno
	private RegistrazioneUtenteDAO daoRegUtente;
	private FeedbackDAO daoFeedUtente;
	// ... dichiarazione di altri eventuali DAO

	// costruire qui tutti i dao di cui si ha bisogno
	public UtenteServiceImpl() throws ConnessioneException {
		daoRegUtente = new RegistrazioneUtenteDAOImpl();
		// ... costruzione dei altri eventuali dao
	}

	/*
	 * registrazione nel sistema di un nuovo utente Se l'utente è già presente si
	 * solleva una eccezione
	 */
	@Override
	public void registrazioneUtente(Utente u) throws DAOException {
		try {
			daoRegUtente.insert(u);
		} catch (SQLException e) {
			throw new DAOException("Utente già registrato! :'(", e);
		}
	}

	/**
	 * controllo della presenza di un utente in base a idUtente e password Se
	 * l'utente � presente viene recuperato e ritornato Se l'utente non � presente
	 * (o password errata) si solleva una eccezione
	 */
	@Override
	public Utente checkCredenziali(String idUtente, String psw) throws DAOException {
		try {
			Utente result = daoRegUtente.select(idUtente);
			if (!result.getPassword().equals(psw)) {
				throw new DAOException("Password errata");
			}
			return result;
		} catch (SQLException e) {
			throw new DAOException("Utente non presente", e);
		}
	}

	/*
	 * cancellazione di un utente dal sistema l'utente è cancellabile solo se non vi
	 * sono dati correlati. (ad esempio, non è (o è stato) iscritto a nessuna
	 * edizione) se l'utente non è cancellabile si solleva una eccezione
	 * 
	 */
	@Override
	public void cancellaRegistrazioneUtente(String idUtente) throws DAOException {
		try {
			daoRegUtente.delete(idUtente);
		} catch (SQLException e) {
			throw new DAOException("Impossibile eliminare l'utenza", e);
		}
	}

	/*
	 * modifica tutti i dati di un utente l'utente viene individuato in base a
	 * idUtente se l'utente non è presente si solleva una eccezione
	 */
	@Override
	public void modificaDatiUtente(Utente u) throws DAOException {
		try {
			daoRegUtente.update(u);
		} catch (SQLException e) {
			throw new DAOException("utente " + u.getIdUtente() + " non presente", e);
		}

	}

	/*
	 * legge tutti gli utenti registrati sul sistema se non vi sono utenti il metodo
	 * ritorna una lista vuota
	 */
	@Override
	public ArrayList<Utente> visualizzaUtentiRegistrati() throws DAOException {
		try {
			ArrayList<Utente> result = daoRegUtente.select();
			return result;
		} catch (SQLException e) {
			throw new DAOException("non sono presenti utenti registrati", e);
		}
	}

	/*
	 * inserisce un feedback per una certa edizione Un utente può inserire un
	 * feedback solo per i corsi già frequentati e già terminati e solo se non lo ha
	 * già fatto in precedenza (un solo feedback ad utente per edizione) se l'utente
	 * non può inserire un feedback si solleva una eccezione
	 */
	@Override
	public void inserisciFeedback(Feedback f) throws DAOException {
		try {
			daoFeedUtente.insert(f);
		} catch (SQLException e) {
			throw new DAOException("impossibile inserire feedback", e);
		}
	}

	/*
	 * modifica della descrizione e/o del voto di un feedback. Il feedback è
	 * modificabile solo da parte dell'utente che lo ha inserito e solo entro un
	 * mese dal termine della edizione del corso Se l'utente non può modificare un
	 * feedback si solleva una eccezione.
	 */
	@Override
	public void modificaFeedback(Feedback feedback) throws DAOException {
		try {
			daoFeedUtente.update(feedback);
		} catch (SQLException e) {
			throw new DAOException("feedback " + feedback.getIdFeedback() + " non presente", e);
		}

	}

	/*
	 * eliminazione di un feedback il feedback è cancellabile solo da parte
	 * dell'utente che lo ha inserito e solo entro un mese dal termine della
	 * edizione del corso se l'utente non può cancellare un feedback si solleva una
	 * eccezione
	 */
	@Override
	public void cancellaFeedback(int idFeedback) throws DAOException {
		try {
			daoFeedUtente.delete(idFeedback);
		} catch (SQLException e) {
			throw new DAOException("Impossibile eliminare il feedback", e);
		}
	}
}
