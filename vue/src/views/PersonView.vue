<template>
  <div>
    <div class="login-form">
      <el-form :model="admin">
        <el-form-item>
          <el-upload
              action="http://localhost:9090/api/files/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="admin.avatar" :src="'http://localhost:9090/api/files/' + admin.avatar"
                 style="width: 100px; height: 100px; border-radius: 10px"/>
            <el-avatar v-else shape="square" :size="80"
                       src="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png">
            </el-avatar>
          </el-upload>
        </el-form-item>
        <el-form-item prop="username">
          <el-input v-model="admin.username" size="medium" prefix-icon="el-icon-user"
                    placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="age">
          <el-input v-model="admin.age" size="medium" prefix-icon="el-icon-star-off"
                    placeholder="年龄"></el-input>
        </el-form-item>
        <el-form-item prop="sex">
          <el-input v-model="admin.sex" size="medium" prefix-icon="el-icon-magic-stick"
                    placeholder="性别"></el-input>
        </el-form-item>
        <el-form-item prop="address">
          <el-input v-model="admin.address" size="medium" prefix-icon="el-icon-location"
                    placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="admin.email" size="medium" prefix-icon="el-icon-message"
                    placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="medium" style="background: #505458; border: none"
                     type="primary" @click="update">修改
          </el-button>
          <el-button size="medium" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "PersonalView",
  data() {
    return {
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
    }
  },
  methods: {
    handleAvatarSuccess(res) {
      this.admin.avatar = res.data
    },

    cancel() {
      this.$router.push("/")
    },
    update() {
      // 保存当前的用户信息到数据库
      request.post('/admin', this.admin).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('修改成功')

          // 更新浏览器缓存里的用户信息
          localStorage.setItem('admin', JSON.stringify(this.admin))

          // 触发父级的数据更新
          this.$emit('update:admin', this.admin)
        } else {
          this.$message.error(res.msg)
        }
      })
    },

  },
}
</script>

<style scoped>
.login-form {
  text-align: center;
  width: 400px;
  background-color: rgba(255, 255, 255, 0.8);
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  overflow: hidden;
  padding: 40px;
}
</style>
