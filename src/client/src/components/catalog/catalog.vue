<template>
    <div class="catalog">
        <div class="catalog_top">
            <vBreadcrums/>
        </div>
        <div class="filter">
            <span class="filter_value" v-if="isDefault" @click="isDefault = !isDefault; this.PRODUCTS.reverse();">Сначала дороже</span>
            <span class="filter_value" v-else @click="isDefault = !isDefault; this.PRODUCTS.reverse();">Сначала дешевле</span>
        </div>
        <div class="catalog_items">
            <v-catalog-item
                v-for="(prod, index) in PRODUCTS" 
                :key="index "
                :prod="prod"
                @addToCart="addToCart"
            />
        </div>
    </div>
</template>

<script>
import vCatalogItem from './catalog-item.vue'
import vBreadcrums from './v-breadcrums.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
    name: 'v-catalog',
    data() {
        return {
            isDefault: true
        }
    },
    components: {
        vCatalogItem,
        vBreadcrums
    },
    computed: {
        ...mapGetters([
            'PRODUCTS',
            'BRDCRMS'
        ])
    },
    methods: {
        ...mapActions([
            'GET_PRODUCTS_FROM_API',
            'ADD_TO_CART'
        ]),
        addToCart(prod_cart) {
            this.ADD_TO_CART(prod_cart)
        }
    }
}
</script>

<style lang="scss" scoped>
.filter{
    display: flex;
    justify-content: flex-end;
    min-width: 800px;
    margin-right: 50px;
    margin-bottom: 10px;
    &_value{
        cursor: pointer;
        &:hover{
            color: gray;
        }
    }
}
.catalog_top {
    margin-bottom: 10px;
}
.catalog_items {
    border-top: 1px solid lightgray;
    margin: auto;
}
</style>