import { createRouter, createWebHistory} from "vue-router";

import cart from "./components/cart/cart.vue";
import catalog from "./components/catalog/catalog.vue";
import userLogin from "./components/user/user-login.vue";
import userRegist from "./components/user/user-reg.vue"

const router = createRouter({
    history: createWebHistory(), 
    routes: [
        {
            path: '/',
            name: 'home',
            component: catalog
        },
        {
            path: '/:type',
            name: 'type',
            component: catalog,
        },
        {
            path: '/:type/:category',
            name: 'category',
            component: catalog,
        },
        {
            path: '/:type/:category/:subcategories',
            name: 'subcat',
            component: catalog,
        },
        {
            path: '/cart',
            name: 'cart',
            component: cart
        },
        {
            path: '/login',
            name: 'login',
            component: userLogin,
        }, 
        {
            path: '/regist',
            name: 'regist',
            component: userRegist,
        }
    ]
})

export default router;