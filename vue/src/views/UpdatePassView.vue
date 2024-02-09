<template>
  <div>
    <div class="login-form">
      <el-form :model="admin" ref="formRef" :rules="rules">
        <h3 class="login_title">
          <span style="font-size: 20px;">修改密码</span>
        </h3>
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input size="medium" show-password v-model="admin.oldPassword" placeholder="旧密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input size="medium" show-password v-model="admin.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input size="medium" show-password v-model="admin.confirmPassword" placeholder="确认新密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button size="medium" style="background: #505458; border: none"
                     type="primary" @click="updatePassword">确定
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
  name: "UpdatePass",
  data() {
    //校验新密码
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('确定新密码不能为空'))
      } else if (value !== this.admin.newPassword) {
        callback(new Error('新密码必须一致'))
      } else {
        callback()
      }
    }
    return {
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
      rules: {
        oldPassword: [
          {required: true, message: '旧密码不能为空！', trigger: 'blur'},
        ],
        newPassword: [
          {required: true, message: '新密码不能为空！', trigger: 'blur'},
        ],
        confirmPassword: [
          {required: true, message: '确认新密码不能为空！', trigger: 'blur'},
          {validator: validatePassword, required: true, trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    cancel() {
      this.$router.push("/")
    },
    updatePassword() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          // 保存当前的用户信息到数据库
          request.put('/admin/updatePass', this.admin).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('保存成功')
              localStorage.removeItem("admin")
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  },
}
</script>

<style scoped>

/deep/ .el-form-item__label {
  font-weight: bold;
}

.login-form {
  text-align: center;
  width: 400px;
  background-color: rgba(255, 255, 255, 0.8);
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  overflow: hidden;
  padding: 40px;
}

.login_title {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #505458;
}
</style>