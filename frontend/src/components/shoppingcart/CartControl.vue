<template>

    <div class="cart-control">
        <button class="control-buttons" type="button" @click="handleMarkAll">
            Mark all
        </button>

        <button class="control-buttons" type="button" @click="handleBuy">
            Buy
        </button>

        <button class="control-buttons" type="button" @click="handleDelete">
            Delete
        </button>
    </div>
</template>

<script>
import {boolean} from 'yup';

export default {
    props: {},
    methods: {
        handleMarkAll() {
            this.$emit("check-all");
        },
        handleBuy() {
            this.$emit("buy");
        },
        handleDelete() {
            this.$emit("delete");
        }
    },
};
</script>
<style>

.cart-control {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 16px;
    background-color: #f8f8f8;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.control-buttons {
    display: inline-block;
    margin: 0;
    padding: 8px 16px;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    color: #fff;
    background-color: #007aff;
    border: none;
    border-radius: 20px; /* add rounded edges to the buttons */
    cursor: pointer;
}

.control-buttons:hover {
    background-color: #0061b5;
}

.control-buttons:last-child {
    background-color: #34c759;
}

.control-buttons:last-child:hover {
    background-color: #30b34e;
}

@media only screen and (max-width: 800px) {

}

@media only screen and (min-width: 350px) and (max-width: 480px) {

}

@media only screen and (max-width: 350px) {

}
</style>
  