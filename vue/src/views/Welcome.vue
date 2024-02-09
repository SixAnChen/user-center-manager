<template>
  <div>
    <el-row>
      <el-col :span="8">
        <el-card style="margin-right: 20px">
          <div class="user">
            <el-avatar v-if="admin.avatar" shape="square" :size="150"
                       style="border-radius: 50%"
                       :src="'http://localhost:9090/api/files/' + admin.avatar">
            </el-avatar>
            <el-avatar v-else shape="square" :size="150"
                       style="border-radius: 50%"
                       src="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png">
            </el-avatar>
            <div style="margin-left: 40px;">
              <p style="font-size: 32px; margin-bottom: 10px">{{ admin.username }}</p>
              <p style="color: #999;font-size: 14px">
                <el-tag size="medium">超级管理员</el-tag>
              </p>
            </div>
          </div>
          <div style="font-size: 14px; color:#999;margin-left: 10px">
            <p style="margin-bottom: 5px">上次登录时间：<span style="margin-left: 50px">2023-10-5</span></p>
            <p>上次登录地点：<span style="margin-left: 50px">广东省广州市增城区</span></p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card style="height: 274px;overflow-y: scroll">
          <div class="title">
            <span>系统公告</span>
          </div>
          <el-collapse accordion v-model="activeName">
            <el-collapse-item v-for="item in noticeData" :title="item.name" :name="item.id">
              <div>{{ item.content }}</div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <div style="margin-top: 20px">
        <el-col :span="8">
          <el-card style="margin-right: 20px">
            <div id="pie" style="width: 100%;height: 400px"></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card style="margin-right: 20px">
            <div id="bar" style="width: 100%;height: 400px"></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div id="line" style="width: 100%;height: 400px"></div>
          </el-card>
        </el-col>
      </div>
    </el-row>
  </div>


</template>

<script>

import request from "@/utils/request";
import * as echarts from "echarts";

export default {
  name: "Welcome",
  data() {
    return {
      activeName: '1', // 公告默认展开项
      noticeData: [],  // 系统公告数据
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
    }
  },
  // 页面加载完成后渲染元素
  mounted() {
    this.findNotice();
    this.initEcharts();
  },
  methods: {

    // Echarts表后端数据获取
    initEcharts() {
      request.get('/user/echarts/pie').then(res => {
        if (res.code === '200') {
          this.initPie(res.data)
        } else {
          this.$message.error(res.msg)
        }
      })
      request.get("/user/echarts/bar").then(res => {
        if (res.code === '200') {
          // 开始去渲染柱状图数据啦
          let map = res.data;
          this.initBar(map.xAxis, map.yAxis)
          // 开始去渲染折线图数据啦
          this.initLine(map.xAxis, map.yAxis)
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    // Echarts 折线图
    initLine(xAxis, yAxis) {
      let chartDom = document.getElementById('line');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '用户统计（折线图）',
          subtext: '统计维度：用户角色',
          left: 'center'
        },
        grid: {
          // 控制图表的位置和大小
          left: '10%',
          right: '10%',
          bottom: '10%',
          top: '100px', // 调整图表与标题的距离
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'line',
            smooth: true
          }
        ]
      };

      option && myChart.setOption(option);
    },

    // Echarts 柱状图
    initBar(xAxis, yAxis) {
      let chartDom = document.getElementById('bar');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        // 标题
        title: {
          text: '统计用户（柱状图）',
          subtext: '统计维度：用户角色',
          left: 'center'
        },
        grid: {
          // 控制图表的位置和大小
          left: '10%',
          right: '10%',
          bottom: '10%',
          top: '100px', // 调整图表与标题的距离
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },

    // Echarts 饼图
    initPie(data) {
      let chartDom = document.getElementById('pie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        // 标题
        title: {
          text: '统计用户（饼图）',
          subtext: '统计维度：用户角色',
          left: 'center'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c}人 ({d}%)'
        },

        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            name: '用户角色',
            type: 'pie',
            radius: [20, 140],
            center: ['50%', '60%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 5
            },
            data: data
          }
        ]
      };
      option && myChart.setOption(option);
    },

    // 系统公告
    findNotice() {
      request.get('/notice').then(res => {
        if (res.code === '200') {
          this.noticeData = res.data
          // this.activeName = res.data[0].id
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.title {
  color: #0f9876;
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 20px;
}


.user {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ccc;
}
</style>
