package mybatis_plus.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import mybatis_plus.aspect.DataFilterAspect;
import mybatis_plus.entity.DataScope;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 数据过滤
 * 三步骤：
 *  1 实现 Interceptor 接口
 *  2 添加拦截注解 Intercepts
 *      mybatis 拦截器默认可拦截的类型只有四种，即四种接口类型 Executor、StatementHandler、ParameterHandler 和 ResultSetHandler
 *  *  对于我们的自定义拦截器必须使用 mybatis 提供的注解来指明我们要拦截的是四类中的哪一个类接口
 *  *  具体规则如下：
 *  *    a：Intercepts 标识我的类是一个拦截器
 *  *    b：Signature 则是指明我们的拦截器需要拦截哪一个接口的哪一个方法
 *  *        type    对应四类接口中的某一个，比如是 Executor
 *  *        method  对应接口中的哪类方法，比如 Executor 的 update 方法
 *  *        args    对应接口中的哪一个方法，比如 Executor 中 query 因为重载原因，方法有多个，args 就是指明参数类型，从而确定是哪一个方法
 *  3 配置文件中添加拦截器
 *
 *  拦截器顺序
 *  * 1 不同拦截器顺序：
 *  *      Executor -> ParameterHandler -> StatementHandler -> ResultSetHandler
 *  * 2 对于同一个类型的拦截器的不同对象拦截顺序：
 *  *      在 mybatis 核心配置文件根据配置的位置，拦截顺序是 从上往下
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataFilterInterceptor extends AbstractSqlParserHandler implements Interceptor {

    @Autowired
    private DataFilterAspect dataFilterAspect;

    /**
     * 这个方法里面就是拦截之后要做的事情
     * 1 我们知道，mybatis拦截器默认只能拦截四种类型
     *      Executor、StatementHandler、ParameterHandler 和 ResultSetHandler
     * 2 不管是哪种代理，代理的目标对象就是我们要拦截对象，举例说明：
     *    比如我们要拦截 Executor中的update(MappedStatement ms, Object parameter)} 方法，
     *    那么 Invocation 就是这个对象，Invocation 里面有三个参数 target method args
     *      target 就是 Executor
     *      method 就是 update
     *      args   就是 MappedStatement ms, Object parameter
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // SQL解析
        this.sqlParser(metaObject);
        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 执行的SQL语句
        String originalSql = boundSql.getSql();
        // 需要拼接的部分
        Object paramObj=dataFilterAspect.getThreadLocal().get();
        // 判断参数里是否有DataScope对象
        DataScope scope = null;
        if (paramObj instanceof DataScope) {
            scope = (DataScope) paramObj;
        }
        // 不用数据过滤
        if(scope == null){
            return invocation.proceed();
        }
        // 拼接新SQL
        originalSql = originalSql + scope.getSqlFilter();
        // 重写SQL
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        //移除本地线程中的数据
        dataFilterAspect.getThreadLocal().remove();
        return invocation.proceed();
    }

    /**
     * 作用：Mybatis在创建拦截器代理时候会判断一次，
     * 当前这个 Interceptor 类到底需不需要生成一个代理进行拦截，
     * 如果需要拦截，就生成一个代理对象，这个代理就是一个 Plugin，
     * 它实现了jdk的动态代理接口 InvocationHandler，
     * 如果不需要代理，则直接返回目标对象本身
     *
     * Mybatis为什么会判断一次是否需要代理呢？
     * 默认情况下，Mybatis只能拦截四种类型的接口：Executor、StatementHandler、ParameterHandler 和 ResultSetHandler
     * 通过Intercepts 和 Signature 两个注解共同完成
     * 试想一下，如果我们开发人员在自定义拦截器上没有指明类型，或者随便写一个拦截点，比如Object，那Mybatis疯了，难道所有对象都去拦截
     * 所以Mybatis会做一次判断，拦截点看看是不是这四个接口里面的方法，不是则不拦截，直接返回目标对象，如果是则需要生成一个代理
     *  该方法在 mybatis 加载核心配置文件时被调用
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            //让mybatis判断，要不要进行拦截
            //Mybatis判断依据是利用反射，获取这个拦截器 MyInterceptor 的注解 Intercepts和Signature，然后解析里面的值，
            //1 先是判断要拦截的对象是四个类型中 Executor、StatementHandler、ParameterHandler、 ResultSetHandler 的哪一个
            //2 然后根据方法名称和参数(因为有重载)判断对哪一个方法进行拦截  Note：mybatis可以拦截这四个接口里面的任一一个方法
            //3 做出决定，是返回一个对象呢还是返回目标对象本身(目标对象本身就是四个接口的实现类，我们拦截的就是这四个类型)
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 这个方法最好理解，如果我们拦截器需要用到一些变量参数，而且这个参数是支持可配置的，
     *  类似Spring中的@Value("${}")从application.properties文件获取
     * 这个时候我们就可以使用这个方法
     *
     * 如何使用？
     * 只需要在 mybatis 配置文件中加入类似如下配置，然后 {@link Interceptor#setProperties(Properties)} 就可以获取参数
     *      <plugin interceptor="liu.york.mybatis.study.plugin.MyInterceptor">
     *           <property name="username" value="LiuYork"/>
     *           <property name="password" value="123456"/>
     *      </plugin>
     *      方法中获取参数：properties.getProperty("username");
     *
     * 问题：为什么要存在这个方法呢，比如直接使用 @Value("${}") 获取不就得了？
     * 原因是 mybatis 框架本身就是一个可以独立使用的框架，没有像 Spring 这种做了很多依赖注入的功能
     *
     *  该方法在 mybatis 加载核心配置文件时被调用
     */
    @Override
    public void setProperties(Properties properties) {

    }
}