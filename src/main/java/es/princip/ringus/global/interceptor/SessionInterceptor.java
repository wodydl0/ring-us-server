package es.princip.ringus.global.interceptor;

import es.princip.ringus.domain.exception.MemberErrorCode;
import es.princip.ringus.global.annotation.SessionCheck;
import es.princip.ringus.global.exception.CustomRuntimeException;
import es.princip.ringus.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(isCheckSession(handler)){
            vaildateSession(request, response);
        }
        return true;
    }

    private boolean isCheckSession(Object handler){
        return handler instanceof HandlerMethod handlerMethod && handlerMethod.hasMethodAnnotation(SessionCheck.class);
    }

    private void vaildateSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("memberId") == null){
            throw new CustomRuntimeException(MemberErrorCode.SESSION_EXPIRED);
        }

        System.out.println("[Session Interceptor] : Interceptor 실행, 세션 유효, 멤버 ID: " + session.getAttribute("memberId"));
        CookieUtil.addSessionCookie(response, session.getId());
        request.setAttribute("memberId", session.getAttribute("memberId"));
    }
}
