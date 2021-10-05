package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.entity.Admin;
import cn.unuuc.novel.service.AdminService;
import cn.unuuc.novel.utils.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    AdminService adminService;

    /**
     * 管理员登录
     * @param account
     * @param pwd
     * @param code
     * @param session
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxJson login(String account, String pwd, String code, HttpSession session){
        if(account == null || pwd == null || code == null)
            return AjaxJson.getNotJur("字段为空");
        String rightCode = (String) session.getAttribute("rightCode");
        if(rightCode == null || !rightCode.equals(code))
            return AjaxJson.getNotJur("验证码不一致!");
        Admin admin = adminService.getAdminByAccount(account);
        if(admin == null){
            return AjaxJson.getNotJur("用户不存在");
        }
        if(admin.getPassword().equals(pwd)){
            session.setAttribute("loginId",admin.getId());
            session.setAttribute("name",account);
            return AjaxJson.getSuccess("登录成功!");
        }
        return AjaxJson.getNotJur("密码错误");
    }

    /**
     * 管理员退出登录
     * @param session
     * @param resp
     * @throws IOException
     */
    @GetMapping("/exit")
    public void loginExit(HttpSession session, HttpServletResponse resp) throws IOException {
        session.removeAttribute("loginId");
        resp.sendRedirect("/admin/login");
    }

    /**
     * 管理员修改密码
     * @param oldPwd
     * @param newPwd
     * @param newPwdS
     * @param session
     * @return
     */
    @PostMapping("/alter/pwd")
    @ResponseBody
    public AjaxJson alterPwd(String oldPwd,String newPwd,String newPwdS,HttpSession session){
        if(oldPwd == null || newPwd == null || newPwdS == null)
            return AjaxJson.getNotJur("字段为空");
        if(!newPwd.equals(newPwdS))
            return AjaxJson.getNotJur("两次密码不一致");
        Integer loginId = (Integer) session.getAttribute("loginId");
        Admin admin = adminService.getAdminById(loginId);
        if(admin.getPassword().equals(oldPwd)){
            admin.setPassword(newPwd);
            adminService.updateAdmin(admin);
            return AjaxJson.getSuccess("修改成功!");
        }else {
            return AjaxJson.getNotJur("旧密码不正确");
        }
    }
}
