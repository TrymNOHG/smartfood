<template>

    <input
        v-bind="$attrs"
        :value="modelValue"
        :placeholder="label"
        @input="$emit('update:modelValue', $event.target.value)"
        class="form-control"
        :style="{ borderColor: error ? 'red' : ''}"
        aria-invalid='error ? true : null'
    >
    <p
      v-if="error"
      class="errorMessage"
      aria-live="assertive"
      >
      {{ error }}
    </p>
  <div id="shadow">
    </div>
  </template>
  
  <script>
  export default {
    props: {
      label: {
        type: String,
        default: ''
      },
      modelValue: {
        type: [String, Number],
        default: ''
      },
      error: {
        type: String,
        default: ''
      }
    }
  }
  </script>
  
  <style scoped>

  #searchbar{
    display: flex;
  }
  .form-control {
  padding: 0px 10px 0 20px;
  margin-top: 10px;
  color: #333;
  font-size: 28px;
  font-weight: 500;
    border: 3px solid #555;
    -webkit-box-shadow: none;
    box-shadow: none;
    min-height:60px;
    height: auto;
    border-radius: 50px 0  0 50px !important;
}
.form-control :focus {
        -webkit-box-shadow: none;
        box-shadow: none;
        border-color: transparent;

    }
#shadow{
  display: none;
}

  @media only screen and (min-width: 350px) and (max-width: 480px) {

    .form-control {
      display: flex;
      align-items: flex-end;
      justify-content: space-evenly;
      position: fixed;
      bottom: 62px;
      width: 95%;
      height: 60px;
      font-size: 20px;
      background-color: white;
      border: 7px solid #31c48d;
      border-radius: 50px 50px 50px 50px !important;
    }



    #shadow{
      display: flex;
      align-items: center;
      justify-content: space-evenly;
      position: fixed;
      bottom: 70px;
      width: 95%;
      height: 30px;
      font-size: 20px;
      background-color: #31c48d;
      z-index: -2;

    }

  }

  </style>