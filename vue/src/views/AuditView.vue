<template>
  <el-card>
    <div style="margin-bottom: 15px">
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入请假缘由"
                @keyup.enter.native="findBySearch"></el-input>
      <el-button icon="el-icon-refresh-left" style="margin-left: 10px" @click="reset()"/>
      <el-button type="success" icon="el-icon-plus" style="margin-left: 10px" @click="add()"/>
      <el-button
          type="danger"
          icon="el-icon-delete"
          @click="delAll"
          :disabled="multipleSelection.length === 0"
          style="margin-left:15px;">
        一键删除
      </el-button>
    </div>
    <div>
      <el-table :data="tableData"
                style="width: 100%"
                border
                height="480"
                :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}"
                @selection-change="handleSelectionChange"
                ref="multipleTable"
                row-key="id">
        <el-table-column
            type="selection"
            align="center"
            width="50"
            :reserve-selection="true">
        </el-table-column>
        <el-table-column align="center" prop="name" label="请假原由"></el-table-column>
        <el-table-column align="center" prop="time" label="请假日期"></el-table-column>
        <el-table-column align="center" prop="day" label="请假天数"></el-table-column>
        <el-table-column align="center" prop="userName" label="请假用户"></el-table-column>
        <el-table-column align="center" prop="status" label="审核状态"></el-table-column>
        <el-table-column align="center" prop="reason" label="审核意见"></el-table-column>
        <el-table-column align="center" label="操作" width="300">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-button type="success" @click="audit(scope.row)">审核</el-button>
            <el-button slot="reference" type="danger" style="margin-left: 5px" @click="del(scope.row.id)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="margin-top: 10px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[10, 20, 50]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="40%">
        <el-form :model="form">
          <el-form-item label="请假原由" label-width="20%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="请假日期" label-width="20%">
            <el-date-picker v-model="form.time" type="date" style="width: 90%" placeholder="选择日期"
                            value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="请假天数" label-width="20%">
            <el-input v-model="form.day" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <div>
      <el-dialog title="请审核" :visible.sync="auditVisible" width="40%">
        <el-form :model="form">
          <el-form-item label="审核状态" label-width="20%">
            <el-radio v-model="form.status" label="审核通过"></el-radio>
            <el-radio v-model="form.status" label="审核不通过"></el-radio>
          </el-form-item>
          <el-form-item label="审核意见" label-width="20%">
            <el-input v-model="form.reason" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="auditVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      params: {
        name: '',
        pageNum: 1,
        pageSize: 10
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      auditVisible: false,
      multipleSelection: [],
      form: {},
      user: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {}
    }
  },
  // 页面加载的时候，做一些事情，在created里面
  created() {
    this.findBySearch();
  },
  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    findBySearch() {
      request.get("/audit/search", {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    add() {
      this.form = {};
      this.form.userId = this.user.id;
      this.dialogFormVisible = true;
    },
    edit(obj) {
      this.form = JSON.parse(JSON.stringify(obj));  // 深拷贝
      this.dialogFormVisible = true;
    },
    audit(obj) {
      this.form = obj;
      this.auditVisible = true;
    },
    reset() {
      this.params = {
        name: '',
        pageNum: 1,
        pageSize: 10,
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post("/audit", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.dialogFormVisible = false;
          this.auditVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete("/audit/" + id).then(res => {
          if (res.code === '200') {
            this.$message.success("删除成功")
            this.findBySearch();
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    delAll() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.put("/audit/delBatch", this.multipleSelection).then(res => {
          if (res.code === '200') {
            this.$message.success("批量删除成功")
            this.$refs.multipleTable.clearSelection()
            this.findBySearch()
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
    }
  }
}
</script>
