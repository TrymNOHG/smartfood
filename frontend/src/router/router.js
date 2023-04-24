import { createRouter, createWebHistory } from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import DinnerView from "@/views/DinnerView.vue";
import FridgesView from "@/views/FridgesView.vue";
import CartView from "@/views/CartView.vue";
import ProfileView from "@/views/ProfileView.vue";
import StatisticsView from "@/views/StatisticsView.vue";
import FridgeView from "@/views/FridgeView.vue";
import { useLoggedInStore } from '@/store/store';
import itemView from "@/views/itemView.vue";
import WelcomeComponent from "@/components/WelcomeComponent.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'FrontPage',
      component: WelcomeComponent,
      meta: { requiresAuth: false }
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { requiresAuth: false }
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
      meta: { requiresAuth: false }
    },
    {
      path: '/dinner',
      name: 'dinner',
      component: DinnerView,
      meta: { requiresAuth: true }

    },
    {
      path: '/fridges',
      name: 'fridges',
      component: FridgesView,
      meta: { requiresAuth: true }

    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView,
      meta: { requiresAuth: true }

    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView,
      meta: { requiresAuth: true }

    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
      meta: { requiresAuth: true }
    },
    {
      path: '/fridges/:name/fridge?id=:id',
      name: 'fridgeView',
      component: FridgeView,
      props: true
    },
    {
      path: '/fridge/:itemName/item?id=:itemId',
      name: 'itemView',
      component: itemView,
      props: true,
      meta: { requiresAuth: true }

    },
    {

    }

  ]
})




router.beforeEach((to, from, next) => {
  const userStore = useLoggedInStore();
  const isAuthenticated = userStore.isLoggedIn;

  const notRequiresAuth = to.matched.some(record => record.meta.requiresAuth === false);


  if (notRequiresAuth) {
    if (['/', '/register', '/login'].includes(to.path) && isAuthenticated) {
      next({ path: '/fridges' });
    } else {
      next();
    }
  } else {
    if (!isAuthenticated) {
      next({ path: '/' });
    } else {
      next();
    }
  }
});

export default router
