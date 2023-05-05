<template>
  <div class="notification">
    <div class="notification-header">
      <h3 class="notification-title">
        {{notification.itemName}} {{ $t('soon_expire') }}
      </h3>
      <span class="notification-date">
        {{ $t('expire_date') }}: {{new Date(notification.expirationDate)
          .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotificationList",

  props: {
    notification: {
      notificationId: Number,
      itemName: String,
      expirationDate: String,
      isRead: Boolean,
      fridgeId: Number
    },
    userStatus: Boolean,
  }
}
</script>

<style scoped>

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
}

</style>
