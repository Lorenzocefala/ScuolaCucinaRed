package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import entity.Utente;

class AmministratoreDAOImplTest {

	@Test
	void insert() throws Exception {
		AmministratoreDAO dao = new AmministratoreDAOImpl();
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Utente u = new Utente("2", "password", "aa", "bb", df.parse("1975-02-25"), "pp@gmail.com", "123456789",
					true);

			dao.insert(u);
			Utente result = dao.select("2");

			assertEquals(result.getIdUtente(), u.getIdUtente());
			assertEquals(result.getPassword(), u.getPassword());
			assertEquals(result.getNome(), u.getNome());
			assertEquals(result.getCognome(), u.getCognome());
			assertEquals(result.getEmail(), u.getEmail());
			assertEquals(result.getTelefono(), u.getTelefono());

			dao.delete("2");
		} catch (Exception ex) {
			fail("Unexpected exception: " + ex.getMessage());
		} finally {
			try {
				dao.select("2");
				fail("Select should have thrown an exception here!");
			} catch (Exception ex) {
				// as expected
			}
		}

	}

	@Test
	void update() throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		AmministratoreDAO dao = new AmministratoreDAOImpl();
		try {
			Utente u = new Utente("2", "password", "aa", "bb", df.parse("1975-02-25"), "pp@gmail.com", "123456789",
					true);
			dao.insert(u);

			u.setCognome("Doria");
			dao.update(u);

			Utente result = dao.select("2");

			assertEquals(result.getCognome(), u.getCognome());

			dao.delete("2");
		} catch (Exception ex) {
			fail("Unexpected exception: " + ex.getMessage());
		} finally {
			try {
				dao.select("2");
				fail("Select should have thrown an exception here!");
			} catch (Exception ex) {
				// as expected
			}
		}
	}

}
