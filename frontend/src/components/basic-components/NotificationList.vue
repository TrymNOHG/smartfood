<template>
  <div class="notification">
    <div class="notification-header">
      <h3 class="notification-title">
        {{notification.name}} går snart ut på dato!
      </h3>
      <span class="notification-date">
        {{ $t('expire_date') }}: {{new Date(notification.expirationDate)
          .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}
      </span>
    </div>
    <font-awesome-icon icon="fa-solid fa-trash"  @click="deleteItem" class="delete-icon icons"/>
  </div>
</template>

<script>
export default {
  name: "NotificationList",

  props: {
    notification: {
      name: String,
      expirationDate: String
    }
  },

  methods: {
    deleteItem() {
      this.$emit("delete-notification")
    }
  }
}
</script>

<style scoped>

.notification {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 1rem;
  background-color: #fff;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
  border-radius: 0.25rem;
  padding: 1rem;
  border: red 2px solid;
}

.notification-header {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.notification-title {
  margin: 0;
  font-size: 1.125rem;
}

.notification-date {
  font-size: 0.875rem;
  color: #999;
}
</style>