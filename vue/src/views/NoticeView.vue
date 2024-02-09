<template>
  <el-card>
    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px">
      <el-input size="medium" v-model="params.name" style="width: 240px" placeholder="请输入公告名称按回车搜素"
                @keyup.enter.native="load" suffix-icon="el-icon-search"></el-input>
      <el-button plain @click="reset" icon="el-icon-refresh-left" style="margin-left: 10px">重置
      </el-button>
      <el-button type="success" @click="add" icon="el-icon-plus" style="margin-left: 10px">新增
      </el-button>
      <el-button
          type="danger"
          icon="el-icon-delete"
          @click="delBatch"
          :disabled="multipleSelection.length === 0"
          style="margin-left:10px;">
        一键删除
      </el-button>
    </div>

    <!-- 表格 -->
    <el-table
        style="margin-bottom: 20px; width: 100%"
        height="460"
        :data="tableData"
        border
        @selection-change="handleSelectionChange"
        ref="multipleTable"
        row-key="id">
      <el-table-column
          type="selection"
          align="center"
          width="50"
          :reserve-selection="true">
      </el-table-column>
      <el-table-column
          label="序号"
          align="center"
          type="index"
          :index="tableIndex"
      />
      <el-table-column
          align="center"
          prop="name"
          width="200"
          label="公告名称"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          align="center"
          prop="content"
          width="300"
          show-overflow-tooltip
          label="公告内容">
      </el-table-column>
      <el-table-column
          align="center"
          width="300"
          label="更新时间">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.time }}</span>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          label="发布">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status"
                     active-color="#13ce66"
                     inactive-color="#ff4949"
                     @change="changeStatus(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="params.pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="params.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total">
    </el-pagination>

    <!-- 新增、编辑弹窗 -->
    <el-dialog title="公告信息" :visible.sync="dialogFormVisible" width="40%" :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item label="公告名称" label-width="15%" prop="name">
          <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="公告内容" label-width="15%" prop="content">
          <el-input type="textarea" :rows="14" v-model="form.content" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "NoticeView",
  data() {
    return {
      params: {
        name: '',
        pageNum: 1,  // 当前页码
        pageSize: 10 // 每页展示数据条数
      },
      total: 0,  // 数据总条数
      tableData: [], // 表格数据

      form: {},  // 新增、编辑表单
      dialogFormVisible: false, // 新增、编辑弹窗

      // 编辑、新增表单校验规则
      rules: {
        name: [
          {required: true, message: "公告名称不能为空"},
        ],
        content: [
          {required: true, message: "公告内容不能为空"},
        ]
      },
      multipleSelection: [] // 批量删除
    }
  },
  created() {
    this.load()
  },
  methods: {
    reset() {
      this.params = {
        name: '',
        pageNum: 1,
        pageSize: 10
      }
      this.load()
    },
    load() {
      request.get('/notice/search', {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    changeStatus(row) {
      row = JSON.parse(JSON.stringify(row)) //深拷贝
      request.post('/notice', row).then(res => {
        if (res.code === '200') {
          this.$message.success("修改成功")
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    submit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request.post("/notice", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("提交成功")
              this.dialogFormVisible = false;
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    handleEdit(index, row) {
      this.form = JSON.parse(JSON.stringify(row));  // 深拷贝
      this.dialogFormVisible = true;
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
            request.delete("/notice/" + row.id).then(res => {
              if (res.code === '200') {
                this.$message.success("删除成功")
                this.load();
              } else {
                this.$notify.error(res.msg)
              }
            });
          }
      ).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.load();
    }
    ,
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.load();
    }
    ,

    // 数据表格序号 自增
    tableIndex(index) {
      const pageNum = this.params.pageNum // 当前页码
      const pageSize = this.params.pageSize // 每页条数
      return index + 1 + (pageNum - 1) * pageSize
    },

    // 批量删除
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delBatch() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.put("/notice/delBatch", this.multipleSelection).then(res => {
          if (res.code === '200') {
            this.$message.success("批量删除成功")
            this.$refs.multipleTable.clearSelection()
            this.load()
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

<style>
.el-tooltip__popper {
  max-width: 300px !important;
}
</style>
