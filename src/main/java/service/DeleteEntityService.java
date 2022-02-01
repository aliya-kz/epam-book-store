package service;
import DAO.AddressDao;
import DAO.CardDao;
import DAO.SqlDaoFactory;
import DAO.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class DeleteEntityService implements Service{
    SqlDaoFactory factory = SqlDaoFactory.getInstance();
    UserDao userDao = factory.getUserDao();
    CardDao cardDao = factory.getCardDao();
    AddressDao addressDao = factory.getAddressDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        String uri = request.getParameter("uri");
        if (table.equals("cards")) {
            cardDao.deleteById(id);
        } else if (table.equals("addresses")) {
            addressDao.deleteById(id);
        } else if (lang == null) {
            userDao.deleteById(table, id);
        } else {
            userDao.deleteByIdLang(table, id, lang);
        }
        request.getRequestDispatcher(request.getParameter("uri") + "?msg=error");
    }
}
