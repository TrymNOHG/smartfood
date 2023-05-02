<template>
  <div class="notification" :style="notification.border ? 'border: 2px solid red;' : 'border: 2px solid black;'">
    <div class="notification-header">
      <h3 class="notification-title">
        {{notification.name}} {{ $t('soon_expire') }}
      </h3>
      <span class="notification-date">
        {{ $t('expire_date') }}: {{new Date(notification.expirationDate)
          .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}
      </span>
    </div>
    <div class="notification-icons">
      <font-awesome-icon icon="fa-solid fa-check-circle" @click="removeBorder" class="check-icon icons"/>
      <font-awesome-icon icon="fa-solid fa-trash" @click="deleteItem" class="delete-icon icons"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotificationList",

  props: {
    notification: {
      name: String,
      expirationDate: String,
      border: Boolean
    }
  },

  methods: {
    deleteItem() {
      this.$emit("delete-notification")
    },

    removeBorder() {
      this.$emit("remove-border")
    }
  }
}
</script>

<style scoped>

.notification-icons {
  gap: 25%;
  margin-left: auto;
}

.notification {
  display: flex;
  flex-direction: row;
  text-align: center;
  gap: 1rem;
  background-color: #fff;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
  border-radius: 0.25rem;
  padding: 1rem;
  margin: 2% 0 0 2%;
}

.delete-icon:hover {
  color: red;
}

.check-icon:hover {
  color: lime;
}

.notification-header {
  display: flex;
  flex-direction: column;
  text-align: center;
  width: 100%;
}

.notification-title {
  margin: 0;
  font-size: 1.125rem;
}

.notification-date {
  font-size: 0.875rem;
  color: #999;
}

@media (max-width: 768px) {
  .notification-icons {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 0;
    margin: 1rem 0 0 0;
  }

  .notification {
    flex-direction: column;
    gap: 0.5rem;
  }

  .notification-title {
    font-size: 1rem;
  }

  .notification-date {
    font-size: 0.75rem;
  }

  .notification-icons {
    font-size: 1.5rem;
  }
}

</style>