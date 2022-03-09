package service;

import DAO.UserDao;
import DAO.impl.UserDaoImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;

public class EditProfileService implements Service {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter(ID));
        User user = (User) session.getAttribute(USER);
        String name = request.getParameter(NAME).trim();
        if (name != null && name.length() > 0) {
            int result = userDao.setColumnValue(USERS, id, NAME, name);
            if (result > 0) user.setName(name);
        }

        String surname = request.getParameter(SURNAME).trim();
        if (surname != null && surname.length() > 0) {
            int result = userDao.setColumnValue(USERS, id, SURNAME, surname);
            if (result > 0) user.setSurname(surname);
        }

        String phone = request.getParameter(PHONE);

        if (phone != null && phone.length() > 0) {
            phone = phone.replaceAll("[\\s\\-\\(\\)]", "");
            int result = userDao.setColumnValue(USERS, id, PHONE, phone);
            if (result > 0) user.setPhone(phone);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile#prof-personal");
        dispatcher.forward(request, response);
    }
}
