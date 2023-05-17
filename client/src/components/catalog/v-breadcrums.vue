<template>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item" v-for="el in BRDCRMS" :key="el">{{ el }}</li>
        </ol>
    </nav>
    <div>
        <h3> {{  BRDCRMS[BRDCRMS.length - 1] }} </h3>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'v-breadcrums',
    data() {
        return {
            arr: ['Главная'],
            nameCat: 'Главная'
        }
    },
    computed: {
        ...mapGetters([
            'BRDCRMS'
        ])
    }
}
</script>