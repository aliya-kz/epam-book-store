package service;

import DAO.SqlDaoFactory;
import DAO.UserDao;
import entity.Book;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditProfileService implements Service {

    UserDao userDao = SqlDaoFactory.getInstance().getUserDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        User user = (User) session.getAttribute("user");
        String name = request.getParameter("name").trim();
        if (name != null && name.length()>0) {
            int result = userDao.setColumnValue("users", id, "name", name);
            if (result > 0) user.setName(name);
        }

        String surname = request.getParameter("surname").trim();
        if (surname != null && surname.length()>0) {
            int result = userDao.setColumnValue("users", id, "surname", surname);
            if (result > 0) user.setSurname(surname);
        }

        String phone = request.getParameter("phone").trim();
        if (phone != null && phone.length()>0) {
            int result = userDao.setColumnValue("users", id, "phone", phone);
            if (result > 0) user.setPhone(phone);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile#prof-personal");
        dispatcher.forward(request, response);
    }
}
