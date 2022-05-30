import request from "../utils/request";
export default {
  //查询热门课程和老师
  getIndexData() {
    //查询前两条banner数据
    return request({
      url: `/eduservice/indexfront/index`,
      method: 'get'
    })
  }
}
