package njurestaurant.njutakeout.Log;

import njurestaurant.njutakeout.dataservice.SystemLogService;
import njurestaurant.njutakeout.entity.SystemLog;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.transaction.FailedToLoadCodeResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Description: 定义日志切入类
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:05
 * @Version: 1.0
 */
@Aspect
@Component
@Order(-5)
public class SystemLogAspect {
    private int uid;

    Object proceed = null;
    @Autowired
    private SystemLogService systemLogService;

//    /***
//     * 定义service切入点拦截规则，拦截SystemServiceLog注解的方法
//     */
//    @Pointcut("@annotation(com.vesus.springbootlog.annotation.SystemServiceLog)")
//    public void serviceAspect(){}

    /***
     * 定义controller切入点拦截规则，拦截SystemControllerLog注解的方法
     */
    @Pointcut("@annotation(njurestaurant.njutakeout.Log.SystemControllerLog)")
    public void controllerAspect() {
    }

    /***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public ResponseEntity<Response> recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        FailedToLoadCodeResponse failedToLoadCodeResponse = null;
        WrongResponse wrongResponse = null;
        SystemLog systemLog = new SystemLog();
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        systemLog.setUserid(uid);
        //获取请求的ip
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        //获取执行的方法名
        String methodName = joinPoint.getSignature().getName();
        systemLog.setActionmethod(methodName);
        //获取方法执行前时间
        Date date = new Date();
        systemLog.setActiondate(date);
        proceed = joinPoint.proceed();
        //提取controller中ExecutionResult的属性
        ResponseEntity<Response> result = (ResponseEntity<Response>) proceed;
        JSONResponse jsonResponse = (JSONResponse) result.getBody();

        if (jsonResponse.getCode() == 200) {
            systemLog.setType("正常");
            systemLog.setDescription(getControllerMethodDescription(joinPoint) + ":" + "成功");
//            systemLog.setParams(getParamValue(joinPoint));
//            systemLogService.save(systemLog);
//            return new ResponseEntity<>(new JSONResponse(200, personalCardAddResponse), HttpStatus.OK);
        } else {
            systemLog.setType("异常");
            systemLog.setDescription(getControllerMethodDescription(joinPoint) + ":" + "失败");
            System.out.println("8888888888888888888888888888888888888888888");
            System.out.println(jsonResponse.getData().getClass().getName());
            if (jsonResponse.getData().getClass().getName().equals("njurestaurant.njutakeout.response.transaction.FailedToLoadCodeResponse")){
                failedToLoadCodeResponse =(FailedToLoadCodeResponse) jsonResponse.getData();
                systemLog.setExceptioncode(jsonResponse.getCode());
                systemLog.setExceptiondetail(failedToLoadCodeResponse.getReason());
            }
            if (jsonResponse.getData().getClass().getName().equals("njurestaurant.njutakeout.response.WrongResponse")) {
                wrongResponse = (WrongResponse) jsonResponse.getData();
                systemLog.setExceptioncode(wrongResponse.getInfoCode());
                systemLog.setExceptiondetail(wrongResponse.getDescription());
            }
            //return new ResponseEntity<>(new JSONResponse(10110, new WrongResponse(wrongResponse.getInfoCode(), wrongResponse.getDescription())), HttpStatus.OK);
        }
        systemLog.setParams(getParamValue(joinPoint));
        systemLogService.save(systemLog);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);


    }

    private static String[] types = {"java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char", "java.lang.String", "int", "double", "long", "short", "byte", "boolean", "char", "float"};

    public static String getParamValue(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] strings = methodSignature.getParameterNames();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        //获取所有的参数
        Object[] args = joinPoint.getArgs();
        for (int k = 0; k < args.length; k++) {
            Object arg = args[k];
            // 获取对象类型
            String typeName = arg.getClass().getTypeName();
            for (String t : types) {
                //1 判断是否是基础类型
                if (t.equals(typeName)) {
                    sb.append(strings[k] + ":" + arg + ", ");
                } else {
                    i++;
                }
            }
            System.out.println("dfsfmdskfdsf");
            System.out.println(i);
            System.out.println(types.length);
            if (i == types.length)
                //2 通过反射获取实体类属性
                sb.append(getFieldsValue(arg));
            i = 0;
        }
        if (sb.charAt(sb.length() - 1) == ',')
            sb.deleteCharAt(sb.lastIndexOf("."));
        return sb.toString();
    }

    //解析实体类，获取实体类中的属性
    public static String getFieldsValue(Object obj) {
        //通过反射获取所有的字段，getFileds()获取public的修饰的字段
        //getDeclaredFields获取private protected public修饰的字段
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();
        for (String t : types) {
            if (t.equals(typeName)) {
                return "";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Field f : fields) {
            //在反射时能访问私有变量
            f.setAccessible(true);
            try {
                for (String str : types) {
                    //这边会有问题，如果实体类里面继续包含实体类，这边就没法获取。
                    // 其实，我们可以通递归的方式去处理实体类包含实体类的问题。
                    if (f.getType().getName().equals(str)) {
                        sb.append(f.getName() + " : " + f.get(obj) + ", ");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }

//    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
//    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
//        SystemLog systemLog = new SystemLog();
//        Object proceed = null;
//        //获取session中的用户
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String opContent = optionContent(joinPoint);
//        systemLog.setUserid(uid);
//        //获取请求的ip
//        String ip = request.getRemoteAddr();
//        systemLog.setRequestip(ip);
//        systemLog.setType("异常");
//        systemLog.setExceptioncode(e.getClass().getName());
//        systemLog.setExceptiondetail(e.getMessage());
//        systemLog.setActiondate(new Date());
//        systemLog.setActionmethod(joinPoint.getSignature().getName());
//        systemLog.setParams(opContent);
//        systemLogService.save(systemLog);
//    }

    /***
     * 获取service的操作信息
     * @param joinpoint
     * @return
     * @throws Exception
     */
//    public String getServiceMethodMsg(JoinPoint joinpoint) throws Exception{
//        //获取连接点目标类名
//        String className =joinpoint.getTarget().getClass().getName() ;
//        //获取连接点签名的方法名
//        String methodName = joinpoint.getSignature().getName() ;
//        //获取连接点参数
//        Object[] args = joinpoint.getArgs() ;
//        //根据连接点类的名字获取指定类
//        Class targetClass = Class.forName(className);
//        //拿到类里面的方法
//        Method[] methods = targetClass.getMethods() ;
//
//        String description = "" ;
//        //遍历方法名，找到被调用的方法名
//        for (Method method : methods) {
//            if (method.getName().equals(methodName)){
//                Class[] clazzs = method.getParameterTypes() ;
//                if (clazzs.length==args.length){
//                    //获取注解的说明
//                    description = method.getAnnotation(SystemServiceLog. class).decription();
//                    break;
//                }
//            }
//        }
//        return description ;
//    }

    /***
     * 获取controller的操作信息
     * @param point
     * @return
     */
    public String getControllerMethodDescription(ProceedingJoinPoint point) throws Exception {
        //获取连接点目标类名
        String targetName = point.getTarget().getClass().getName();
        //获取连接点签名的方法名
        String methodName = point.getSignature().getName();
        //获取连接点参数
        Object[] args = point.getArgs();
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length) {
                    description = method.getAnnotation(SystemControllerLog.class).descrption();
                    break;
                }
            }
        }
        return description;
    }

}
