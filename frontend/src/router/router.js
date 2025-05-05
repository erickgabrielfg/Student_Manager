import {createRouter, createWebHistory} from "vue-router";
import HomePage from "@/pages/HomePage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import SignUpPage from "@/pages/SignUpPage.vue";

const routes = [
    {
        path: "/login",
        name: "login",
        component: LoginPage,
        meta: {hideNavBar: true, requiresAuth: false},
    },
    {
        path: "/signUp",
        name: "signUp",
        component: SignUpPage,
        meta: {hideNavBar: true, requiresAuth: false}
    },
    {
        path: "/",
        redirect: "/login"
    },
    {
        path: "/home",
        name: "home",
        component: HomePage,
        meta: {requiresAuth: true}
    },
    {
        path: "/courses",
        name: "courses",
        component: () => import("../pages/Courses.vue"),
        meta: {requiresAuth: true}
    },
    {
        path: "/edit/users",
        name: "editUsers",
        component: () => import("../pages/EditUserPage.vue"),
        meta: {requiresAuth: true, hideNavBar: true}
    },
    {
        path: "/edit/courses",
        name: "editCourses",
        component: () => import("../pages/EditCoursePage.vue"),
        meta: {requiresAuth: true, hideNavBar: true}
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');

    if(to.meta.requiresAuth && !token){
        next("/login")
    }
    else if(to.path === "/home" && !token){
        next("/login")
    }
    else{
        next();
    }
});

export default router
