<template>
  <div class="login-container">
    <div class="login-form">
      <el-form :model="form" :rules="rules" ref="form">
        <h3 class="login_title">
          <span style="font-size: 20px; margin-right: 5px">系统登陆</span>
          <el-button size="medium" @click="toRegister">点我注册</el-button>
        </h3>
        <el-form-item prop="username">
          <el-input v-model="form.username" size="medium" prefix-icon="el-icon-user"
                    placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" size="medium" prefix-icon="el-icon-lock"
                    placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="medium" style="width: 100%; background: #505458; border: none"
                     type="primary" @click="login('form')">登录
          </el-button>

          <div style="flex: 1; text-align: right"><span style="color: #0f9876; cursor: pointer"
                                                        @click="handleForgetPass">忘记密码</span></div>
        </el-form-item>
      </el-form>

      <el-dialog title="忘记密码" :visible.sync="forgetPassDialogVis" width="30%" :close-on-click-modal="false">
        <el-form :model="forgetPassForm" ref="formRef" :rules="rules" label-width="100px" style="padding-right: 20px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="forgetPassForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="forgetPassForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input show-password v-model="forgetPassForm.newPassword"
                      placeholder="请输入新密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input show-password v-model="forgetPassForm.confirmPassword"
                      placeholder="请确认新密码"></el-input>
          </el-form-item>
        </el-form>
        <div>
          <el-button size="medium" style="background: #505458; border: none"
                     type="primary" @click="resetPassword">重置密码
          </el-button>
          <el-button size="medium" @click="forgetPassDialogVis = false">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LoginView",
  data() {
    //校验新密码
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('确定新密码不能为空'))
      } else if (value !== this.forgetPassForm.newPassword) {
        callback(new Error('新密码必须一致'))
      } else {
        callback()
      }
    }
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入电话', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '新密码不能为空！', trigger: 'blur'},
        ],
        confirmPassword: [
          {required: true, message: '确认新密码不能为空！', trigger: 'blur'},
          {validator: validatePassword, required: true, trigger: 'blur'},
        ],
      },
      forgetPassForm: {},   // 忘记密码的表单数据
      forgetPassDialogVis: false,
    }
  }
  ,
  methods: {
    // 忘记密码
    handleForgetPass() {   //  初始化表单的数据
      this.forgetPassForm = {}
      this.forgetPassDialogVis = true
    }
    ,

    /**
     * 忘记密码
     */
    resetPassword() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request.put('/admin/resetPass', this.forgetPassForm).then(res => {
            if (res.code === '200') {
              this.$message.success('重置成功')
              this.forgetPassDialogVis = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
    ,

    toRegister() {
      this.$router.push("/register")
    }
    ,

    login(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/admin/login', this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("登录成功")
              localStorage.setItem("admin", JSON.stringify(res.data));
              this.$router.push('/')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
  ,
}
</script>

<style scoped>

.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  /*实现全屏背景*/
  background-image: url("../assets/wallhaven-gpd6v7.jpg");
  background-size: cover;
  background-position: 50%;
  overflow: hidden;
}

/*登录表单*/
.login-form {
  text-align: center;
  width: 400px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 20px;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  overflow: hidden;
  padding: 40px;
}

.login_title {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 18px;
  color: #505458;
}
</style>
