<template>
  <div>
    <div style="margin-bottom: 15px">
      <el-button
          type="danger"
          icon="el-icon-delete"
          @click="delBatch"
          :disabled="multipleSelection.length === 0"
          style="margin-left:10px">
        一键删除
      </el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%"
                height="500"
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
        <el-table-column align="center" prop="hotelName" label="酒店名称"></el-table-column>
        <el-table-column align="center" prop="userName" label="预订人"></el-table-column>
        <el-table-column align="center" prop="time" label="预订时间"></el-table-column>
      </el-table>
    </div>
    <el-pagination
        style="margin-top: 20px"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="params.pageNum"
        :page-sizes="[10, 20, 50]"
        :page-size="params.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      params: {
        pageNum: 1,
        pageSize: 10
      },
      tableData: [],
      total: 0,
      multipleSelection: []
    }
  },
  // 页面加载的时候，做一些事情，在created里面
  created() {
    this.findBySearch();
  },
  // 定义一些页面上控件出发的事件调用的方法
  methods: {
    findBySearch() {
      request.get("/reserve/search", {
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
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
      }
      this.findBySearch();
    },
    delBatch() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.put("/reserve/delBatch", this.multipleSelection).then(res => {
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
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleRowClick(row, column, event) {
      this.$refs.multipleTable.toggleRowSelection(row);
    },
  }
}
</script>
