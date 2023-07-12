<template>
  <div 
    v-if="ERROR"
    class="modal fade show" 
    id="exampleModalLive" 
    tabindex="-1" 
    aria-labelledby="exampleModalLiveLabel" 
    style="display: block; background: rgba(0, 0, 0, .5);" 
    aria-modal="true" 
    role="dialog"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLiveLabel">Ошибка 404</h5>
        </div>
        <div class="modal-body">
          <p>Что-то пошло не так. Попробуйте еще раз позже.</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="Reload">Перезагрузить</button>
        </div>
      </div>
    </div>
  </div>

  <div 
    id="app" 
    style="margin: 0 100px 0 100px"
  >
    <Header v-if="$route.path !== '/login' && $route.path !== '/regist'"/>
    <MainWrapper/>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import MainWrapper from './components/main-wrapper.vue';
import Header from './components/layout/header.vue';

export default {
  name: 'App',
  components: {
    MainWrapper,
    Header
  },
  computed: {
    ...mapGetters([
      'ERROR'
    ])
  },
  methods: {
    ...mapActions([
            'GET_PRODUCTS_FROM_API',
            'GET_CATEGORIES_FROM_API'
          ]),
    Reload() {
      window.location.reload()
    }
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
