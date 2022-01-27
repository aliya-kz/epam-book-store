package service;
import DAO.SqlDaoFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteEntityService implements Service{
    SqlDaoFactory factory = SqlDaoFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        Service service = new GetAllCategoriesService();
        switch (table) {
            case "categories": factory.getCategoryDao().deleteById(table, id);
            service.execute(request, response);
            break;
            case "categories_lang": factory.getCategoryDao().deleteByIdLang(table, id, lang);
            service.execute(request, response);
            case "authors": factory.getAuthorDao().deleteById(table, id);
            service = new GetAllAuthorsService();
            service.execute(request, response);
            break;
            case "authors_lang": factory.getAuthorDao().deleteByIdLang(table, id, lang);
            service = new GetAllAuthorsService();
            service.execute(request, response);
            break;
            default: request.getRequestDispatcher(request.getParameter("uri") + "?msg=error");
        }
    }
}
