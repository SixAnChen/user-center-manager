import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        meta: {name: '登录'},
        component: () => import('../views/Login.vue'),
    },
    {
        path: '/register',
        name: 'register',
        meta: {name: '注册'},
        component: () => import('../views/Register.vue'),
    },
    {
        path: '/',
        name: 'home',
        redirect: '/welcome',
        component: () => import('../views/HomeView.vue'),
        children: [
            {
                path: 'welcome',
                name: 'welcome',
                meta: {name: '系统首页'},
                component: () => import('../views/Welcome.vue')
            },
            {
                path: 'user',
                name: 'user',
                meta: {name: '用户管理'},
                component: () => import('../views/UserView')
            },
            {
                path: 'person',
                name: 'person',
                meta: {name: '个人信息'},
                component: () => import('../views/PersonView')
            },
            {
                path: 'password',
                name: 'password',
                meta: {name: '修改密码'},
                component: () => import('../views/UpdatePassView')
            },
            {
                path: 'notice',
                name: 'notice',
                meta: {name: '公告管理'},
                component: () => import('../views/NoticeView')
            },
            {
                path: 'log',
                name: 'log',
                meta: {name: '操作日志'},
                component: () => import('../views/LogView.vue')
            },
            {
                path: 'role',
                name: 'role',
                meta: {name: '角色管理'},
                component: () => import('../views/TypeView.vue')
            },
            {
                path: 'audit',
                name: 'audit',
                meta: {name: '请假审核'},
                component: () => import('../views/AuditView.vue')
            },
            {
                path: 'hotel',
                name: 'hotel',
                meta: {name: '酒店信息'},
                component: () => import('../views/HotelView.vue')
            },
            {
                path: 'reserve',
                name: 'reserve',
                meta: {name: '预订信息'},
                component: () => import('../views/ReserveView.vue')
            },
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 解决重复路由跳转问题
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

// 路由守卫
router.beforeEach((to, from, next) => {
    if (to.path === '/login') {
        next();
    }
    const admin = localStorage.getItem("admin");
    if (!admin && to.path !== '/login' && to.path !== '/register') {
        return next("/login");
    }
    next();
})

export default router
