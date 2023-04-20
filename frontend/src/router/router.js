import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import DinnerView from "@/views/DinnerView.vue";
import FridgeView from "@/views/FridgeView.vue";
import CartView from "@/views/CartView.vue";
import ProfileView from "@/views/ProfileView.vue";
import StatisticsView from "@/views/StatisticsView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/dinner',
      name: 'dinner',
      component: DinnerView
    },
    {
      path: '/fridge',
      name: 'fridge',
      component: FridgeView
    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView 
    }

  ]
})

export default router
