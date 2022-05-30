import request from "../../utils/request";

export default {
  //1根据课程id获取章节和小节列表
  getAllChapterVideo(courseId) {
    return request({
      url: `/eduservice/edu-chapter/getChapterVideo/${courseId}`,
      method: 'get'
    })
  },
  //2添加章节
  addChapter(chapter) {
    return request({
      url: `/eduservice/edu-chapter/addChapter`,
      method: 'post',
      data:chapter
    })
  },
  //3根据id查询章节
  getChapter(chapterId) {
    return request({
      url: `/eduservice/edu-chapter/getChapterInfo/${chapterId}`,
      method: 'get'
    })
  },
  //4修改章节
  updateChapter(chapter) {
    return request({
      url: `/eduservice/edu-chapter/updateChapter`,
      method: 'post',
      data:chapter
    })
  },
  //5删除章节
  deleteChapter(chapterId) {
    return request({
      url: `/eduservice/edu-chapter/${chapterId}`,
      method: 'delete'
    })
  },
}
