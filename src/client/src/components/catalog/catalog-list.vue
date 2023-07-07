<template>
    <div class="dropdown">
        <button 
            class="btn btn-secondary dropdown-toggle" 
            type="button" 
            id="dropdownMenuButton1" 
            data-bs-toggle="dropdown" 
            aria-expanded="false"
            @click="drawTree"
        >
        Каталог
        </button>
        <ul 
            class="dropdown-menu" 
            aria-labelledby="dropdownMenuButton1"
        >
            <li 
                v-for="category in tree[0]" 
                :key="category"
            >
                <RouterLink 
                    style="text-decoration: none" 
                    :to="{ name: 'type', params: { type: category.categoryId}}"
                >
                    <h4 
                        @click="setBrdcrm(category)" 
                        class="dropdown-item"
                    >
                    {{ category.nameCategory }}
                    </h4>
                </RouterLink>
                <Vtree 
                    :tree="tree"
                    :catId="category.categoryId"
                    @setBrdcrm="setBrdcrm"
                /> 
            </li>
        </ul>
    </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex';
import Vtree from './v-tree.vue';

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
            'GET_PRODUCTS_BY_CATEGORY'
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
        setBrdcrm(cat) {
            this.CLEAR_BRDCRMS()
            let brdcrm = this.CATEGORIES.find(el => el.categoryId === cat.categoryId)
            this.GET_PRODUCTS_BY_CATEGORY(brdcrm.categoryId)
            while (brdcrm.parentCategoryId) {
                this.SET_BRDCRMS(brdcrm.nameCategory)
                brdcrm = this.CATEGORIES.find(el => el.categoryId === brdcrm.parentCategoryId)
            }
            this.SET_BRDCRMS(brdcrm.nameCategory)
            this.SET_BRDCRMS('Главная')
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