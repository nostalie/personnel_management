package cn.edu.jlu.personnel.management.util;

/**
 * Created by jessin on 17-4-1.
 */

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 获取客户端信息的工具
 */
public final class NetworkUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(NetworkUtil.class);

    private final static String UNKNOWN_HOST = "unknown";
    private final static Splitter SPLITTER = Splitter.on(",").trimResults();
    // xxx.xxx.xxx.xxx
    private final static int MAX_IP_LENGTH = 15;

    /**
     * 获取请求主机IP地址,如果通过代理(如nginx)进来，则透过防火墙获取客户端真实IP地址;
     * 
     * @param httpServletRequest
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest httpServletRequest) {

        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        LOGGER.debug("X-Forwarded-For : {}", ip);

        if (Strings.isNullOrEmpty(ip) || UNKNOWN_HOST.equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
            LOGGER.debug("Proxy-Client-IP : {}", ip);
        }

        if (Strings.isNullOrEmpty(ip) || UNKNOWN_HOST.equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
            LOGGER.debug("WL-Proxy-Client-IP : {}", ip);
        }

        if (Strings.isNullOrEmpty(ip) || UNKNOWN_HOST.equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
            LOGGER.debug("HTTP_CLIENT_IP : {}", ip);
        }

        if (Strings.isNullOrEmpty(ip) || UNKNOWN_HOST.equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
            LOGGER.debug("HTTP_X_FORWARDED_FOR : {}", ip);
        }

        if (Strings.isNullOrEmpty(ip) || UNKNOWN_HOST.equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
            LOGGER.debug("getRemoteAddr : {}", ip);
        }
        // 超过IP地址的最大长度时，认为有多个IP，获取第一个非unknown的主机
        if (!Strings.isNullOrEmpty(ip) && ip.length() > MAX_IP_LENGTH) {
            Iterable<String> allIp = SPLITTER.split(ip);
            for (String tmpIp : allIp) {
                if (!Strings.isNullOrEmpty(tmpIp) && !UNKNOWN_HOST.equalsIgnoreCase(tmpIp)) {
                    ip = tmpIp;
                    break;
                }
            }
        }
        return ip;
    }
}
