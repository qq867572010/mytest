package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.util.BeanUtils;
import com.itheima.util.MailUtils;
import com.itheima.util.UUIDUtils;
import com.itheima.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/user", name = "UserServlet")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    /*注册用户*/
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //先校验验证码
            String check = request.getParameter("check");
            String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
            if (check.equalsIgnoreCase(checkcode_server)) {
                //1.接收参数
                Map<String, String[]> map = request.getParameterMap();
                //2.封装实体
                User user = BeanUtils.populate(map, User.class);
                user.setStatus("N");//设置未激活状态
                user.setCode(UUIDUtils.getUuid());//给用户一个唯一激活码
                //3.完成功能
                boolean bn = service.register(user);
                if (bn) {
                    //注册成功，给邮箱发邮件
                    String email = user.getEmail();
                    String ip = request.getServerName();
                    int port = request.getServerPort();
                    String contextPath = request.getContextPath();

                    String url = "http://" + ip + ":" + port + contextPath + "/user?action=active&code=" + user.getCode();
                    String content = "注册成功，请<a href='" + url + "'>点击激活</a>后再登录";

                    MailUtils.sendMail(email, content);
                }
                //4.处理结果
                info = new ResultInfo(bn);
            } else {
                info = new ResultInfo(false, "验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统正忙，请稍后访问...");
        }

        //将info格式转换为json格式，返回客户端
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(info);
        response.getWriter().print(value);
    }

    /*激活用户*/
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String code = request.getParameter("code");
        //2.封装实体
        //3.完成功能
        boolean bn = service.active(code);
        //4.处理结果
        if (bn) {
            //激活成功，跳转登录页面
            response.sendRedirect(request.getContextPath() + "/login.html");
        } else {
            //激活失败
            response.getWriter().print("激活失败");
        }
    }

    /*登录查询*/
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1.接收参数
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String check = request.getParameter("check");
            String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
            //2.封装实体
            //3.完成功能
            if (check.equalsIgnoreCase(checkcode_server)) {
                //验证码正确，验证用户名密码
                User user = service.login(username, password);
                if (user != null) {
                    //验证是否激活
                    if (user.getStatus().equals("Y")) {
                        info = new ResultInfo(true);
                        //登陆成功
                        request.getSession().setAttribute("loginUser", user);
                    } else {
                        //账号尚未激活
                        info = new ResultInfo(false, "账号尚未激活");
                    }
                } else {
                    //用户名或密码错误
                    info = new ResultInfo(false, "用户名或密码错误");
                }
            } else {
                //验证码错误
                info = new ResultInfo(false, "验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统正忙，请稍后访问......");
        }
        //4.处理结果,将info转换成json并返回客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*登录前后的页面显示*/
    public void getLoginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        //1.从session里获取loginUser
        User user = (User) request.getSession().getAttribute("loginUser");
        //2.判断
        if (user != null) {
            //处于登录状态
            info = new ResultInfo(true, user.getName(), "");
        } else {
            //无账号登录
            info = new ResultInfo(false);
        }
        //将info转换成json返回客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*退出*/
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.清除数据session
        request.getSession().invalidate();
        //2.跳转首页
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}