package aspect;

import annotation.LogOperation;
import com.alibaba.fastjson.JSON;
import config.ModuleConfig;
import log.SysLogOperation;
import log.enums.LogTypeEnum;
import log.enums.OperationStatusEnum;
import log.producer.LogProducer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.HttpContextUtils;
import utils.IpUtils;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志，切面处理类
 */
@Aspect
@Component
public class LogOperationAspect {
    @Autowired
    private ModuleConfig moduleConfig;
    @Autowired
    private LogProducer logProducer;

    @Pointcut("@annotation(annotation.LogOperation)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            //执行方法
            Object result = point.proceed();
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.SUCCESS.value());
            return result;
        }catch(Exception e) {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.FAIL.value());
            throw e;
        }
    }


    private void saveLog(ProceedingJoinPoint joinPoint, long time, Integer status) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogOperation log = new SysLogOperation();
        LogOperation annotation = method.getAnnotation(LogOperation.class);
        if(annotation != null){
            //注解上的描述
            log.setOperation(annotation.value());
        }
        //登录用户信息
        StaffDetail user = SecurityStaff.getStaff();
        if(user != null){
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
        }

        log.setType(LogTypeEnum.OPERATION.value());
        log.setModule(moduleConfig.getName());
        log.setStatus(status);
        log.setRequestTime((int)time);
        log.setCreateDate(new Date());
        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setRequestUri(request.getRequestURI());
        log.setRequestMethod(request.getMethod());
        //请求参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = JSON.toJSONString(args[0]);
            log.setRequestParams(params);
        }catch (Exception e){

        }
        //保存到Redis队列里
        logProducer.saveLog(log);
    }
}