<template>
  <el-card>
    <div style="margin-bottom: 20px; display: flex">
      <el-input v-model="params.username" style="width: 120px" placeholder="请输入姓名"
                @keyup.enter.native="load"></el-input>
      <el-input v-model="params.address" style="width: 120px;margin-left: 10px" placeholder="请输入地址"
                @keyup.enter.native="load"></el-input>
      <el-button type="primary" plain style="margin-left: 10px" @click="load" icon="el-icon-search"/>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset" icon="el-icon-refresh-left"/>
      <el-button type="success" plain style="margin-left: 10px" @click="add()" icon="el-icon-plus"/>
      <div style="margin-left: 20px">
        <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
          <el-button slot="reference" type="danger" plain style="margin-left: 10px">批量删除</el-button>
        </el-popconfirm>
        <el-button type="warning" plain style="margin-left: 10px" @click="exp()">导出报表</el-button>
        <el-upload action="http://localhost:9090/api/user/upload" style="display: inline-block; margin-left: 10px"
                   :show-file-list="false" :on-success="uploadBatch">
          <el-button type="primary" plain>批量导入</el-button>
        </el-upload>
      </div>
    </div>

    <el-table
        style="margin-bottom: 20px; width: 100%"
        height="460"
        :data="tableData"
        border
        :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}"
        @selection-change="handleSelectionChange"
        ref="multipleTable"
        :row-key="getRowKeys">
      <el-table-column type="selection" width="55" align="center" :reserve-selection="true"></el-table-column>
      <el-table-column
          label="#"
          align="center"
          type="index"
          fixed
          :index="tableIndex"
          width="60"
      />
      <el-table-column label="头像" align="center" width="80">
        <template v-slot="scope">
          <el-image
              style="width: 40px; height: 40px; border-radius: 50%"
              :src="'http://localhost:9090/api/files/' + scope.row.avatar"
              :preview-src-list="['http://localhost:9090/api/files/' + scope.row.avatar]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
          prop="typeName"
          align="center"
          label="角色"
          width="100">
        <template slot-scope="scope">
          <el-tag size="medium"
                  :type="scope.row.typeId == '1'?'success':scope.row.typeId=='2'?'danger':scope.row.typeId=='3'?'warning':'info'">
            {{ scope.row.typeName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          label="简介"
          width="100">
        <template v-slot="scope">
          <el-button type="danger" @click="viewEditor(scope.row.introduce)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column
          prop="username"
          sortable
          align="center"
          label="姓名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="address"
          sortable
          align="center"
          width="300"
          label="地址">
      </el-table-column>
      <el-table-column
          prop="phone"
          sortable
          align="center"
          label="电话"
          width="200">
      </el-table-column>
      <el-table-column
          prop="email"
          sortable
          align="center"
          label="邮箱"
          width="200">
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="260" align="center">
        <template slot-scope="scope">
          <el-button type="primary" plain @click="edit(scope.row)">编辑</el-button>
          <el-button slot="reference" type="danger" plain style="margin-left: 5px" @click="del(scope.row.id)">删除
          </el-button>
          <el-button type="success" plain style="margin-left: 5px" @click="down(scope.row.avatar)">
            下载
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="params.pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="params.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="50%" top="60px" center
               :before-close="handleClose">
      <el-form :model="form" style="height: 400px; overflow-y: scroll">
        <el-form-item label="头像" label-width="15%">
          <el-upload action="http://localhost:9090/api/files/upload"
                     list-type="picture"
                     :file-list="fileList"
                     :on-success="successUpload"
                     style="width: 90%">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户姓名" label-width="15%">
          <el-input v-model="form.username" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="用户角色" label-width="15%">
          <el-select v-model="form.typeId" placeholder="请选择" style="width: 90%">
            <el-option
                v-for="item in typeObjs"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户地址" label-width="15%">
          <el-input v-model="form.address" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" label-width="15%">
          <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址" label-width="15%">
          <el-input v-model="form.email" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="个人简介" label-width="15%">
          <div id="editor" style="width: 90%"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 简介预览弹窗 -->
    <el-dialog title="个人简介" :visible.sync="InfoVisible" width="60%" top="60px" center>
      <el-card style="height: 460px; overflow-y: scroll;">
        <div v-html="this.userInfo" class="w-e-text"></div>
      </el-card>
    </el-dialog>
  </el-card>
</template>

<script>
import request from "@/utils/request";
import E from 'wangeditor'
import hljs from 'highlight.js'

let editor

function initWangEditor(content) {
  setTimeout(() => {
    if (!editor) {
      // 初始化 渲染到div
      editor = new E('#editor')
      // 默认提示
      editor.config.placeholder = '请输入内容'
      // 图片上传
      editor.config.uploadFileName = 'file'
      editor.config.uploadImgServer = 'http://localhost:9090/api/files/wangeditor'
      editor.config.uploadImgParams = {
        type: 'img'
      }
      // 视频上传
      editor.config.uploadVideoName = 'file'
      editor.config.uploadVideoServer = 'http://localhost:9090/api/files/wangeditor'
      editor.config.uploadVideoParams = {
        type: 'video'
      }
      // 代码高亮
      editor.highlight = hljs
      editor.create()
    }
    editor.txt.html(content)
  }, 0)
}

export default {
  name: "UserView",
  data() {
    return {
      tableData: [],
      params: {
        username: '',
        address: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      form: {},
      dialogFormVisible: false,
      InfoVisible: false,
      userInfo: '',
      multipleSelection: [],
      fileList: [],
      typeObjs: []
    }
  },
  created() {
    this.load()
    this.findType()
  },
  methods: {

    // 角色信息获取
    findType() {
      request.get("/type").then(res => {
        if (res.code === '200') {
          this.typeObjs = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    // 刷新
    load() {
      request.get('/user/search', {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    // 重置
    reset() {
      this.params = {
        username: '',
        address: '',
        pageNum: 1,
        pageSize: 10
      }
      this.load()
    },


    // 分頁
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.load();
    },

    // 新增
    add() {
      this.form = {};
      initWangEditor("");
      this.dialogFormVisible = true;
    },

    // 编辑
    edit(obj) {
      this.form = JSON.parse(JSON.stringify(obj));  // 深拷贝
      initWangEditor(obj.introduce ? this.form.introduce : "");
      this.dialogFormVisible = true;
    },

    // 简介预览
    viewEditor(data) {
      this.InfoVisible = true
      this.userInfo = data
    },

    // 表單提交
    submit() {
      this.form.introduce = editor.txt.html();
      request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.dialogFormVisible = false;
          this.fileList = [];
          this.load();
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    // 删除
    del(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete("/user/" + id).then(res => {
          if (res.code === '200') {
            this.$message.success("删除成功")
            this.load();
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },


    // 批量删除
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //记录行被选中的状态
    getRowKeys(row) {
      return row.id
    },
    delBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请勾选您要删除的项")
        return
      }
      console.log(this.multipleSelection)
      request.put("/user/delBatch", this.multipleSelection).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.$refs.multipleTable.clearSelection()
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    // 数据表格序号 自增
    tableIndex(index) {
      const pageNum = this.params.pageNum // 当前页码
      const pageSize = this.params.pageSize // 每页条数
      return index + 1 + (pageNum - 1) * pageSize
    },

    // 文件上传, 头像
    successUpload(res) {
      this.form.avatar = res.data;
    },
    down(flag) {
      location.href = 'http://localhost:9090/api/files/' + flag
    },
    // 导出报表
    exp() {
      let user = localStorage.getItem("admin");
      location.href = 'http://localhost:9090/api/user/export?token=' + JSON.parse(user).token
    },
    // 批量导入
    uploadBatch(res) {
      if (res.code === '200') {
        this.$message.success("批量导入成功")
        this.load()
      } else {
        this.$message.error(res.msg)
      }
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    }
  },
}
</script>


