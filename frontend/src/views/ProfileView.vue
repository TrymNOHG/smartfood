<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('profile') }}</h2>
    <div class="information-button">
      <img src="src/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>
  <div>
    <profile-component/>
  </div>

</template>

<script>
import ProfileComponent from "../components/ProfileComponent.vue";
export default {
  name: "ProfileView",
  components: {ProfileComponent},

  methods: {

    showInformation(){
      //TODO: INFORMATION PROFILE put information API in here
    },
  }
}
</script>

<style>

  .grey-bar {
    background-color: #6C6C6C;
    max-height : 35px;
    text-align: center;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
  }

  #grey-header{
    grid-column: 2;
    color: white;
  }

  .information-button{
    grid-column: 3;
    text-align: right;
    padding: 2px 5px;
  }

  #info-picture{
    height: 30px;
    width: 30px;
    cursor: pointer;
  }

</style>