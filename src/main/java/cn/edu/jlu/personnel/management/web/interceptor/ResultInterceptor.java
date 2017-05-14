package cn.edu.jlu.personnel.management.web.interceptor;

import cn.edu.jlu.personnel.management.vo.ResponseResult;
import cn.edu.jlu.personnel.management.vo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by nostalie on 17-5-12.
 */
public class ResultInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.debug("come in interceptor");
        LOGGER.debug("model and view is null: {}",modelAndView == null);
        if(modelAndView != null && !modelAndView.getViewName().contains("/")){
            String viewName = modelAndView.getViewName();
            modelAndView.addObject(viewName,viewName);
        }
        if(modelAndView != null && modelAndView.getModel() != null){
            Object result = null;
            for(Map.Entry<String,Object> entry : modelAndView.getModel().entrySet()){
                LOGGER.debug("key is: {},value is: {}",entry.getKey(),entry.getValue());
                if(!entry.getKey().contains(".")){
                    result = entry.getValue();
                }
            }
            ResponseResult responseResult = ResponseResult.ok(result);
            response.setCharacterEncoding("utf8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(responseResult));
            writer.flush();
        }
    }
}
