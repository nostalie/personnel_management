package cn.edu.jlu.personnel.management.support;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.concurrent.ExecutionException;

/**
 * 解析RowBounds分页
 * Created by nostalie on 17-3-16.
 */
public class RowBoundsArgumentResolver implements HandlerMethodArgumentResolver {

    private static Logger logger = LoggerFactory.getLogger(RowBoundsArgumentResolver.class);

    private static LoadingCache<String,RowBounds> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .initialCapacity(10)
            .build(new CacheLoader<String, RowBounds>() {
                @Override
                public RowBounds load(String key) throws Exception {
                    try {
                        String[] keys = StringUtils.tokenizeToStringArray(key,"_");
                        int offset = Integer.parseInt(keys[0]);
                        int limit = Integer.parseInt(keys[1]);
                        return new RowBounds(offset,limit);
                    } catch (NumberFormatException e) {
                        logger.error("解析RowBounds参数失败，使用默认值 offset：0，limit：100",e);
                        return new RowBounds(0,100);
                    }
                }
            });

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return RowBounds.class.isAssignableFrom(clazz);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer
            , NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        RowBounds result;
        String offset = null;
        String limit = null;
        try {
            offset = nativeWebRequest.getParameter("offset");
            limit = nativeWebRequest.getParameter("limit");
            result = loadingCache.get(offset + "_" + limit);
            logger.info("RowBounds is: {}",result);
            return result;
        } catch (ExecutionException e) {
            logger.error("从缓存中取RowBounds失败，offset is: {},limit is: {},使用默认RowBounds",offset,limit,e);
            return new RowBounds(0,100);
        }
    }
}
