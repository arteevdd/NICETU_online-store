<template>
<div class="user_login" >
    <form>
        <h4 class="user_login__i">Вход</h4>
        <div class="user_login__i form-group">
          <label for="exampleInputEmail1">Почта</label>
          <input  v-model="user.email" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
        </div>
        <div class="user_login__i form-group">
          <label for="exampleInputPassword1">Пароль</label>
          <input v-model="user.password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <button type="button" @click="addUser()" class="btn btn-primary" style="margin-bottom: 10px">Вход</button>
        <br>
        <RouterLink to="/regist">Регистрация</RouterLink>
    </form>
</div>
</template>

<script>
import axios from 'axios';
import { mapActions } from 'vuex';
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
        ...mapActions([
            'INSTALL_CART'
        ]),
        async addUser() {
            try {
                const user = await axios({
                    method: 'post',
                    url: 'http://localhost:8080/online-store/v1/login',
                    data: {
                        email: this.user.email, 
                        password: this.user.password
                    },
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                    });
                this.$router.push({name: 'home'})
                this.INSTALL_CART()
                localStorage.user = JSON.stringify(user.data)
            } catch (e) {
                if(e.response.status === 401) {
                    alert('Неверный пароль!')
                }
                if(e.response.status === 409) {
                    alert('Имя пользователя не зарегестрировано!')
                }
                console.log(e)
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