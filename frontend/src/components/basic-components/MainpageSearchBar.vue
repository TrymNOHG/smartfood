<template>
  <div class="search-window">
    <div class="label">
      <label>Search for item</label>
    </div>
    <div class="searchbox">
      <input placeholder="Search..."
             class="searchInput"
             @keydown.enter
             type="text"
             v-model="searchTerm"
             @input="onInput"
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "mainpageSearchBar",
  data() {
    return {
      searchTerm: "",
    };
  },
  methods: {
    onInput() {
      this.$emit("search-term-updated", this.searchTerm);
    },
  },
};
</script>

<style scoped>
  .search-window{
    display: grid;
    grid-template-rows: 1fr 2fr;
    grid-template-columns: 1fr;
    margin-top: 10px;
    text-align: left;
  }
  .searchInput{
    height: inherit;
    max-height: fit-content;
    border: 5px solid #7EB09B;
    border-radius: 10px;
    width: inherit;
    font-size: large;
  }
  .searchbox{
    grid-template-columns: 1fr;
    display: grid;
  }

</style>
