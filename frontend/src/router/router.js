import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import DinnerView from "@/views/DinnerView.vue";
import FridgesView from "@/views/FridgesView.vue";
import CartView from "@/views/CartView.vue";
import ProfileView from "@/views/ProfileView.vue";
import StatisticsView from "@/views/StatisticsView.vue";
import FridgeView from "@/views/FridgeView.vue";

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
      path: '/fridges',
      name: 'fridges',
      component: FridgesView
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
    },
    {
      path: '/fridges/:name/fridge?id=:id',
      name: 'fridgeView',
      component: FridgeView,
    },

  ]
})

export default router
