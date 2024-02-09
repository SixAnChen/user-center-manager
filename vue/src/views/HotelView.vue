<template>
  <el-card>
    <div style="margin-bottom: 15px">
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入酒店名称"
                @keyup.enter.native="load"></el-input>
      <el-button plain type="primary" style="margin-left: 10px" @click="reset" icon="el-icon-refresh-left"/>
      <el-button type="success" style="margin-left: 10px" @click="add()" icon="el-icon-plus"/>
    </div>
    <el-table
        :data="tableData"
        style="width: 100%"
        height="460"
        border
        :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}">
      <el-table-column
          label="#"
          align="center"
          type="index"
          fixed
          :index="tableIndex"
          width="60"
      />
      <el-table-column label="酒店图片" align="center" width="100">
        <template v-slot="scope">
          <el-image
              style="width: 40px; height: 40px; border-radius: 50%"
              :src="'http://localhost:9090/api/files/' + scope.row.img"
              :preview-src-list="['http://localhost:9090/api/files/' + scope.row.img]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="酒店名称"></el-table-column>
      <el-table-column align="center" prop="price" label="酒店价格"></el-table-column>
      <el-table-column align="center" prop="num" label="剩余间数"></el-table-column>
      <el-table-column align="center" label="操作" width="240">
        <template slot-scope="scope">
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-button type="warning" @click="reserve(scope.row)">预订</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
            <el-button slot="reference" type="danger" style="margin-left: 10px">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="params.pageNum"
        :page-sizes="[10, 20, 50]"
        :page-size="params.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form">
        <el-form-item label="酒店名称" label-width="20%">
          <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="酒店图片" label-width="20%">
          <el-upload action="http://localhost:9090/api/files/upload" :on-success="successUpload">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="酒店价格" label-width="20%">
          <el-input v-model="form.price" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="剩余间数" label-width="20%">
          <el-input v-model="form.num" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </div>
    </el-dialog>
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
      total: 0,
      tableData: [],
      dialogFormVisible: false,
      form: {},
      user: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {}
    }
  },
  // 页面加载的时候，做一些事情，在created里面
  created() {
    this.load()
  },
  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    // 刷新
    load() {
      request.get('/hotel/search', {params: this.params}).then(res => {
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
        name: '',
        pageNum: 1,
        pageSize: 10
      }
      this.load()
    },
    // 新增
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },

    // 编辑
    edit(obj) {
      // 浅拷贝
      // this.form = obj;
      this.form = JSON.parse(JSON.stringify(obj));  // 深拷贝
      this.dialogFormVisible = true;
    },
    del(id) {
      request.delete("/hotel/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    submit() {
      request.post("/hotel", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    successUpload(res) {
      this.form.img = res.data;
    },
    down(flag) {
      location.href = 'http://localhost:8080/api/files/' + flag
    },
    reserve(row) {
      let param = {hotelId: row.id, userId: this.user.id}
      request.post("/reserve", param).then(res => {
        if (res.code === '200') {
          this.$message.success("预订成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.load();
    },

    // 数据表格序号 自增
    tableIndex(index) {
      const pageNum = this.params.pageNum // 当前页码
      const pageSize = this.params.pageSize // 每页条数
      return index + 1 + (pageNum - 1) * pageSize
    },
  }
}
</script>
