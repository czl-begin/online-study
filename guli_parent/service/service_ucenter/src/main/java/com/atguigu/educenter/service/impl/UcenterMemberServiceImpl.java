package com.atguigu.educenter.service.impl;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.JwtUtils;
import com.atguigu.educenter.utils.MD5;
import com.atguigu.servicebase.exceptionhandler.GuliExcepption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    //返回token值，使用jwt生成
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile=member.getMobile();
        String password=member.getPassword();

        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliExcepption(20001,"手机号或者密码为空");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null){
            throw new GuliExcepption(20001,"手机号不存在");
        }

        //判断密码是否正确
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，在和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliExcepption(20001,"密码错误");
        }

        //判断是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliExcepption(20001,"该账号被禁用");
        }

        //登录成功
        // 生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
        return jwtToken;
    }
    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
  /*      //验证码
        String code=registerVo.getCode();*/
        //手机号
        String mobile=registerVo.getMobile();
        //昵称
        String nickname=registerVo.getNickname();
        //密码
        String password=registerVo.getPassword();
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new GuliExcepption(20001,"注册昵称或密码为空");
        }

        //数据添加到数据库
        UcenterMember member=new UcenterMember();
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setMobile(mobile);
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg");
        baseMapper.insert(member);
    }
}
