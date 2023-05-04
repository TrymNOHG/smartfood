<template>
  <div id="inf">
    <div>
      <font-awesome-icon icon="fa-solid fa-bell" class="bell-icon" @click="changeNotifications"/>
      <div id="notification-list">
        <NotificationList
            v-if="showNotifications"
            v-for="notification in notifications" :notification="notification"
            @delete-notification="deleteNotification(notification)"
            @remove-border="removeBorder(notification)"
            :user-status = userStatus
        />
      </div>
      <div class="redd-dot" v-bind:style="[!showNotifications && notifications.length !== 0 ? 'visibility: visible' : 'visibility: hidden']">{{notifications.length}}</div>
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
    let userStatus = ref(false);

    userStatus = fridgeStore.isSuperUser
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
      userStatus,
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
  max-height: 250px;
  -ms-overflow-style: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-size: 125%;
  position: absolute;
  left: -350px;
}

#notification-list::-webkit-scrollbar {
  display: none;
}
#inf {
  display: flex;
  gap: 34%;
  margin-left: auto;
  bottom: 0;
}
.bell-icon {
  color: white;
  text-align: right;
  padding: 2px 5%;
  height: 100%;
  max-height: 25px;
  cursor: pointer;
  top: 10%;
}

.redd-dot {
  background-color: red;
  color: white;
  width: 20px;
  height: 20px;
  bottom: 70%;
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

  .bell-icon {

    color: white;
    text-align: right;
    padding: 2px 0;
    height: 60px;
    max-height: 25px;
    cursor: pointer;
    top: 15px;
  }

  #notification-list {
    z-index: 998;
    overflow-y: auto;
    max-height: 350px;
    display: flex;
    flex-direction: column;
    width: 1450%;
    top: 80%;
    left: -260px;
  }

  .bell-icon:hover {
    transition: .5s;
    transform:none;
  }

  .redd-dot {
    background-color: red;
    color: white;
    width: 20px;
    height: 20px;
    bottom: 45%;
    left: 12px;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }



}


</style>