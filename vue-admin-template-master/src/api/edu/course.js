import request from "../../utils/request";

export default {
  //1添加课程信息
  addCourseInfo(courseInfo){
    return request({
      url:`/eduservice/edu-course/addCourseInfo`,
      method:'post',
      data:courseInfo
    })
  },
  //查询所有讲师
  getListTeacher(){
    return request({
      url:`/eduservice/teacher/findAll`,
      method:'get'
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfoId(courseId){
    return request({
      url:`/eduservice/edu-course/getCourseInfo/${courseId}`,
      method:'get'
    })
  },
  //修改课程信息
  updateCourseInfo(courseInfo){
    return request({
      url:`/eduservice/edu-course/updateCourseInfo`,
      method:'post',
      data:courseInfo
    })
  },

  //删除课程信息
  deleteCourseInfo(courseId){
    return request({
      url:`/eduservice/edu-course/deleteCourseInfo/${courseId}`,
      method:'delete'
    })
  },
  //课程信息确认显示
  getPublishCourseInfo(id){
    return request({
      url:`/eduservice/edu-course/getPublishCourseInfo/${id}`,
      method:'get',
    })
  },
  //课程最终发布
   publishCourse(id){
    return request({
      url:`/eduservice/edu-course/publishCourse/${id}`,
      method:'post',
    })
  },
  /*//TODO 课程列表
  getCourseList(){
    return request({
      url:`/eduservice/edu-course/getCourseList`,
      method:'get',
    })
  }*/

  getCourseList(current, limit, courseQuery) {
    return request({
      /* url: '/eduservice/teacher/pageTeacherCondition/+current+"/"+limit',*/
      url: `/eduservice/edu-course/pageCourseCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示把对象转换json进行传递到接口里面
      data:courseQuery
    })
  },
}
