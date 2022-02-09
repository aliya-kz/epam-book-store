package service;

import entity.Book;
import entity.Category;
import entity.Format;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class BookFilterService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        List<Book> books = (List<Book>) session.getAttribute("books");
        List<Category> categories = (List<Category>) session.getAttribute("categories");
        int categoriesNum = categories.size();
        List<Format> formats = (List<Format>) session.getAttribute("formats");
        int formatsNum = formats.size();
        Map<String, int[]> filterParams = (HashMap<String, int[]>) session.getAttribute("filterParams");

        String paramName = request.getParameter("paramName");
        String stringIds[] = request.getParameterValues("id");
        int[] ids = Stream.of(stringIds).mapToInt(Integer::parseInt).toArray();

        if (filterParams == null) {
            filterParams = new HashMap<>();
            filterParams.put("category", range(categoriesNum));
            filterParams.put("format", range(formatsNum));
        }

        for (String s: filterParams.keySet()) {
            System.out.println("s " + s + " " + filterParams.get(s).length) ;
        }

        filterParams.replace(paramName, ids);
        System.out.println("new " + filterParams.get("format")[0]);
        session.setAttribute("filterParams", filterParams);
        List<Book> filterByCategory = filterByCategory(books, filterParams);
        List<Book> filteredBooks = filterByFormat(filterByCategory, filterParams);
        session.setAttribute("filteredBooks", filteredBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/books");
        dispatcher.forward(request, response);
    }

    public List<Book> filterByFormat(List<Book> books, Map<String, int[]> filterParams) {
        List <Book> result = new ArrayList<>();
        for (String param : filterParams.keySet()) {
            if (param.equals("format")) {
                int[] ids = filterParams.get(param);
                for (int id : ids) {
                    for (Book book: books) {
                        if (id == book.getFormatId()) {
                            result.add(book);
                        }
                    }
                }
            }
        }
        System.out.println(result.size());
        return result;
    }

    public List<Book> filterByCategory(List<Book> books, Map<String, int[]> filterParams) {
        List <Book> result = new ArrayList<>();
        for (String param : filterParams.keySet()) {
            if (param.equals("category")) {
                int[] ids = filterParams.get(param);
                for (int id : ids) {
                    for (Book book: books) {
                        if (id == book.getCategoryId()) {
                            result.add(book);
                        }
                    }
                }
            }
        }
        System.out.println(result.size());
        return result;
    }

    public int[] range(int number) {
        int[] result = new int[number];
        for (var i = 0; i < number; i += 1) {
            result[i] = i + 1;
        }
        return result;
    }
}
