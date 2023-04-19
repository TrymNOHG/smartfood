<template>
  <div class="thumbnail" style="overflow-y: auto">
    <figure class="picture">
      <img :src='item.thumbnail' alt="Thumbnail of item" width="100" height="100"/>
    </figure>
    <label class="description"> {{$t('briefdescription')}}: </label>
    <h4 class="description"> {{item.briefDesc}} </h4>
    <label class="description"> {{$t('price')}} </label>
    <h4 class="cost"> {{item.price}} </h4>
  </div>
</template>

<script>
export default {
  name: "ItemThumbnail",

  props: {
    item: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
h4 {
  font-size: 15px;
  font-weight: normal;
  margin-top: 5px;
  color: #3b3b3b;
}

label {
  margin-top: 5px;
  font-weight: bold;
  text-align: center;
  color: #555555;
}

.thumbnail {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  justify-content: center;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

.thumbnail:hover {
  transform: translateY(-5px);
}

.picture {
  margin-top: 10px;
  border-radius: 10px;
  height: 100px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #d3d3d3;
}

.picture img {
  object-fit: cover;
  height: 100%;
  width: 100%;
  border-radius: 10px;
}
</style>
