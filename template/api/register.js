import request from "../utils/request";

export default {
  //注册的方法
  registerMember(formItem){
    return request({
      url:`/educenter/ucenter-member/register`,
      method:'post',
      data:formItem
    })
  }

}
