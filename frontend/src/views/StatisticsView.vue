<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('statistics') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

</template>

<script>
export default {
  name: "StatisticsView",

  methods: {

    showInformation(){
      //TODO: INFORMATION STATISTICS put information API in here
    },
  }
}
</script>

<style scoped>
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