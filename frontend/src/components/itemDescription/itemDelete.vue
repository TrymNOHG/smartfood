<template>
  <div>
    <div class="info-window">
      <h2 style="font-weight: bold" id="how-much-left">{{ $t('how_much_is_left') }}</h2>
      <div class="slider">
        <input type="range" min="0" max="100" :value="sliderValue" @input="sliderValue = $event.target.value" class="slider-range">
        <div class="slider-bar"></div>
        <div class="slider-value" id="rangeValue">{{sliderValue}}%</div>
      </div>
      <basic-button :button-text="$t('delete_item')" class="basic-button" @click="deleteItem(item)"/>
    </div>
  </div>
</template>

<script>
import BasicFridgeItem from "@/components/SpecificFridge/BasicSquareList.vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";
export default {
  name: "itemDelete",
  components: {BasicButton, BasicFridgeItem},

  props: {
    item: {
      type: Object
    }
  },

  methods: {
    deleteItem(item){
      this.$emit('delete-item', item, this.sliderValue);
    }
  },

  data() {
    return {
      sliderValue: 50
    }
  },
};
</script>

<style scoped>

.info-window {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 20px;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.info-window h2 {
  margin-top: 0;
}

.basic-button {
  margin-top: 5%;
  max-width: 25%;
}

.slider{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 10%;
  width: 60%;
}

.slider-range {
  width: 60%;
  height: 50%;
  margin: 0;
  padding: 0;
}


.slider input[type="range"] {
  -webkit-appearance: none;
  width: 100%;
  height: 100%;
  background: transparent;
  margin: 0;
  padding: 0;
  z-index: 2;
  position: relative;
}

.slider input[type="range"]::-webkit-slider-runnable-track {
  height: 6px;
  -webkit-appearance: none;
  color: #7E6D57;
  margin-top: -3px;
}

.slider input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #0077be;
  margin-top: -8px;
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.2);
  z-index: 3;
  position: relative;
}


.slider .slider-bar {
  position: absolute;
  top: 50%;
  left: 0;
  width: 100%;
  height: 6px;
  background-color: #7E6D57;
  transform: translateY(-50%);
  z-index: 1;
}

.slider-value {
  position: absolute;
  top: -30px;
  right: 0;
  font-weight: 600;
  color: #7E6D57;
  text-shadow: 0 2px 3px rgba(255,255,255,0.5);
}

.slider .slider-value {
  position: absolute;
  top: -30px;
  right: 0;
  font-weight: 600;
  color: #7E6D57;
}
</style>
