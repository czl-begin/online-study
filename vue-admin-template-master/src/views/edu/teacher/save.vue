<template>
  <div class="app-container">
    讲师添加
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
    v-show：是否显示上传组件
    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
    :url：后台上传的url地址
    @close：关闭上传组件
    @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>

      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacher from "../../../api/edu/teacher";
import ImageCropper from '@/components/ImageCropper';
import PanThumb from '@/components/PanThumb';

export default {
  name: "save",
  components: {ImageCropper, PanThumb},
  data() {
    return {
      teacher: {
        avatar: ''
      },
      //上传弹框组件是否显示
      imagecropperShow: false,
      //上传组件的key值
      imagecropperKey: 0,
      //获取dev.env.js里面地址
      BASE_API: process.env.BASE_API,
      saveBtnDisabled: false,//保持按钮是否禁用
      id: 1

    }
  },
  //页面渲染之前执行，只执行一次
  created() {
    console.log('created')
    this.init()
  },
  watch: {//监听
    $route(to, from) {//路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
  },
  methods: {
    //图片关闭上传弹框的方法
    close() {
      this.imagecropperShow = false
      //上传组件初始化
      this.imagecropperKey=this.imagecropperKey+1
    },
    //图片上传成功方法
    cropSuccess(data) {
      this.imagecropperShow = false
      this.teacher.avatar = data.url
      //上传组件初始化
      this.imagecropperKey=this.imagecropperKey+1
    },
    init() {
      //判断路径是否有id值，有的话做修改
      if (this.$route.params && this.$route.params.id) {
        this.id = this.$route.params.id
        this.getInfo()
      }
      //没有id值做添加
      else {
        //清空表单
        this.teacher = {}
      }
    },
    //判断修改还是添加
    //根据teacher是否有id
    saveOrUpdate() {
      if (!this.teacher.id) {
        //添加
        this.saveTeacher()
      } else {
        //修改
        this.updateTeacher()
      }
    },
    updateTeacher() {
      this.$confirm('此操作将修改讲师信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacher.updateTeacher(this.teacher)
          .then(response => {
            this.$router.push({path: '/edu/table'})
            console.log("成功")
          })
          .catch(error => {
            console.log("失败")
          })
        this.$message({
          type: 'success',
          message: '修改成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消修改'
        });
      });
    },
    saveTeacher() {
      this.$confirm('此操作将添加新讲师, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacher.addTeacher(this.teacher)
          .then(response => {
            this.$router.push({path: '/edu/table'})
            console.log("成功")
          })
          .catch(error => {
            console.log("失败")
          })
        this.$message({
          type: 'success',
          message: '添加成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消添加'
        });
      });
    },
    getInfo() {
      teacher.getInfo(this.id)
        .then(response => {
          this.teacher = response.data.teacher
        })
        .catch(error =>
          console.log(error)
        )
    }


  }
}
</script>

<style scoped>

</style>
