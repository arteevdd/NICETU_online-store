<template>
    <div class="cart">
        <div class="cart__item">
            <CartItem
                v-for="(item, index) in CART"
                :key="item.productId"
                :cart_data_item="item"
                @deleteFromCart="deleteFromCart(index)"
                @decrementItem="decrementItem(index)"
                @incrementItem="incrementItem(index)"
            />
            <div 
                v-if="CART.length === 0"
                style="text-align: center; margin-top: 20px"
            >
                <h4>Корзина пустая :(</h4>
                <br>
                <RouterLink :to="{name: 'home'}">
                    Перейти к покупкам?
                </RouterLink>
            </div>
        </div>
        <div class="cart_total">
            <h5 style="margin-bottom: 20px">Your order</h5>
            <p style="font-size: 17px; margin-bottom: 5px">Количество товаров: {{ CART.reduce((a, b) => a + b.quantity, 0) }}</p>
            <p style="font-size: 17px;">Итого: {{ CART.reduce((a, b) => a + b.quantity * b.salePrice, 0) }}Р</p>
            <button 
                class="btn btn-primary" 
                @click="Buy"
            >
            Оформить заказ
            </button>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import CartItem from './cart-item.vue'
import axios from 'axios';

export default {
    name:'v-cart',
    components: {
    CartItem
    },
    computed: {
        ...mapGetters([
            'CART'
        ])
    },
    methods: {
        ...mapActions([
            'DELETE_FROM_CART',
            'DECREMENT_ITEM',
            'INCREMENT_ITEM',
            'CLEAN_CART'
        ]),
        deleteFromCart(index) {
            this.DELETE_FROM_CART(index)
        },
        decrementItem(index) {
            this.DECREMENT_ITEM(index)
        },
        incrementItem(index) {
            this.INCREMENT_ITEM(index)
        },
        async Buy() {
            let cart = []
            for (let i in this.CART) {
                let pro = {
                    productId: this.CART[i].productId,
                    quantity: this.CART[i].quantity
                }
                cart.push(pro)
            }
            try {
                const user = await axios({
                    method: 'post',
                    url: 'http://localhost:8080/online-store/v1/orders',
                    data: cart,
                    headers: {
                        "Content-type": "application/json; charset=UTF-8",
                        'Authorization': 'Bearer ' + JSON.parse( localStorage.user ).token,
                        'Cache-Control': null,
                        'X-Requested-With': null,
                    }
                    });
                alert('Заказ успешно оформлен!')
                this.CLEAR_CART(JSON.parse( localStorage.user ).email)
                return user
            } catch (e) {
                if(e.response.status === 500) {
                    alert('Попробуйте еще раз')
                }
                if(e.response.status === 400) {
                    alert('Одного из товаров больше нет в наличии')
                }
                console.log(e)
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.cart{
    display: flex;
    flex-direction: row;
    min-width: 968px;
    justify-content: space-around;
    &__item {
        width: 70%;
    }
    &_total {
        padding: 20px;
        position: sticky;
        top: 10px;
        min-width: 230px;
        height: 200px;
        align-items: center;
        border: 1px solid grey;
        border-radius: 10px;
    }
}
</style>