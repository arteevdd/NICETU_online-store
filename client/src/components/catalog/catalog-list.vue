<template>
    <div class="dropdown">
        <button 
            class="btn btn-secondary dropdown-toggle" 
            type="button" 
            id="dropdownMenuButton1" 
            data-bs-toggle="dropdown" 
            aria-expanded="false"
            @click="drawTree">
          Каталог
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <li v-for="category in tree[0]" :key="category">
                <RouterLink style="text-decoration: none" :to="{ name: 'type', params: { type: category.categoryId}}">
                    <h4 @click="setBrdcrm(category.nameCategory)" class="dropdown-item">{{ category.nameCategory }}</h4>
                </RouterLink>
                <Vtree 
                    :tree="tree"
                    :catId="category.categoryId"
                    @setBrdcrm="setBrdcrm"/> 
            </li>
        </ul>
    </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex';
import Vtree from './v-tree.vue';
import axios from 'axios';

export default {
    name: 'v-catalog-list',
    components: {Vtree},
    data() {
        return {
            tree: []
        }
    },
    computed: {
        ...mapGetters([
            'CATEGORIES',
            'BRDCRMS'
        ])
    },
    methods: {
        ...mapActions([
            'SET_BRDCRMS',
            'CLEAR_BRDCRMS',
            'SET_PRODUCTS_TO_STATE'
        ]),
        drawTree() {
            this.tree = this.formTree(this.CATEGORIES)
        },
        formTree(arr){
            let tree = []
            for (let i in arr) {
                if (arr[i].parentCategoryId === null)
                {
                    if(Array.isArray(tree[0])) {
                        tree[0].push((arr[i]))
                    }
                    else {
                        tree[0] = []
                        tree[0].push((arr[i]))
                    }
                }
                else {
                    if(Array.isArray(tree[arr[i].parentCategoryId])) {
                        tree[arr[i].parentCategoryId].push((arr[i]))
                    }
                    else {
                        tree[arr[i].parentCategoryId] = []
                        tree[arr[i].parentCategoryId].push((arr[i]))
                    }
                }
            }
            for (let i = 0; i < tree.length; i++) {
                if (tree[i] === undefined) {
                    tree[i] = 0
                }
            }
            return tree
        },
        setBrdcrm(catName) {
            this.CLEAR_BRDCRMS()
            let brdcrm = this.CATEGORIES.find(el => el.nameCategory === catName)
            const products = axios.get(`http://localhost:8080/online-shop/product_category/${brdcrm.categoryId}`)
            this.SET_PRODUCTS_TO_STATE(products.data)
            while (brdcrm.parentCategoryId) {
                this.SET_BRDCRMS(brdcrm.nameCategory)
                brdcrm = this.CATEGORIES.find(el => el.categoryId === brdcrm.parentCategoryId)
            }
            this.SET_BRDCRMS(brdcrm.nameCategory)
        }
    }
}
</script>

<style lang="scss" scoped>
.btn {
    color: black;
    background-color: white;
    &:hover {
        background-color: lightgray;
    }
}
</style>