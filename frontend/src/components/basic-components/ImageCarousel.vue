<template>
  <!-- template based on https://www.w3schools.com/howto/howto_js_slideshow.asp -->
  <div class="container" v-if="pictures">
    <div class="slides">
        <img v-bind:src="pictures[this.slideIndex]" alt="Slide" width="200" height="200">
    </div>
    <a class="prev" @click="changeSlides(-1)">&#10094;</a>
    <a class="next" @click="changeSlides(1)">&#10095;</a>
  </div>
</template>

<script>
import IconArrow from "@/components/icons/IconArrow.vue";
import {ref} from "vue";
import {loadImagesByItemId} from "@/services/ItemService";

export default {
  name: "imageCarousel",
  components: {IconArrow},
  props: {
    itemId: {
      type: Number,
      required: true
    },
  },
  methods: {

    changeSlides(n) {
      console.log("Inne")
      this.slideIndex = (this.slideIndex + n) % this.pictures.length
      console.log(this.slideIndex)
      this.currentPicture = this.pictures[this.slideIndex];
      console.log(this.currentPicture)
    },
  },
  setup(props) {
    const pictures = ref([])
    function convertImageBackToUrl(image) {
      return `data:image/png;base64,${image}`;
    }

    loadImagesByItemId(props.itemId).then(response => {
      const listOfDecodedPictures = []
      for(let i = 0; i < response.data.pictures.length; i++) {
        listOfDecodedPictures.push(convertImageBackToUrl(response.data.pictures[i]))
      }
      pictures.value = listOfDecodedPictures
    }).catch(error => {
      console.log('error: ', error)
    })

    return {
      pictures
    }

  },
  data(){
    return{
      slideIndex: 0,
      currentPicture: null
    }
  },
}
</script>

<style scoped>
* {box-sizing:border-box}

 .container{
   position: relative;
   margin: auto;
}

/* Next & previous buttons */
.prev, .next {
  cursor: pointer;
  position: absolute;
  top: 50%;
  width: auto;
  margin-top: -22px;
  padding: 16px;
  color: white;
  font-weight: bold;
  font-size: 18px;
  transition: 0.6s ease;
  border-radius: 0 3px 3px 0;
  user-select: none;
}

/* Position the "next button" to the right */
.next {
  right: 0;
  border-radius: 3px 0 0 3px;
}

.numPictures{
  color: #f2f2f2;
  font-size: 15px;
  padding: 8px 12px;
  position: absolute;
  bottom: 8px;
  width: 100%;
  text-align: center;
}

</style>
