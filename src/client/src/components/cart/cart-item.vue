<template>
    <div class="cart_item">
            <img class="cart_item__img" 
                :src="require(`../../../public/images/${cart_data_item.road}`)" 
                alt="img"
            >
            <div class="cart_item_about">
                <h2>{{ cart_data_item.nameProduct }}</h2>
            </div>
            <div class="cart_item_right">
                <div class="cart_item_right__quantity">
                    <button 
                        @click="decrementItem" 
                        class="btn btn-outline-primary btn-sm"
                    >
                    -
                    </button>
                    <p class="cart_item_right__quantity_i">{{ cart_data_item.quantity }}</p>
                    <button 
                        @click="incrementItem" 
                        class="btn btn-outline-primary btn-sm" 
                        :disabled="flagBtn"
                    >
                    +
                    </button>
                </div>
                <button 
                    class="cart_item_right__delete_btn btn btn-primary"
                    @click="deleteFromCart"
                >
                Delete
                </button>
            </div>
            <h5>{{ (cart_data_item.salePrice).toLocaleString('ru-RU') }} ₽</h5>
    </div>
</template>

<script>

export default {
    name:'v-cart-item',
    props: {
        cart_data_item: {
            type: Object,
            default() {
                return {}
            }
        }
    },
    data() {
        return {
            flagBtn: false
        }
    },
    methods: {
        deleteFromCart() {
            this.$emit('deleteFromCart')
        },
        decrementItem() {
            this.$emit('decrementItem')
        },
        incrementItem(){
            if (this.cart_data_item.count > 0) {
            this.$emit('incrementItem')
            }
            else {
                this.flagBtn = true
            }
        }
    },
    mounted() {
        if (this.cart_data_item.count === 0) {
            this.flagBtn = true
        }
    }
}
</script>

<style lang="scss" scoped>
.cart_item{
    border-bottom: 1px solid lightgray;
    margin-bottom: 40px;
    display: grid;
    grid-template-columns: 1fr 4fr 1fr 1fr;
    height: 300px;
    align-items: center;
    &__img {
        margin: 15px;
        max-width: 220px;
        max-height: 260px;
    }
    &_about {
        margin-top: 30px;
        width: 50%;
    }
    &_right {
        width: 10%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        &__quantity {
            margin-bottom: 10px;
            display: flex;
            &_i {
                margin: 0 5px 0 5px;
            }
        }
    }
}   
</style>