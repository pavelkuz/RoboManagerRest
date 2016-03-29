package kz.kuzovatov.pavel.utils;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final String REQUEST_RESPONSE_ENCODING = "utf-8";
    private static final String CONTENT_TYPE = "text/plain";
    private String encoding = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (encoding != null){
            request.setCharacterEncoding(REQUEST_RESPONSE_ENCODING);
            response.setCharacterEncoding(REQUEST_RESPONSE_ENCODING);
            response.setContentType(CONTENT_TYPE);
        }
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }

    public void destroy() {
        this.encoding = null;
    }
}
