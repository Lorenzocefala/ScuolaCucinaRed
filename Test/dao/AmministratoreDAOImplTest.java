package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// import entity.Utente;

class AmministratoreDAOImplTest {

	@Test
	void test() throws Exception {
		Utente u = new Utente("2","password","aa","aa", new java.util.Date(01/11/1997),"pp@gmail.com","123456789", true);
		try {
		AmministratoreDAO dao= new AmministratoreDAOImpl();
		dao.insert(u);
		dao.select("2");
		assertThat (u, is ("2","password","aa","aa", new java.util.Date(01/11/1997),"pp@gmail.com","123456789", true));
		} catch (Exception ex) {
			fail ("UEx" + ex.getMessage());
		}
//		u.setCognome("Doria");
//		dao.delete("aa");
//		dao.update(u);
//		System.out.println(dao.select("marco81"));
	}

}
