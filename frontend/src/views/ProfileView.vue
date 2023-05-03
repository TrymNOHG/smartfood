<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('profile') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
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

<style scoped>

  .grey-bar {
    background-color: #6C6C6C;

    text-align: center;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
  }

  #grey-header{
    grid-column: 2;
    color: white;
  }

  .information-button{
    display: flex;
    grid-column: 3;
    text-align: right;
    margin-left: auto;
  }

  #info-picture{
    height: 30px;
    width: 30px;
    cursor: pointer;

  }

  @media only screen and (min-width: 10px) and (max-width: 650px) {

    .grey-bar{
      display: flex;
      align-content: center;
      align-items: center;
      flex-wrap: wrap;
      justify-content: center;
      text-align: center;
      margin-top: 5px;
      background-color: #31c48d;
      max-height: 60px;
      height: 60px;
      border-radius: 20px 20px 0 0;
      width: 100%;
    }

    #grey-header{
      margin-left: 25%;
      height: 60px !important;
      background-color: white;
      border-radius: 20px 20px 0 0;
      font-weight: bold;
      text-decoration: none;
      text-shadow: none;
      color: black;
      margin-top: 20px;
      padding-top: 10px;
      width: 50%;
    }
  }

</style>