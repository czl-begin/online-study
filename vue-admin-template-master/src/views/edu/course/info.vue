<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

      <el-form label-width="120px">

        <el-form-item label="课程标题">
          <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
<!--        一级分类-->
        <el-form-item label="课程分类">
          <el-select
            v-model="courseInfo.subjectParentId"
            placeholder="一级分类" @change="subjectLevelOneChange">
            <!--            提交之后提交的是value的值-->
            <el-option
              v-for="subject in subjectOneList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"/>
          </el-select>
          <!-- 二级分类 -->
          <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
            <el-option
              v-for="subject in subjectTwoList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"/>
          </el-select>
        </el-form-item>
        <!-- 课程讲师 TODO -->
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
          <el-select
            v-model="courseInfo.teacherId"
            placeholder="讲师姓名">
<!--            提交之后提交的是value的值-->
            <el-option
              v-for="teacher in teacherList"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="总课时">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <!-- 课程简介-->
        <el-form-item label="课程简介">
          <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>
        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
        <el-form-item label="课程封面">

          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduoss/fileoss'"
            class="avatar-uploader">
            <img :src="courseInfo.cover">
          </el-upload>

        </el-form-item>


        <el-form-item label="课程价格">
          <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="SavaOrUpdate">保存并下一步</el-button>
        </el-form-item>
      </el-form>
    </el-form>
  </div>
</template>

<script>
import course from "../../../api/edu/course";
import subject from "../../../api/edu/subject";
import Tinymce from '@/components/Tinymce'//引入组件
export default {
  name: "info",
  //声明组件，因为是第三方插件，需要声明才能使用
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseInfo:{
        title: '',
        subjectId: '',
        teacherId: '',//课程二级分类id
        subjectParentId:'',//课程一级分类id
        lessonNum: 0,
        description: '',
        cover: '/static/vue01.jpg',
        price: 0
      },
      teacherList:[],
      subjectOneList:[],
      subjectTwoList:[],
      BASE_API: process.env.BASE_API,
      courseId:''

    }
  },

  created() {
    console.log('info created')
    if(this.$route.params&&this.$route.params.id){
      this.courseId=this.$route.params.id
      this.getInfo()
    }else{
      //初始化所有讲师信息
      this.getListTeacher()
      this.getOneSubject()
    }

  },
  watch: {//监听
    $route(to, from) {//路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
  },

  methods: {
    init() {
      //判断路径是否有id值，有的话做修改
      if (this.$route.params && this.$route.params.id) {
        this.id = this.$route.params.id
        this.getInfo()
      }
      //没有id值做添加
      else {
        //清空表单
        this.courseInfo = {}
      }
    },
    //根据课程id查询信息
    getInfo(){
      course.getCourseInfoId(this.courseId)
      .then(response=>{
        //在courseInfo中有课程基本信息，包含一级id 和 二级分类id
        this.courseInfo=response.data.courseInfoVo
        //解决二级分类回显不显示title，显示的是id的bug
        //1查询所有课程的分类，包含一级和二级
        subject.getSubejectList()
        .then(response=>{
          //2获取所有一级分类
          this.subjectOneList=response.data.list
          //3把所有的一级分类数组进行遍历
          for (var i=0;i<this.subjectOneList.length;i++){
            //获取每个一级分类
            var oneSubject=this.subjectOneList[i]
            //比较当前courseInfo里面一级分类id和所有的一级分类id
            if (this.courseInfo.subjectParentId===oneSubject.id){
              //获取一级分类所有的二级分类问题
              this.subjectTwoList=oneSubject.children
            }
          }
        })
        //讲师信息也需要回显，因为courseInfo表只有id我们需要通过调用该方法
        //来比较是否与edu_teacher表的id相同，从而显示name
        //初始化所有讲师信息
        this.getListTeacher()
      })
    },
    handleAvatarSuccess(res, file) {
      console.log(res)// 上传响应
      console.log(URL.createObjectURL(file.raw))// base64编码
      this.courseInfo.cover = res.data.url
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    subjectLevelOneChange(value){
      //value就是一级分类id值
      /*alert(value)*/
      //遍历所有的分类，包含一级和二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject=this.subjectOneList[i]
        //判断：所有一级分类id和点击所选择的一级分类id是否一样
        if(value===oneSubject.id){
          //从一级分类获取里面的所有二级分类
          this.subjectTwoList=oneSubject.children

        }
      }
      //把二级分类id值清空
      this.courseInfo.subjectId=''
    },
    //查询所有一级分类
    getOneSubject(){
      subject.getSubejectList()
      .then(response=>{
        this.subjectOneList=response.data.list
      })
    },
    //查询所有讲师
    getListTeacher() {
       course.getListTeacher()
      .then(response=>{
        this.teacherList=response.data.items
      })
      .catch()
    },
    //添加课程
    addCourse(){
      course.addCourseInfo(this.courseInfo)
        .then(response=>{

          this.$message({
            type: 'success',
            message: '添加课程信息成功'
          })
          this.$router.push({ path: '/course/chapter/'+response.data.courseId})
        })
        .catch(error=>{
            this.$message({
              type: 'info',
              message: '失败'
            });
          }
        )
      console.log('next')

    },
    //修改课程
    updateCourse(){
      course.updateCourseInfo(this.courseInfo)
        .then(response=>{
          this.$message({
            type: 'success',
            message: '修改课程信息成功'
          })
          this.$router.push({ path: '/course/chapter/'+this.courseId})
        })
        .catch(error=>{
            this.$message({
              type: 'info',
              message: '失败'
            });
          }
        )
      console.log('next')
    },
    SavaOrUpdate() {
       if (!this.courseId){
         //添加
         this.addCourse()
       }
       else{
         this.updateCourse()
       }
    }
  }
}
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
