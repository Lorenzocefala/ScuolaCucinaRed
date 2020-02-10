package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entity.Utente;

class AmministratoreDAOImplTest {

	@Test
	void test() throws Exception {
		AmministratoreDAO dao= new AmministratoreDAOImpl();
		Utente u = new Utente("aa","aa","aa","aa", new java.util.Date(),"pp","pp", true);
		dao.insert(u);
//		u.setCognome("Doria");
//		dao.delete("aa");
//		dao.update(u);
//		System.out.println(dao.select("marco81"));
	}

}
