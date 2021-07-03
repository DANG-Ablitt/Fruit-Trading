package security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import security.user.SecurityStaff;
import security.user.StaffDetail;


/**
 * 当前登录用户
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Component
public class UserDetailHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(StaffDetail.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) {
        //获取用户信息
        StaffDetail user = SecurityStaff.getStaff();

        return user;
    }
}