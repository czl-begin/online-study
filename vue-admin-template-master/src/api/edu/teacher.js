import request from '@/utils/request'

export default {
  getTeacherListPage(current, limit, teacherQuery) {
    return request({
      /* url: '/eduservice/teacher/pageTeacherCondition/+current+"/"+limit',*/
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示把对象转换json进行传递到接口里面
      data: teacherQuery
    })
  },
  //删除讲师
  deleteTeacher(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示把对象转换json进行传递到接口里面
    })
  },
  //添加讲师
  addTeacher(teacher) {
    return request({
      url: `/eduservice/teacher/addTeacher`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示把对象转换json进行传递到接口里面
      data: teacher
    })
  },
  //根据id查讲师数据
  getInfo(id) {
    return request({
      url: `/eduservice/teacher/getTeacher/${id}`,
      method: 'get'
    })
  },
  //修改讲师
  updateTeacher(teacher) {
    return request({
      url: `/eduservice/teacher/updateTeacher`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示把对象转换json进行传递到接口里面
      data: teacher
    })
  }
}
