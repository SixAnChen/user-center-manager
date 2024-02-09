<template>
      <el-card>
        <div style="margin-bottom: 20px; display: flex">
          <el-input size="medium" v-model="params.name" style="width: 200px" placeholder="请输入名称按回车搜索"
                    @keyup.enter.native="load" suffix-icon="el-icon-search"></el-input>
          <el-button plain style="margin-left: 10px" @click="reset" icon="el-icon-refresh-left">重置
          </el-button>
          <el-button type="success" style="margin-left: 10px" @click="add" icon="el-icon-plus">新增
          </el-button>
        </div>
        <el-table
            :data="tableData"
            :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}"
            stripe
            border
            style="width: 100%">
          <el-table-column
              label="#"
              align="center"
              type="index"
              :index="tableIndex"
              width="50"
          />
          <el-table-column
              align="center"
              label="角色名称"
              prop="name">
          </el-table-column>
          <el-table-column label="操作" align="center" width="180">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="edit(scope.row)">编辑
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="del (scope.row.id)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            style="margin-top: 20px"
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="params.pageNum"
            :page-sizes="[5, 10, 50, 100]"
            :page-size="params.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>


        <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%" @close="handleCancel">
          <el-form :model="form" :rules="rules" ref="form">
            <el-form-item label="角色名称" label-width="20%" prop="name">
              <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible= false">取 消</el-button>
            <el-button type="primary" @click="submit()">确 定</el-button>
          </div>
        </el-dialog>
      </el-card>
</template>

<script>
import request from "@/utils/request";

let editor

export default {
  data() {
    return {
      tableData: [],
      params: {
        name: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      form: {},
      dialogFormVisible: false,
      rules: {
        name: [
          {required: true, message: '必填项不能为空', trigger: 'blur'}
        ],
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/type/search', {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.params = {
        name: '',
        pageNum: 1,
        pageSize: 5
      }
      this.load()
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.load();
    },
    add() {
      this.form = {}
      this.dialogFormVisible = true
    },
    edit(obj) {
      this.form = JSON.parse(JSON.stringify(obj));  // 深拷贝
      this.dialogFormVisible = true;
    },
    del(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
            request.delete("/type/" + id).then(res => {
              if (res.code === '200') {
                this.$message.success("删除成功")
                this.load();
              } else {
                this.$message.error(res.msg)
              }
            });
          }
      ).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          request.post("/type", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("更新成功")
              this.dialogFormVisible = false;
              this.load();
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })

    },
    tableIndex(index) {
      const pageNum = this.params.pageNum // 当前页码
      const pageSize = this.params.pageSize // 每页条数
      return index + 1 + (pageNum - 1) * pageSize
    },

    handleCancel() {
      this.$refs.form.resetFields();
    }
  }
}
</script>


