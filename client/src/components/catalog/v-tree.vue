<template>
    <ul>
        <li type="disc" v-for="cat in tree[catId]" :key="cat">
            <RouterLink style="text-decoration: none" :to="{ name: 'type', params: { type: cat.categoryId}}">
                <h5 @click="this.$emit('setBrdcrm', cat.nameCategory)" class="dropdown-item">{{ cat.nameCategory }}</h5>
            </RouterLink>
            <vTree 
                :tree="tree"
                :catId="cat.categoryId"
                @setBrdcrm="addBrdcrm"/>
        </li>
    </ul>
</template>

<script>
export default {
    name: 'v-tree',
    props: {
        catId: Number,
        tree: Array
    },
    methods: {
        addBrdcrm(nameCat) {
            this.$emit('setBrdcrm', nameCat)
        }
    }
}
</script>