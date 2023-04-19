<template>
  <div class="uploader">
    <div class="preview">
      <div
          class="image"
          v-for="(img, i) in imageList" :key="i"
          :style="{ 'background-image': `url(${img})` }"
      >
        <div class="remove" @click="removeImage(i)">X</div>
      </div>
      <div class="upload">
        <input class="input-image" ref="fileInput" type="file" @change="whenSelected" multiple />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "pictureUploadComponent",

  data () {
    return {
      imageList: []
    }
  },

  methods: {

    /**
     * this method is called when a file is chosen by a user
     * when chosen the file-reader makes a bse64 string from the image chosen
     * its then saved in a list of images
     * the image is also emitted to the parent and stored in backend as form data
     * the method is async just to make sure that the image is in the list before it's data is sent further
     * @returns {Promise<void>}
     */
    async whenSelected() {
      const input = this.$refs.fileInput
      const files = input.files
      if (files && !input.empty) {
        for (let i = 0; i < files.length; i++) {
          const imageReader = new FileReader()

          const imageAsBase64 = await new Promise((resolve) => {
            imageReader.onload = (image) => resolve(image.target.result)
            imageReader.readAsDataURL(files[i])
          })

          this.imageList.push(imageAsBase64)
          this.$emit('send-image-data', files[i])
        }
      }
    },

    /**
     * this method is used for removing a chosen image
     * the button on the image triggers this method which takes the index of the image
     * and then removes it from the imageList
     * @param index the index of the image you want to remove
     */
    removeImage(index) {
      this.imageList.splice(index, 1);
    }
  }
};

</script>

<style scoped>
.uploader {
  margin: 25px;
}

.preview {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.upload {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 100px;
  margin: 5px;
  border: 1px dashed #ccc;
  background-color: #f7f7f7;
  cursor: pointer;
}

.image {
  width: 100px;
  height: 100px;
  margin: 5px;
  border: 1px solid #ccc;
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
}

.remove {
  color: black;
  background-color: #d3d3d6;
  width: 25px;
  height: 25px;
  float: right;
  solid-color: black;
  border-radius: 2px;

}

.remove:hover{
  background-color: #a8a8ab;
}

.upload:hover {
  background-color: #eee;
}

.upload {
  width: 200px;
  height: 24px;
}

.upload-placeholder span {
  font-size: 14px;
  font-weight: bold;
}

</style>
