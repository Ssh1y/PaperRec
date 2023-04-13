package com.ssh1y.paperrec.controller;


import com.ssh1y.paperrec.dto.RatingForm;
import com.ssh1y.paperrec.dto.UpdateInfoDto;
import com.ssh1y.paperrec.entity.Users;
import com.ssh1y.paperrec.service.RatingsService;
import com.ssh1y.paperrec.service.SearchhistoryService;
import com.ssh1y.paperrec.service.UsersService;
import com.ssh1y.paperrec.utils.JwtHelper;
import com.ssh1y.paperrec.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenweihong
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UsersService usersService;

    @Resource
    private RatingsService ratingsService;

    @Resource
    private SearchhistoryService searchhistoryService;

    /**
     * 获取用户信息
     *
     * @param token token
     * @return Result
     */
    @RequestMapping("/getInfo")
    public Result index(@RequestHeader("Authorization") String token) {
        Long userId = JwtHelper.getUserId(token);
        Map<String, Object> map = new LinkedHashMap<>();

        Users user = usersService.selectUserByUserId(userId);
        map.put("name", user.getUsername());
        map.put("role", user.getRole());
        map.put("avatar", user.getAvatar());
        map.put("email", user.getEmail());
        return Result.success(map);
    }

    /**
     * 评分
     *
     * @param token      token
     * @param ratingForm 评分表单
     * @return Result
     */
    @RequestMapping("/rating")
    public Result rating(@RequestHeader("Authorization") String token, @RequestBody RatingForm ratingForm) {
        Integer userId = Objects.requireNonNull(JwtHelper.getUserId(token)).intValue();
        String query = searchhistoryService.getQueryByUserId(userId);
        Integer paperid = ratingForm.getPaperId();
        Integer rating = ratingForm.getRating();
        Boolean res = ratingsService.saveRatings(userId, paperid, query, rating);
        if (res) {
            return Result.success("评分成功! 谢谢您的反馈!");
        } else {
            return Result.error("评分失败，请重试");
        }
    }

    /**
     * 更新用户信息
     *
     * @param token         token
     * @param updateInfoDto 更新信息表单
     * @return Result
     */
    @RequestMapping("/updateInfo")
    public Result updateInfo(@RequestHeader("Authorization") String token, @RequestBody UpdateInfoDto updateInfoDto) {
        Long userId = JwtHelper.getUserId(token);
        String NewUsername = updateInfoDto.getUsername();
        String NewEmail = updateInfoDto.getEmail();

        if (NewUsername == null || NewEmail == null) {
            return Result.error("用户名或者邮箱不能为空");
        }

        Users user = usersService.selectUserByUserId(userId);
        user.setUsername(NewUsername);
        user.setEmail(NewEmail);
        usersService.updateById(user);

        return Result.success("更新信息成功");
    }

    /**
     * 退出登录
     * 使token失效
     *
     * @return Result
     */
    @RequestMapping("/logout")
    public Result logout() {
        // TODO 使token失效
        return Result.success();
    }
}
