<template>
    <div class="catalog_item">
    <img class="catalog_item__img" :src="require(`../../../public/images/${prod.road}`)" alt="img">
        <div class="catalog_item_about">
            <p> {{ prod.nameProduct }}</p>
            <p>{{ prod.description }}</p>
        </div>
        <div class="catalog_item__status">
            <p :style="{ color: status.color }">{{ status.txt }}</p>
        </div>
        <div class="catalog_item_add_to_cart">
            <h5 v-if="!EMAIL" style="margin-bottom: 20px" class="catalog_item_add_to_cart__price">{{ prod.price }} $</h5>
            <h5 v-else style="margin-bottom: 20px" class="catalog_item_add_to_cart__price">{{ prod.salePrice }} $ <small class="text-muted" style="text-decoration: line-through">{{ prod.price }} $</small></h5>
            <button 
                :disabled="status.req"
                class="catalog_item_add_to_cart__btn btn btn-primary"
                @click="addToCart"
                >Add to cart</button>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'v-catalog-item',
    data() {
        return {
            status: {
                txt: "Нет в наличии",
                color: "red",
                req: true
            }
        }
    },
    props: {
        prod: {
            type: Object,
            default() {
                return {}
            }
        }
    },
    computed: {
        ...mapGetters([
            'EMAIL'
        ])
    },
    methods: {
        addToCart() {
            this.$emit('addToCart', this.prod);
            if (this.prod.count === 0) {
                this.status.req = true;
            }
        }
    },
    mounted() {
        if (this.prod.count > 0 && this.prod.count < 6) {
            this.status.color = '#ffaa69';
            this.status.txt = 'Мало';
            this.status.req = false
        }
        else if (this.prod.count > 5) {
            this.status.color = 'green';
            this.status.txt = 'В наличии';
            this.status.req = false
        }
    }
}
</script>

<style lang="scss" scoped>
.catalog_item{
    border-bottom: 1px solid lightgray;
    margin-bottom: 40px;
    display: grid;
    grid-template-columns: 1fr 4fr 1fr 1fr;
    height: 300px;
    align-items: center;
    &__img {
        margin-right: 30px;
        max-width: 220px;
        max-height: 300px;
    }
    &_add_to_cart {
        display: flex;
        flex-direction: column;
        justify-content: center;
        min-width: 110px;
        &__price{
            text-align: center;
        }
        margin: 10px;
    }
    &_about{
        min-width: 450px;
        margin: 10px;
    }
    &__status {
        margin: auto;
    }
}
</style>