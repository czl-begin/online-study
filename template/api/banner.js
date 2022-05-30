import request from "../utils/request";
export default {
  getListBanner() {
    //查询前两条banner数据
    return request({
      url: `/educms/crm-bannerfront/getAllBanner`,
      method: 'get'
    })
  }
}
