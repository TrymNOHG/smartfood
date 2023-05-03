<template>
  <div id="info-and-bell">
    <div>
      <font-awesome-icon icon="fa-solid fa-bell" class="bell-icon" @click="changeNotifications"/>
      <div id="notification-list">
        <NotificationList
            v-if="showNotifications"
            v-for="notification in notifications" :notification="notification"
            @delete-notification="deleteNotification(notification)"
            @remove-border="removeBorder(notification)"/>
      </div>
      <div class="redd-dot" v-if="!showNotifications && notifications.length !== 0">{{notifications.length}}</div>
    </div>
  </div>
</template>

<script>
import NotificationList from "@/components/basic-components/NotificationList.vue";
import {ref} from "vue";
import {useFridgeStore} from "@/store/store";

export default {
  name: "InfoAndBell",
  components: {NotificationList},

  setup() {
    const fridgeStore = useFridgeStore();
    const notifications = ref([]);
    const fridge = fridgeStore.getCurrentFridge;

    /*
    const getNotifications = async () => {
      fridgeStore.fetchNotifications(fridge.fridgeId).then((notification) => {
        notifications.value = notification;
      });
    }
     */

    notifications.value = [
      {
        name: "bruhh",
        expirationDate: "123123123",
        border: true
      },
      {
        name: "bruhh",
        expirationDate: "123123123",
        border: true
      },
      {
        name: "Naan Håndlagde m/Hvitløk 260g Nirus\n",
        expirationDate: "123123123",
        border: false
      },
      {
        name: "bruhh",
        expirationDate: "123123123",
        border: false
      },
      {
        name: "bruhh",
        expirationDate: "123123123",
        border: false
      },
    ];

    //getNotifications();
    return {
      notifications,
      //getNotifications,
    }
  },

  data() {
    return {
      showNotifications: false,
    }
  },

  methods: {
    removeBorder(notification) {
      console.log(notification)
      //this.fridgeStore.removeBorderForNotification(notification, this.fridge.fridgeId);
      //this.getNotifications();
    },

    deleteNotification(notification) {
      console.log(notification)
      //this.fridgeStore.deleteNotificationUsingId(notification, this.fridge.fridgeId);
    },

    changeNotifications() {
      this.showNotifications = !this.showNotifications;
    },
  }
}
</script>

<style scoped>

#notification-list  {
  z-index: 998;
  overflow-y: auto;
  max-height: 320px;
  -ms-overflow-style: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-size: 125%;
}

#notification-list::-webkit-scrollbar {
  display: none;
}

.bell-icon {
  color: white;
  grid-column: 3;
  text-align: right;
  padding: 2px 5%;
  height: 100%;
  max-height: 25px;
  cursor: pointer;
}

.redd-dot {
  background-color: red;
  color: white;
  width: 20px;
  height: 20px;
  bottom: 80%;
  left: 50%;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.bell-icon:hover {
  transition: .5s;
  transform: rotate(45deg);
}

@media (max-width: 650px) {

  #notification-list {
    z-index: 998;
    position: absolute;
    overflow-y: auto;
    max-height: 350px;
    display: flex;
    flex-direction: column;
    width: 1720%;
    right: -50px;
  }
}

</style>