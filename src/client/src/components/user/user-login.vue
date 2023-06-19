<template>
<div class="user_login" >
    <form action="">
        <h4 class="user_login__i">Log in</h4>
        <div class="user_login__i form-group">
          <label for="exampleInputEmail1">Email address</label>
          <input  v-model="user.email" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
          <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
        </div>
        <div class="user_login__i form-group">
          <label for="exampleInputPassword1">Password</label>
          <input v-model="user.password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <button type="button" @click="addUser()" class="btn btn-primary" style="margin-bottom: 10px">Log In</button>
        <br>
        <RouterLink to="/regist">Registration</RouterLink>
    </form>
</div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
    name: 'v-user-login',
    data() {
        return {
            user: {
                email: '',
                password: ''
            }
        } 
    },
    computed: {
        ...mapGetters([
            'USER'
        ])
    },
    methods: {
        ...mapActions([
            'SET_USER'
        ]),
        async addUser() {
          let res = await this.SET_USER(this.user)
          if (res) {
            this.$router.push({name: 'home'})
          }
          else {
            console.log('Вы не авторизовались!!')
          }
        }
    }
}
</script>

<style lang="scss" scoped>
.user_login{
    margin: auto;
    margin-top: 270px;
    width: 500px;
    padding: 20px 40px 20px 40px;
    border: 1px solid lightgrey;
    &__i {
        margin-bottom: 20px;
    }
}
</style>