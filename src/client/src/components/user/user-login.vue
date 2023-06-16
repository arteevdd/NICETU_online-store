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
import axios from 'axios';
// import { mapActions } from 'vuex';

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
    methods: {
        addUser() {
            axios({
                method: 'post',
                url: 'http://localhost:8080/online-store/v1/login',
                data: {
                    email: this.user.email, 
                    password: this.user.password
                },
                headers: {
                  "Content-type": "application/json; charset=UTF-8"
                }
              })
              .then(function(response) {
                console.log('Ответ сервера успешно получен!');
                console.log(response.data);
              })
              .catch(function(error) {
                console.log(error);
              });
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