package filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding;
    private ServletContext context;

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        encoding = fConfig.getInitParameter("characterEncoding");
        context = fConfig.getServletContext();
    }
}
