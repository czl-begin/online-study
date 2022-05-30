import request from "../../utils/request";
export default {
  //添加小节
  addVideo(video){
    return request({
      url:`/eduservice/edu-video/addVideo`,
      method:'post',
      data:video
    })
  },

  //5删除章节
  deleteVideo(videoId) {
    return request({
      url: `/eduservice/edu-video/${videoId}`,
      method: 'delete'
    })
  },

  //3根据id查询章节
  getVideo(videoId) {
    return request({
      url: `/eduservice/edu-video/getVideoInfo/${videoId}`,
      method: 'get'
    })
  },
  //4修改章节
  updateChapter(video) {
    return request({
      url: `/eduservice/edu-video/updateVideo`,
      method: 'post',
      data:video
    })
  },

  //6删除阿里云视频
  deleteAlyVideo(id) {
    return request({
      url: `/eduvod/video/removeAlyVideo/${id}`,
      method: 'delete'
    })
  },

}
