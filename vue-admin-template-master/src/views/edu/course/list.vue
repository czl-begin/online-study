<script src="../../../router/index.js"></script>
<template>
  <div class="app-container">
    课程列表
    <!-- 表格 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option :value="'Normal'" label="已发布"/>
          <el-option :value="'Draft'" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="courseQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="courseQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <!--      //prop=“name"  ==   {{ scope.row.name}}-->
      <el-table-column prop="title" label="名称" width="80"/>

      <el-table-column label="状态" width="80">
        <!--        //用于处理想输出的信息与数据库不同的时候-->
        <template slot-scope="scope">
          <!--        JavaScript中  ==判断值 ===判断值与类型-->
          {{ scope.row.status === 'Normal' ? '已发布' : '未发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数"/>
      <el-table-column prop="viewCount" label="浏览数量"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

<!--      <el-table-column prop="sort" label="排序" width="60"/>-->

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/edu/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲信息</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click=" removeCourseDataById(scope.row.id)">删除课程信息
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <!--    分页中的方法可以不写(),写()要记得写参数-->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
//引入调用teacher.js文件
import course from "../../../api/edu/course";
//写核心代码的位置
export default {
  name: "list",
  //data:{},
  data() {//定义变量和初始值
    return {
      list: null,//查询之后接口返回对象
      page: 1,//当前页
      limit: 10,//每页记录数
      total: 0,//总记录数
      courseQuery: {
        //双向绑定可以不写变量初始化
      }//条件封装对象
    }
  },
  created() {//页面渲染之前执行，一般调用methods定义的方法
    this.getList();
  },
  methods: {//创建具体的方法，调用teacher.js定义的方法
    getList(page = 1) {//讲师列表的方法
      this.page = page
      course.getCourseList(this.page, this.limit, this.courseQuery)
        .then(response => {//请求成功
          // response就是接口返回的数据
          console.log(response)
          this.list = response.data.records
          this.total = response.data.total
          console.log(this.list)
          console.log(this.total)

        })
        .catch(error => {//请求失败
          console.log(error)
        })
    },
    resetData() {
      this.teacherQuery = {}
      this.getList()
    },
    //在方法里使用未在data里面定义的数据时，该方法需要写参数
    removeCourseDataById(courseId) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        course.deleteCourseInfo(courseId)
          .then(response => {
            this.getList()
            console.log("成功")
          })
          .catch(error => {
            console.log("失败")
          })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }

}
</script>

<style scoped>

</style>
