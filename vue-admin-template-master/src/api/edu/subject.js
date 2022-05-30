import request from '@/utils/request'

export default {
  getSubejectList() {
    return request({
      /* url: '/eduservice/teacher/pageTeacherCondition/+current+"/"+limit',*/
      url: `/eduservice/subject/getAllSubject`,
      method: 'get',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示把对象转换json进行传递到接口里面
    })
  }
}
