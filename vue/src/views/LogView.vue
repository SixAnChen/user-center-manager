<template>
  <el-card>
    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px">
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入关键字按回车搜素"
                @keyup.enter.native="load" suffix-icon="el-icon-search"></el-input>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset" icon="el-icon-refresh-left">重置
      </el-button>
      <el-button
          type="danger"
          icon="el-icon-delete"
          @click="delBatch"
          :disabled="multipleSelection.length === 0"
          style="margin-left:10px">
        一键删除
      </el-button>
      <el-button
          type="primary"
          icon="el-icon-success"
          @click="selectAll"
          style="margin-left:10px">
        一键全选
      </el-button>
    </div>
    <!-- 表格 -->
    <el-table
        style="margin-bottom: 20px; width: 100%"
        height="500"
        :data="tableData"
        @selection-change="handleSelectionChange"
        @row-click="handleRowClick"
        ref="multipleTable"
        row-key="id"
        border>
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
      />
      <el-table-column
          align="center"
          prop="name"
          width="300"
          label="操作内容">
      </el-table-column>
      <el-table-column
          align="center"
          prop="username"
          width="100"
          label="操作人">
      </el-table-column>
      <el-table-column
          align="center"
          prop="ip"
          label="操作人IP">
      </el-table-column>
      <el-table-column
          align="center"
          width="300"
          label="操作时间">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.time }}</span>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LogView",
  data() {
    return {
      params: {
        name: '',
      },
      total: 0,  // 数据总条数
      tableData: [], // 表格数据
      multipleSelection: []
    }
  },
  // 页面加载的时候，做一些事情，在created里面
  created() {
    this.load();
  },
  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    // 全选
    selectAll() {
      this.$refs.multipleTable.toggleAllSelection();
    },

    reset() {
      this.params = {
        name: '',
      }
      this.load()
    },
    load() {
      request.get("/log/search", {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleRowClick(row, column, event) {
      this.$refs.multipleTable.toggleRowSelection(row);
    },

    //点击批量删除
    delBatch() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.put("/log/delBatch", this.multipleSelection).then(res => {
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

  },
}
</script>

<style scoped>

</style>
