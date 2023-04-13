package com.ssh1y.paperrec.controller;

import com.ssh1y.paperrec.dto.LoginDto;
import com.ssh1y.paperrec.entity.Users;
import com.ssh1y.paperrec.service.PapersService;
import com.ssh1y.paperrec.service.RatingsService;
import com.ssh1y.paperrec.service.UsersService;
import com.ssh1y.paperrec.utils.CreateVerifyCodeImage;
import com.ssh1y.paperrec.utils.JwtHelper;
import com.ssh1y.paperrec.utils.Md5Encrypt;
import com.ssh1y.paperrec.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chenweihong
 */
@RestController
@RequestMapping("/sms/system")
public class AccountController {

    @Resource
    private UsersService usersService;

    @Resource
    private RatingsService ratingsService;

    @Resource
    private PapersService papersService;

    /**
     * 获取验证码图片
     *
     * @param session  session
     * @param response response
     * @throws IOException IO异常
     */
    @RequestMapping("/getVerifyCodeImage")
    public void getVerifyCodeImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 获取验证码图片
        BufferedImage verifyCodeImage = CreateVerifyCodeImage.getVerifyCodeImage();
        // 获取验证码图片上的值
        String verifyCode = new String(CreateVerifyCodeImage.getVerifyCode());
        // 将验证码图片上的值存入session中
        session.setAttribute("verifyCode", verifyCode);
        // 将验证码图片响应给客户端
        ImageIO.write(verifyCodeImage, "png", response.getOutputStream());
    }

    /**
     * 登录
     *
     * @param loginForm 用户名、密码、验证码
     * @param session   session
     * @return Result
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginDto loginForm, HttpSession session) {
        // 获取session中的验证码
        String verifyCode = (String) session.getAttribute("verifyCode");
        // 获取用户输入的验证码
        String inputVerifyCode = loginForm.getVerifyCode();
        Map<String, Object> map = new LinkedHashMap<>();
        // 判断验证码是否失效
        if (verifyCode == null || "".equals(verifyCode)) {
            return Result.error("验证码失效");
        }
        // 判断验证码是否正确
        if (!verifyCode.equalsIgnoreCase(inputVerifyCode)) {
            return Result.error("验证码错误");
        }
        // 销毁session中的验证码
        session.removeAttribute("verifyCode");
        // 账号密码输入是否正确
        Users user = usersService.selectUserByUserNameAndPwd(loginForm.getUsername(),
                Md5Encrypt.encrypt(loginForm.getPassword()));
        if (user == null) {
            return Result.error("账号或密码错误");
        }
        // 生成token
        String token = JwtHelper.createToken(user.getUserid().longValue(), user.getRole());
        map.put("token", token);
        // 返回token
        return Result.success(map);
    }
}
