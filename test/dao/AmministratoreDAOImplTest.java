package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import entity.Utente;
import exceptions.ConnessioneException;

class AmministratoreDAOImplTest {

	@Test
	void insert() throws Exception {
		Utente u = new Utente("2", "password", "aa", "bb", new java.util.Date(), "pp@gmail.com", "123456789", true);
		try {
			AmministratoreDAO dao = new AmministratoreDAOImpl();
//			DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
//			Date date = df.parse("1975-02-25");
			dao.insert(u);
			assertEquals("2", u.getIdUtente());
			assertEquals("password", u.getPassword());
			assertEquals("aa", u.getNome());
			assertEquals("bb", u.getCognome());
//          assertEquals("", u.getDataNascita());
			assertEquals("pp@gmail.com", u.getEmail());
			assertEquals("123456789", u.getTelefono());
		} catch (Exception ex) {
			fail("UEx" + ex.getMessage());
		}
	}

	@Test
	void select() throws Exception {
		Utente u = new Utente("2", "password", "aa", "bb", new java.util.Date(), "pp@gmail.com", "123456789", true);
		try {
			AmministratoreDAO dao = new AmministratoreDAOImpl();
			dao.select("2");
			assertEquals("2", u.getIdUtente());
		} catch (Exception ex) {
			fail("UEx" + ex.getMessage());
		}
	}

	@Test
	void set() throws Exception {
		Utente u = new Utente("2", "password", "aa", "bb", new java.util.Date(), "pp@gmail.com", "123456789", true);
		try {
			AmministratoreDAO dao = new AmministratoreDAOImpl();
			u.setCognome("Doria");
			assertEquals("Doria", u.getCognome());
		} catch (Exception ex) {
			fail("UEx" + ex.getMessage());
		}
	}
	
	@Test
	void delete() throws Exception {
		Utente u = new Utente("2", "password", "aa", "bb", new java.util.Date(), "pp@gmail.com", "123456789", true);
		try {
			AmministratoreDAO dao = new AmministratoreDAOImpl();
			dao.delete("aa");
//			assertEquals("", u.getNome());
		} catch (Exception ex) {
			fail("UEx" + ex.getMessage());
		}
	}
		
//		u.setCognome("Doria");
//		dao.delete("aa");
//		dao.update(u);
//		System.out.println(dao.select("marco81"));
	}
