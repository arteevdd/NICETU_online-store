<template>
  <div id="app" style="margin: 0 100px 0 100px">
    <Header v-if="$route.path !== '/login' && $route.path !== '/regist'"/>
    <MainWrapper/>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import MainWrapper from './components/main-wrapper.vue'
import Header from './components/layout/header.vue';

export default {
  name: 'App',
  components: {
    MainWrapper,
    Header
  },
  methods: {
    ...mapActions([
            'GET_PRODUCTS_FROM_API',
            'GET_CATEGORIES_FROM_API'
          ])
  },
  mounted() {
    this.GET_PRODUCTS_FROM_API();
    this.GET_CATEGORIES_FROM_API();
  }
}
</script>

<style>
@import'~bootstrap/dist/css/bootstrap.css'
</style>
