package service;

import dao.BaseDao;
import dao.impl.BookDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.GeneralConstants.*;

public class GetImageService implements Service {
    private final static String CONTENT_TYPE = "image/jpeg";
    private final BaseDao dao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int imageId = Integer.parseInt(request.getParameter(IMAGE_ID));
        String table = request.getParameter(TABLE);
        byte[] imageBytes = dao.getByteImage(imageId, table);
        response.setContentType(CONTENT_TYPE);
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}
