<template>
<header class="header" >
    <div class="header_catalog">
        <catalogList/>
    </div>
    <div class="header_right">
        <RouterLink v-if="Object.keys(USER).length === 0" to="/login">
            <div class="header_right_user">
                Войти
            </div>
        </RouterLink>
        <div v-else>
            {{ USER.firstName }}
            <button @click="exit" class="btn btn-link">Выйти</button>
        </div>
        <RouterLink to="/cart">
            <div class="header_right_cart">
                Корзина ({{ CART.reduce((a, b) => +a + +b.quantity, 0) }})
            </div>
        </RouterLink>
    </div>
</header>
</template>


<script>
import catalogList from '../catalog/catalog-list.vue';
import { mapActions, mapGetters } from 'vuex';
import router from '@/router';

export default {
    name: 'v-header',
    components: {
        catalogList
    },
    computed: {
        ...mapGetters([
            'CART',
            'USER'
        ])
    },
    methods: {
        ...mapActions ([
            'EXIT',
        ]),
        exit() {
            this.EXIT('');
            router.push({name: 'home'})
        }
    }
}
</script>

<style lang="scss" scoped>
.header {
    height: 130px;
    display: flex;
    justify-content: space-between;

    align-items: center;
    &_catalog {
        padding: 10px;
    }
    &_right {
        align-items: center;
        display: flex;
        &_user {
            padding: 10px;
        }
        &_cart {
            padding: 10px;
        }
    }
}
</style>