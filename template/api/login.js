import request from "../utils/request";

export default {
  //登录的方法
  submitLogin(userInfo){
    return request({
      url:`/educenter/ucenter-member/login`,
      method:'post',
      data:userInfo
    })
  },

  //信息
  getLoginUserInfo(){
    return request({
      url:`/educenter/ucenter-member/getMemberInfo`,
      method:'get'
    })
  }
}
