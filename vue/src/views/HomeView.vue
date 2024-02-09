<template>
  <div>
    <el-container style="min-height: 100vh">
      <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
        <el-menu
            router
            :default-active="$route.path"
            style="min-height: 100%; overflow-x: hidden"
            background-color="rgb(32, 33, 35)"
            text-color="#fff"
            :collapse-transition="false"
            :collapse="isCollapse">
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 0px"/>
            <b style="color: white; margin-left: 5px" v-show="logoTextShow">后台管理系统</b>
          </div>
          <el-menu-item index="/welcome">
            <i class="el-icon-menu"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/notice">
            <i class="el-icon-data-board"></i>
            <span slot="title">公告管理</span>
          </el-menu-item>
          <el-menu-item index="/log">
            <i class="el-icon-data-line"></i>
            <span slot="title">操作日志</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/user">
              <i class="el-icon-user"></i>
              用户管理
            </el-menu-item>
            <el-menu-item index="/role">
              <i class="el-icon-user-solid"></i>
              角色管理
            </el-menu-item>
            <el-menu-item index="/audit">
              <i class="el-icon-qingjiashenhe"></i>
              请假审核
            </el-menu-item>
            <el-menu-item index="/hotel">
              <i class="el-icon-hotel-order"></i>
              酒店信息
            </el-menu-item>
            <el-menu-item index="/reserve">
              <i class="el-icon-6"></i>
              预订信息
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="
            line-height: 60px;
            display: flex;
            align-items: center;
            box-shadow: 2px 0 6px rgb(0 21 41 / 35%);
          ">
          <div style="width: 20px; font-size: 20px; margin-right: 20px">
            <span
                :class="collapseBtnClass"
                style="cursor: pointer"
                @click="collapse"
            ></span>
          </div>
          <div style="flex: 1">
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>

          <el-dropdown style="cursor: pointer; margin-right: 10px" placement="bottom">
            <div style="display:flex; align-items: center; justify-content: center">
              <el-avatar v-if="admin.avatar" shape="square" :size="45"
                         style="border-radius: 10px"
                         :src="'http://localhost:9090/api/files/' + admin.avatar">
              </el-avatar>
              <el-avatar v-else shape="square" :size="45"
                         style="border-radius: 10px"
                         src="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png">
              </el-avatar>
            </div>
            <el-dropdown-menu slot="dropdown" style="margin-top: 2px">
              <el-dropdown-item @click.native="person">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="updatePass">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-main style="height: calc(100vh - 100px); overflow-y: scroll; /* 垂直滚动条 */">
          <router-view @update:admin="updateAdmin"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      audio: [
        {
          name: '东西（Cover：林俊呈）',
          artist: '纳豆',
          url: 'https://cdn.moefe.org/music/mp3/thing.mp3',
          cover: 'https://p1.music.126.net/5zs7IvmLv7KahY3BFzUmrg==/109951163635241613.jpg?param=300y300', // prettier-ignore
          lrc: 'https://cdn.moefe.org/music/lrc/thing.lrc',
        },
        {
          name: '响喜乱舞（Cover：MARiA）',
          artist: '泠鸢yousa',
          url: 'https://cdn.moefe.org/music/mp3/kyoukiranbu.mp3',
          cover: 'https://p1.music.126.net/AUGVPQ_rVrngDH9ocQrn3Q==/109951163613037822.jpg?param=300y300', // prettier-ignore
          lrc: 'https://cdn.moefe.org/music/lrc/kyoukiranbu.lrc',
        },
        {
          name: '啵唧',
          artist: 'Hanser',
          url: 'https://cdn.moefe.org/music/mp3/kiss.mp3',
          cover: 'https://p1.music.126.net/K0-IPcIQ9QFvA0jXTBqoWQ==/109951163636756693.jpg?param=300y300', // prettier-ignore
          lrc: 'https://cdn.moefe.org/music/lrc/kiss.lrc',
        },
      ],
      tableData: [],
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: "headerBg",
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
    };
  },
  methods: {
    collapse() {
      // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        // 收缩
        this.sideWidth = 64;
        this.collapseBtnClass = "el-icon-s-unfold";
        this.logoTextShow = false;
      } else {
        // 展开
        this.sideWidth = 200;
        this.collapseBtnClass = "el-icon-s-fold";
        this.logoTextShow = true;
      }
    },
    logout() {
      localStorage.removeItem("admin");
      this.$router.push("/login");
    },
    person() {
      this.$router.push("/person")
    },
    updateAdmin(admin) {   // 获取子组件传过来的数据  更新当前页面的数据
      this.admin = JSON.parse(JSON.stringify(admin))  // 让父级的对象跟子级的对象毫无关联
    },
    updatePass() {
      this.$router.push("/password")
    }
  },
};
</script>

<style>
.el-menu-item i, .el-submenu__title i {
  margin-right: 20px !important;
}

.el-menu-item:hover, .el-submenu__title:hover {
  background: #000 !important;
  border-radius: 5px !important;
  color: #fff !important;
}

.el-menu-item:hover i, .el-submenu__title:hover i {
  color: #fff !important;
}

.el-menu-item, .el-submenu__title {
  height: 50px !important;
  line-height: 50px !important;
}

.el-menu-item.is-active {
  background: rgba(0, 0, 0, 0.65) !important;
  border-radius: 10px !important;
  color: #fff !important;
}
</style>
