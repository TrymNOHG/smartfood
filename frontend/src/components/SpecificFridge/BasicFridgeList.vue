<template>
  <div class="card-container" :style="{ 'border-color': borderColor }">
    <router-link
      to="/fridge/item"
      @click="storeCurrentItem(item)"
      id="item-link"
    >
      <div class="card">
        <img class="item-img" :src="item.image" alt="item image" />
        <h3 class="item-name">{{ item.name }}</h3>
        <div class="item-info">
          <h4>
            {{ $t("expire_date") }}:
            {{
              new Date(item.expirationDate).toLocaleDateString("nb-NO", {
                day: "numeric",
                month: "long",
                year: "numeric",
              })
            }}
          </h4>
          <h4 class="price-text">{{ $t("price") }} {{ item.price }}</h4>
        </div>
        <font-awesome-icon
            v-if="isSuperUser"
            icon="fa-solid fa-trash"
            @click.prevent="deleteCard(item)"
            class="delete-icon icons"
        />
      </div>
    </router-link>
  </div>
</template>

<script>
import { number } from "yup";
import { useItemStore } from "@/store/store";
import swal from "sweetalert2";
import Swal from "sweetalert2";

export default {
  name: "BasicFridgeList",

  props: {
    item: {
      type: Object,
      default: () => ({
        description: String,
        expirationDate: String,
        image: String,
        name: String,
        price: String,
        purchaseDate: String,
        quantity: number,
        store: String,
      }),
    },
    isSuperUser: {
      type: Boolean,
      default: false
    }
  },

  methods: {
    storeCurrentItem(item) {
      this.itemStore.setCurrentItem(item);
    },

    deleteCard(item) {
      let deletePercentage = null;

      Swal.fire({
        html: `
         <div class="swal2-content">
           <div class="swal2-text">
             ${this.$t("Percent-wise, how much was left?")}
           </div>
           <div id="range-value-text" class="swal2-text"></div>
         </div>
       `,
        input: "range",
        inputAttributes: {
          min: 0,
          max: 100,
          step: 1,
        },
        didOpen: () => {
          deletePercentage = Swal.getInput();
          const inputNumber =
              Swal.getHtmlContainer().querySelector("#range-value");
          const rangeValueText =
              Swal.getHtmlContainer().querySelector("#range-value-text");

          deletePercentage.nextElementSibling.style.display = "none";
          deletePercentage.style.width = "100%";

          deletePercentage.addEventListener("input", () => {
            inputNumber.value = deletePercentage.value;
            rangeValueText.innerText = `${deletePercentage.value}%`;
          });
        },
        showCancelButton: true,
        confirmButtonText: this.$t("confirm_button"),
        cancelButtonText: this.$t("cancel_button"),
        customClass: {
          container: "my-swal-dialog-container",
        },
      }).then((result) => {
        if (result.isConfirmed) {
          swal
              .fire({
                title: "Vil du kjøpe den på nytt",
                icon: "success",
                showCancelButton: true,
                confirmButtonColor: "#4dce38",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes",
                cancelButtonText: "No",
                customClass: {
                  container: "my-swal-dialog-container",
                },
              })
              .then((result) => {
                if (result.isConfirmed) {
                  this.$emit("add-shopping", item);
                }
                this.$emit("delete-item", item, deletePercentage.value);
              });
        }
      });
    }
  },

  setup(props) {
    const itemStore = useItemStore();

    let borderColor = calculateExpirationDate(
      props.item.purchaseDate,
      props.item.expirationDate
    );
    console.log(props.item);
    function calculateExpirationDate(purchaseDate, expirationDate) {
      const currentDate = new Date();
      const purchase = new Date(purchaseDate);
      const expiration = new Date(expirationDate);

      const remainingDays = Math.ceil(
        (expiration.getTime() - currentDate.getTime()) / (1000 * 3600 * 24)
      );

      let borderColor;

      if (remainingDays >= 5) {
        borderColor = "green"; // Green for 7 or more days left
      } else if (remainingDays >= 3 && remainingDays < 5) {
        borderColor = "orange"; // Orange for 1 to 2 days left
      } else {
        borderColor = "red"; // Red for less than 1 day left
      }

      return borderColor;
    }

    return {
      borderColor,
      itemStore,
    };
  },
};
</script>

<style scoped>
.card {
  display: flex;
  justify-content: space-around;
  align-items: center;
  max-height: 100%;
  max-width: 100%;
  position: relative;
  background-color: white;
  text-align: center;
}

.item-img {
  max-height: 80px;
  max-width: 100%;
  object-fit: contain;
}

.item-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding-left: 10px;
}

.item-info {
  margin-left: auto;
}

.item-name {
  margin-left: auto;
}

#item-link {
  text-decoration: none;
  color: inherit;
  height: 100%;
  width: 100%;
}

.card-container {
  height: 100px;
  background-color: white;
  border: 3px solid white;
  border-radius: 5px;
  margin: 10px 5px;
  padding: 10px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  position: relative;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: auto;
}

.card-container:hover {
  box-shadow: 0 0 5px #ccc;
  transform: scale(1.02);
}

.icons {
  display: flex;
  align-items: center;
}

.delete-icon {
  font-size: 1.2rem;
  cursor: pointer;
  color: black;
  transition: color 0.2s ease-in-out;
  margin-left: auto;
}

.delete-icon:hover {
  color: #f00;
}

.swal2-modal {
  background-color: white;
  flex-direction: column;
  position: fixed;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  padding: 20px;
  border-radius: 5px;
  z-index: 100;
  display: flex;
  width: 60%;
  max-width: 500px;
}

.swal2-title {
  background-color: white;
  margin-top: 5%;
}

@media (max-width: 650px) {
  .item-img {
    margin-right: auto;
    width: 80px;
    height: 80px;
    border-radius: 5px;
    background-color: #eee;
    color: #eee;
  }

  .price-text {
    display: none;
  }
}
</style>
