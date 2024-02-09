<template>
  <div class="login-container">
    <div class="login-form">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm">
        <h3 class="login_title">
          <span style="font-size: 20px; margin-right: 5px">系统注册</span>
          <el-button size="medium" @click="toLogin">去登录</el-button>
        </h3>
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" size="medium" prefix-icon="el-icon-user-solid"
                    placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="ruleForm.password" size="medium" prefix-icon="el-icon-lock"
                    placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input v-model="ruleForm.checkPass" size="medium" prefix-icon="el-icon-lock"
                    placeholder="请确认密码"></el-input>
        </el-form-item>


        <el-form-item>
          <el-button size="medium" style="background: #505458; border: none"
                     type="primary" @click="register('ruleForm')">注册
          </el-button>
          <el-button size="medium" @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "RegisterView",
  data() {
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        username: '',
        password: '',
        checkPass: ''
      },
      rules: {
        username: [
          {required: true, message: '账号不能为空', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/admin/register', this.ruleForm).then(res => {
            if (res.code === '200') {
              this.$message.success('注册成功,请返回登录')
              this.$refs[formName].resetFields()
            } else {
              this.$message.error(res.msg)
            }
          })
        }

      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    toLogin() {
      this.$router.push("/login")
    }
  },
}
</script>

<style scoped>

.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  /*实现全屏背景*/
  background-image: url("../assets/wallhaven-yx28wl.jpg");
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
